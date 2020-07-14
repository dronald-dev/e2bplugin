package ua.dronald.e2bplugin;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import ua.dronald.e2bplugin.commands.*;
import ua.dronald.e2bplugin.events.EventLogger;
import ua.dronald.e2bplugin.events.EventSpawn;
import ua.dronald.e2bplugin.utils.PluginUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;

public class E2BPlugin extends JavaPlugin {

    public static Logger logger = Logger.getLogger("Minecraft");
    public WorldGuardPlugin wg;
    public Economy economy = null;
    public final String NAME = ChatColor.GRAY + "[" + ChatColor.DARK_RED + "E2BPlugin" + ChatColor.GRAY + "]";
    public final String NAMEWS = NAME + " ";

    public File logsItems;
    public File logsBlocks;
    public File logsPlayers;

    public E2BPlugin() {}

    public void onLoad() {
    }

    public void onDisable() {
        logger.info("[E2BPlugin] Disabled!");
    }

    public void onEnable() {

        getCommand("prefix").setExecutor(new CommandPrefix(this));
        getCommand("prefixdelete").setExecutor(new CommandPrefixDelete(this));
        getCommand("colornick").setExecutor(new CommandColorNick(this));
        getCommand("colornickdelete").setExecutor(new CommandColorNickDelete(this));
        getCommand("buyflag").setExecutor(new CommandBuyFlag(this));
        getCommand("spawn").setExecutor(new CommandSpawn(this));
        getCommand("setspawn").setExecutor(new CommandSetSpawn(this));

        Bukkit.getPluginManager().registerEvents(new EventSpawn(this), this);
        Bukkit.getPluginManager().registerEvents(new EventLogger(this), this);

        if (!setupEconomy()) {
            logger.warning("[E2BPlugin] Нету Vault. Are you crazy?");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");
        if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
            logger.warning("[E2BPlugin] Нету WorldGuard. Are you crazy?");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        wg = (WorldGuardPlugin) plugin;

        File config = new File(getDataFolder() + File.separator + "config.yml");
        if(!config.exists()) {
            logger.info("[E2BPlugin] Создание конфигурационного файла...");
            getConfig().options().copyDefaults(true);
            saveDefaultConfig();
        }

        logsItems = new File(getDataFolder() + File.separator + PluginUtils.getDate() + "-items.log");
        if(!logsItems.exists()) {
            try {
                logsItems.createNewFile();
            } catch (IOException e) {
                logger.warning("[E2BPlugin] " + e.toString());
                e.printStackTrace();
            }
        }
        saveLogs(TypeLog.ITEMS, "<ВРЕМЯ><ИВЕНТ><(КООРДИНАТЫ_ИГРОКА)><(КООРДИНАТЫ_ПРЕДМЕТОВ)><МИР>", false);

        logsBlocks = new File(getDataFolder() + File.separator + PluginUtils.getDate() + "-blocks.log");
        if(!logsBlocks.exists()) {
            try {
                logsBlocks.createNewFile();
            } catch (IOException e) {
                logger.warning("[E2BPlugin] " + e.toString());
                e.printStackTrace();
            }
        }
        saveLogs(TypeLog.BLOCKS, "<ВРЕМЯ><ИВЕНТ><(КООРДИНАТЫ_ИГРОКА)><(КООРДИНАТЫ_БЛОКОВ)><МИР>", false);

        logsPlayers = new File(getDataFolder() + File.separator + PluginUtils.getDate() + "-players.log");
        if(!logsPlayers.exists()) {
            try {
                logsPlayers.createNewFile();
            } catch (IOException e) {
                logger.warning("[E2BPlugin] " + e.toString());
                e.printStackTrace();
            }
        }
        saveLogs(TypeLog.PLAYERS, "<ВРЕМЯ><ИВЕНТ><КООРДИНАТЫ_ИГРОКА>[<КООРДИНАТЫ_УБИЙЦЫ>]/[<КООРДИНАТЫ_БЛОКА>]<МИР>", false);

        logger.info("[E2BPlugin] Enabled!");
    }

    private boolean setupEconomy()
    {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        return (economy != null);
    }

    public void debugMessage(String message) {
        if(getConfig().getBoolean("debug")) {
            logger.info(NAMEWS + message);
        }
    }

    private void save(File log, String message) {
        Bukkit.getScheduler().runTaskAsynchronously(this, () -> {
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(log, true));
                bw.write(message);
                bw.newLine();
                bw.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
    }

    public void saveLogs(TypeLog typeLog, String message) {
        saveLogs(typeLog, message, true);
    }

    public void saveLogs(TypeLog typeLog, String message, boolean addTime) {
        String time;
        if(addTime)
            time = PluginUtils.getLogsTime();
        else
            time = "";
        switch (typeLog) {
            case ITEMS:
                save(this.logsItems, time + message);
                break;
            case BLOCKS:
                save(this.logsBlocks, time + message);
                break;
            case PLAYERS:
                save(this.logsPlayers, time + message);
                break;
            default:
                logger.warning("[E2BPlugin] Invalid Type TypeLog");
                break;
        }
    }

}