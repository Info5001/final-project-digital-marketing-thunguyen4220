/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.MarketModel;

import java.util.ArrayList;

/**
 *
 * @author kal bugrara
 */
public class ChannelCatalog {
    ArrayList<Channel> channels;

    public ChannelCatalog() {
        channels = new ArrayList<Channel>();
    }

    public Channel addChannel(String name) {
        Channel newChannel = new Channel(name);
        channels.add(newChannel);
        return newChannel;
    }

    public ArrayList<Channel> getChannelList() {
        return channels;
    }
}
