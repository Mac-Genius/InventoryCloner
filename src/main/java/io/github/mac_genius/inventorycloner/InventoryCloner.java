package io.github.mac_genius.inventorycloner;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * This is the main class for the plugin. It allows moderators to check
 * players' inventories and clone them to the moderators' inventories.
 *
 * @author John Harrison
 * @version 1.1
 */
public class InventoryCloner extends JavaPlugin {
    protected Plugin plugin = this;

    /**
     * This is called when the server is starting up.
     */
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(new EventListener(this), this);
        this.getCommand("ic").setExecutor(new Commands(plugin));
        getLogger().info("Plugin enabled!");
    }

    /**
     * This is called when the plugin server is restarting
     * or shutting down.
     */
    public void onDisable() {
        getLogger().info("Plugin disabled!");
    }
}
