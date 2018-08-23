package com.bytekangaroo.hotsauce.utils;

import com.bytekangaroo.hotsauce.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerRegisterChannelEvent;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Mark on 8/22/2018
 * Written for project HotSauce
 * Please do not use or edit this code unless permission has been given (or if it's on GitHub...)
 * Contact me on Twitter, @Mobkinz78, with any questions
 * §
 */
public class HotSauceManager {

    private String prefix = Main.getInstance().getPrefix();

    private static HotSauceManager instance = new HotSauceManager();
    private static HashMap<Player, ArrayList<Block>> playerBlockTrails = new HashMap<Player, ArrayList<Block>>();

    private static ArrayList<Player> enabledPlayers = new ArrayList<Player>();
    private static HashMap<Player, HotSauceTimer> playerHotSauceTimers = new HashMap<Player, HotSauceTimer>();

    private int configTrailLength = Main.getInstance().getConfig().getInt("trail-length");
    private int configSeconds = Main.getInstance().getConfig().getInt("hotsauce-duration");
    private int seconds = configSeconds * 20;

    private HotSauceManager() {

    }

    public static HotSauceManager getInstance() {
        return instance;
    }

    /* Getters */
    public HashMap<Player, ArrayList<Block>> getPlayerBlockTrails(){
        return playerBlockTrails;
    }

    public HashMap<Player, HotSauceTimer> getPlayerHotSauceTimers(){
        return playerHotSauceTimers;
    }

    /* Enable/Disable HotSauce */
    public void enableHotSauce(Player player){
        // Just a quick double check so we don't screw up the HashMap
        if(enabledPlayers.contains(player)) return;

        // Otherwise, enable onward!
        enabledPlayers.add(player);
        HotSauceTimer hotSauceTimer = new HotSauceTimer(player, seconds);
        playerHotSauceTimers.put(player, hotSauceTimer);
        hotSauceTimer.runTaskLater(Main.getInstance(), seconds);

        // Send player confirmation messages
        player.sendMessage(prefix + "HotSauce has been " + ChatColor.GREEN + ChatColor.BOLD + "enabled!");
        player.sendMessage(prefix + "HotSauce will disable in " + configSeconds + " seconds!");
        player.sendMessage(prefix + "Use " + ChatColor.RED + "/hotsauce disable" + ChatColor.getLastColors(prefix) + " to disable earlier.");
    }

    public void disableHotSauce(Player player){
        if(!enabledPlayers.contains(player)) return; // The timer could have hit this after already disabled by command, thus don't send any message.

        extinguishEntireTrail(player);
        getPlayerBlockTrails().remove(player);
        enabledPlayers.remove(player);
        player.sendMessage(prefix + "HotSauce has been " + ChatColor.DARK_RED + ChatColor.BOLD + "disabled!");
    }

    /* Fire Management */
    private void setFire(Location feetLocation){
        Location location = feetLocation;
        if(location.getBlock().getType().equals(Material.AIR)){
            Block litBlock = feetLocation.getBlock();
            litBlock.setType(Material.FIRE);
        }
    }

    private void extinguishFire(Block block){
        if(!block.getType().equals(Material.FIRE)) return; // Ensure the block is indeed on fire
        block.setType(Material.AIR);
    }

    /* Fire Trail Management */
    private void extinguishEntireTrail(Player player){
        ArrayList<Block> blocksOnFire = getPlayerBlockTrails().get(player);
        for(Block block : blocksOnFire){
            extinguishFire(block);
        }
    }

    /* Player Methods */
    public boolean playerIsEnabled(Player player){
        return enabledPlayers.contains(player);
    }

    private void updatePlayerBlockTrails(Player player, Block block){
        // Check to make sure it is indeed on fire
        if(!block.getType().equals(Material.FIRE)) return;

        // Add block to the arraylist
        ArrayList<Block> blocksInTrail = getPlayerBlockTrails().get(player);
        if(blocksInTrail == null){ // If the player isn't in the list... Which shouldn't happen
            System.out.println("Player not in the hashmap...?");
            return;
        }
        blocksInTrail.add(block);

        // Make sure to extinguish fire blocks so it doesn't become too much
        if(blocksInTrail.size() >= configTrailLength) {
            extinguishFire(blocksInTrail.get(0));
            blocksInTrail.remove(0);
        }
        // Update HashMap
        getPlayerBlockTrails().remove(player);
        getPlayerBlockTrails().put(player, blocksInTrail);
    }

    // This is the main method being called in the PlayerMoveEvent
    public void igniteBlock(Player player){
        if(!playerIsEnabled(player)) return; // Just an extra check

        Location location = player.getLocation();
        Block litBlock = player.getLocation().getBlock();
        // Add player to hashmap if they are not already in it
        if(!getPlayerBlockTrails().containsKey(player)){
            getPlayerBlockTrails().put(player, new ArrayList<Block>());
        }

        // Light fire at player's feet location
        setFire(location);

        // Update player and block info in the HashMap
        updatePlayerBlockTrails(player, litBlock);
    }

    /* Config values to update upon reload */
    public void resetConfigValues(){
        this.configSeconds = Main.getInstance().getConfig().getInt("hotsauce-duration");
        this.configTrailLength = Main.getInstance().getConfig().getInt("trail-length");
    }
}
