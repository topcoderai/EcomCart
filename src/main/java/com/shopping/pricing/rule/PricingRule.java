package com.shopping.pricing.rule;

public interface PricingRule {
    boolean isApplicable(String itemName, int quantity);
    int calculatePrice(String itemName, int unitPrice, int quantity);
    int getPriority();
}