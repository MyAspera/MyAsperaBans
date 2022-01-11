package pl.myaspera.bans.data.database;

import pl.myaspera.bans.object.Ban;
import pl.myaspera.bans.object.Mute;

public interface DataModel {

    void load();
    void save();
    void save(final Ban ban);
    void save(final Mute mute);
    void deleteAllBans();
    void deleteAllMutes();
    void delete(final Ban ban);
    void delete(final Mute mute);
    void shutdown();
}
