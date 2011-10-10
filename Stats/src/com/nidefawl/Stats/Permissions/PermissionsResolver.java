package com.nidefawl.Stats.Permissions;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public interface PermissionsResolver {

	public abstract boolean load();

	public abstract boolean permission(CommandSender player, String permCmd);

	public abstract String getGroup(Player player);

	public abstract boolean inGroup(Player player, String group);

	public abstract void reloadPerms();

}