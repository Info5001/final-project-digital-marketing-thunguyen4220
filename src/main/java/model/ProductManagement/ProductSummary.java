/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.ProductManagement;

/**
 *
 * @author kal bugrara
 */

// this class will extract summary data from the product
public class ProductSummary {
    int rank; // will be done later
    Product subjectProduct;
    float salesVolume;
    float averagePrice;
    int salesQuantity;
    int numberofsalesabovetarget;
    int numberofsalesbelowtarget;
    int productpriceperformance; // total profit above target --could be negative

    public ProductSummary(Product p) {

        numberofsalesabovetarget = p.getNumberOfProductSalesAboveTarget();
        productpriceperformance = p.getOrderPricePerformance();
        numberofsalesbelowtarget = p.getNumberOfProductSalesBelowTarget();

        rank = 0;
        subjectProduct = p;
        salesVolume = p.getSalesVolume();
        averagePrice = p.getAveragePrice();
        salesQuantity = p.getTotalQuantity();
    }

    public boolean isProductAlwaysAboveTarget() {
        return false; // to be implemented
    }

    public int getNumberAboveTarget() {
        return numberofsalesabovetarget;
    }

    public int getNumberBelowTarget() {
        return numberofsalesbelowtarget;
    }

    public Product getSubjectProduct() {
        return subjectProduct;
    }

    public float getSalesVolume() {
        return salesVolume;
    }

    public float getAveragePrice() {
        return averagePrice;
    }

    public void printProductSummaryByMarket() {
        var salesQuantity = subjectProduct.getSalesVolumeListByMarket();
        var adCosts = subjectProduct.getAdBudgetListByMarket();

        System.out.println(String.format("Product %s Summary", subjectProduct.getName()));
        salesQuantity.forEach((k, v) -> {
            var adCost = adCosts.get(k);
            var profit = v - adCost;

            System.out.println(String.format("MARKET %s", k.getName()));
            System.out.println(String.format("- Revenues: %d", v));
            System.out.println(String.format("- Advertsing cost: %d", adCost));
            System.out.println(String.format("- Profit: %d", profit));
        });
        // System.out.println(subjectproduct.getName() + " | " + acutalsalesvolume + " |
        // " + numberofsalesabovetarget + " | " + productpriceperformance);
    }

    public void printProductSummaryByChannel() {
        var salesQuantity = subjectProduct.getSalesVolumeListByChannel();
        var adCosts = subjectProduct.getAdBudgetListByChannel();

        System.out.println(String.format("Product %s Summary", subjectProduct.getName()));
        salesQuantity.forEach((channel, ravenues) -> {
            var adCost = adCosts.get(channel);
            var profit = ravenues - adCost;

            System.out.println(String.format("CHANNEL %s", channel.getName()));
            System.out.println(String.format("- Revenues: %d", ravenues));
            System.out.println(String.format("- Advertsing cost: %d", adCost));
            System.out.println(String.format("- Profit: %d", profit));
        });
        // System.out.println(subjectproduct.getName() + " | " + acutalsalesvolume + " |
        // " + numberofsalesabovetarget + " | " + productpriceperformance);
    }

}
