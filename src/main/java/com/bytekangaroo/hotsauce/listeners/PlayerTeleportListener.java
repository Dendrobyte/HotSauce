package com.bytekangaroo.hotsauce.listeners;

import com.bytekangaroo.hotsauce.Main;
import com.bytekangaroo.hotsauce.utils.HotSauceManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

/**
 * Created by Mark on 8/22/2018
 * Written for project HotSauce
 * Please do not use or edit this code unless permission has been given (or if it's on GitHub...)
 * Contact me on Twitter, @Mobkinz78, with any questions
 * §
 */
public class PlayerTeleportListener implements Listener {

    HotSauceManager manager = HotSauceManager.getInstance();
    String prefix = Main.getInstance().getPrefix();

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event){
        if(!manager.playerIsEnabled(event.getPlayer())) return;

        Player player = event.getPlayer();
        manager.disableHotSauce(player);
        player.sendMessage(prefix + "(HotSauce disables upon teleportation)");
    }

}
