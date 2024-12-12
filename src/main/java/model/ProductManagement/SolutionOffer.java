/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.ProductManagement;

import java.util.ArrayList;
import java.util.HashMap;

import model.MarketModel.Market;
import model.MarketModel.MarketChannelAssignment;
import model.OrderManagement.OrderItem;

/**
 * Bundle of products that are offered together at a certain price
 * @author kal bugrara
 */
public class SolutionOffer {
    String name;
    HashMap<Product, Float> products;
    Product bundledProduct;
    int targetPrice;// floor, ceiling, and target ideas
    MarketChannelAssignment marketChannelComb;
    ArrayList<OrderItem> orderItems;


    public SolutionOffer(MarketChannelAssignment m, int price, Product p) {
        targetPrice = price;
        marketChannelComb = m;
        m.addSolutionOffer(this);
        products = new HashMap<Product, Float>();
        products.put(p, 1f);
        p.addBundle(this);
        orderItems = new ArrayList<OrderItem>();
    }

    public void addProduct(Product p, float priceFract) {
        products.put(p, priceFract);
        p.addBundle(this);
        // TODO update target price
        targetPrice += priceFract * p.getTargetPrice();
        normalize();
    }

    public void normalize() {
        float total = 0f;
        for (float share : products.values()) {
            total += share;
        }

        for (Product p : products.keySet()) {
            float currentShare = products.get(p);
            products.replace(p, currentShare / total);
        }
    }

    public Market getMarket() {
        return marketChannelComb.getMarket();
    }

    public int getSalesQuanity() {
        int total = 0;
        for (OrderItem oi : orderItems) {
            total += oi.getQuantity();
        }

        return total;
    }

    public void setPrice(int p) {
        targetPrice = p;
    }

    public void addOrderItem(OrderItem oi) {
        orderItems.add(oi);
    }

    public int getTargetPrice() {
        return targetPrice;
    }

    public String getBundleName() {
        if (products.size() == 0) {
            return "No products in the bundle";
        }

        return name;
    }

    public int getSalesVolume() {
        int total = 0;
        for (OrderItem item : orderItems) {
            total += item.getOrderItemTotal();
        }

        return total;
    }

    public int getSalesShare(Product p) {
        Float result = products.get(p) == null ? 0 : products.get(p) * getSalesVolume();
        return result.intValue();
    }

    public int getAdsBudgetShare(Product p) {
        Float result = products.get(p) == null ? 0
                : products.get(p) * marketChannelComb.getAdvertisingBudgetShare(this);
        return result.intValue();
    }
}
