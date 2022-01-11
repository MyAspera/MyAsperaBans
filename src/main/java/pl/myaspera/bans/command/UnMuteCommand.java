package pl.myaspera.bans.command;

import me.mattstudios.mf.annotations.Command;
import me.mattstudios.mf.annotations.Completion;
import me.mattstudios.mf.annotations.Default;
import me.mattstudios.mf.annotations.Permission;
import me.mattstudios.mf.base.CommandBase;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.myaspera.bans.BansPlugin;
import pl.myaspera.bans.object.Mute;
import pl.myaspera.bans.util.ChatUtil;

@Command("unmute")
public class UnMuteCommand extends CommandBase {

    public UnMuteCommand(final BansPlugin plugin){
        plugin.getCommandManager().register(this);
        this.plugin = plugin;
    }
    private final BansPlugin plugin;

    @Default
    @Permission("mabans.unmute")
    @Completion("#mutedplayers")
    public void unmuteCommand(final CommandSender sender, final String[] args) {
        if(args.length < 1) {
            ChatUtil.sendMessage(sender, "&7Prawidłowe użycie komendy: &3/unmute <gracz>");
            return;
        }
        if(this.plugin.getPluginData().getMute(args[0]) == null) {
            ChatUtil.sendMessage(sender, this.plugin.getMessagesConfiguration().playerNotMuted);
            return;
        }
        String admin = sender.getName();
        if(sender.getName().equalsIgnoreCase("console")) {
            admin = "konsola";
        }
        Mute mute = this.plugin.getPluginData().getMute(args[0]);
        ChatUtil.sendBroadcast(this.plugin.getMessagesConfiguration().unMuteBroadcast.replace("%player%", mute.getPlayerName()).replace("%admin%", admin));
        Player playerMuted = Bukkit.getPlayer(mute.getPlayerName());
        if(playerMuted != null) {
            ChatUtil.sendMessage(playerMuted, this.plugin.getMessagesConfiguration().unMute);
        }
        this.plugin.getDatabase().delete(mute);
        this.plugin.getPluginData().removeMute(mute);
    }
}
