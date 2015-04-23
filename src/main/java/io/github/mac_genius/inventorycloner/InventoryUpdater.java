package io.github.mac_genius.inventorycloner;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

/**
 * This copies the player's inventory to a new one so the
 * moderator can view it
 *
 * @author John Harrison
 */
public class InventoryUpdater implements Runnable {
    private Player fetch;
    private Player push;

    /**
     * The constructor gets the player to clone from and the player
     * to clone to.
     *
     * @param fetchIn is the player to clone from
     * @param pushIn is the player to clone to
     */
    public InventoryUpdater(Player fetchIn, Player pushIn) {
        fetch = fetchIn;
        push = pushIn;
    }

    /**
     * Sets up the inventory for the moderator to view
     */
    @Override
    public void run() {

        // Creates a new inventory
        PlayerInventory inventory = fetch.getInventory();
        ItemStack[] newInventory = new ItemStack[54];

        // Filler for the slots that will not filled up
        ItemStack filler = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
        ItemMeta fillerData = filler.getItemMeta();
        fillerData.setDisplayName(ChatColor.RED + "");
        filler.setItemMeta(fillerData);
        for (int i = 0; i < inventory.getContents().length; i++) {
            newInventory[i] = inventory.getContents()[i];
        }
        Inventory playerInventory = Bukkit.createInventory(push, 54, fetch.getName());
        playerInventory.setContents(newInventory);
        for (int i = 36; i < 45; i++) {
            playerInventory.setItem(i, filler);
        }
        for (int i = 49; i < 51; i++) {
            playerInventory.setItem(i, filler);
        }

        // The anvil to let moderators clone a player's inventory
        ItemStack clone = new ItemStack(Material.ANVIL);
        ItemMeta cloneData = clone.getItemMeta();
        cloneData.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Clone it!");
        ArrayList<String> cloneLore = new ArrayList<>();
        cloneLore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Clones " + fetch.getName() + "'s to your inventory!");
        cloneData.setLore(cloneLore);
        clone.setItemMeta(cloneData);
        playerInventory.setItem(52, clone);

        // The redstone block to let a moderator close the inventory
        ItemStack exit = new ItemStack(Material.REDSTONE_BLOCK);
        ItemMeta exitData = exit.getItemMeta();
        exitData.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Close Inventory");
        ArrayList<String> exitLore = new ArrayList<>();
        exitLore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Closes " + fetch.getName() + "'s inventory view");
        exitData.setLore(exitLore);
        exit.setItemMeta(exitData);
        playerInventory.setItem(53, exit);

        // The enderchest to allow a moderator to view a player's enderchest inventory
        ItemStack enderchest = new ItemStack(Material.ENDER_CHEST);
        ItemMeta enderchestData = enderchest.getItemMeta();
        enderchestData.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "View " + fetch.getName() + "'s Enderchest inventory");
        ArrayList<String> enderchestLore = new ArrayList<>();
        enderchestLore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Views " + fetch.getName() + "'s EnderChest inventory");
        enderchestData.setLore(enderchestLore);
        enderchest.setItemMeta(enderchestData);
        playerInventory.setItem(51, enderchest);

        // Get's the armor from the player
        playerInventory.setItem(45, inventory.getHelmet());
        playerInventory.setItem(46, inventory.getChestplate());
        playerInventory.setItem(47, inventory.getLeggings());
        playerInventory.setItem(48, inventory.getBoots());

        // Allows the moderator to view the player's inventory
        push.openInventory(playerInventory);
    }
}
