package pl.myaspera.bans.task;

import org.bukkit.Bukkit;
import pl.myaspera.bans.BansPlugin;

public final class AutosaveTask implements Runnable {

    public AutosaveTask(final BansPlugin plugin) {
        this.plugin = plugin;
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, this, this.plugin.getPluginConfiguration().autoSaveTicks, this.plugin.getPluginConfiguration().autoSaveTicks);
    }
    private final BansPlugin plugin;

    @Override
    public void run() {
        this.plugin.getLogger().info("Automatyczny zapis banów...");
        this.plugin.getDatabase().save();
    }
}
