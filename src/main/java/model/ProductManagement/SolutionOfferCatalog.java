/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.ProductManagement;

import java.util.ArrayList;
import java.util.Random;

import model.Business.Business;
import model.MarketModel.MarketChannelAssignment;

/**
 *
 * @author kal bugrara
 */
public class SolutionOfferCatalog {
    Business business;
    ArrayList<SolutionOffer> solutionOffers;

    public SolutionOfferCatalog(Business b) {
        business = b;
        solutionOffers = new ArrayList<SolutionOffer>();
    }

    public SolutionOffer newBundle(MarketChannelAssignment m, int price, Product p) {
        SolutionOffer bundle = new SolutionOffer(m, price, p);
        solutionOffers.add(bundle);
        return bundle;
    }

    public SolutionOffer pickRandomBundle() {
        Random ran = new Random();
        //System.out.println(solutionOffers.size());

        if (solutionOffers.size() <= 0)
            return null;
        else {
            int idx = ran.nextInt(solutionOffers.size());
            return solutionOffers.get(idx);
        }
    }
    
    public void add(SolutionOffer so){
        solutionOffers.add(so);
    }
}
