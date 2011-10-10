package com.nidefawl.Achievements.Commands;

import com.nijikokun.register.payment.Method.MethodAccount;
import com.nijikokun.register.payment.Methods;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.nidefawl.Achievements.Achievements;
import com.nidefawl.Achievements.Messaging.AchMessaging;

public class AchCommandMoney {

public static boolean handleCommand(Achievements plugin, Player player, String[] s) {

if (s.length < 2) {
Achievements.LogError("Bad command (not enough arguments) correct is: money amount");
return false;
}
double amount = 0;
try {
amount = Double.parseDouble(s[1]);
} catch (NumberFormatException ex2) {
try {
amount = Integer.parseInt(s[1]);
} catch (NumberFormatException ex) {
Achievements.LogError("Bad command '" + s[0] + " " + s[1] + " " + s[2] + "'(amount is not a number) correct is: money amount");
return false;
}
}

if(checkRegister(plugin) && Methods.hasMethod() ){
String currency = "Coins";
MethodAccount acc = Methods.getMethod().getAccount(player.getName());
if(acc == null) {
Achievements.LogError("Player '"+player.getName()+"' does not have an account");
return false;
}
acc.add(amount);
if (amount > 0)
AchMessaging.send(player, plugin.color + "Reward: &f" + amount + " &2" + currency);
else
AchMessaging.send(player, plugin.color + "Penalty: &f" + amount + " &2" + currency);
return true;
}
else {
Achievements.LogError("Didn't find the Register plugin... no money for you!");
return false;
}


}

private static boolean checkRegister(Achievements plugin){
Plugin registerTemp = plugin.getServer().getPluginManager().getPlugin("Register");

if (registerTemp != null && registerTemp.getClass().getName().equals("com.nijikokun.register.Register") && registerTemp.isEnabled() && Methods.hasMethod()) return true;
return false;
}
}