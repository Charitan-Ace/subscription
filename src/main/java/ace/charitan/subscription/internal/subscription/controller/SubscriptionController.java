package ace.charitan.subscription.internal.subscription.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import ace.charitan.subscription.internal.subscription.dto.InternalSubscriptionDto;
import ace.charitan.subscription.internal.subscription.service.InternalSubscriptionService;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
class SubscriptionController {

    @Autowired
    private InternalSubscriptionService subscriptionService;

    @PostMapping("/subscribe/project/category/{category}")
    ResponseEntity<InternalSubscriptionDto> subscribeNewProjectByCategory(@PathVariable String category) {
        InternalSubscriptionDto subscriptionDto = subscriptionService.subscribeNewProjectByCategory(category);
        return new ResponseEntity<>(subscriptionDto, HttpStatus.OK);

    }

}
