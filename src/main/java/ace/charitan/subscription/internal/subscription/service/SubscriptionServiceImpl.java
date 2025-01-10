package ace.charitan.subscription.internal.subscription.service;

import java.util.Objects;

import org.springframework.stereotype.Service;

import ace.charitan.subscription.internal.subscription.dto.InternalSubscriptionDto;
import ace.charitan.subscription.internal.subscription.service.SubscriptionEnum.CategoryType;

@Service
class SubscriptionServiceImpl implements InternalSubscriptionService {

    private CategoryType convertFromStringToCategoryType(String category) {
        try {
            CategoryType categoryType = CategoryType.fromValue(category);
            return categoryType;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public InternalSubscriptionDto subscribeNewProjectByCategory(String category) {
        // TODO Auto-generated method stub

        // Validate category
        CategoryType categoryType = convertFromStringToCategoryType(category);

        if (Objects.isNull(categoryType)) {
            return null;
        }

        return null;

    }

}
