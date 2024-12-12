/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.Business;

import java.util.Random;
import java.util.ArrayList;

import com.github.javafaker.Faker;

import model.CustomerManagement.CustomerDirectory;
import model.CustomerManagement.CustomerProfile;
import model.OrderManagement.MasterOrderList;
import model.OrderManagement.Order;
import model.Personnel.Person;
import model.Personnel.PersonDirectory;
import model.ProductManagement.ProductCatalog;
import model.ProductManagement.SolutionOffer;
import model.ProductManagement.SolutionOfferCatalog;
import model.ProductManagement.Product;
import model.Supplier.Supplier;
import model.Supplier.SupplierDirectory;
import model.MarketModel.MarketCatalog;
import model.MarketModel.ChannelCatalog;
import model.MarketModel.MarketChannelAssignment;

/**
 *
 * @author kal bugrara
 */
public class ConfigureABusiness {

  static int upperPriceLimit = 50;
  static int lowerPriceLimit = 10;
  static int range = 5;
  static int productMaxQuantity = 5;
  static int marketCount = 3;
  static int channelCount = 4;

  public static Business createABusinessAndLoadALotOfData(
      String name, int supplierCount, int productCount,
      int customerCount, int orderCount, int itemCount) {
    Business business = new Business(name);

    loadSuppliers(business, supplierCount);
    loadProducts(business, productCount);
    loadCustomers(business, customerCount);
    loadMarkets(business, marketCount);
    loadChannels(business, channelCount);
    loadMarketChannelAssignments(business);
    loadSolutionOffers(business);
    loadOrders(business, orderCount, itemCount);
    

    return business;
  }

  public static void loadSuppliers(Business b, int supplierCount) {
    Faker faker = new Faker();

    SupplierDirectory supplierDirectory = b.getSupplierDirectory();
    for (int index = 1; index <= supplierCount; index++) {
      supplierDirectory.newSupplier(faker.company().name());
    }
  }

  static void loadProducts(Business b, int productCount) {
    SupplierDirectory supplierDirectory = b.getSupplierDirectory();

    for (Supplier supplier : supplierDirectory.getSupplierList()) {
      int randomProductNumber = getRandom(1, productCount);
      ProductCatalog productCatalog = supplier.getProductCatalog();

      for (int index = 1; index <= randomProductNumber; index++) {
        String productName = "Product #" + index + " from " + supplier.getName();
        int randomFloor = getRandom(lowerPriceLimit, lowerPriceLimit + range);
        int randomCeiling = getRandom(upperPriceLimit - range, upperPriceLimit);
        int randomTarget = getRandom(randomFloor, randomCeiling);

        productCatalog.newProduct(productName, randomFloor, randomCeiling, randomTarget);
      }
    }
  }

  static int getRandom(int lower, int upper) {
    Random r = new Random();

    // nextInt(n) will return a number from zero to 'n'. Therefore e.g. if I want
    // numbers from 10 to 15
    // I will have result = 10 + nextInt(5)
    int randomInt = lower + r.nextInt(upper - lower);
    return randomInt;
  }

  static void loadCustomers(Business b, int customerCount) {
    CustomerDirectory customerDirectory = b.getCustomerDirectory();
    PersonDirectory personDirectory = b.getPersonDirectory();

    Faker faker = new Faker();

    for (int index = 1; index <= customerCount; index++) {
      Person newPerson = personDirectory.newPerson(faker.name().fullName());
      customerDirectory.newCustomerProfile(newPerson);
    }
  }

  static void loadOrders(Business b, int customerCount, int maxOrderCount) {
    MasterOrderList mol = b.getMasterOrderList();

    // pick a random customer (reach to customer directory)
    CustomerDirectory cd = b.getCustomerDirectory();
    SupplierDirectory sd = b.getSupplierDirectory();

    for (int index = 0; index < customerCount; index++) {
      CustomerProfile randomCustomer = cd.pickRandomCustomer();
      if (randomCustomer == null) {
        System.out.println("Cannot generate orders. No customers in the customer directory.");
        return;
      }

      Order customerOrder = mol.newOrder(randomCustomer);

      // add order items
      // -- pick a supplier first (randomly)
      // -- pick a product (randomly)
      // -- actual price, quantity

      int randomOrderCount = getRandom(1, maxOrderCount);
      for (int itemIndex = 0; itemIndex < randomOrderCount; itemIndex++) {
        Supplier randomSupplier = sd.pickRandomSupplier();
        if (randomSupplier == null) {
          System.out.println("Cannot generate orders. No supplier in the supplier directory.");
          return;
        }
        SolutionOfferCatalog soc = b.getSolutionOfferCatalog();
        SolutionOffer randomBundle = soc.pickRandomBundle();
        if (randomBundle == null) {
          System.out.println("Cannot generate orders. No products in the product catalog.");
          return;
        }

        int randomQuantity = getRandom(1, productMaxQuantity);
        customerOrder.newOrderItem(randomBundle, randomBundle.getTargetPrice(), randomQuantity);
      }
    }
    // Make sure order items are connected to the order
  }

  static void loadMarkets(Business b, int marketCount) {
    MarketCatalog marketCatalog = b.getMarketCatalog();

    for (int index = 0; index < marketCount; index++) {
      Faker faker = new Faker();
      marketCatalog.newMarket(faker.country().name());
    }
  }

  static void loadChannels(Business b, int channelCount) {
    ChannelCatalog channelCatalog = b.getChannelCatalog();

    for (int index = 0; index < channelCount; index++) {
      Faker faker = new Faker();
      channelCatalog.addChannel(faker.esports().event());
    }
  }

  static void loadMarketChannelAssignments(Business b) {
    MarketCatalog marketCatalog = b.getMarketCatalog();
    ChannelCatalog channelCatalog = b.getChannelCatalog();
    ArrayList<MarketChannelAssignment> marketChannelAssignments = b.getMarketChannelAssignments();

    for (int i = 0; i < marketCount; i++) {
      for (int j = 0; j < channelCount; j++) {
        MarketChannelAssignment ass = new MarketChannelAssignment(
            marketCatalog.getMarketList().get(i),
            channelCatalog.getChannelList().get(j));
        marketChannelAssignments.add(ass);
      }
    }
  }

  static void loadSolutionOffers(Business b) {
    SupplierDirectory suppliers = b.getSupplierDirectory();
    SolutionOfferCatalog catalog = b.getSolutionOfferCatalog();

    ArrayList<MarketChannelAssignment> assignments = b.getMarketChannelAssignments();

    for (int i = 0; i < assignments.size(); i++) {
      MarketChannelAssignment assignment = assignments.get(i);

      Integer productCount = 3;
      ArrayList<Product> randomProducts = new ArrayList<Product>();

      // pick [productCount] number of products randomly
      while (randomProducts.size() < productCount) {
        Product randomProduct = suppliers.pickRandomSupplier()
            .getProductCatalog()
            .pickRandomProduct();

        if (randomProducts.contains(randomProduct)) {
          continue;
        }
        randomProducts.add(randomProduct);
      }

      SolutionOffer solutionOffer = new SolutionOffer(assignment, randomProducts.get(0).getTargetPrice(),
          randomProducts.get(0));

      // add the rest products to the offer
      for (int j = 1; j < randomProducts.size(); j++) {
        float randomFraction = getRandom(1, 10) / 10;
        solutionOffer.addProduct(randomProducts.get(j), randomFraction);
      }

      assignment.addSolutionOffer(solutionOffer);
      catalog.add(solutionOffer);
    }
  }
}
