package pl.myaspera.bans.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import pl.myaspera.bans.BansPlugin;
import pl.myaspera.bans.object.Mute;
import pl.myaspera.bans.util.ChatUtil;

public class PlayerChat implements Listener {

    public PlayerChat(final BansPlugin plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    private final BansPlugin plugin;

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerChat(final AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if(event.isCancelled()) return;
        if(!player.hasPermission("mabans.mute.bypass")) {
            Mute mute = this.plugin.getPluginData().getMute(player.getName());
            if(mute != null) {
                if(mute.isPerm()) {
                    event.setCancelled(true);
                    ChatUtil.sendMessage(player, mute.replaceMute());
                    ChatUtil.sendBroadcast(this.plugin.getMessagesConfiguration().mutedPlayerTryChat.replace("%player%", mute.getPlayerName()), "mabans.mutetrychat");
                } else {
                    if(!mute.isExpire()) {
                        event.setCancelled(true);
                        ChatUtil.sendMessage(player, mute.replaceMute());
                        ChatUtil.sendBroadcast(this.plugin.getMessagesConfiguration().mutedPlayerTryChat.replace("%player%", mute.getPlayerName()), "mabans.mutetrychat");
                    } else {
                        this.plugin.getDatabase().delete(mute);
                        this.plugin.getPluginData().removeMute(mute);
                        ChatUtil.sendBroadcast(this.plugin.getMessagesConfiguration().mutedPlayerTryChatExpire.replace("%player%", mute.getPlayerName()), "mabans.mutetrychat");
                    }

                }
            }
        }
    }
}
