/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.MarketModel;

import java.util.ArrayList;
import model.ProductManagement.SolutionOffer;

/**
 *
 * @author kal bugrara
 */
public class Market {
  ArrayList<SolutionOffer> solutionOffers;
  ArrayList<MarketChannelAssignment> marketChannelCombs;
  ArrayList<String> characteristics;
  ArrayList<Market> submarkets;
  String name;
  int size;

  public Market(String name) {
    characteristics = new ArrayList<String>();
    this.name = name;
  }

  public MarketChannelAssignment getMarketChannelComb(Channel c) {
    for (MarketChannelAssignment mca : marketChannelCombs) {
      if (mca.getChannel().equals(c))
        return mca;
    }
    MarketChannelAssignment newMca = new MarketChannelAssignment(this, c);
    marketChannelCombs.add(newMca);
    return newMca;
  }

  public void addSolutionOffer(SolutionOffer so) {
    solutionOffers.add(so);
  }

  public ArrayList<SolutionOffer> getSolutionOffers() {
    return solutionOffers;
  }

  public String getName() {
    return name;
  }
}
