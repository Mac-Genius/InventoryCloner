package io.github.mac_genius.inventorycloner;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Mac on 4/19/2015.
 */
public class EventListener implements Listener {
    private Plugin plugin;
    private BukkitScheduler scheduler;
    private HandlerList handlerList;

    public EventListener(Plugin pluginIn) {
        plugin = pluginIn;
        scheduler = Bukkit.getServer().getScheduler();
        handlerList = new HandlerList();
    }

    @EventHandler
     public void onInventoryClick(InventoryClickEvent event) {
        Collection<? extends Player> onlinePlayers = Bukkit.getServer().getOnlinePlayers();
        for (Player p : onlinePlayers) {
            if (p.getOpenInventory().getTitle().equals(event.getWhoClicked().getName())) {
                if (p.getOpenInventory().getTopInventory().getSize() == 54) {
                    scheduler.runTaskLater(plugin, new InventoryUpdater((Player) event.getWhoClicked(), p), 1);
                }
                if (p.getOpenInventory().getTopInventory().getSize() == 45) {
                    scheduler.runTaskLater(plugin, new EnderChestUpdater((Player) event.getWhoClicked(), p), 1);
                }
            }
            if (event.getInventory().getName().equals(p.getName())) {
                if (event.getCurrentItem() != null && event.getCurrentItem().getType() == Material.ANVIL
                        && event.getCurrentItem().getItemMeta() != null && event.getCurrentItem().getItemMeta().getDisplayName() != null
                        && event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "" + ChatColor.BOLD + "Clone it!")) {
                    if (event.getWhoClicked().hasPermission("ic.clone")) {
                        scheduler.runTaskLater(plugin, new ConfirmScreen((Player) event.getWhoClicked(), p), 1);
                        scheduler.runTaskLater(plugin, new StartListener(plugin, p, (Player) event.getWhoClicked()), 2);
                    }
                    event.setCancelled(true);
                    return;
                }
                if (event.getCurrentItem() != null && event.getCurrentItem().getType() == Material.REDSTONE_BLOCK
                        && event.getCurrentItem().getItemMeta() != null && event.getCurrentItem().getItemMeta().getDisplayName() != null
                        && event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.RED + "" + ChatColor.BOLD + "Close Inventory")) {
                    event.getWhoClicked().closeInventory();
                    event.setCancelled(true);
                    return;
                }
                if (event.getCurrentItem() != null && event.getCurrentItem().getType() == Material.ENDER_CHEST
                        && event.getCurrentItem().getItemMeta() != null && event.getCurrentItem().getItemMeta().getDisplayName() != null
                        && event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "View " + p.getName() + "'s Enderchest inventory")) {
                    scheduler.runTaskLater(plugin, new EnderChestUpdater(p, (Player) event.getWhoClicked()), 1);
                    event.setCancelled(true);
                    return;
                }
                if (event.getCurrentItem() != null && event.getCurrentItem().getType() == Material.CHEST
                        && event.getCurrentItem().getItemMeta() != null && event.getCurrentItem().getItemMeta().getDisplayName() != null
                        && event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "View " + p.getName() + "'s inventory")) {
                    scheduler.runTaskLater(plugin, new InventoryUpdater(p, (Player) event.getWhoClicked()), 1);
                    event.setCancelled(true);
                    return;
                }
                event.setCancelled(true);
                event.getWhoClicked().sendMessage(ChatColor.GREEN + "[InventoryCloner] " + ChatColor.WHITE + "You can only view this inventory");
                return;
            }
        }
    }

    @EventHandler
    public void onItemPickUp(PlayerPickupItemEvent event) {
        ArrayList<Player> onlinePlayers = new ArrayList<>(Bukkit.getServer().getOnlinePlayers());
        for (Player p : onlinePlayers) {
            if (p.getOpenInventory().getTitle().equals(event.getPlayer().getName())) {
                if (p.getOpenInventory().getTopInventory().getSize() == 54) {
                    scheduler.runTaskLater(plugin, new InventoryUpdater((Player) event.getPlayer(), p), 1);
                }
                if (p.getOpenInventory().getTopInventory().getSize() == 45) {
                    scheduler.runTaskLater(plugin, new EnderChestUpdater((Player) event.getPlayer(), p), 1);
                }
            }
        }

    }

    @EventHandler
    public void onItemDropUp(PlayerDropItemEvent event) {
        ArrayList<Player> onlinePlayers = new ArrayList<>(Bukkit.getServer().getOnlinePlayers());
        for (Player p : onlinePlayers) {
            if (p.getOpenInventory().getTitle().equals(event.getPlayer().getName())) {
                if (p.getOpenInventory().getTopInventory().getSize() == 54) {
                    scheduler.runTaskLater(plugin, new InventoryUpdater((Player) event.getPlayer(), p), 1);
                }
                if (p.getOpenInventory().getTopInventory().getSize() == 45) {
                    scheduler.runTaskLater(plugin, new EnderChestUpdater((Player) event.getPlayer(), p), 1);
                }
            }
        }

    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        ArrayList<Player> onlinePlayers = new ArrayList<>(Bukkit.getServer().getOnlinePlayers());
        for (Player p : onlinePlayers) {
            if (p.getOpenInventory().getTitle().equals(event.getEntity().getName())) {
                if (p.getOpenInventory().getTopInventory().getSize() == 54) {
                    scheduler.runTaskLater(plugin, new InventoryUpdater((Player) event.getEntity(), p), 1);
                }
                if (p.getOpenInventory().getTopInventory().getSize() == 45) {
                    scheduler.runTaskLater(plugin, new EnderChestUpdater((Player) event.getEntity(), p), 1);
                }
            }
        }
    }

    @EventHandler
    public void onPlaceBlock(BlockPlaceEvent event) {
        ArrayList<Player> onlinePlayers = new ArrayList<>(Bukkit.getServer().getOnlinePlayers());
        for (Player p : onlinePlayers) {
            if (p.getOpenInventory().getTitle().equals(event.getPlayer().getName())) {
                if (p.getOpenInventory().getTopInventory().getSize() == 54) {
                    scheduler.runTaskLater(plugin, new InventoryUpdater((Player) event.getPlayer(), p), 1);
                }
                if (p.getOpenInventory().getTopInventory().getSize() == 45) {
                    scheduler.runTaskLater(plugin, new EnderChestUpdater((Player) event.getPlayer(), p), 1);
                }
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        ArrayList<Player> onlinePlayers = new ArrayList<>(Bukkit.getServer().getOnlinePlayers());
        for (Player p : onlinePlayers) {
            if (p.getOpenInventory().getTitle().equals(event.getPlayer().getName())) {
                if (p.getOpenInventory().getTopInventory().getSize() == 54) {
                    scheduler.runTaskLater(plugin, new InventoryUpdater((Player) event.getPlayer(), p), 1);
                }
                if (p.getOpenInventory().getTopInventory().getSize() == 45) {
                    scheduler.runTaskLater(plugin, new EnderChestUpdater((Player) event.getPlayer(), p), 1);
                }
            }
        }
    }
}
