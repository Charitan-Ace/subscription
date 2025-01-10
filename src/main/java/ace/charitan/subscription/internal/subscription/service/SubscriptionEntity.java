package ace.charitan.subscription.internal.subscription.service;

import ace.charitan.subscription.internal.common.AbstractEntity;
import ace.charitan.subscription.internal.subscription.service.SubscriptionEnum.SubscriptionType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "subscription")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class SubscriptionEntity extends AbstractEntity {

    @Enumerated(EnumType.STRING)
    private SubscriptionType subscriptionType;

    @Enumerated(EnumType.STRING)
    private String lookUpId;

    private boolean isActive;

    private String donorId;

}
