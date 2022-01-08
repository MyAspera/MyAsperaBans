package pl.myaspera.bans.data;

import pl.myaspera.bans.BansPlugin;
import pl.myaspera.bans.object.Ban;

import java.util.HashMap;
import java.util.Map;

public final class BanData {

    private final BansPlugin plugin;
    private final Map<String, Ban> bans;

    public BanData(final BansPlugin plugin) {
        this.plugin = plugin;
        this.bans = new HashMap<>();
    }

    public Map<String, Ban> getBans() {
        return bans;
    }

    public void addBan(final Ban ban) {
        if(this.bans.containsKey(ban.getPlayerName()) || this.bans.containsValue(ban)) return;
        this.bans.put(ban.getPlayerName(), ban);
    }
    public void removeBan(final Ban ban) {
        this.bans.remove(ban.getPlayerName());
    }

    public Ban getBan(final String playerName){
        return this.bans.values().parallelStream().filter(user -> user.getPlayerName().equalsIgnoreCase(playerName)).findFirst().orElse(null);
    }

    public void unbanAll() {
        this.plugin.getDatabase().delete();
        this.bans.clear();
    }

}
