package com.shopping.pricing.rule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Rule implementing promotions like "3 for the price of 2" or "4 for the price of 3"
 */
public class GroupDiscountRule implements PricingRule {
    private static final Logger logger = LoggerFactory.getLogger(GroupDiscountRule.class);

    private final String applicableItem;
    private final int groupSize;
    private final int priceForSize;
    private final int minQuantity;
    private final int priority;

    public GroupDiscountRule(String applicableItem, int groupSize, int priceForSize, int priority) {
        this.applicableItem = applicableItem;
        this.groupSize = groupSize;
        this.priceForSize = priceForSize;
        this.minQuantity = groupSize;
        this.priority = priority;
    }

    @Override
    public boolean isApplicable(String itemName, int quantity) {
        return itemName.equals(applicableItem) && quantity >= minQuantity;
    }

    @Override
    public int calculatePrice(String itemName, int unitPrice, int quantity) {

        int completeGroups = quantity / groupSize;

        int remainingItems = quantity % groupSize;

        // Calculate total price: complete groups at discounted price + remaining items at normal price
        int totalPrice = (completeGroups * priceForSize * unitPrice) + (remainingItems * unitPrice);

        logger.debug("Group discount for {}: {} items in groups of {} for the price of {} = {}p",
                itemName, quantity, groupSize, priceForSize, totalPrice);
        logger.debug("Promotion applied: {} complete groups, {} remaining items",
                completeGroups, remainingItems);

        return totalPrice;
    }

    @Override
    public int getPriority() {
        return priority;
    }
}
