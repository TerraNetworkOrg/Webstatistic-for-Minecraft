package com.nidefawl.Achievements.Commands;
import org.bukkit.entity.Player;

import com.nidefawl.Achievements.Achievements;

public class AchCommandRemovePermission {

	protected static boolean handleCommand(Achievements plugin, Player player, String[] s) {
		if (s.length < 2) {
			Achievements.LogError("Bad command (not enough arguments) correct is: removepermission <permissions-name>");
			return false;
		}
		else {
			Achievements.LogError("removepermission is only supported when GroupManager is installed - http://bit.ly/GroupManager");
			return true;
		}
	}


}
