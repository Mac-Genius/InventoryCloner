package io.github.mac_genius.inventorycloner;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Mac on 4/19/2015.
 */
public class InventoryCloner extends JavaPlugin {
    protected Plugin plugin = this;

    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(new EventListener(this), this);
        this.getCommand("ic").setExecutor(new Commands(plugin));
        getLogger().info("Plugin enabled!");
    }

    public void onDisable() {
        getLogger().info("Plugin disabled!");
    }
}
