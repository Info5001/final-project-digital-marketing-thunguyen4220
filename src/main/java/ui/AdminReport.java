package ui;
import java.util.ArrayList;
import java.util.Scanner;

import model.Business.Business;
import model.Business.ConfigureABusiness;
import model.ProductManagement.ProductSummary;
import model.ProductManagement.ProductsReport;

public class AdminReport {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Business business = ConfigureABusiness.createABusinessAndLoadALotOfData("Amazon", 10, 12, 1000, 1500, 100); 

        while (true) {
            System.out.println("\nAdmin Report Viewer");
            System.out.println("Please choose a report to view:");
            System.out.println("1. Market Profitability Report");
            System.out.println("2. Channel Profitability Report");
            System.out.println("3. Advertising Efficiency Report");
            System.out.println("4. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    business.getSupplierDirectory().getSupplierList().forEach(supplier -> {
                        ProductsReport productsReport = supplier.getProductCatalog().generateProductPerformanceReport("Price");  
                        productsReport.printMarketProfitabilityReport();
                    });
                    break;
              
                case 2:
                    business.getSupplierDirectory().getSupplierList().forEach(supplier -> {
                        ProductsReport productsReport = supplier.getProductCatalog().generateProductPerformanceReport("Price");
                        productsReport.printChannelProfitabilityReport();
                    });
                    break;

                case 3:
                    business.advertisingEfficiency();
                    break;

                case 4:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                    
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}