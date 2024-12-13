/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.ProductManagement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.MarketModel.Channel;
import model.MarketModel.Market;

/**
 *
 * @author kal bugrara
 */
public class ProductsReport {

    ArrayList<ProductSummary> productSummaryList;

    ProductSummaryComparator comparator;

    public ProductsReport(String sortingRule) {
        productSummaryList = new ArrayList<ProductSummary>();
        comparator = new ProductSummaryComparator(sortingRule);
        /// We could make it "pre-populate" with the existing product data

    }

    public void addProductSummary(ProductSummary ps) {
        productSummaryList.add(ps);
        Collections.sort(productSummaryList, comparator);
    }

    public ProductSummary getTopProductAboveTarget() {
        ProductSummary currentTopProduct = null;

        for (ProductSummary ps : productSummaryList) {
            if (currentTopProduct == null) {
                currentTopProduct = ps; // initial step
            } else if (ps.getNumberAboveTarget() > currentTopProduct.getNumberAboveTarget()) {
                currentTopProduct = ps; // we have a new higher total
            }

        }
        return currentTopProduct;
    }

    public ArrayList<ProductSummary> getProductsAlwaysAboveTarget() {
        ArrayList<ProductSummary> productsAlwaysAboveTarget = new ArrayList<ProductSummary>(); // temp array list

        for (ProductSummary ps : productSummaryList) {
            if (ps.isProductAlwaysAboveTarget() == true) {
                productsAlwaysAboveTarget.add(ps);
            }
        }

        return productsAlwaysAboveTarget;
    }

    public void printMarketProfitabilityReport() {
        System.out.println("------------------------");
        System.out.println("Markets Performance Report");
        //System.out.println("Below are product name, actual sales and number of sales above target.");
        for (ProductSummary ps : productSummaryList) {
            if (ps.getSalesVolume() == 0.0) {
                continue;
            }
            ps.printProductSummaryByMarket();
        }
    }

    public void printChannelProfitabilityReport() {
        System.out.println("------------------------");
        System.out.println("Channels Profit Report");
        for (ProductSummary ps : productSummaryList) {
            if (ps.getSalesVolume() == 0.0) {
                continue;
            }
            ps.printProductSummaryByChannel();
        }
    }

    public List<Map<String, Integer>> getAdsByMarketList(Market market) {
        List<Map<String, Integer>> listAdsByMarket = new ArrayList<>();

        for (ProductSummary ps : productSummaryList) {
            if (ps.getSalesVolume() == 0.0) {
                continue;
            }

            int cost = ps.printAdsProductByMarket(market, ps.subjectProduct.getName());

            if (cost != 0) {
                Map<String, Integer> adMap = new HashMap<>();
                adMap.put(ps.subjectProduct.getName(), cost);

                listAdsByMarket.add(adMap);
            }
        }

        return listAdsByMarket;
    }

    public List<Map<String, Integer>> getAdsByChannelList(Channel channel) {
        List<Map<String, Integer>> listAdsByChannel = new ArrayList<>();

        for (ProductSummary ps : productSummaryList) {
            if (ps.getSalesVolume() == 0.0) {
                continue;
            }
            
            int cost = ps.printAdsProductByChannel(channel, ps.subjectProduct.getName());

            if (cost != 0) {
                Map<String, Integer> adMap = new HashMap<>();
                adMap.put(ps.subjectProduct.getName(), cost);

                listAdsByChannel.add(adMap);
            }
        }

        return listAdsByChannel;
    }
}
