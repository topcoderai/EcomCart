package com.shopping.service;

import com.shopping.model.ShoppingBasket;
import com.shopping.pricing.RuleEngine;
import com.shopping.pricing.rule.BuyXGetYFreeRule;
import com.shopping.pricing.rule.GroupDiscountRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class PricingService {
    private static final Logger logger = LoggerFactory.getLogger(PricingService.class);

    private final Map<String, Integer> priceList;
    private final RuleEngine ruleEngine;

    public PricingService() {
        // Initialize the price list
        priceList = new HashMap<>();
        priceList.put("Apple", 35);
        priceList.put("Banana", 20);
        priceList.put("Melon", 50);
        priceList.put("Lime", 15);

        // Initialize rule engine
        ruleEngine = new RuleEngine();

        // Add pricing rules
        // Buy one get one free for Melons
        ruleEngine.addRule(new BuyXGetYFreeRule("Melon", 1, 1, 10));

        // Three for the price of two for Limes
        ruleEngine.addRule(new GroupDiscountRule("Lime", 3, 2, 20));

        logger.info("PricingService initialized with pricing rules");
    }

    public int calculateTotalPrice(ShoppingBasket basket) {
        logger.info("Calculating price for basket with {} items", basket.getItems().size());

        // Group items by name and count occurrences
        Map<String, Long> itemCounts = basket.getItems().stream()
                .collect(Collectors.groupingBy(item -> item, Collectors.counting()));

        int totalBasketPrice = 0;

        // Calculate price for each group of items
        for (Map.Entry<String, Long> entry : itemCounts.entrySet()) {
            String itemName = entry.getKey();
            int quantity = entry.getValue().intValue();

            Integer unitPrice = priceList.get(itemName);
            if (unitPrice == null) {
                logger.warn("Unknown item: {}, skipping from price calculation", itemName);
                continue;
            }

            int itemTotalPrice = ruleEngine.calculatePrice(itemName, unitPrice, quantity);
            totalBasketPrice += itemTotalPrice;

            logger.debug("Added {}p for {} {} to basket total", itemTotalPrice, quantity, itemName);
        }

        logger.info("Total basket price: {}p", totalBasketPrice);
        return totalBasketPrice;
    }

    // For testing and extensibility
    public void addPricingRule(String itemName, int priceInPence) {
        priceList.put(itemName, priceInPence);
        logger.info("Added pricing for {} at {}p", itemName, priceInPence);
    }

    // Add a custom rule to the rule engine
    public void addRule(String itemName, int priceInPence, int groupSize, int priceForSize, int priority) {
        priceList.putIfAbsent(itemName, priceInPence);
        ruleEngine.addRule(new GroupDiscountRule(itemName, groupSize, priceForSize, priority));
        logger.info("Added group discount rule for {} ({}p): {} for the price of {}, priority {}",
                itemName, priceInPence, groupSize, priceForSize, priority);
    }

    public void addBuyXGetYFreeRule(String itemName, int priceInPence, int buyQuantity, int freeQuantity, int priority) {
        priceList.putIfAbsent(itemName, priceInPence);
        ruleEngine.addRule(new BuyXGetYFreeRule(itemName, buyQuantity, freeQuantity, priority));
        logger.info("Added buy {} get {} free rule for {} ({}p), priority {}",
                buyQuantity, freeQuantity, itemName, priceInPence, priority);
    }
}