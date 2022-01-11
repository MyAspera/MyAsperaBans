package pl.myaspera.bans.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import pl.myaspera.bans.BansPlugin;
import pl.myaspera.bans.data.MessagesConfiguration;
import pl.myaspera.bans.object.Ban;
import pl.myaspera.bans.util.ChatUtil;

public final class PlayerPreLogin implements Listener {

    public PlayerPreLogin(final BansPlugin plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    private final BansPlugin plugin;

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerLogin(AsyncPlayerPreLoginEvent e){
        String name = e.getName();
        Ban ban = this.plugin.getPluginData().getBan(name);
        if(ban !=null) {
            MessagesConfiguration messageConfiguration = this.plugin.getMessagesConfiguration();
            if(ban.isPerm()) {
                e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, ChatUtil.fixColor(ban.replaceBan()));
                ChatUtil.sendBroadcast(messageConfiguration.bannedPlayerLogin.replace("%player%", ban.getPlayerName()), "mabans.bannedtryjoin");
            } else {
                if(!ban.isExpire()) {
                    e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, ChatUtil.fixColor(ban.replaceBan()));
                    ChatUtil.sendBroadcast(messageConfiguration.bannedPlayerLogin.replace("%player%", ban.getPlayerName()), "mabans.bannedtryjoin");
                } else {
                    this.plugin.getDatabase().delete(ban);
                    this.plugin.getPluginData().removeBan(ban);
                    ChatUtil.sendBroadcast(messageConfiguration.bannedPlayerLoginExpire.replace("%player%", ban.getPlayerName()), "mabans.bannedtryjoin");
                }
            }
        }
    }
}
