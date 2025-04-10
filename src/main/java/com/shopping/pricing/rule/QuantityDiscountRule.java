package com.shopping.pricing.rule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Standard pricing rule with no discounts
 */
public class QuantityDiscountRule implements PricingRule {
    private static final Logger logger = LoggerFactory.getLogger(QuantityDiscountRule.class);

    private final String applicableItem;
    private final int minQuantity;
    private final double discountPercentage;
    private final int priority;

    public QuantityDiscountRule(String applicableItem, int minQuantity, double discountPercentage, int priority) {
        this.applicableItem = applicableItem;
        this.minQuantity = minQuantity;
        this.discountPercentage = discountPercentage;
        this.priority = priority;
    }

    @Override
    public boolean isApplicable(String itemName, int quantity) {
        return itemName.equals(applicableItem) && quantity >= minQuantity;
    }

    @Override
    public int calculatePrice(String itemName, int unitPrice, int quantity) {
        double discount = 1.0 - discountPercentage;
        int totalPrice = (int) (unitPrice * quantity * discount);

        logger.debug("Applied quantity discount rule for {}: {} items at {}p each with {}% discount = {}p",
                itemName, quantity, unitPrice, discountPercentage * 100, totalPrice);

        return totalPrice;
    }

    @Override
    public int getPriority() {
        return priority;
    }
}
