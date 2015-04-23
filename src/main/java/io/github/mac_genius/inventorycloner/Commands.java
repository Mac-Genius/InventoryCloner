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
 * This is the class that holds all the commands for the plugin.
 * Includes /ic, /ic view, /ic help,  and /ic clone
 *
 * @author John Harrison
 */
public class Commands implements CommandExecutor {
    private BukkitScheduler scheduler;
    private Plugin plugin;

    /**
     * Constructor for the class. This gets the main instance
     * of the plugin and sets up the Bukkit scheduler for
     * running tasks.
     *
     * @param pluginIn takes in the main instance of the plugin.
     */
    public Commands(Plugin pluginIn) {
        scheduler = Bukkit.getServer().getScheduler();
        plugin = pluginIn;
    }

    /**
     * This is the main method for executing commands.
     *
     * @param commandSender is the user that uses the command. Player only here.
     * @param command is the command that the user sent.
     * @param s is not used here
     * @param args is the extra words in the command such as "view" or "clone"
     * @return whether the command is executed or not
     */
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        // Grabs all online players
        Collection<? extends Player> onlinePlayers = Bukkit.getServer().getOnlinePlayers();

        // Main command
        if (command.getName().equalsIgnoreCase("ic")){
            if (commandSender instanceof Player && commandSender.hasPermission("ic.help")) {

                // If the player does not specify a command  to use
                if (args.length == 0) {
                    commandSender.sendMessage(ChatColor.GREEN + "-- InventoryCloner Help --");
                    commandSender.sendMessage(ChatColor.GOLD + "/ic view <player>" + ChatColor.WHITE + " lets you view a player's inventory");
                    commandSender.sendMessage(ChatColor.GOLD + "/ic clone <player>" + ChatColor.WHITE + " lets you clone a player's inventory");
                    commandSender.sendMessage(ChatColor.GOLD + "/ic help" + ChatColor.WHITE + " shows the commands");
                    return true;
                }

                // If the player types is /ic view
                if (args[0].equalsIgnoreCase("view") && commandSender.hasPermission("ic.view")) {

                    // If the player does not type in a username
                    if (args.length == 1) {
                        commandSender.sendMessage(ChatColor.GREEN + "[InventoryCloner] " + ChatColor.WHITE + "Please enter a playername.");
                        return true;
                    }
                    for (Player p : onlinePlayers) {

                        // Makes sure the player cannot get his own inventory
                        if (args[1].equals(commandSender.getName())) {
                            commandSender.sendMessage(ChatColor.GREEN + "[InventoryCloner] " + ChatColor.WHITE + "You cannot get your own inventory.");
                            return true;
                        }

                        // Gets the inventory of the specified player
                        if (p.getName().equals(args[1])) {
                            scheduler.runTaskLater(plugin, new InventoryUpdater(p, (Player) commandSender), 1);
                            return true;
                        }
                    }

                    // If the player is not online
                    commandSender.sendMessage(ChatColor.GREEN + "[InventoryCloner] " + ChatColor.WHITE + " That player is not online currently.");
                    return true;
                }

                // If the player types /ic clone
                if (args[0].equalsIgnoreCase("clone")) {

                    // If the player does not type in a username
                    if (args.length == 1) {
                        commandSender.sendMessage(ChatColor.GREEN + "[InventoryCloner] " + ChatColor.WHITE + "Please enter a playername.");
                        return true;
                    }
                    if (commandSender.hasPermission("ïc.clone"))
                    for (Player p : onlinePlayers) {

                        // Makes sure the player cannot clone his own inventory
                        if (args[1].equals(commandSender.getName())) {
                            commandSender.sendMessage(ChatColor.GREEN + "[InventoryCloner] " + ChatColor.WHITE + "You cannot clone your own inventory.");
                            return true;
                        }

                        // Clones the inventory of the specified player
                        if (p.getName().equals(args[1])) {
                            scheduler.runTask(plugin, new StartListener(plugin, p, (Player) commandSender));
                            scheduler.runTaskLater(plugin, new ConfirmScreen((Player) commandSender, p), 1);
                            return true;
                        }
                    }

                    // If the player is not online
                    commandSender.sendMessage(ChatColor.GREEN + "[InventoryCloner] " + ChatColor.WHITE + " That player is not online currently.");
                    return true;
                }

                // If the player does /ic help
                if (args[0].equalsIgnoreCase("help") && commandSender.hasPermission("ic.help")) {
                    commandSender.sendMessage(ChatColor.GREEN + "-- InventoryCloner Help --");
                    commandSender.sendMessage(ChatColor.GOLD + "/ic view <player>" + ChatColor.WHITE + " lets you view a player's inventory");
                    commandSender.sendMessage(ChatColor.GOLD + "/ic clone <player>" + ChatColor.WHITE + " lets you clone a player's inventory");
                    commandSender.sendMessage(ChatColor.GOLD + "/ic help" + ChatColor.WHITE + " shows the commands");
                    return true;
                }
            }

            // If the user is the console or a command block
            commandSender.sendMessage("You have to be a player to do that!");
            return true;
        }
        return false;
    }
}
