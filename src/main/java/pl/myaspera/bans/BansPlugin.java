package pl.myaspera.bans;

import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.validator.okaeri.OkaeriValidator;
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import me.mattstudios.mf.base.CommandManager;
import org.bukkit.plugin.java.JavaPlugin;
import pl.myaspera.bans.command.*;
import pl.myaspera.bans.data.MessagesConfiguration;
import pl.myaspera.bans.data.PluginConfiguration;
import pl.myaspera.bans.data.PluginData;
import pl.myaspera.bans.data.database.DataModel;
import pl.myaspera.bans.data.database.flat.FlatDataModel;
import pl.myaspera.bans.data.database.mysql.MySQLDataModel;
import pl.myaspera.bans.listener.PlayerChat;
import pl.myaspera.bans.listener.PlayerJoin;
import pl.myaspera.bans.listener.PlayerPreLogin;
import pl.myaspera.bans.task.AutosaveTask;
import pl.myaspera.bans.util.ChatUtil;
import pl.myaspera.bans.util.UpdatePlugin;

import java.io.File;
import java.util.ArrayList;

public class BansPlugin extends JavaPlugin {

    private static BansPlugin plugin;
    private boolean newPluginUpdate = false;
    private CommandManager commandManager;

    private PluginConfiguration pluginConfiguration;
    private MessagesConfiguration messagesConfiguration;

    private PluginData pluginData;

    private DataModel dataModel;

    @Override
    public void onDisable() {
        this.dataModel.shutdown();
    }

    @Override
    public void onEnable() {
        plugin = this;
        this.checkPluginUpdate();
        this.loadConfiguration();
        this.registerCommands();
        this.registerListeners();

        this.pluginData = new PluginData(this);
        this.dataModel = (this.pluginConfiguration.databaseType.equalsIgnoreCase("mysql") ? new MySQLDataModel(this) : new FlatDataModel(this));
        this.dataModel.load();

        new AutosaveTask(this);
    }

    private void loadConfiguration() {
        if(!this.getDataFolder().exists()) this.getDataFolder().mkdir();
        this.pluginConfiguration = ConfigManager.create(PluginConfiguration.class, (it) -> {
            it.withConfigurer(new OkaeriValidator(new YamlBukkitConfigurer(), true));
            it.withBindFile(new File(this.getDataFolder(), "config.yml"));
            it.saveDefaults();
            it.load(true);
        });
        this.messagesConfiguration = ConfigManager.create(MessagesConfiguration.class, (it) -> {
            it.withConfigurer(new OkaeriValidator(new YamlBukkitConfigurer(), true));
            it.withBindFile(new File(this.getDataFolder(), "messages.yml"));
            it.saveDefaults();
            it.load(true);
        });
    }

    private void registerCommands() {
        this.commandManager = new CommandManager(this);
        this.commandManager.getMessageHandler().register("cmd.no.exists", sender -> ChatUtil.sendMessage(sender, "&cTaka komenda nie istnieje!"));
        this.commandManager.getMessageHandler().register("cmd.no.permission", sender -> ChatUtil.sendMessage(sender, "&cNie posiadasz uprawnień do tej komendy!"));
        this.commandManager.getCompletionHandler().register("#bannedplayers", input -> new ArrayList<>(this.pluginData.getBans().keySet()));
        this.commandManager.getCompletionHandler().register("#mutedplayers", input -> new ArrayList<>(this.pluginData.getMutes().keySet()));

        new BanCommand(this);
        new BanInfoCommand(this);
        new TempBanCommand(this);
        new UnBanAllCommand(this);
        new UnBanCommand(this);
        new MuteCommand(this);
        new MuteInfoCommand(this);
        new TempMuteCommand(this);
        new UnMuteAllCommand(this);
        new UnMuteCommand(this);
    }

    private void registerListeners() {
        new PlayerChat(this);
        new PlayerJoin(this);
        new PlayerPreLogin(this);
    }

    private void checkPluginUpdate() {
        this.getLogger().info("Sprawdzam czy jest nowa wersja pluginu...");
        new UpdatePlugin(this).getVersion(version -> {
            if (this.getDescription().getVersion().equalsIgnoreCase(version)) {
                this.getLogger().info("Korzystasz z aktualnej wersji!");
            } else {
                this.newPluginUpdate = true;
                this.getLogger().info("Nowa wersja jest już dostępna do pobrania:");
                this.getLogger().info("https://github.com/MyAspera/MyAsperaBans/releases");
            }
        });
    }

    public static BansPlugin getPlugin() {
        return plugin;
    }

    public boolean isNewPluginUpdate() {
        return this.newPluginUpdate;
    }

    public CommandManager getCommandManager() {
        return this.commandManager;
    }

    public PluginConfiguration getPluginConfiguration() {
        return this.pluginConfiguration;
    }

    public MessagesConfiguration getMessagesConfiguration() {
        return this.messagesConfiguration;
    }

    public PluginData getPluginData() {
        return this.pluginData;
    }

    public DataModel getDatabase() {
        return this.dataModel;
    }
}
