/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.ProductManagement;

import java.util.ArrayList;
import java.util.HashMap;

import model.MarketModel.Market;
import model.MarketModel.Channel;
import model.OrderManagement.OrderItem;

/**
 *
 * @author kal bugrara
 */
public class Product {
    private String name;
    private int floorPrice;
    private int ceilingPrice;
    private int targetPrice;
    ArrayList<SolutionOffer> bundles;

    public Product(int fp, int cp, int tp) {
        floorPrice = fp;
        ceilingPrice = cp;
        targetPrice = tp;
        bundles = new ArrayList<SolutionOffer>();
    }

    public Product(String n, int fp, int cp, int tp) {
        name = n;
        floorPrice = fp;
        ceilingPrice = cp;
        targetPrice = tp;
        bundles = new ArrayList<SolutionOffer>();
    }

    public Product updateProduct(int fp, int cp, int tp) {
        floorPrice = fp;
        ceilingPrice = cp;
        targetPrice = tp;
        return this; // returns itself
    }

    public int getTargetPrice() {
        return targetPrice;
    }

    public void addBundle(SolutionOffer so) {
        bundles.add(so);
    }

    public int getSalesQuanity(Market m) {
        int total = 0;
        for (SolutionOffer so : bundles) {
            if (so.getMarket() == m) {
                total += so.getSalesQuanity();
            }
        }
        return total;
    }

    public float getSalesVolumeForMarket(Market m) {
        int total = 0;
        for (SolutionOffer so : bundles) {
            if (so.getMarket() == m) {
                total += so.getSalesShare(this);
            }
        }
        return total;
    }

    public HashMap<Market, Integer> getAdBudgetListByMarket() {
        HashMap<Market, Integer> resultsbyMarket = new HashMap<Market, Integer>();
        for (SolutionOffer so : bundles) {
            Market m = so.getMarket();
            if (resultsbyMarket.get(m) != null) {
                resultsbyMarket.replace(m, resultsbyMarket.get(m) + (int) so.getAdsBudgetShare(this));
            } else {
                resultsbyMarket.put(m, (int) so.getAdsBudgetShare(this));
            }
        }

        return resultsbyMarket;
    }

    public HashMap<Channel, Integer> getAdBudgetListByChannel() {
        HashMap<Channel, Integer> resultsbyChannel = new HashMap<Channel, Integer>();
        for (SolutionOffer so : bundles) {
            Channel m = so.getChannel();
            if (resultsbyChannel.get(m) != null) {
                resultsbyChannel.replace(m, resultsbyChannel.get(m) + (int) so.getAdsBudgetShare(this));
            } else {
                resultsbyChannel.put(m, (int) so.getAdsBudgetShare(this));
            }
        }

        return resultsbyChannel;
    }

    public float getSalesVolume() {
        int total = 0;
        for (SolutionOffer so : bundles) {
            total += so.getSalesShare(this);
        }
        return total;
    }

    public HashMap<Market, Integer> getSalesQuanityList() {
        HashMap<Market, Integer> resultsbyMarket = new HashMap<Market, Integer>();
        for (SolutionOffer so : bundles) {
            Market m = so.getMarket();
            if (resultsbyMarket.get(m) != null) {
                resultsbyMarket.replace(m, resultsbyMarket.get(m) + so.getSalesQuanity());
            } else {
                resultsbyMarket.put(m, so.getSalesQuanity());
            }
        }

        return resultsbyMarket;
    }

    /**
     * Returns the sales ravenues for each market
     */
    public HashMap<Market, Integer> getSalesVolumeListByMarket() {
        HashMap<Market, Integer> resultsbyMarket = new HashMap<Market, Integer>();
        for (SolutionOffer so : bundles) {
            Market m = so.getMarket();
            if (resultsbyMarket.get(m) != null) {
                resultsbyMarket.replace(m, resultsbyMarket.get(m) + so.getSalesShare(this));
            } else {
                resultsbyMarket.put(m, so.getSalesShare(this));
            }
        }

        return resultsbyMarket;
    }

    /**
     * Returns the sales ravenues for each channel
     */
    public HashMap<Channel, Integer> getSalesVolumeListByChannel() {
        HashMap<Channel, Integer> resultsbyChannel = new HashMap<Channel, Integer>();
        for (SolutionOffer so : bundles) {
            if (so.getSalesShare(this) == 0) {
                continue;
            }
            Channel m = so.getChannel();
            if (resultsbyChannel.get(m) != null) {
                resultsbyChannel.replace(m, resultsbyChannel.get(m) + so.getSalesShare(this));
            } else {
                resultsbyChannel.put(m, so.getSalesShare(this));
            }
        }

        return resultsbyChannel;
    }

    // Number of item sales above target
    public int getNumberOfProductSalesAboveTarget() {
        int sum = 0;
        /*
         * for (OrderItem oi : orderItems) {
         * if (oi.isActualAboveTarget() == true)
         * sum = sum + 1;
         * }
         */
        return sum;
    }

    public int getNumberOfProductSalesBelowTarget() {
        int sum = 0;
        /*
         * for (OrderItem oi : orderItems) {
         * if (oi.isActualBelowTarget() == true)
         * sum = sum + 1;
         * }
         */
        return sum;
    }

    public boolean isProductAlwaysAboveTarget() {
        /*
         * for (OrderItem oi : orderItems) {
         * if (oi.isActualAboveTarget() == false)
         * return false; //
         * }
         */
        return true;
    }
    // calculates the revenues gained or lost (in relation to the target)
    // For example, if target is at $2000 and actual is $2500 then revenue gained
    // is $500 above the expected target. If the actual is $1800 then the lose will
    // be $200
    // Add all these difference to get the total including wins and loses

    public int getOrderPricePerformance() {
        int sum = 0;
        /*
         * for (OrderItem oi : orderItems) {
         * sum = sum + oi.calculatePricePerformance(); // positive and negative values
         * }
         */
        return sum;
    }

    public int getTotalQuantity() {
        int totalQuantity = 0;

        // for (OrderItem oi : orderItems) {
        //     totalQuantity = totalQuantity + oi.getQuantity();
        // }

        return totalQuantity;
    }

    public float getAveragePrice() {
        if (getTotalQuantity() == 0)
            return 0;
        return (float) getSalesVolume() / getTotalQuantity();
    }

    public void setName(String n) {
        name = n;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public int getFloorPrice() {
        return floorPrice;
    }

    public int getCeilingPrice() {
        return ceilingPrice;
    }
}
