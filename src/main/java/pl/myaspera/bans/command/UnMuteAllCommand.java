package pl.myaspera.bans.command;

import me.mattstudios.mf.annotations.Command;
import me.mattstudios.mf.annotations.Default;
import me.mattstudios.mf.annotations.Permission;
import me.mattstudios.mf.base.CommandBase;
import org.bukkit.command.CommandSender;
import pl.myaspera.bans.BansPlugin;
import pl.myaspera.bans.util.ChatUtil;

@Command("unmuteall")
public class UnMuteAllCommand extends CommandBase {

    public UnMuteAllCommand(final BansPlugin plugin){
        plugin.getCommandManager().register(this);
        this.plugin = plugin;
    }
    private final BansPlugin plugin;

    @Default
    @Permission("mabans.unmuteall")
    public void unmuteCommand(final CommandSender sender) {
        String admin = sender.getName();
        if(sender.getName().equalsIgnoreCase("console")) {
            admin = "konsola";
        }
        this.plugin.getPluginData().unmuteAll();
        ChatUtil.sendBroadcast(this.plugin.getMessagesConfiguration().unMuteAllBroadcast.replace("%admin%", admin));
    }
}
