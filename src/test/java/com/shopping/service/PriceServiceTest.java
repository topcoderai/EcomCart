package com.shopping.service;

import com.shopping.model.ShoppingBasket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PricingServiceTest {

    private PricingService pricingService;

    @BeforeEach
    void setUp() {
        pricingService = new PricingService();
    }

    @Test
    void testEmptyBasket() {
        ShoppingBasket basket = new ShoppingBasket();
        assertEquals(0, pricingService.calculateTotalPrice(basket));
    }

    @Test
    void testSingleApple() {
        ShoppingBasket basket = new ShoppingBasket();
        basket.addItem("Apple");

        assertEquals(35, pricingService.calculateTotalPrice(basket));
    }

    @Test
    void testMultipleApples() {
        ShoppingBasket basket = new ShoppingBasket();
        basket.addItem("Apple");
        basket.addItem("Apple");
        basket.addItem("Apple");

        assertEquals(105, pricingService.calculateTotalPrice(basket));
    }

    @Test
    void testSingleBanana() {
        ShoppingBasket basket = new ShoppingBasket();
        basket.addItem("Banana");

        assertEquals(20, pricingService.calculateTotalPrice(basket));
    }

    @Test
    void testSingleMelon() {
        ShoppingBasket basket = new ShoppingBasket();
        basket.addItem("Melon");

        // Single melon should be charged at full price
        assertEquals(50, pricingService.calculateTotalPrice(basket));
    }

    @Test
    void testBuyOneGetOneFreeOffer() {
        ShoppingBasket basket = new ShoppingBasket();
        basket.addItem("Melon");
        basket.addItem("Melon");

        // Should be charged for only one Melon (50p)
        assertEquals(50, pricingService.calculateTotalPrice(basket));
    }

    @Test
    void testMultipleBuyOneGetOneFreeOffers() {
        ShoppingBasket basket = new ShoppingBasket();
        basket.addItem("Melon");
        basket.addItem("Melon");
        basket.addItem("Melon");
        basket.addItem("Melon");
        basket.addItem("Melon");

        // Should be charged for 3 melons (50p each)
        // 2 pairs (2 * 50p) + 1 single (50p) = 150p
        assertEquals(150, pricingService.calculateTotalPrice(basket));
    }

    @Test
    void testSingleLime() {
        ShoppingBasket basket = new ShoppingBasket();
        basket.addItem("Lime");

        // Single lime should be charged at full price
        assertEquals(15, pricingService.calculateTotalPrice(basket));
    }

    @Test
    void testTwoLimes() {
        ShoppingBasket basket = new ShoppingBasket();
        basket.addItem("Lime");
        basket.addItem("Lime");

        // Two limes should be charged at full price
        assertEquals(30, pricingService.calculateTotalPrice(basket));
    }

    @Test
    void testThreeForTwoOffer() {
        ShoppingBasket basket = new ShoppingBasket();
        basket.addItem("Lime");
        basket.addItem("Lime");
        basket.addItem("Lime");

        // Should be charged for only two Limes (15p each)
        assertEquals(30, pricingService.calculateTotalPrice(basket));
    }

    @Test
    void testMultipleThreeForTwoOffers() {
        ShoppingBasket basket = new ShoppingBasket();
        // Add 7 limes
        for (int i = 0; i < 7; i++) {
            basket.addItem("Lime");
        }

        // 2 groups of three (2 * 2 * 15p) + 1 single (15p) = 75p
        assertEquals(75, pricingService.calculateTotalPrice(basket));
    }

    @Test
    void testMixedBasket() {
        ShoppingBasket basket = new ShoppingBasket();
        basket.addItem("Apple");
        basket.addItem("Apple");
        basket.addItem("Banana");
        basket.addItem("Melon");
        basket.addItem("Melon");
        basket.addItem("Lime");
        basket.addItem("Lime");
        basket.addItem("Lime");

        // 2 Apples (2 * 35p) + 1 Banana (20p) + 2 Melons BOGOF (50p) + 3 Limes 3for2 (2 * 15p) = 170p
        assertEquals(170, pricingService.calculateTotalPrice(basket));
    }

    @Test
    void testDynamicallyAddedRule() {
        ShoppingBasket basket = new ShoppingBasket();
        basket.addItem("Grapes");
        basket.addItem("Grapes");
        basket.addItem("Grapes");
        basket.addItem("Grapes");

        // Add a pricing rule for grapes: 25p each, 4 for the price of 3
        pricingService.addRule("Grapes", 25, 4, 3, 30);

        // 4 grapes should be charged as 3 (3 * 25p = 75p)
        assertEquals(75, pricingService.calculateTotalPrice(basket));
    }

    @Test
    void testDynamicallyAddedBuyXGetYFreeRule() {
        ShoppingBasket basket = new ShoppingBasket();
        basket.addItem("Orange");
        basket.addItem("Orange");
        basket.addItem("Orange");
        basket.addItem("Orange");
        basket.addItem("Orange");

        // Add a pricing rule for oranges: 30p each, buy 2 get 1 free
        pricingService.addBuyXGetYFreeRule("Orange", 30, 2, 1, 25);

        // 5 oranges should be (2+1) + (2+0) = 4 paid items = 120p
        assertEquals(120, pricingService.calculateTotalPrice(basket));
    }
}