package ace.charitan.subscription.internal.subscription.service;

import ace.charitan.common.dto.subscription.NewProjectSubscriptionDto.NewProjectSubscriptionRequestDto;
import ace.charitan.subscription.internal.subscription.dto.InternalSubscriptionDto;

public interface InternalSubscriptionService {

    InternalSubscriptionDto subscribeNewProjectByCategory(String category);

    InternalSubscriptionDto subscribeNewProjectByRegion(String region);

    void notifySubcribersForNewProject(NewProjectSubscriptionRequestDto projectDto);

}
