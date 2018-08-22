package com.bytekangaroo.hotsauce.command;

import com.bytekangaroo.hotsauce.Main;
import com.bytekangaroo.hotsauce.utils.HotSauceManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Mark on 8/22/2018
 * Written for project HotSauce
 * Please do not use or edit this code unless permission has been given (or if it's on GitHub...)
 * Contact me on Twitter, @Mobkinz78, with any questions
 * §
 */
public class BaseCommand implements CommandExecutor {

    String prefix = Main.getInstance().getPrefix();
    HotSauceManager manager = HotSauceManager.getInstance();

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(prefix + "Sorry, HotSauce can only be used by a player!");
            return true;
        }

        Player player = (Player) sender;

        /* Base usage command */
        if(command.getName().equalsIgnoreCase("hotsauce")){
            // Make sure player has proper permission for usage
            if(!player.hasPermission("hotsauce.use")){
                player.sendMessage(prefix + "Sorry, you can not use HotSauce!");
                return true;
            }
            if(args.length == 0){
                player.sendMessage(prefix + "Make sure to include " + ChatColor.GREEN + ChatColor.BOLD + "enable" + ChatColor.getLastColors(prefix) + " or "
                        + ChatColor.RED + ChatColor.BOLD + "disable!");
                return true;
            }
            if(args.length > 1){
                player.sendMessage(prefix + "Make sure to use only " + ChatColor.GREEN + ChatColor.BOLD + "enable" + ChatColor.getLastColors(prefix) + " or "
                        + ChatColor.RED + ChatColor.BOLD + "disable!");
                return true;
            }

            // Enable HotSauce
            if(args[0].equalsIgnoreCase("enable")){
                if(manager.playerIsEnabled(player)) {
                    player.sendMessage(prefix + "You already have HotSauce enabled!");
                    return true;
                }
                manager.enableHotSauce(player);
            }

            // Disable HotSauce
            if(args[0].equalsIgnoreCase("disable")){
                if(!manager.playerIsEnabled(player)) {
                    player.sendMessage(prefix + "HotSauce is not enabled, try " + ChatColor.GREEN + ChatColor.ITALIC + "/hotsauce enable!");
                    return true;
                }
                manager.disableHotSauce(player);
            }

            // Make sure player has proper permission for reload
            if(args[0].equals("reload")) {
                if(!player.hasPermission("hotsauce.reload")) {
                    player.sendMessage(prefix + "You do not have permission to do this!");
                }
                Main.getInstance().reloadConfig();
                manager.resetConfigValues();
                player.sendMessage(prefix + "HotSauce duration: " + ChatColor.RED + ChatColor.BOLD + Main.getInstance().getConfig().getInt("hotsauce-duration") +
                        ChatColor.getLastColors(prefix) + " seconds.");
                player.sendMessage(prefix + "Trail length: " + ChatColor.RED + ChatColor.BOLD + Main.getInstance().getConfig().getInt("trail-lenght") +
                        ChatColor.getLastColors(prefix) + " blocks.");
                player.sendMessage(prefix + "HotSauce v" + Main.getInstance().getDescription().getVersion() + " config.yml has been reloaded!");
                return true;
            }
        }

        return true;
    }

}
