package com.nidefawl.Stats.udpates;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.nidefawl.Stats.Stats;
import com.nidefawl.Stats.StatsSettings;
import com.nidefawl.Stats.ItemResolver.hModItemResolver;
import com.nidefawl.Stats.ItemResolver.itemResolver;
import com.nidefawl.Stats.datasource.StatsSQLConnectionManager;

public class Update2 {
	public static void execute(Stats plugin) {
		FileWriter writer;
		itemResolver items = new hModItemResolver(new File(plugin.getDataFolder(),"items.txt"));
		try {
			writer = new FileWriter(new File(plugin.getDataFolder(),"items.txt"),true);
			if (writer != null) {
				try {
					if(items.getItem(26).equals("26")) {
						Stats.LogInfo("Adding bedblock (26) to items.txt");
						writer.write("bedblock:26\r\n");
						Stats.LogInfo("Updating stats in database");
						UpdateItemStatKey("26","bedblock");
					}
					if(items.getItem(93).equals("93")) {
						Stats.LogInfo("Adding repeateron (93) to items.txt");
						writer.write("repeateron:93\r\n");
						Stats.LogInfo("Updating stats in database");
						UpdateItemStatKey("93","repeateron");
					}
					if(items.getItem(94).equals("94")) {
						Stats.LogInfo("Adding repeateroff (94) to items.txt");
						writer.write("repeateroff:94\r\n");
						Stats.LogInfo("Updating stats in database");
						UpdateItemStatKey("94","repeateroff");
					}
					if(items.getItem(355).equals("355")) {
						Stats.LogInfo("Adding bed (355) to items.txt");
						writer.write("bed:355\r\n");
						Stats.LogInfo("Updating stats in database");
						UpdateItemStatKey("355","bed");
					}
					if(items.getItem(356).equals("356")) {
						Stats.LogInfo("Adding repeater (356) to items.txt");
						writer.write("repeater:356\r\n");
						Stats.LogInfo("Updating stats in database");
						UpdateItemStatKey("356","repeater");
					}
					writer.close();
					plugin.setItems( new hModItemResolver(new File(plugin.getDataFolder(),"items.txt")));
				} catch (IOException e) {
					Stats.LogError("Exception while updating "+plugin.getDataFolder().getPath()+"/items.txt "+ e);
					e.printStackTrace();
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	static void UpdateItemStatKey(String oldKey,String newKey) {

		int result = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = StatsSQLConnectionManager.getConnection(StatsSettings.useMySQL);
			ps = conn.prepareStatement("UPDATE " + StatsSettings.dbTable + " set stat = ? where stat = ? and (category = 'blockdestroy' or category = 'blockcreate' or category = 'itemuse' or category = 'itemdrop' or category = 'itempickup');");
			ps.setString(1, newKey);
			ps.setString(2, oldKey);
			result = ps.executeUpdate();
		} catch (SQLException ex) {
			Stats.LogError("SQL exception" + ex);
			ex.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException ex) {
				Stats.LogError("SQL exception on close"+ ex);
				ex.printStackTrace();
			}
		}
		Stats.LogInfo("Updated " + result + " stats.");
	}
}
