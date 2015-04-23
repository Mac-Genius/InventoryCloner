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
 * This is the listener class. It checks for people to click on items
 * in the inventory. It also updates the person viewing an inventory
 * when the person being viewed changes their inventory.
 *
 * @author John Harrison
 */
public class EventListener implements Listener {
    private Plugin plugin;
    private BukkitScheduler scheduler;
    private HandlerList handlerList;

    /**
     * This grabs the main instance of the server and sets up a handlerlist and
     * a scheduler.
     *
     * @param pluginIn is the main instance of the plugin.
     */
    public EventListener(Plugin pluginIn) {
        plugin = pluginIn;
        scheduler = Bukkit.getServer().getScheduler();
        handlerList = new HandlerList();
    }

    /**
     * This is activated when a user interacts whis his/her inventory.
     *
     * @param event is the rightclick event for a player
     */
    @EventHandler
     public void onInventoryClick(InventoryClickEvent event) {

        // Gets the online players
        Collection<? extends Player> onlinePlayers = Bukkit.getServer().getOnlinePlayers();

        // Iterates through the players
        for (Player p : onlinePlayers) {

            // Updates the moderator's inventory when the player he is viewing changes his inventory.
            if (p.getOpenInventory().getTitle().equals(event.getWhoClicked().getName())) {

                // The player's main inventory
                if (p.getOpenInventory().getTopInventory().getSize() == 54) {
                    scheduler.runTaskLater(plugin, new InventoryUpdater((Player) event.getWhoClicked(), p), 1);
                }

                // The player's enderchest inventory
                if (p.getOpenInventory().getTopInventory().getSize() == 45) {
                    scheduler.runTaskLater(plugin, new EnderChestUpdater((Player) event.getWhoClicked(), p), 1);
                }
            }

            // If the inventory name is equal to the inventory being viewed
            if (event.getInventory().getName().equals(p.getName())) {

                // If the moderator clicks the anvil
                if (event.getCurrentItem() != null && event.getCurrentItem().getType() == Material.ANVIL
                        && event.getCurrentItem().getItemMeta() != null && event.getCurrentItem().getItemMeta().getDisplayName() != null
                        && event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "" + ChatColor.BOLD + "Clone it!")) {

                    // Takes the moderator to the confirm screen and bring up the listener for the confirm screen
                    if (event.getWhoClicked().hasPermission("ic.clone")) {
                        scheduler.runTaskLater(plugin, new ConfirmScreen((Player) event.getWhoClicked(), p), 1);
                        scheduler.runTaskLater(plugin, new StartListener(plugin, p, (Player) event.getWhoClicked()), 2);
                    }

                    // Makes sure moderators cannot remove item when being viewed
                    event.setCancelled(true);
                    return;
                }

                // If the moderator clicks the redstone block his inventory will be closed
                if (event.getCurrentItem() != null && event.getCurrentItem().getType() == Material.REDSTONE_BLOCK
                        && event.getCurrentItem().getItemMeta() != null && event.getCurrentItem().getItemMeta().getDisplayName() != null
                        && event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.RED + "" + ChatColor.BOLD + "Close Inventory")) {
                    event.getWhoClicked().closeInventory();
                    event.setCancelled(true);
                    return;
                }

                // If the moderator clicks the enderchest he will view the player's enderchest
                if (event.getCurrentItem() != null && event.getCurrentItem().getType() == Material.ENDER_CHEST
                        && event.getCurrentItem().getItemMeta() != null && event.getCurrentItem().getItemMeta().getDisplayName() != null
                        && event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "View " + p.getName() + "'s Enderchest inventory")) {
                    scheduler.runTaskLater(plugin, new EnderChestUpdater(p, (Player) event.getWhoClicked()), 1);
                    event.setCancelled(true);
                    return;
                }

                // If the moderator clicks the chest he will view the player's main inventory
                if (event.getCurrentItem() != null && event.getCurrentItem().getType() == Material.CHEST
                        && event.getCurrentItem().getItemMeta() != null && event.getCurrentItem().getItemMeta().getDisplayName() != null
                        && event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "View " + p.getName() + "'s inventory")) {
                    scheduler.runTaskLater(plugin, new InventoryUpdater(p, (Player) event.getWhoClicked()), 1);
                    event.setCancelled(true);
                    return;
                }

                // Makes sure moderators cannot remove item when being viewed
                event.setCancelled(true);
                event.getWhoClicked().sendMessage(ChatColor.GREEN + "[InventoryCloner] " + ChatColor.WHITE + "You can only view this inventory");
                return;
            }
        }
    }

    /**
     * This will update the moderator's inventory view when the player he is viewing
     * picks up an item.
     *
     * @param event is the PlayerPickupItem event
     */
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

    /**
     * This will update the moderator's inventory view when the player he is viewing
     * drops an item.
     *
     * @param event is the PlayerDropItem event
     */
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

    /**
     * This will update the moderator's inventory view when the player he is viewing
     * dies.
     *
     * @param event is the PlayerDeath event
     */
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

    /**
     * This will update the moderator's inventory view when the player he is viewing
     * places a block down on the ground.
     *
     * @param event is the PlaceBlock event
     */
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

    /**
     * This will update the moderator's inventory view when the player he is viewing
     * interacts with his inventory.
     *
     * @param event is the PlayerInteract event
     */
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
