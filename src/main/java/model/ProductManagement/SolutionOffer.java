/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.ProductManagement;

import java.util.ArrayList;
import java.util.HashMap;

import model.MarketModel.Channel;
import model.MarketModel.Market;
import model.MarketModel.MarketChannelAssignment;
import model.OrderManagement.OrderItem;

/**
 * Bundle of products that are offered together at a certain price
 * 
 * @author kal bugrara
 */
public class SolutionOffer {
    String name;
    HashMap<Product, Float> productsSaleRate;
    Product bundledProduct;
    int targetPrice;// floor, ceiling, and target ideas
    MarketChannelAssignment marketChannelComb;
    ArrayList<OrderItem> orderItems;

    public SolutionOffer(MarketChannelAssignment m, int price, Product p) {
        targetPrice = price;
        marketChannelComb = m;
        m.addSolutionOffer(this);
        productsSaleRate = new HashMap<Product, Float>();
        productsSaleRate.put(p, 1f);
        p.addBundle(this);
        orderItems = new ArrayList<OrderItem>();
    }

    public void addProduct(Product p, float priceFract) {
        productsSaleRate.put(p, priceFract);
        p.addBundle(this);
        targetPrice += priceFract * p.getTargetPrice();
        normalize();
    }

    public void normalize() {
        float total = 0f;
        for (float share : productsSaleRate.values()) {
            total += share;
        }

        for (Product p : productsSaleRate.keySet()) {
            float currentShare = productsSaleRate.get(p);
            productsSaleRate.replace(p, currentShare / total);
        }
    }

    public Market getMarket() {
        return marketChannelComb.getMarket();
    }

    public Channel getChannel() {
        return marketChannelComb.getChannel();
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
        if (productsSaleRate.size() == 0) {
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
        Float result = productsSaleRate.get(p) == null ? 0 : productsSaleRate.get(p) * getSalesVolume();
        return result.intValue();
    }

    public float getAdsBudgetShare(Product p) {
        var productSaleRate = productsSaleRate.get(p);
        float result = productSaleRate == null
                ? 0
                : productSaleRate * marketChannelComb.getAdvertisingBudgetShare(this);
        return result;
    }

    public Market getProduct() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getProduct'");
    }
}
