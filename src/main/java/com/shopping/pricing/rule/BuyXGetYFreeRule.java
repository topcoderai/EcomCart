package com.shopping.pricing.rule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Rule implementing promotions like "Buy one get one free"
 */
public class BuyXGetYFreeRule implements PricingRule {
    private static final Logger logger = LoggerFactory.getLogger(BuyXGetYFreeRule.class);

    private final String applicableItem;
    private final int buyQuantity;
    private final int freeQuantity;
    private final int minQuantity;
    private final int priority;

    public BuyXGetYFreeRule(String applicableItem, int buyQuantity, int freeQuantity, int priority) {
        this.applicableItem = applicableItem;
        this.buyQuantity = buyQuantity;
        this.freeQuantity = freeQuantity;
        this.minQuantity = buyQuantity + freeQuantity; // Minimum quantity to be eligible for the promotion
        this.priority = priority;
    }

    @Override
    public boolean isApplicable(String itemName, int quantity) {
        return itemName.equals(applicableItem) && quantity >= minQuantity;
    }

    @Override
    public int calculatePrice(String itemName, int unitPrice, int quantity) {

        int totalGroups = quantity / (buyQuantity + freeQuantity);

        int remainingItems = quantity % (buyQuantity + freeQuantity);

        // Calculate total price: charged groups + remaining items (up to buyQuantity)
        int chargeableItems = (totalGroups * buyQuantity) + Math.min(remainingItems, buyQuantity);
        int totalPrice = chargeableItems * unitPrice;

        logger.debug("Buy {} get {} free for {}: {} items ({}p each) = {}p",
                buyQuantity, freeQuantity, itemName, quantity, unitPrice, totalPrice);
        logger.debug("Promotion applied: {} full groups, {} remaining items, {} chargeable items",
                totalGroups, remainingItems, chargeableItems);

        return totalPrice;
    }

    @Override
    public int getPriority() {
        return priority;
    }
}

