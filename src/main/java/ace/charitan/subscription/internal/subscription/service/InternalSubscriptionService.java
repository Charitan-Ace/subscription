package ace.charitan.subscription.internal.subscription.service;

import ace.charitan.common.dto.project.ExternalProjectDto;
import ace.charitan.subscription.internal.subscription.dto.InternalSubscriptionDto;

public interface InternalSubscriptionService {

    InternalSubscriptionDto subscribeNewProjectByCategory(String category);

    void notifySubcribersForNewProject(ExternalProjectDto projectDto);

}
