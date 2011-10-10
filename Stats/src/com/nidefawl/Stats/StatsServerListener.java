package com.nidefawl.Stats;

import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.event.server.ServerListener;
import org.bukkit.plugin.Plugin;

import com.nidefawl.Stats.Permissions.NijiPermissionsResolver;
import com.nidefawl.Stats.Permissions.defaultResolver;

public class StatsServerListener extends ServerListener {
	Stats stats = null;
	public StatsServerListener(Stats plugin) {
		this.stats = plugin;
	}
    @Override
    public void onPluginEnable(PluginEnableEvent event) {
        Plugin plugin = event.getPlugin();
        String name = plugin.getDescription().getName();
        if (name.equals("Permissions")) {
        	stats.setPerms(new NijiPermissionsResolver(plugin));
        }
    }

    /**
     * Called when a plugin is disabled
     *
     * @param event Relevant event details
     */
    @Override
    public void onPluginDisable(PluginDisableEvent event) {
        Plugin plugin = event.getPlugin();
        String name = plugin.getDescription().getName();
        if (name.equals("Permissions")) {
        	stats.setPerms(new defaultResolver());
        }
    }

}
