package io.github.mac_genius.inventorycloner;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredListener;

import java.util.ArrayList;

/**
 * This is the listener for the confirmation screen. It checks
 * to see what the player will pick.
 *
 * @author John Harrison
 */
public class ClickCheck implements Listener {
    private Player cloneFrom;
    private Player cloneTo;
    private Plugin plugin;
    private ArrayList<RegisteredListener> listeners;
    private ArrayList<HandlerList> handlerLists;

    /**
     * Imports the main instance of the plugin, gets the moderator to clone
     * the inventory to, and gets the player to clone from.
     *
     * @param cloneFromIn is the player to clone from
     * @param cloneToIn is the moderator to clone the inventory to
     * @param pluginIn is the main instance of the plugin
     */
    public ClickCheck(Player cloneFromIn, Player cloneToIn, Plugin pluginIn) {
        cloneFrom = cloneFromIn;
        cloneTo = cloneToIn;
        plugin = pluginIn;
        listeners = HandlerList.getRegisteredListeners(plugin);
        handlerLists = HandlerList.getHandlerLists();
    }

    /**
     * Checks to see player clicks in the inventory
     *
     * @param event the InventoryClick event
     */
    @EventHandler
    public void onClick(InventoryClickEvent event) {

        // If the person who clicked is the player to clone to
        if (event.getWhoClicked().getName().equals(cloneTo.getName())) {

            // If the item clicked is the confirm button
            if (event.getCurrentItem() != null && event.getCurrentItem().getType() == Material.WOOL
                    && event.getCurrentItem().getItemMeta() != null
                    && event.getCurrentItem().getItemMeta().getDisplayName() != null
                    && event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "" + ChatColor.BOLD + "Clone " + cloneFrom.getName())) {

                // Unregisters this listener after finishing
                for (HandlerList l : handlerLists) {
                    for (RegisteredListener r : l.getRegisteredListeners()) {
                        if (r.getListener() == this) {
                            l.unregister(this);
                        }
                    }
                }

                // Clones the inventory to the moderator
                event.getWhoClicked().getInventory().setContents(cloneFrom.getInventory().getContents());
                event.getWhoClicked().getInventory().setBoots(cloneFrom.getInventory().getBoots());
                event.getWhoClicked().getInventory().setLeggings(cloneFrom.getInventory().getLeggings());
                event.getWhoClicked().getInventory().setChestplate(cloneFrom.getInventory().getChestplate());
                event.getWhoClicked().getInventory().setHelmet(cloneFrom.getInventory().getHelmet());
                event.getWhoClicked().closeInventory();
                event.getWhoClicked().sendMessage(ChatColor.GREEN + "[InventoryCloner] " + ChatColor.WHITE + cloneFrom.getName() + "'s Inventory's cloned!");
                event.setCancelled(true);
                return;
            }

            // If the item clicked is the cancel button
            if (event.getCurrentItem() != null && event.getCurrentItem().getType() == Material.WOOL
                    && event.getCurrentItem().getItemMeta() != null && event.getCurrentItem().getItemMeta().getDisplayName() != null
                    && event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "" + ChatColor.BOLD + "Nevermind")) {

                // Unregisters the listener after being used
                for (HandlerList l : handlerLists) {
                    for (RegisteredListener r : l.getRegisteredListeners()) {
                        if (r.getListener() == this) {
                            l.unregister(this);
                        }
                    }
                }

                // Takes the moderator back to the player's main inventory
                plugin.getServer().getScheduler().runTaskLater(plugin, new InventoryUpdater(cloneFrom, (Player) event.getWhoClicked()), 1);
                event.setCancelled(true);
                return;
            }

            // Makes sure the moderator cannot click items while viewing the player's inventory
            event.setCancelled(true);
            event.getWhoClicked().sendMessage(ChatColor.GREEN + "[InventoryCloner] " + ChatColor.WHITE + "You can only view this inventory");
        }
    }

    /**
     * Checks to see if the player closes his inventory while in the confirmation screen
     *
     * @param event is the InventoryClose event
     */
    @EventHandler
    public void onClose(InventoryCloseEvent event) {

        // If the player who closed his inventory is the moderator
        if (event.getPlayer().getName().equals(cloneTo.getName())) {
            event.getPlayer().sendMessage(ChatColor.GREEN + "[InventoryCloner] " + ChatColor.WHITE + cloneFrom.getName() + "'s inventory not cloned.");

            // Unregisters the listener after finishing
            for (HandlerList l : handlerLists) {
                for (RegisteredListener r : l.getRegisteredListeners()) {
                    if (r.getListener() == this) {
                        l.unregister(this);
                    }
                }
            }
        }
    }
}
