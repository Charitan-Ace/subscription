package ace.charitan.subscription.internal.subscription.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
enum SubscriptionProducerTopic {

    SUBSCRIPTION_GEOGRAPHY_GET_COUNTRY_BY_ISO_CODE("subscription-geography-get-country-by-iso-code");

    private String topic;

}
