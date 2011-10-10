package com.nidefawl.Stats.Permissions;

import java.util.logging.Logger;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import com.nijikokun.bukkit.Permissions.Permissions;

public class NijiPermissionsResolver implements PermissionsResolver {
	public static final Logger log = Logger.getLogger("Minecraft");
	Plugin plugin = null;
	private Permissions perms = null;

	public NijiPermissionsResolver(Plugin plugin) {
		this.plugin = plugin;
		load();
	}

	@Override
	public boolean load() {
		if (perms == null) {
			Plugin checkPlugin = plugin.getServer().getPluginManager().getPlugin("Permissions");
			if (checkPlugin != null && checkPlugin.isEnabled() && checkPlugin instanceof Permissions) {
				perms = (Permissions) checkPlugin;
			} else {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean permission(CommandSender sender, String permCmd) {
		if(sender.isOp()) return true;
		if(!(sender instanceof Player)) return false;
		if (!load())
			return false;
		return perms.getHandler().permission((Player)sender, permCmd);
	}

	@Override
	public String getGroup(Player player) {
		if (!load())
			return "";
		return perms.getHandler().getGroup(player.getWorld().getName(), player.getName());
	}



	@Override
	public boolean inGroup(Player player, String group) {
		if (!load())
			return false;
		return perms.getHandler().inGroup(player.getWorld().getName(), player.getName(), group);
	}

	@Override
	public void reloadPerms() {
		if (!load())
			return;
		if(Permissions.version.toLowerCase().startsWith("3.")) {
			((Permissions)Permissions.instance).getHandler().reload();
		} else {
			perms.setupPermissions(null);
		}
	}

}
