package pl.myaspera.bans.data.database.mysql;

import com.zaxxer.hikari.HikariDataSource;
import pl.myaspera.bans.BansPlugin;
import pl.myaspera.bans.data.PluginConfiguration;
import pl.myaspera.bans.data.database.DataModel;
import pl.myaspera.bans.object.Ban;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Consumer;

public final class MySQLDataModel implements DataModel {

    private final HikariDataSource dataSource;
    private final BansPlugin plugin;

    public MySQLDataModel(final BansPlugin plugin) {
        this.plugin = plugin;
        PluginConfiguration pluginConfiguration = plugin.getPluginConfiguration();

        this.dataSource = new HikariDataSource();
        int poolSize = pluginConfiguration.mysqlPoolSize;
        if(poolSize == -1){
            poolSize = Runtime.getRuntime().availableProcessors() * 2 + 1;
        }

        this.dataSource.setMaximumPoolSize(poolSize);
        this.dataSource.setConnectionTimeout(pluginConfiguration.mysqlTimeout);
        this.dataSource.setJdbcUrl("jdbc:mysql://" + pluginConfiguration.mysqlHost + ":" + pluginConfiguration.mysqlPort + "/" + pluginConfiguration.mysqlDatabase + "?useSSL=" + pluginConfiguration.mysqlUseSSL);
        this.dataSource.setUsername(pluginConfiguration.mysqlUser);
        if (pluginConfiguration.mysqlPassword != null) {
            this.dataSource.setPassword(pluginConfiguration.mysqlPassword);
        }
        this.dataSource.addDataSourceProperty("cachePrepStmts", true);
        this.dataSource.addDataSourceProperty("prepStmtCacheSize", 250);
        this.dataSource.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
        this.dataSource.addDataSourceProperty("useServerPrepStmts", true);
        this.plugin.getLogger().info("Korzystam z bazy danych: MYSQL");
    }

    @Override
    public void load() {
        String table = "CREATE TABLE IF NOT EXISTS `" + this.plugin.getPluginConfiguration().mysqlTable + "` (" +
                "playerName varchar(50) NOT NULL," +
                "reason TEXT NOT NULL," +
                "duration BIGINT NOT NULL," +
                "date BIGINT NOT NULL," +
                "admin varchar(50) NOT NULL," +
                "PRIMARY KEY(playerName));";
        this.executeUpdate(table);

        this.executeQuery("SELECT * FROM `" + this.plugin.getPluginConfiguration().mysqlTable + "`", result -> {
            int i = 0;
            try {
                while (result.next()) {
                    this.plugin.getBanData().addBan(new Ban(result));
                    i++;
                }
                this.plugin.getLogger().info("Załadowano " + i + " banów!");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
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
    public void save(Ban ban) {
        String insert = "INSERT INTO `" + this.plugin.getPluginConfiguration().mysqlTable + "` VALUES(" +
                "'%playerName%'," +
                "'%reason%'," +
                "'%duration%'," +
                "'%date%'," +
                "'%admin%'" +
                ") ON DUPLICATE KEY UPDATE " +
                "reason='%reason%'," +
                "duration='%duration%'," +
                "date='%date%'," +
                "admin='%admin%';";
        insert = insert.replace("%playerName%", ban.getPlayerName());
        insert = insert.replace("%reason%", ban.getReason());
        insert = insert.replace("%duration%", Long.toString(ban.getLongBanDuration()));
        insert = insert.replace("%date%", Long.toString(ban.getLongBanDate()));
        insert = insert.replace("%admin%", ban.getBanAdmin());
        this.executeUpdate(insert);
        ban.setChanges(false);
    }

    @Override
    public void delete() {
        this.executeUpdate("DELETE FROM `" + this.plugin.getPluginConfiguration().mysqlTable + "`");
    }

    @Override
    public void delete(Ban ban) {
        this.executeUpdate("DELETE FROM `" + this.plugin.getPluginConfiguration().mysqlTable + "` WHERE playerName='" + ban.getPlayerName() + "'");
    }

    public void executeQuery(String query, Consumer<ResultSet> action) {
        try (Connection connection = this.dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet result = statement.executeQuery()) {
            action.accept(result);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public int executeUpdate(String query) {
        try (Connection connection = this.dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            if (statement == null) {
                return 0;
            }
            return statement.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    public boolean isConnect() {
        try {
            if (this.dataSource != null && this.dataSource.getConnection() != null) {
                return true;
            }
        } catch (Exception ex) {
            return false;
        }
        return false;
    }

    @Override
    public void shutdown() {
        this.save();
        if(this.isConnect()) {
            this.dataSource.close();
        }
    }
}
