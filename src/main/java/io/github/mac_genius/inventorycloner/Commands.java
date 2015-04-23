package io.github.mac_genius.inventorycloner;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.Collection;

/**
 * Created by Mac on 4/19/2015.
 */
public class Commands implements CommandExecutor {
    private BukkitScheduler scheduler;
    private Plugin plugin;

    public Commands(Plugin pluginIn) {
        scheduler = Bukkit.getServer().getScheduler();
        plugin = pluginIn;
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        Collection<? extends Player> onlinePlayers = Bukkit.getServer().getOnlinePlayers();
        if (command.getName().equalsIgnoreCase("ic")){
            if (commandSender instanceof Player && commandSender.hasPermission("ic.help")) {
                if (args.length == 0) {
                    commandSender.sendMessage(ChatColor.GREEN + "-- InventoryCloner Help --");
                    commandSender.sendMessage(ChatColor.GOLD + "/ic view <player>" + ChatColor.WHITE + " lets you view a player's inventory");
                    commandSender.sendMessage(ChatColor.GOLD + "/ic clone <player>" + ChatColor.WHITE + " lets you clone a player's inventory");
                    commandSender.sendMessage(ChatColor.GOLD + "/ic help" + ChatColor.WHITE + " shows the commands");
                    return true;
                }
                if (args[0].equalsIgnoreCase("view") && commandSender.hasPermission("ic.view")) {
                    if (args.length == 1) {
                        commandSender.sendMessage(ChatColor.GREEN + "[InventoryCloner] " + ChatColor.WHITE + "Please enter a playername.");
                        return true;
                    }
                    for (Player p : onlinePlayers) {
                        if (args[1].equals(commandSender.getName())) {
                            commandSender.sendMessage(ChatColor.GREEN + "[InventoryCloner] " + ChatColor.WHITE + "You cannot get your own inventory.");
                            return true;
                        }
                        if (p.getName().equals(args[1])) {
                            scheduler.runTaskLater(plugin, new InventoryUpdater(p, (Player) commandSender), 1);
                            return true;
                        }
                    }
                    commandSender.sendMessage(ChatColor.GREEN + "[InventoryCloner] " + ChatColor.WHITE + " That player is not online currently.");
                    return true;
                }
                if (args[0].equalsIgnoreCase("clone")) {
                    if (args.length == 1) {
                        commandSender.sendMessage(ChatColor.GREEN + "[InventoryCloner] " + ChatColor.WHITE + "Please enter a playername.");
                        return true;
                    }
                    if (commandSender.hasPermission("ïc.clone"))
                    for (Player p : onlinePlayers) {
                        if (args[1].equals(commandSender.getName())) {
                            commandSender.sendMessage(ChatColor.GREEN + "[InventoryCloner] " + ChatColor.WHITE + "You cannot clone your own inventory.");
                            return true;
                        }
                        if (p.getName().equals(args[1])) {
                            scheduler.runTask(plugin, new StartListener(plugin, p, (Player) commandSender));
                            scheduler.runTaskLater(plugin, new ConfirmScreen((Player) commandSender, p), 1);

                            return true;
                        }
                    }
                    commandSender.sendMessage(ChatColor.GREEN + "[InventoryCloner] " + ChatColor.WHITE + " That player is not online currently.");
                    return true;
                }
                if (args[0].equalsIgnoreCase("help") && commandSender.hasPermission("ic.help")) {
                    commandSender.sendMessage(ChatColor.GREEN + "-- InventoryCloner Help --");
                    commandSender.sendMessage(ChatColor.GOLD + "/ic view <player>" + ChatColor.WHITE + " lets you view a player's inventory");
                    commandSender.sendMessage(ChatColor.GOLD + "/ic clone <player>" + ChatColor.WHITE + " lets you clone a player's inventory");
                    commandSender.sendMessage(ChatColor.GOLD + "/ic help" + ChatColor.WHITE + " shows the commands");
                    return true;
                }
            }
            commandSender.sendMessage("You have to be a player to do that!");
            return true;
        }
        return false;
    }
}
