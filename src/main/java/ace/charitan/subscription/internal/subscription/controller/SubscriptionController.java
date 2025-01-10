package ace.charitan.subscription.internal.subscription.controller;

import java.util.Objects;

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
        if (Objects.isNull(subscriptionDto)) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(subscriptionDto, HttpStatus.OK);
    }

    @PostMapping("/subscribe/project/region/{region}")
    ResponseEntity<InternalSubscriptionDto> subscribeNewProjectByRegion(@PathVariable String region) {
        InternalSubscriptionDto subscriptionDto = subscriptionService.subscribeNewProjectByRegion(region);
        if (Objects.isNull(subscriptionDto)) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(subscriptionDto, HttpStatus.OK);
    }

}
