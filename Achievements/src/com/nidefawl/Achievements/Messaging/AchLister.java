package com.nidefawl.Achievements.Messaging;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import com.nidefawl.Achievements.AchievementListData;
import com.nidefawl.Achievements.Achievements;
import com.nidefawl.Achievements.PlayerAchievement;

public class AchLister {

	public static void sendAchievementMessage(Achievements plugin, Player p, AchievementListData achListData) {
		String achName = achListData.getName();
		String shortenedName = achName.length() > 26 ? achName.substring(0, 23) + "..." : achName;
		String achDesc = achListData.getDescription();
		String shortenedDesc = achDesc.length() > 26 ? achDesc.substring(0, 23) + "..." : achDesc;
		String category = achListData.getCategory();
		String key = achListData.getKey();
		String value = String.valueOf(achListData.getValue());
		String commands = achListData.commands.toString();
		String conditions = achListData.conditions.toString();
		String maxawards = String.valueOf(achListData.getMaxawards());
		String broadcastMessage = AchMessaging.argument(plugin.formatAchNotifyBroadcast, new String[] { "+playername", "+achname", "+shortenedachname", "+description", "+shorteneddescription", "+category", "+key", "+value", "+reward", "+condition", "+maxawards" }, new String[] { p.getName(), plugin.color + achName,
				plugin.color + shortenedName, achDesc, shortenedDesc, category, key, value, commands, conditions, maxawards });
		AchMessaging.broadcast(plugin.getServer(), broadcastMessage);

		AchMessaging.send(
				p,
				AchMessaging.argument(plugin.formatAchNotifyPlayer, new String[] { "+playername", "+achname", "+shortenedachname", "+description", "+shorteneddescription", "+category", "+key", "+value", "+reward", "+condition", "+maxawards" }, new String[] { p.getName(), plugin.obtainedColor + achName,
						plugin.obtainedColor + shortenedName, achDesc, shortenedDesc, category, key, value, commands, conditions, maxawards }));

	}

	public static void SendAchInfo(Achievements plugin, Player player, int index, List<String> format, boolean SendDisabled, boolean PlayerAchs) {
		AchievementListData achListData = null;
		if (PlayerAchs) {
			if (plugin.playerAchievements.get(player.getName()) == null || plugin.playerAchievements.get(player.getName()).size() == 0)
				return;
			if (index < 0 || index > plugin.playerAchievements.get(player.getName()).size())
				return;
			String name = plugin.playerAchievements.get(player.getName()).getName(index);
			if (name == null)
				return;
			achListData = plugin.achievementList.get(name);
			index = plugin.getIndexOfAch(name);
			if (index == -1)
				return;
		} else {
			achListData = (AchievementListData) (plugin.achievementList.values().toArray())[index];
		}

		if (index < 0 || index > plugin.achievementList.size()) {
			return;
		}
		String achName = achListData.getName();
		String pre = plugin.color;
		if (!achListData.isEnabled()) {
			if (!SendDisabled)
				return;
			pre = ChatColor.GRAY.toString();
		}
		PlayerAchievement pa = plugin.playerAchievements.get(player.getName());
		if (pa != null && pa.get(achName) != null) {
			pre = plugin.obtainedColor;
		}
		String shortenedAchName = achName.length() > 26 ? achName.substring(0, 23) + "..." : achName;
		String achDesc = achListData.getDescription();
		String shortenedDesc = achDesc.length() > 26 ? achDesc.substring(0, 23) + "..." : achDesc;
		String category = achListData.getCategory();
		String key = achListData.getKey();
		String value = String.valueOf(achListData.getValue());
		String commands = achListData.commands.toString();
		String conditions = achListData.conditions.toString();
		String maxawards = String.valueOf(achListData.getMaxawards());
		for (String line : format) {
			if (line.isEmpty())
				continue;
			AchMessaging.send(
					player,
					AchMessaging.argument(line, new String[] { "+id", "+achname", "+shortenedachname", "+description", "+shorteneddescription", "+category", "+key", "+value", "+reward", "+condition", "+maxawards" }, new String[] { "#" + String.valueOf(index + 1), pre + achName, pre + shortenedAchName, achDesc,
							shortenedDesc, category, key, value, commands, conditions, maxawards }));

		}
	}

	public static void SendAchList(Achievements plugin, Player player, String[] args) {
		int page = 0;
		if (args.length >= 1) {
			if (args[0].charAt(0) == '#') {
				SendAchDetail(plugin, player, args);
				return;
			}
			try {
				page = Integer.parseInt(args[0]);
				if (page >= 1)
					page--;
			} catch (NumberFormatException ex) {
				page = 0;
			}
		}
		int nrPages = (int) Math.ceil((double) plugin.achievementList.size() / plugin.achsPerPage);
		if (page >= nrPages - 1)
			page = nrPages - 1;
		if (page < 0)
			page = 0;
		AchMessaging.send(player, "------------------------------------------------");
		AchMessaging.send(player, "&e Achievements &f(&ePage &f" + (page + 1) + "&e of&f " + nrPages + "&e, &f" + plugin.achievementList.size() + "&e entries&f) /listach <page|#id>");
		AchMessaging.send(player, "------------------------------------------------");

		for (int i = page * plugin.achsPerPage; i < (page + 1) * plugin.achsPerPage && i < plugin.achievementList.size(); i++) {
			AchLister.SendAchInfo(plugin, player, i, plugin.formatAchList, true, false);
		}
		if (plugin.achievementList.size() == 0) {
			AchMessaging.send(player, "&6 no active achievements");
		}
		AchMessaging.send(player, "------------------------------------------------");
	}

	public static void SendDoneAchList(Achievements plugin, Player player, String[] args) {
		if (plugin.playerAchievements.get(player.getName()) == null)
			return;
		int page = 0;
		if (args.length >= 1) {
			if (args[0].charAt(0) == '#') {
				SendAchDetail(plugin, player, args);
				return;
			}
			try {
				page = Integer.parseInt(args[0]);
				if (page >= 1)
					page--;
			} catch (NumberFormatException ex) {
				page = 0;
			}
		}
		int nrPages = (int) Math.ceil((double) plugin.playerAchievements.get(player.getName()).size() / plugin.achsPerPage);
		if (page >= nrPages - 1)
			page = nrPages - 1;
		if (page < 0)
			page = 0;
		AchMessaging.send(player, "------------------------------------------------");
		AchMessaging.send(player, "&e Your Achievements &f(&ePage &f" + (page + 1) + "&e of&f " + nrPages + "&e, &f" + plugin.playerAchievements.get(player.getName()).size() + "&e entries&f) /ach <page|#id>");
		AchMessaging.send(player, "------------------------------------------------");

		for (int i = page * plugin.achsPerPage; i < (page + 1) * plugin.achsPerPage && i < plugin.playerAchievements.get(player.getName()).size(); i++) {
			AchLister.SendAchInfo(plugin, player, i, plugin.formatAchList, true, true);
		}
		if (plugin.playerAchievements.get(player.getName()).size() == 0) {
			AchMessaging.send(player, "&6 You don't have any achievements!");
		}
		AchMessaging.send(player, "------------------------------------------------");
	}

	private static void SendAchDetail(Achievements plugin, Player player, String[] args) {
		int argsId = 0;
		if (args.length >= 1) {
			try {
				argsId = Integer.parseInt(args[0].substring(1));
				if (argsId >= 1)
					argsId--;
			} catch (NumberFormatException ex) {
				argsId = 0;
			}
		}
		if (argsId + 1 > plugin.achievementList.size())
			argsId = plugin.achievementList.size() - 1;
		AchLister.SendAchInfo(plugin, player, argsId, plugin.formatAchDetail, true, false);
		return;
	}
}
