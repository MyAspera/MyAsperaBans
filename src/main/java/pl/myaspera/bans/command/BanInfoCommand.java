package pl.myaspera.bans.command;

import me.mattstudios.mf.annotations.Command;
import me.mattstudios.mf.annotations.Completion;
import me.mattstudios.mf.annotations.Default;
import me.mattstudios.mf.annotations.Permission;
import me.mattstudios.mf.base.CommandBase;
import org.bukkit.command.CommandSender;
import pl.myaspera.bans.BansPlugin;
import pl.myaspera.bans.object.Ban;
import pl.myaspera.bans.util.ChatUtil;

@Command("baninfo")
public class BanInfoCommand extends CommandBase {

    public BanInfoCommand(final BansPlugin plugin){
        plugin.getCommandManager().register(this);
        this.plugin = plugin;
    }
    private final BansPlugin plugin;

    @Default
    @Permission("mabans.baninfo")
    @Completion("#bannedplayers")
    public void banInfoCommand(final CommandSender sender, final String[] args) {
        if(args.length < 1) {
            ChatUtil.sendMessage(sender, "&7Prawidłowe użycie komendy: &3/baninfo <gracz>");
            return;
        }
        Ban ban = this.plugin.getPluginData().getBan(args[0]);
        if(ban == null) {
            ChatUtil.sendMessage(sender, this.plugin.getMessagesConfiguration().playerNotBanned);
            return;
        }

        ChatUtil.sendMessage(sender, this.plugin.getMessagesConfiguration().banInfo
                .replace("%player%", ban.getPlayerName())
                .replace("%banDate%", ban.getBanDate())
                .replace("%banAdmin%", ban.getBanAdmin())
                .replace("%reason%", ban.getReason())
                .replace("%expire%", ban.isPerm() ? "nigdy" : ban.getBanDuration()));
    }
}
