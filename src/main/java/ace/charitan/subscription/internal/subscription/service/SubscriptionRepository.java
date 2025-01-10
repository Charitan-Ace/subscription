package ace.charitan.subscription.internal.subscription.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ace.charitan.subscription.internal.subscription.service.SubscriptionEnum.SubscriptionType;

@Repository
interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, UUID> {
    // findBySubscriptionTypeAndLookupIdAndDonorIdAndIsActive
    Optional<SubscriptionEntity> findBySubscriptionTypeAndLookupIdAndDonorIdAndIsActive(
            SubscriptionType subscriptionType, String lookupId, String donorId, boolean isActive);

    List<SubscriptionEntity> findAllBySubscriptionTypeAndLookupIdAndIsActive(SubscriptionType subscriptionType,
            String lookupId, boolean isActive);
}
