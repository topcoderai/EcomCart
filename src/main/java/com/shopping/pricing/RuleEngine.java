package com.shopping.pricing;

import com.shopping.pricing.rule.PricingRule;
import com.shopping.pricing.rule.QuantityDiscountRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RuleEngine {
    private static final Logger logger = LoggerFactory.getLogger(RuleEngine.class);

    private final List<PricingRule> rules;

    // Default rule for items with no matching rules
    private final PricingRule defaultRule = new QuantityDiscountRule("DEFAULT", 1, 0.0, Integer.MAX_VALUE);

    public RuleEngine() {
        this.rules = new ArrayList<>();
        logger.info("Rule engine initialized");
    }

    public void addRule(PricingRule rule) {
        rules.add(rule);
        // Sort rules by priority
        rules.sort(Comparator.comparingInt(PricingRule::getPriority));
        logger.info("Added new pricing rule with priority {}", rule.getPriority());
    }

    public int calculatePrice(String itemName, int unitPrice, int quantity) {
        logger.debug("Calculating price for {} {} at {}p each", quantity, itemName, unitPrice);

        // Find the first applicable rule
        for (PricingRule rule : rules) {
            if (rule.isApplicable(itemName, quantity)) {
                logger.debug("Found applicable rule with priority {}", rule.getPriority());
                return rule.calculatePrice(itemName, unitPrice, quantity);
            }
        }

        // If no rule applies, use the default rule
        logger.debug("No applicable rules found, using default pricing");
        return defaultRule.calculatePrice(itemName, unitPrice, quantity);
    }
}
