/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.Business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.CustomerManagement.CustomerDirectory;
import model.MarketModel.ChannelCatalog;
import model.MarketModel.MarketCatalog;
import model.MarketingManagement.MarketingPersonDirectory;
import model.OrderManagement.MasterOrderList;
import model.Personnel.EmployeeDirectory;
import model.Personnel.PersonDirectory;
import model.ProductManagement.ProductSummary;
import model.ProductManagement.ProductsReport;
import model.ProductManagement.SolutionOffer;
import model.ProductManagement.SolutionOfferCatalog;
import model.SalesManagement.SalesPersonDirectory;
import model.Supplier.Supplier;
import model.Supplier.SupplierDirectory;
import model.UserAccountManagement.UserAccountDirectory;
import model.MarketModel.MarketChannelAssignment;
import model.MarketModel.Channel;
import model.MarketModel.Market;

/**
 *
 * @author kal bugrara
 */
public class Business {
    String name;
    PersonDirectory personDirectory;
    MasterOrderList masterOrderList;
    SupplierDirectory suppliers;
    MarketCatalog marketCatalog;
    ChannelCatalog channelCatalog;
    SolutionOfferCatalog solutionOfferCatalog;
    CustomerDirectory customerDirectory;
    EmployeeDirectory employeeDirectory;
    SalesPersonDirectory salesPersonDirectory;
    UserAccountDirectory userAccountDirectory;
    MarketingPersonDirectory marketingPersonDirectory;
    ArrayList<MarketChannelAssignment> marketChannelAssignments;

    public Business(String n) {
        name = n;
        masterOrderList = new MasterOrderList();
        suppliers = new SupplierDirectory(this);
        solutionOfferCatalog = new SolutionOfferCatalog(this);
        personDirectory = new PersonDirectory();
        customerDirectory = new CustomerDirectory(this);
        salesPersonDirectory = new SalesPersonDirectory(this);
        userAccountDirectory = new UserAccountDirectory();
        marketingPersonDirectory = new MarketingPersonDirectory(this);
        employeeDirectory = new EmployeeDirectory(this);
        marketCatalog = new MarketCatalog(this);
        channelCatalog = new ChannelCatalog();
        marketChannelAssignments = new ArrayList<MarketChannelAssignment>();
    }

    public int getSalesVolume() {
        return masterOrderList.getSalesVolume();

    }

    public PersonDirectory getPersonDirectory() {
        return personDirectory;
    }

    public UserAccountDirectory getUserAccountDirectory() {
        return userAccountDirectory;
    }

    public MarketingPersonDirectory getMarketingPersonDirectory() {
        return marketingPersonDirectory;
    }

    public SupplierDirectory getSupplierDirectory() {
        return suppliers;
    }

    public ProductsReport getSupplierPerformanceReport(String n) {
        Supplier supplier = suppliers.findSupplier(n);
        if (supplier == null) {
            return null;
        }
        return supplier.prepareProductsReport();

    }

    public ArrayList<ProductSummary> getSupplierProductsAlwaysAboveTarget(String n) {
        ProductsReport productsreport = getSupplierPerformanceReport(n);
        return productsreport.getProductsAlwaysAboveTarget();

    }

    public int getHowManySupplierProductsAlwaysAboveTarget(String n) {
        ProductsReport productsreport = getSupplierPerformanceReport(n); // see above
        int i = productsreport.getProductsAlwaysAboveTarget().size(); // return size of the arraylist
        return i;
    }

    public CustomerDirectory getCustomerDirectory() {
        return customerDirectory;
    }

    public SalesPersonDirectory getSalesPersonDirectory() {
        return salesPersonDirectory;
    }

    public MasterOrderList getMasterOrderList() {
        return masterOrderList;
    }

    public EmployeeDirectory getEmployeeDirectory() {
        return employeeDirectory;
    }

    public MarketCatalog getMarketCatalog() {
        return marketCatalog;
    }

    public ChannelCatalog getChannelCatalog() {
        return channelCatalog;
    }

    public SolutionOfferCatalog getSolutionOfferCatalog() {
        return solutionOfferCatalog;
    }

    public void addMarketChannelAssignment(MarketChannelAssignment mca) {
        marketChannelAssignments.add(mca);
    }

    public ArrayList<MarketChannelAssignment> getMarketChannelAssignments() {
        return marketChannelAssignments;
    }

    public void printShortInfo() {
        System.out.println("Checking what's inside the business hierarchy.");
        suppliers.printShortInfo();
        customerDirectory.printShortInfo();
        masterOrderList.printShortInfo();
    }

    /* public void advertisingEfficiency() {
        this.getMarketCatalog().getMarketList().forEach(market -> {
            System.out.println("Market: " + market.getName());
            this.channelCatalog.getChannelList().forEach(channel -> {
                System.out.println("Channel: " + channel.getName());
                Integer total = 0;
                for (MarketChannelAssignment mca : this.marketChannelAssignments) {
                    if (mca.getMarket().equals(market) && mca.getChannel().equals(channel)) {
                        total += (int) mca.getTotalAdvertisingBudgetShare();
                    }
                }

                System.out.println("Ads cost: " + total);
            });
        });
    } */ 

 public void advertisingEfficiency() {
    List<Channel> channels = this.channelCatalog.getChannelList();
    System.out.print(String.format("%-20s", "Market"));
    channels.forEach(channel -> System.out.print(String.format("%-20s", channel.getName())));
    System.out.println(); 

    this.getMarketCatalog().getMarketList().forEach(market -> {
        System.out.print(String.format("%-20s", market.getName()));

        channels.forEach(channel -> {
            int total = 0;
            for (MarketChannelAssignment mca : this.marketChannelAssignments) {
                if (mca.getMarket().equals(market) && mca.getChannel().equals(channel)) {
                    total += (int) mca.getTotalAdvertisingBudgetShare();
                }
            }
            System.out.print(String.format("%-20d", total));
        });

        System.out.println(); 
    });
}

}
