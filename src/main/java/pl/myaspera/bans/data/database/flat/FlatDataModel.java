package pl.myaspera.bans.data.database.flat;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.myaspera.bans.BansPlugin;
import pl.myaspera.bans.data.database.DataModel;
import pl.myaspera.bans.object.Ban;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class FlatDataModel implements DataModel {

    private final File databaseFile;
    private final YamlConfiguration databaseYaml;
    private final BansPlugin plugin;

    public FlatDataModel(final BansPlugin plugin) {
        this.plugin = plugin;
        this.databaseFile = new File(plugin.getDataFolder(), "database.yml");
        try {
            if(!this.databaseFile.exists()) this.databaseFile.createNewFile();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        this.databaseYaml = YamlConfiguration.loadConfiguration(this.databaseFile);
        this.plugin.getLogger().info("Korzystam z bazy danych: FLAT");
    }

    @Override
    public void load() {
        ConfigurationSection cs1 = this.databaseYaml.getConfigurationSection("bans");
        if(cs1 == null) {
            this.plugin.getLogger().log(Level.INFO, "Brak banów do załadowania!");
            return;
        }
        int i = 0;
        for(String string : cs1.getKeys(false)) {
            ConfigurationSection cs = cs1.getConfigurationSection(string);
            this.plugin.getBanData().addBan(new Ban(string, cs));
            i++;
        }
        this.plugin.getLogger().info("Załadowano " + i + " banów!");
    }

    @Override
    public void save() {
        int i = 0;
        for(Ban ban : this.plugin.getBanData().getBans().values()) {
            if (ban.isExpire()) {
                this.delete(ban);
                this.plugin.getLogger().info("Ban gracza " + ban.getPlayerName() + " wygasł i zostanie usunięty!");
                continue;
            }
            if(!ban.isChanges()) continue;
            this.save(ban);
            i++;
        }
        this.plugin.getLogger().info("Zapisano " + i + " banów!");
    }

    @Override
    public void save(final Ban ban) {
        this.databaseYaml.set("bans." + ban.getPlayerName() + ".reason", ban.getReason());
        this.databaseYaml.set("bans." + ban.getPlayerName() + ".banDuration", ban.getLongBanDuration());
        this.databaseYaml.set("bans." + ban.getPlayerName() + ".banDate", ban.getLongBanDate());
        this.databaseYaml.set("bans." + ban.getPlayerName() + ".banAdmin", ban.getBanAdmin());
        try {
            this.databaseYaml.save(this.databaseFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        ban.setChanges(false);
    }

    @Override
    public void delete() {
        this.databaseYaml.set("bans", null);
        try {
            this.databaseYaml.save(this.databaseFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(final Ban ban) {
        this.databaseYaml.set("bans." + ban.getPlayerName(), null);
        try {
            this.databaseYaml.save(this.databaseFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void shutdown() {
        this.save();
    }
}
