package io.github.mac_genius.inventorycloner;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

/**
 * This sets up the enderchest view of a player for the moderator.
 *
 * @author John Harrison
 */
public class EnderChestUpdater implements Runnable {
    private Player fetch;
    private Player push;

    /**
     * The constructor. Fetches the player to clone from and and clones it to
     * the moderator's inventory.
     *
     * @param fetchIn is the player to clone from
     * @param pushIn is the player to clone to
     */
    public EnderChestUpdater(Player fetchIn, Player pushIn) {
        fetch = fetchIn;
        push = pushIn;
    }

    /**
     * This creates a new inventory and copies the player's inventory into
     * it so the moderator can view it.
     */
    @Override
    public void run() {

        // New chest inventory
        Inventory inventory = fetch.getEnderChest();
        ItemStack[] newInventory = new ItemStack[45];

        // Filler for spaces that won't have an item in it
        ItemStack filler = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
        ItemMeta fillerData = filler.getItemMeta();
        fillerData.setDisplayName(ChatColor.RED + "");
        filler.setItemMeta(fillerData);
        for (int i = 0; i < inventory.getContents().length; i++) {
            newInventory[i] = inventory.getContents()[i];
        }
        Inventory playerInventory = Bukkit.createInventory(push, 45, fetch.getName());
        playerInventory.setContents(newInventory);
        for (int i = 27; i < 43; i++) {
            playerInventory.setItem(i, filler);
        }

        // Redstone block that allows a moderator to exit the inventory
        ItemStack exit = new ItemStack(Material.REDSTONE_BLOCK);
        ItemMeta exitData = exit.getItemMeta();
        exitData.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Close Inventory");
        ArrayList<String> exitLore = new ArrayList<>();
        exitLore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Closes " + fetch.getName() + "'s inventory view");
        exitData.setLore(exitLore);
        exit.setItemMeta(exitData);
        playerInventory.setItem(44, exit);

        // A chest that takes the moderator back to the player's original inventory
        ItemStack chest = new ItemStack(Material.CHEST);
        ItemMeta chestData = chest.getItemMeta();
        chestData.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "View " + fetch.getName() + "'s inventory");
        ArrayList<String> chestLore = new ArrayList<>();
        chestLore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Views " + fetch.getName() + "'s inventory");
        chestData.setLore(chestLore);
        chest.setItemMeta(chestData);
        playerInventory.setItem(43, chest);

        // Sets the moderator's inventory to the player's enderchest inventory
        push.openInventory(playerInventory);
    }
}
