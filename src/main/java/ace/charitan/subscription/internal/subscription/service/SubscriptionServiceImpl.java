package ace.charitan.subscription.internal.subscription.service;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ace.charitan.subscription.internal.subscription.dto.InternalSubscriptionDto;
import ace.charitan.subscription.internal.subscription.service.SubscriptionEnum.CategoryType;
import ace.charitan.subscription.internal.subscription.service.SubscriptionEnum.SubscriptionType;

@Service
class SubscriptionServiceImpl implements InternalSubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    private CategoryType convertFromStringToCategoryType(String category) {
        try {
            CategoryType categoryType = CategoryType.fromValue(category);
            return categoryType;
        } catch (Exception e) {
            return null;
        }
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

}
