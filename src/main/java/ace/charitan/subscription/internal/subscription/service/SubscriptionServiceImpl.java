package ace.charitan.subscription.internal.subscription.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ace.charitan.common.dto.geography.GetCountryByIsoCode.GetCountryByIsoCodeRequestDto;
import ace.charitan.common.dto.geography.GetCountryByIsoCode.GetCountryByIsoCodeResponseDto;
import ace.charitan.common.dto.project.ExternalProjectDto;
import ace.charitan.common.dto.subscription.NewProjectSubscriptionDto.NewProjectSubscriptionRequestDto;
import ace.charitan.subscription.internal.subscription.dto.InternalSubscriptionDto;
import ace.charitan.subscription.internal.subscription.service.SubscriptionEnum.CategoryType;
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

    private List<String> getSubscriberListByCategory(CategoryType categoryType) {
        return subscriptionRepository
                .findAllBySubscriptionTypeAndLookupIdAndIsActive(
                        SubscriptionType.CATEGORY, categoryType.getValue(), true)
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
        SubscriptionEntity subscriptionEntity = new SubscriptionEntity(SubscriptionType.CATEGORY, category, true,
                donorId);
        subscriptionEntity = subscriptionRepository.save(subscriptionEntity);

        // Return to dto impl and convert back
        return subscriptionEntity.toInternalSubscriptionDtoImpl();
    }

    @Override
    public InternalSubscriptionDto subscribeNewProjectByRegion(String region) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'subscribeNewProjectByRegion'");
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
        System.out.println("dto" + getCountryByIsoCodeResponseDto);
        System.out.println("region" + getCountryByIsoCodeResponseDto.getRegionName());

        // List<String> regionDonorIdList = getSubscriberListByRegion();

        // Merge two list to a set by id

        // Get email or etc.

        // TODO Send email and notification to subscriber via Kafka
    }

}
