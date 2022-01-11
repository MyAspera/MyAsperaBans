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
import pl.myaspera.bans.util.StringUtil;
import pl.myaspera.bans.util.TimeUtil;

@Command("tempmute")
public class TempMuteCommand extends CommandBase {

    public TempMuteCommand(final BansPlugin plugin){
        plugin.getCommandManager().register(this);
        this.plugin = plugin;
    }
    private final BansPlugin plugin;

    @Default
    @Permission("mabans.tempmute")
    @Completion("#players")
    public void tempMuteCommand(final CommandSender sender, final String[] args) {
        if(args.length < 2) {
            ChatUtil.sendMessage(sender, "&7Prawidłowe użycie komendy: &3/tempmute <gracz> <czas np. 5min> <powód>");
            return;
        }
        if(this.plugin.getPluginData().getMute(args[0]) != null) {
            ChatUtil.sendMessage(sender, this.plugin.getMessagesConfiguration().playerAlreadyMuted);
            return;
        }
        String reason = this.plugin.getPluginConfiguration().noReasonBan;
        if(args.length >= 3) {
            reason = StringUtil.stringBuilder(args, 2);
        }
        String admin = sender.getName();
        if(sender.getName().equalsIgnoreCase("console")) {
            admin = "konsola";
        }
        long muteDuration = TimeUtil.getTimeWithString(args[1]);
        Mute mute = new Mute(args[0], reason, muteDuration, admin);
        this.plugin.getPluginData().addMute(mute);
        this.plugin.getDatabase().save(mute);
        ChatUtil.sendBroadcast(this.plugin.getMessagesConfiguration().tempMuteBroadcast.replace("%player%", args[0]).replace("%admin%", admin).replace("%data%", mute.getMuteDuration()).replace("%reason%", reason));
        Player playerMuted = Bukkit.getPlayer(args[0]);
        if(playerMuted != null) {
            ChatUtil.sendMessage(playerMuted, mute.replaceMute());
        }
    }
}
