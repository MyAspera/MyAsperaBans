package pl.myaspera.bans.data;

import eu.okaeri.configs.OkaeriConfig;

public final class MessagesConfiguration extends OkaeriConfig {

    public String playerAlreadyBanned = "&8%> &cTen gracz jest już zbanowany!";
    public String playerNotBanned = "&8%> &cTen gracz nie jest zbanowany!";
    public String permBanBroadcast = "&8%> &cGracz &3%player% &czostał zbanowany przez &6%admin%&c! &cPowód: &6%reason%&c.";
    public String tempBanBroadcast = "&8%> &cGracz &3%player% &czostał zbanowany przez &6%admin%&c! &cPowód: &6%reason%&c. Ban wygasa &6%data%&c.";
    public String unBanBroadcast = "&8%> &cGracz &3%player% &czostał odbanowany przez &6%admin%&c!";
    public String unBanAllBroadcast = "&8%> &cAdministrator &6%admin% &codbanował wszystkich graczy!";
    public String bannedPlayerLogin = "&8%> &cGracz &3%player% &cpróbował wejść na serwer ale jest zbanowany. Informacje o banie &6/baninfo %player%";
    public String bannedPlayerLoginExpire = "&8%> &cGraczowi &3%player% &cwygasł tymczasowy ban!";

    public String playerAlreadyMuted = "&8%> &cTen gracz jest już wyciszony!";
    public String playerNotMuted = "&8%> &cTen gracz nie jest wyciszony!";
    public String permMuteBroadcast = "&8%> &cGracz &3%player% &czostał wyciszony przez &6%admin%&c! &cPowód: &6%reason%&c.";
    public String tempMuteBroadcast = "&8%> &cGracz &3%player% &czostał wyciszony przez &6%admin%&c! &cPowód: &6%reason%&c. Wygasa &6%data%&c.";
    public String unMuteBroadcast = "&8%> &cGracz &3%player% &czostał odciszony przez &6%admin%&c!";
    public String unMuteAllBroadcast = "&8%> &cAdministrator &6%admin% &codciszył wszystkich graczy!";
    public String mutedPlayerTryChat = "&8%> &cGracz &3%player% &csię odezwać ale jest wyciszony. Informacje o wyciszeniu &6/muteinfo %player%";
    public String mutedPlayerTryChatExpire = "&8%> &cGraczowi &3%player% &cwygasło tymczasowe wyciszenie!";

    public String banInfo = "&7Informacje o banie &3%player%\n" +
            " &8* &7Zbanowano dnia: &3%banDate%\n" +
            " &8* &7Przez: &3%banAdmin%\n" +
            " &8* &7Powód: &3%reason%\n" +
            " &8* &7Wygasa: &3%expire%\n" +
            "&8&m---------------------------------";
    public String permBan = "&7Zostałeś &4zbanowany &7przez &6%admin%\n" +
            "&7Nadano: &c%data%\n" +
            "&7Powód: &c%reason%\n" +
            "&7Blokada konta jest &cna zawsze\n" +
            "&f\n" +
            "&7Discord: &3https://dc.myaspera.pl\n" +
            "&7Itemshop: &3https://myaspera.pl\n" +
            "&f\n" +
            "&3&oTe wiadomości możesz zmienić w pliku: &bplugins/mabans/messages.yml";
    public String tempBan = "&7Zostałeś &4zbanowany &7przez &6%admin%\n" +
            "&7Nadano: &c%data%\n" +
            "&7Powód: &c%reason%\n" +
            "&7Blokada wygasa: &c%expire%\n" +
            "&f\n" +
            "&7Discord: &3https://dc.myaspera.pl\n" +
            "&7Itemshop: &3https://myaspera.pl\n" +
            "&f\n" +
            "&3&oTe wiadomości możesz zmienić w pliku: &bplugins/mabans/messages.yml";

    public String muteInfo = "&7Informacje o wyciszeniu &3%player%\n" +
            " &8* &7Wyciszony dnia: &3%banDate%\n" +
            " &8* &7Przez: &3%banAdmin%\n" +
            " &8* &7Powód: &3%reason%\n" +
            " &8* &7Wygasa: &3%expire%\n" +
            "&8&m---------------------------------";
    public String permMute = "&7Zostałeś &4wyciszony &7przez &6%admin%\n" +
            "&7Nadano: &c%data%\n" +
            "&7Powód: &c%reason%\n" +
            "&7Blokada jest &cna zawsze\n" +
            "&f\n" +
            "&7Discord: &3https://dc.myaspera.pl\n" +
            "&7Itemshop: &3https://myaspera.pl\n" +
            "&f\n" +
            "&3&oTe wiadomości możesz zmienić w pliku: &bplugins/mabans/messages.yml";
    public String tempMute = "&7Zostałeś &4wyciszony &7przez &6%admin%\n" +
            "&7Nadano: &c%data%\n" +
            "&7Powód: &c%reason%\n" +
            "&7Blokada wygasa: &c%expire%\n" +
            "&f\n" +
            "&7Discord: &3https://dc.myaspera.pl\n" +
            "&7Itemshop: &3https://myaspera.pl\n" +
            "&f\n" +
            "&3&oTe wiadomości możesz zmienić w pliku: &bplugins/mabans/messages.yml";
    public String unMute = "\n\n\n\n&a&lZnowu możesz pisać na czacie!";
}
