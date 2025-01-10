package ace.charitan.subscription.internal.subscription.service;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AllArgsConstructor;

public class SubscriptionEnum {

    @AllArgsConstructor
    public static enum CategoryType {
        FOOD("FOOD"),
        HEALTH("HEALTH"),
        EDUCATION("EDUCATION"),
        ENVIRONMENT("ENVIRONMENT"),
        RELIGION("RELIGION"),
        HUMANITARIAN("HUMANITARIAN"),
        HOUSING("HOUSING"),
        OTHER("OTHER");

        private String value;

        @JsonValue
        public String getValue() {
            return value;
        }

        @JsonCreator
        public static CategoryType fromValue(String value) {
            for (CategoryType category : values()) {
                String currentCategory = category.getValue();
                if (currentCategory.equals(value)) {
                    return category;
                }
            }

            // Return a response entity with a 400 Bad Request category
            throw new IllegalArgumentException("Invalid value for CategoryType Enum: " + value);
        }

    }

    @AllArgsConstructor
    public static enum SubscriptionType {
        CATEGORY("CATEGORY"),
        REGION("REGION");

        private String value;

        @JsonValue
        public String getValue() {
            return value;
        }

        @JsonCreator
        public static SubscriptionType fromValue(String value) {
            for (SubscriptionType subscription : values()) {
                String currentSubscription = subscription.getValue();
                if (currentSubscription.equals(value)) {
                    return subscription;
                }
            }

            // Return a response entity with a 400 Bad Request subscription
            throw new IllegalArgumentException("Invalid value for SubscriptionType Enum: " + value);
        }

    }

    @AllArgsConstructor
    public static enum RegionType {
        AFRICA("AFRICA"),
        ASIA("ASIA"),
        EUROPE("EUROPE"),
        NORTH_AMERIA("NORTH_AMERIA"),
        SOUTH_AMERICA("SOUTH_AMERICA"),
        OCEANIA("OCEANIA"),
        ANTARCTICA("ANTARCTICA");

        private String value;

        @JsonValue
        public String getValue() {
            return value;
        }

        @JsonCreator
        public static RegionType fromValue(String value) {
            for (RegionType subscription : values()) {
                String currentSubscription = subscription.getValue();
                if (currentSubscription.equals(value)) {
                    return subscription;
                }
            }

            // Return a response entity with a 400 Bad Request subscription
            throw new IllegalArgumentException("Invalid value for SubscriptionType Enum: " + value);
        }

    }

}
