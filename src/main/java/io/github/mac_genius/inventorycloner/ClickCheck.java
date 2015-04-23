package io.github.mac_genius.inventorycloner;

import org.bukkit.Bukkit;
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
 * Created by Mac on 4/20/2015.
 */
public class ClickCheck implements Listener {
    private Player cloneFrom;
    private Player cloneTo;
    private Plugin plugin;
    private ArrayList<RegisteredListener> listeners;
    private ArrayList<HandlerList> handlerLists;

    public ClickCheck(Player cloneFromIn, Player cloneToIn, Plugin pluginIn) {
        cloneFrom = cloneFromIn;
        cloneTo = cloneToIn;
        plugin = pluginIn;
        listeners = HandlerList.getRegisteredListeners(plugin);
        handlerLists = HandlerList.getHandlerLists();
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getWhoClicked().getName().equals(cloneTo.getName())) {
            if (event.getCurrentItem() != null && event.getCurrentItem().getType() == Material.WOOL && event.getCurrentItem().getItemMeta() != null && event.getCurrentItem().getItemMeta().getDisplayName() != null && event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "" + ChatColor.BOLD + "Clone " + cloneFrom.getName())) {
                for (HandlerList l : handlerLists) {
                    for (RegisteredListener r : l.getRegisteredListeners()) {
                        if (r.getListener() == this) {
                            l.unregister(this);
                        }
                    }
                }
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
            if (event.getCurrentItem() != null && event.getCurrentItem().getType() == Material.WOOL && event.getCurrentItem().getItemMeta() != null && event.getCurrentItem().getItemMeta().getDisplayName() != null && event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "" + ChatColor.BOLD + "Nevermind")) {
                for (HandlerList l : handlerLists) {
                    for (RegisteredListener r : l.getRegisteredListeners()) {
                        if (r.getListener() == this) {
                            l.unregister(this);
                        }
                    }
                }
                plugin.getServer().getScheduler().runTaskLater(plugin, new InventoryUpdater(cloneFrom, (Player) event.getWhoClicked()), 1);
                event.setCancelled(true);
                return;
            }
            event.setCancelled(true);
            event.getWhoClicked().sendMessage(ChatColor.GREEN + "[InventoryCloner] " + ChatColor.WHITE + "You can only view this inventory");
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (event.getPlayer().getName().equals(cloneTo.getName())) {
            event.getPlayer().sendMessage(ChatColor.GREEN + "[InventoryCloner] " + ChatColor.WHITE + cloneFrom.getName() + "'s inventory not cloned.");
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
