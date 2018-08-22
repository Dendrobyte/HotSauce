package com.bytekangaroo.hotsauce.listeners;

import com.bytekangaroo.hotsauce.Main;
import com.bytekangaroo.hotsauce.utils.HotSauceManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Created by Mark on 8/22/2018
 * Written for project HotSauce
 * Please do not use or edit this code unless permission has been given (or if it's on GitHub...)
 * Contact me on Twitter, @Mobkinz78, with any questions
 * §
 */
public class PlayerMoveListener implements Listener {

    String prefix = Main.getInstance().getPrefix();
    HotSauceManager manager = HotSauceManager.getInstance();

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){

        // Ensure that the block the player is walking 'into' is actually changing
        boolean xVal = event.getFrom().getBlockX() == (event.getTo().getBlockX());
        boolean zVal = event.getFrom().getBlockZ() == (event.getTo().getBlockZ());
        if(xVal && zVal){
            return;
        }

        // Set fire as they move
        if(!manager.playerIsEnabled(event.getPlayer())) return;
        manager.igniteBlock(event.getPlayer());

    }

}
