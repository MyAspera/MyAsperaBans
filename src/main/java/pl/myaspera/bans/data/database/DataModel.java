package pl.myaspera.bans.data.database;

import pl.myaspera.bans.object.Ban;

public interface DataModel {

    void load();
    void save();
    void save(final Ban ban);
    void delete();
    void delete(final Ban ban);
    void shutdown();
}
