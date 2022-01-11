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

@Command("unbanall")
public class UnBanAllCommand extends CommandBase {

    public UnBanAllCommand(final BansPlugin plugin){
        plugin.getCommandManager().register(this);
        this.plugin = plugin;
    }
    private final BansPlugin plugin;

    @Default
    @Permission("mabans.unbanall")
    public void unbanCommand(final CommandSender sender) {
        String unbanAdmin = sender.getName();
        if(sender.getName().equalsIgnoreCase("console")) {
            unbanAdmin = "konsola";
        }
        this.plugin.getPluginData().unbanAll();
        ChatUtil.sendBroadcast(this.plugin.getMessagesConfiguration().unBanAllBroadcast.replace("%admin%", unbanAdmin));
    }
}
