package ace.charitan.subscription.internal.subscription.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import ace.charitan.common.dto.subscription.NewProjectSubscriptionDto.NewProjectSubscriptionRequestDto;

@Component
class SubscriptionConsumerService {

    @Autowired
    private InternalSubscriptionService subscriptionService;

    @KafkaListener(topics = "project-subscription-new-project", groupId = "subscription-id")
    void handleSubscriptionNewProject(NewProjectSubscriptionRequestDto requestDto) {
        subscriptionService.notifySubcribersForNewProject(requestDto);
    }

}
