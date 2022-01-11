package pl.myaspera.bans.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public final class ChatUtil {

    public static String fixColor(String s){
        return ChatColor.translateAlternateColorCodes('&', s.replace("%>", "\u00BB").replace("<%", "\u00AB").replace("*", "\u2022"));
    }

    public static boolean sendMessage(CommandSender sender, String message){
        sender.sendMessage(fixColor(message));
        return true;
    }

    public static boolean sendBroadcast(String message, String permission){
        Bukkit.getOnlinePlayers().stream().filter(player -> player.hasPermission(permission)).forEach(player -> sendMessage(player, message));
        return true;
    }

    public static boolean sendBroadcast(String message){
        Bukkit.broadcastMessage(fixColor(message));
        return true;
    }
}
