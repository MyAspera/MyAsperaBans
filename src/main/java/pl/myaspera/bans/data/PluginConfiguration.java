package pl.myaspera.bans.data;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.Exclude;
import eu.okaeri.configs.annotation.Header;
import eu.okaeri.configs.exception.OkaeriException;

@Header("Konfiguracja pluginu MyAsperaBans")
@Header("Github: https://github.com/MyAspera/MyAsperaBans")
@Header(" ")
@Header("Uprawnienia ogólne:")
@Header("mabans.notifyupdate - Informacja o nowej wersji pluginu po wejściu na serwer")
@Header(" ")
@Header("Uprawnienia banów:")
@Header("mabans.bannedtryjoin - Informacja gdy zbanowany gracz chce wejść na serwer")
@Header("mabans.ban - Dostęp do komendy /ban")
@Header("mabans.tempban - Dostęp do komendy /tempban")
@Header("mabans.unban - Dostęp do komendy /unban")
@Header("mabans.unbanall - Dostęp do komendy /unbanall")
@Header("mabans.baninfo - Dostęp do komendy /baninfo")
@Header(" ")
@Header("Uprawnienia wyciszeń:")
@Header("mabans.mutetrychat - Informacja gdy wyciszony gracz chce pisać na czacie")
@Header("mabans.mute - Dostęp do komendy /mute")
@Header("mabans.tempmute - Dostęp do komendy /tempmute")
@Header("mabans.unmute - Dostęp do komendy /unmute")
@Header("mabans.unmuteall - Dostęp do komendy /unmuteall")
@Header("mabans.muteinfo - Dostęp do komendy /muteinfo")
@Header("mabans.mute.bypass - Gracz mimo wyciszenia może pisać na czacie")
public final class PluginConfiguration extends OkaeriConfig {

    @Comment("Powód bana/wyciszenia gdy jego powód nie jest podany")
    public String noReasonBan = "Złamanie regulaminu!";

    @Comment("Typ bazy danych")
    @Comment("FLAT - Pliki YAML")
    @Comment("MYSQL - Baza danych (zalecane)")
    public String databaseType = "FLAT";

    @Comment("Co ile minut bany mają być zapisywane?")
    @Comment("Jeśli chcesz to wyłączyć wpisz -1")
    @Comment("Zalecana wartość 60 (w przypadku FLAT), 30 (w przypadku MYSQL)")
    public int autoSave = 60;
    @Exclude
    public int autoSaveTicks;

    @Comment("Konfiguracja bazy danych MySQL")
    @Comment("mysqlHost - Host bazy danych")
    @Comment("mysqlPort - Port bazy danych")
    @Comment("mysqlUser - Użytkownik bazy danych")
    @Comment("mysqlPassword - Hasło bazy danych")
    @Comment("mysqlDatabase - Nazwa bazy danych")
    @Comment("mysqlTable - Nazwa tabelki z danymi")
    @Comment("mysqlTimeout - Czas prób połączenia z bazą (zalecane minimum 30000)")
    @Comment("mysqlUseSSL - Czy połączenie ma być szyfrowane? (Jeśli localhost ustaw na false)")
    @Comment("mysqlPoolSize - Ile połączeń ma być otwartych do bazy? (Zalecane minimum 5, ustaw -1 aby plugin sam dobrał optymalną liczbę połączeń)")
    public String mysqlHost = "localhost";
    public int mysqlPort = 3306;
    public String mysqlUser = "root";
    public String mysqlPassword = "";
    public String mysqlDatabase = "minecraft";
    public String mysqlTableBans = "mabans";
    public String mysqlTableMutes = "mamutes";
    public int mysqlTimeout = 30000;
    public boolean mysqlUseSSL = false;
    public int mysqlPoolSize = 5;

    @Override
    public OkaeriConfig load() throws OkaeriException {
        super.load();
        this.autoSaveTicks = this.autoSave * 60 * 20;
        return this;
    }
}
