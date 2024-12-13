package ui;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import model.Business.Business;


import model.Business.ConfigureABusiness;
import model.MarketModel.MarketCatalog;
import model.ProductManagement.ProductsReport;
import model.CustomerManagement.CustomerDirectory;
import model.MarketModel.ChannelCatalog;
import model.MarketingManagement.MarketingPersonDirectory;
import model.OrderManagement.MasterOrderList;
import model.Personnel.EmployeeDirectory;
import model.Personnel.PersonDirectory;
import model.ProductManagement.ProductSummary;
import model.ProductManagement.SolutionOffer;
import model.ProductManagement.SolutionOfferCatalog;
import model.SalesManagement.SalesPersonDirectory;
import model.Supplier.Supplier;
import model.Supplier.SupplierDirectory;
import model.UserAccountManagement.UserAccountDirectory;
import model.MarketModel.MarketChannelAssignment;
import model.MarketModel.Channel;
import model.MarketModel.Market;

public class TerminalInteraction {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Business business = ConfigureABusiness.createABusinessAndLoadALotOfData("Amazon", 10, 12, 1000, 1500, 100);
        ArrayList<Market> markets = business.getMarketCatalog().getMarketList();
        ArrayList<Channel> channels = business.getChannelCatalog().getChannelList();

        
        List<Map<String, Integer>> fullAdsList = new ArrayList<>();
        int sumCost = 0;
        int buyChoice = 0;

    
        System.out.println("1. Select based on market");
        System.out.println("2. Select based on channel");
        System.out.println("3. Exit");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                System.out.println("Select Market:");
                for (int i = 0; i < markets.size(); i++) {
                    System.out.println((i + 1) + ". " + markets.get(i).getName());
                }
                int marketChoice = scanner.nextInt();
                scanner.nextLine();
                Market selectedMarket = markets.get(marketChoice - 1);
                System.out.println("Profile selected: Market - " + selectedMarket.getName() + "\n");

                
                business.getSupplierDirectory().getSupplierList().forEach(supplier -> {
                    ProductsReport productsReport = supplier.getProductCatalog().generateProductPerformanceReport("Price");  
                    List<Map<String, Integer>> adsList = productsReport.getAdsByMarketList(selectedMarket);

                    adsList.forEach(ad -> {
                        fullAdsList.add(ad);  
                    });
                });
                
                while (true) {
                    System.out.println("Please select one or more items from the list:");
                    for (int i = 0; i < fullAdsList.size(); i++) {
                        Map<String, Integer> ad = fullAdsList.get(i);
                        String productName = ad.keySet().iterator().next();
                        System.out.println((i + 1) + ". " + productName + " - Ad Cost: " + ad.get(productName));
                    }
                    System.out.println((fullAdsList.size() + 1) + ". Done selecting (Exit)");

                    System.out.print("Enter the number of your selection (or " + (fullAdsList.size() + 1) + " to exit): ");
                    int productChoice = scanner.nextInt();

                    if (productChoice == fullAdsList.size() + 1) {
                        System.out.println("\n");
                        break;
                    } else if (productChoice > 0 && productChoice <= fullAdsList.size()) {
                        Map<String, Integer> selectedAd = fullAdsList.get(productChoice - 1);
                        String selectedProduct = selectedAd.keySet().iterator().next();
                            sumCost += selectedAd.get(selectedProduct);
                            System.out.println(selectedProduct + " has been added to your selection\n");
                    } else {
                        System.out.println("Invalid product choice, please try again.");
                    }
                }

                System.out.println(String.format("The total cost is $ %d, would you like to proceed ?", sumCost));
                System.out.println("1. Yes");
                System.out.println("2. No");
                System.out.print("Enter your choice: ");
                buyChoice = scanner.nextInt();

                if (buyChoice == 1) {
                    System.out.println("Accepting payment...");
                    System.out.println("Please wait...");
                    System.out.println("Payment received. Thank you for visiting!\n");
                } else {
                    System.out.println("System terminated, closing...");
                    scanner.close();
                    return;
                }
                break;

            case 2:
                System.out.println("Select Channel:");
                for (int i = 0; i < channels.size(); i++) {
                    System.out.println((i + 1) + ". " + channels.get(i).getName());
                }
                int channelChoice = scanner.nextInt();
                scanner.nextLine();
                Channel selectedChannel = channels.get(channelChoice - 1);
                System.out.println("Profile selected: Channel - " + selectedChannel.getName());
          
                System.out.println("Displaying advertisement based on selected profile...\n");
                business.getSupplierDirectory().getSupplierList().forEach(supplier -> {
                    ProductsReport productsReport = supplier.getProductCatalog().generateProductPerformanceReport("Price");
                    List<Map<String, Integer>> adsList = productsReport.getAdsByChannelList(selectedChannel);

                    adsList.forEach(ad -> {
                        fullAdsList.add(ad);  
                    });
                });
                
                while (true) {
                    System.out.println("Please select one or more items from the list:");
                    for (int i = 0; i < fullAdsList.size(); i++) {
                        Map<String, Integer> ad = fullAdsList.get(i);
                        String productName = ad.keySet().iterator().next();
                        System.out.println((i + 1) + ". " + productName + " - Ad Cost: " + ad.get(productName));
                    }
                    System.out.println((fullAdsList.size() + 1) + ". Done selecting (Exit)");

                    System.out.print("Enter the number of your selection (or " + (fullAdsList.size() + 1) + " to exit): ");
                    int productChoice = scanner.nextInt();

                    if (productChoice == fullAdsList.size() + 1) {
                        System.out.println("\n");
                        break;
                    } else if (productChoice > 0 && productChoice <= fullAdsList.size()) {
                        Map<String, Integer> selectedAd = fullAdsList.get(productChoice - 1);
                        String selectedProduct = selectedAd.keySet().iterator().next();
                            sumCost += selectedAd.get(selectedProduct);
                            System.out.println(selectedProduct + " has been added to your selection\n");
                    } else {
                        System.out.println("Invalid product choice, please try again.");
                    }
                }

                System.out.println(String.format("The total cost is $ %d, would you like to proceed ?", sumCost));
                System.out.println("1. Yes");
                System.out.println("2. No");
                System.out.print("Enter your choice: ");
                buyChoice = scanner.nextInt();

                if (buyChoice == 1) {
                    System.out.println("Accepting payment...");
                    System.out.println("Please wait...");
                    System.out.println("Payment received. Thank you for visiting!\n");
                } else {
                    System.out.println("System terminated, closing...");
                    scanner.close();
                    return;
                }
                break;


            case 3:
                System.out.println("Exiting...");
                scanner.close();
                return;
            default:
                System.out.println("Invalid choice. Exiting...");
                scanner.close();
                return;
        }   


        System.out.println("Exiting the application. Goodbye!");
    }

}