package com.shopping;

import com.shopping.model.ShoppingBasket;
import com.shopping.service.PricingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Shopping Basket Pricing Application Started");

        // Example usage
        ShoppingBasket basket = new ShoppingBasket();

        // Add some items
        List<String> shoppingList = Arrays.asList("Apple", "Apple", "Banana", "Melon", "Melon", "Lime", "Lime", "Lime");
        for (String item : shoppingList) {
            basket.addItem(item);
        }

        // Initialize pricing service
        PricingService pricingService = new PricingService();

        // Example of adding a custom rule dynamically
        // "Buy 2 Apples, get 1 free"
        pricingService.addBuyXGetYFreeRule("Apple", 35, 2, 1, 15);

        // Calculate price
        int totalPrice = pricingService.calculateTotalPrice(basket);

        logger.info("Sample shopping basket:");
        logger.info("Items: {}", shoppingList);
        logger.info("Total price: {}p", totalPrice);

        // Format price in rupee and paisa
        int rupee = totalPrice / 100;
        int paisa = totalPrice % 100;
        System.out.printf("Total price: Rs. %d.%02d%n", rupee, paisa);

        logger.info("Shopping Basket Pricing Application Completed");
    }
}