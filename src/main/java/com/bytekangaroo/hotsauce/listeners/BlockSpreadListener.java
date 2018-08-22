package com.bytekangaroo.hotsauce.listeners;

import com.bytekangaroo.hotsauce.utils.HotSauceManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockSpreadEvent;

/**
 * Created by Mark on 8/22/2018
 * Written for project HotSauce
 * Please do not use or edit this code unless permission has been given (or if it's on GitHub...)
 * Contact me on Twitter, @Mobkinz78, with any questions
 * §
 */
public class BlockSpreadListener implements Listener {

    HotSauceManager manager = HotSauceManager.getInstance();

    /* WARNING: DOES NOT FULLY CANCEL FIRE SPREAD IF THERE IS A CRAP TON OF FIRE */
    @EventHandler
    public void onFireSpread(BlockSpreadEvent event){
        Block source = event.getSource();
        if(!source.getType().equals(Material.FIRE)) return;

        for(Player player : manager.getPlayerBlockTrails().keySet()){
            if(manager.getPlayerBlockTrails().get(player).contains(source)){
                event.setCancelled(true);
            }
        }
    }

}
