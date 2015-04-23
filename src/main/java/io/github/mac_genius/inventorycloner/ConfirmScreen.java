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
 * This is the confirm screen inventory. It is called when
 * a moderator clicks on the anvil. Allows a player to
 * clone an inventory or go back to the player inventory.
 *
 * @author John Harrison
 */
public class ConfirmScreen implements Runnable {
    private Player player;
    private  Player playerToClone;

    /**
     * This sets up the class. It imports the moderator and the
     * person he is going to clone.
     *
     * @param playerIn is the moderator viewing the inventory
     * @param playerToCloneIn is the player that will be cloned
     */
    public ConfirmScreen(Player playerIn, Player playerToCloneIn) {
        player = playerIn;
        playerToClone = playerToCloneIn;
    }

    /**
     * This creates the confirm screen inventory
     */
    @Override
    public void run() {

        // A new inventory
        Inventory confirm = Bukkit.createInventory(player, 9, ChatColor.RED + "Are you sure?");

        // Green wool confirming the cloning
        ItemStack yes = new ItemStack(Material.WOOL, 1, (short) 5);
        ItemMeta yesData = yes.getItemMeta();
        yesData.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Clone " + playerToClone.getName());
        ArrayList<String> yesLore = new ArrayList<>();
        yesLore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "THIS IS IRREVERSIBLE. ");
        yesLore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "YOU CANNOT GET YOUR INVENTORY BACK");
        yesData.setLore(yesLore);
        yes.setItemMeta(yesData);
        confirm.setItem(3, yes);

        // Red wool cancelling the cloning
        ItemStack no = new ItemStack(Material.WOOL, 1, (short) 14);
        ItemMeta noData = yes.getItemMeta();
        noData.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Nevermind");
        ArrayList<String> noLore = new ArrayList<>();
        noLore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Cancels cloning");
        noData.setLore(noLore);
        no.setItemMeta(noData);
        confirm.setItem(5, no);

        // Sets the player's inventory to the confirm screen
        player.openInventory(confirm);
    }
}
