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
import pl.myaspera.bans.object.Ban;
import pl.myaspera.bans.util.ChatUtil;
import pl.myaspera.bans.util.StringUtil;
import pl.myaspera.bans.util.TimeUtil;

import java.util.Arrays;
import java.util.stream.Collectors;

@Command("tempban")
public class TempBanCommand extends CommandBase {

    public TempBanCommand(final BansPlugin plugin){
        plugin.getCommandManager().register(this);
        this.plugin = plugin;
    }
    private final BansPlugin plugin;

    @Default
    @Permission("mabans.tempban")
    @Completion("#players")
    public void tempbanCommand(final CommandSender sender, final String[] args) {
        if(args.length < 2) {
            ChatUtil.sendMessage(sender, "&7Prawidłowe użycie komendy: &3/tempban <gracz> <czas np. 5min> <powód>");
            return;
        }
        if(this.plugin.getPluginData().getBan(args[0]) != null) {
            ChatUtil.sendMessage(sender, this.plugin.getMessagesConfiguration().playerAlreadyBanned);
            return;
        }
        String reason = this.plugin.getPluginConfiguration().noReasonBan;
        if(args.length >= 3) {
            reason = StringUtil.stringBuilder(args, 2);
        }
        String banAdmin = sender.getName();
        if(sender.getName().equalsIgnoreCase("console")) {
            banAdmin = "konsola";
        }
        long banDuration = TimeUtil.getTimeWithString(args[1]);
        Ban ban = new Ban(args[0], reason, banDuration, banAdmin);
        this.plugin.getPluginData().addBan(ban);
        this.plugin.getDatabase().save(ban);
        Player banned = Bukkit.getPlayer(args[0]);
        if(banned != null) {
            banned.kickPlayer(ChatUtil.fixColor(ban.replaceBan()));
        }
        ChatUtil.sendBroadcast(this.plugin.getMessagesConfiguration().tempBanBroadcast.replace("%player%", args[0]).replace("%admin%", banAdmin).replace("%data%", ban.getBanDuration()).replace("%reason%", reason));
    }
}
