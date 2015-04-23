package io.github.mac_genius.inventorycloner;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * Created by Mac on 4/20/2015.
 */
public class StartListener implements Runnable {
    private Plugin plugin;
    private Player fetch;
    private Player push;

    public StartListener(Plugin pluginIn, Player fetchIn, Player pushIn) {
        plugin = pluginIn;
        fetch = fetchIn;
        push = pushIn;
    }

    @Override
    public void run() {
        plugin.getServer().getPluginManager().registerEvents(new ClickCheck(fetch, push, plugin), plugin);
    }
}
