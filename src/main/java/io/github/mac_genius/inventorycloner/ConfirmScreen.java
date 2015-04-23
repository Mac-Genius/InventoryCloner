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
 * Created by Mac on 4/20/2015.
 */
public class ConfirmScreen implements Runnable {
    private Player player;
    private  Player playerToClone;
    public ConfirmScreen(Player playerIn, Player playerToCloneIn) {
        player = playerIn;
        playerToClone = playerToCloneIn;
    }

    @Override
    public void run() {
        Inventory confirm = Bukkit.createInventory(player, 9, ChatColor.RED + "Are you sure?");
        ItemStack yes = new ItemStack(Material.WOOL, 1, (short) 5);
        ItemMeta yesData = yes.getItemMeta();
        yesData.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Clone " + playerToClone.getName());
        ArrayList<String> yesLore = new ArrayList<>();
        yesLore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "THIS IS IRREVERSIBLE. ");
        yesLore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "YOU CANNOT GET YOUR INVENTORY BACK");
        yesData.setLore(yesLore);
        yes.setItemMeta(yesData);
        confirm.setItem(3, yes);
        ItemStack no = new ItemStack(Material.WOOL, 1, (short) 14);
        ItemMeta noData = yes.getItemMeta();
        noData.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Nevermind");
        ArrayList<String> noLore = new ArrayList<>();
        noLore.add(ChatColor.GRAY + "" + ChatColor.ITALIC + "Cancels cloning");
        noData.setLore(noLore);
        no.setItemMeta(noData);
        confirm.setItem(5, no);
        player.openInventory(confirm);
    }
}
