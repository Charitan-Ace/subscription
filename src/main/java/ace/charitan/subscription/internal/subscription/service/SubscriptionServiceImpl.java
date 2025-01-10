package ace.charitan.subscription.internal.subscription.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ace.charitan.common.dto.email.subscription.EmailNewProjectSubscription.EmailNewProjectSubscriptionRequestDto;
import ace.charitan.common.dto.geography.GetCountryByIsoCode.GetCountryByIsoCodeRequestDto;
import ace.charitan.common.dto.geography.GetCountryByIsoCode.GetCountryByIsoCodeResponseDto;
import ace.charitan.common.dto.notification.subscription.NotificationNewProjectSubscription.NotificationNewProjectSubscriptionRequestDto;
import ace.charitan.common.dto.project.ExternalProjectDto;
import ace.charitan.common.dto.subscription.NewProjectSubscriptionDto.NewProjectSubscriptionRequestDto;
import ace.charitan.subscription.internal.subscription.dto.InternalSubscriptionDto;
import ace.charitan.subscription.internal.subscription.service.SubscriptionEnum.CategoryType;
import ace.charitan.subscription.internal.subscription.service.SubscriptionEnum.RegionType;
import ace.charitan.subscription.internal.subscription.service.SubscriptionEnum.SubscriptionType;

@Service
class SubscriptionServiceImpl implements InternalSubscriptionService {

        @Autowired
        private SubscriptionRepository subscriptionRepository;

        @Autowired
        private SubscriptionProducerService subscriptionProducerService;

        private CategoryType convertFromStringToCategoryType(String category) {
                try {
                        CategoryType categoryType = CategoryType.fromValue(category);
                        return categoryType;
                } catch (Exception e) {
                        return null;
                }
        }

        private RegionType convertFromStringToRegionType(String region) {
                try {
                        RegionType regionType = RegionType.fromValue(region);
                        return regionType;
                } catch (Exception e) {
                        return null;
                }
        }

        private List<String> getSubscriberListByCategory(CategoryType categoryType) {
                return subscriptionRepository
                                .findAllBySubscriptionTypeAndLookupIdAndIsActive(
                                                SubscriptionType.CATEGORY, categoryType.getValue(), true)
                                .stream().map(
                                                s -> s.getDonorId())
                                .collect(Collectors.toList());
        }

        private List<String> getSubscriberListByRegion(RegionType regionType) {
                return subscriptionRepository
                                .findAllBySubscriptionTypeAndLookupIdAndIsActive(
                                                SubscriptionType.REGION, regionType.getValue(), true)
                                .stream().map(
                                                s -> s.getDonorId())
                                .collect(Collectors.toList());
        }

        @Override
        public InternalSubscriptionDto subscribeNewProjectByCategory(String category) {
                // TODO Auto-generated method stub

                // Validate category
                CategoryType categoryType = convertFromStringToCategoryType(category);

                if (Objects.isNull(categoryType)) {
                        // TODO throw error invalid category
                        return null;
                }

                // TODO Modify donorId
                String donorId = UUID.randomUUID().toString();

                // Check existed subscription
                Optional<SubscriptionEntity> existedOptional = subscriptionRepository
                                .findBySubscriptionTypeAndLookupIdAndDonorIdAndIsActive(SubscriptionType.CATEGORY,
                                                category, donorId, true);

                if (existedOptional.isPresent()) {
                        // TODO throw error existed
                        return null;
                }

                // Save entity to db
                SubscriptionEntity subscriptionEntity = new SubscriptionEntity(SubscriptionType.CATEGORY, category,
                                true,
                                donorId);
                subscriptionEntity = subscriptionRepository.save(subscriptionEntity);

                // Return to dto impl and convert back
                return subscriptionEntity.toInternalSubscriptionDtoImpl();
        }

        @Override
        public InternalSubscriptionDto subscribeNewProjectByRegion(String region) {

                // Validate category
                RegionType regionType = convertFromStringToRegionType(region);

                if (Objects.isNull(regionType)) {
                        // TODO throw error invalid category
                        return null;
                }

                // TODO Modify donorId
                String donorId = UUID.randomUUID().toString();

                // Check existed subscription
                Optional<SubscriptionEntity> existedOptional = subscriptionRepository
                                .findBySubscriptionTypeAndLookupIdAndDonorIdAndIsActive(SubscriptionType.REGION,
                                                region, donorId, true);

                if (existedOptional.isPresent()) {
                        // TODO throw error existed
                        return null;
                }

                // Save entity to db
                SubscriptionEntity subscriptionEntity = new SubscriptionEntity(SubscriptionType.REGION, region,
                                true,
                                donorId);
                subscriptionEntity = subscriptionRepository.save(subscriptionEntity);

                // Return to dto impl and convert back
                return subscriptionEntity.toInternalSubscriptionDtoImpl();
        }

        @Override
        public void notifySubcribersForNewProject(NewProjectSubscriptionRequestDto requestDto) {

                ExternalProjectDto projectDto = requestDto.getProject();

                // Get subscriber list by category
                List<String> categoryDonorIdList = getSubscriberListByCategory(
                                CategoryType.fromValue(projectDto.getCategoryType()));

                // Get subscriber list by regions

                // Get country by region
                GetCountryByIsoCodeResponseDto getCountryByIsoCodeResponseDto = subscriptionProducerService
                                .sendAndReceive(new GetCountryByIsoCodeRequestDto(projectDto.getCountryIsoCode()));

                List<String> regionDonorIdList = getSubscriberListByRegion(
                                RegionType.fromValue(getCountryByIsoCodeResponseDto.getRegionName()));

                // Merge two list to a set by id
                Set<String> donorIdSet = Stream.concat(categoryDonorIdList.stream(), regionDonorIdList.stream())
                                .collect(Collectors.toSet());

                List<String> donorIdList = donorIdSet.stream().collect(Collectors.toList());

                // Send notification to subscriber via Kafka
                subscriptionProducerService.send(new NotificationNewProjectSubscriptionRequestDto(
                                donorIdList, projectDto));

                // Send email to subscriber via Kafka
                subscriptionProducerService.send(new EmailNewProjectSubscriptionRequestDto(donorIdList, projectDto));

        }

}
