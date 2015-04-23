package io.github.mac_genius.inventorycloner;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * Activates the ClickCheck listener
 *
 * @author John Harrison
 */
public class StartListener implements Runnable {
    private Plugin plugin;
    private Player fetch;
    private Player push;

    /**
     * The constructor. Gets the main instance of the plugin, gets the
     * player's inventory to fetch, and the moderator's inventory to push
     * it to.
     *
     * @param pluginIn is the main instance of the plugin
     * @param fetchIn is the player to clone
     * @param pushIn is the moderator to clone to
     */
    public StartListener(Plugin pluginIn, Player fetchIn, Player pushIn) {
        plugin = pluginIn;
        fetch = fetchIn;
        push = pushIn;
    }

    /**
     * Starts the ClickCheck listener.
     */
    @Override
    public void run() {
        plugin.getServer().getPluginManager().registerEvents(new ClickCheck(fetch, push, plugin), plugin);
    }
}
