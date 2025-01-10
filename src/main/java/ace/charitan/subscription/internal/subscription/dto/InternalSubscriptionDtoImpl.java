package ace.charitan.subscription.internal.subscription.dto;

import java.util.UUID;

import ace.charitan.subscription.internal.subscription.service.SubscriptionEnum.SubscriptionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InternalSubscriptionDtoImpl implements InternalSubscriptionDto {

    private UUID id;

    private SubscriptionType subscriptionType;

    private String lookupId;

    private boolean isActive;

    private String donorId;


}
