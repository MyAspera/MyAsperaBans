package pl.myaspera.bans.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.myaspera.bans.BansPlugin;
import pl.myaspera.bans.util.ChatUtil;

public class PlayerJoin implements Listener {

    public PlayerJoin(final BansPlugin plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    private final BansPlugin plugin;

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(final PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if(player.hasPermission("mabans.notifyupdate")) {
            if(this.plugin.isNewPluginUpdate()) {
                ChatUtil.sendMessage(player, "&3&lMABans &8&l%> &cNowa wersja pluginu jest już dostępna do pobrania:");
                ChatUtil.sendMessage(player, "&ahttps://github.com/MyAspera/MyAsperaBans/releases");
            }
        }
    }
}
