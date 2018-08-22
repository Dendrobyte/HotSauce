package com.bytekangaroo.hotsauce.utils;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Mark on 8/22/2018
 * Written for project HotSauce
 * Please do not use or edit this code unless permission has been given (or if it's on GitHub...)
 * Contact me on Twitter, @Mobkinz78, with any questions
 * §
 */
public class HotSauceTimer extends BukkitRunnable {

    Player player = null;
    int seconds = 0;

    HotSauceManager manager = HotSauceManager.getInstance();

    public HotSauceTimer(Player player, int seconds){
        this.player = player;
        this.seconds = seconds;
    }

    public void run() {
        manager.disableHotSauce(player);
    }
}
