package ace.charitan.subscription.internal.subscription.service;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, UUID> {

}
