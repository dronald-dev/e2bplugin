package ua.dronald.e2bplugin.events;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import ua.dronald.e2bplugin.E2BPlugin;
import ua.dronald.e2bplugin.TypeLog;

public class EventLogger implements Listener {

    E2BPlugin plugin;

    public EventLogger(E2BPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        plugin.saveLogs(TypeLog.PLAYERS, event.getPlayer().getDisplayName() + " зашел в игру " + getLocString(event.getPlayer()) + getLogWorld(event.getPlayer()));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        plugin.saveLogs(TypeLog.PLAYERS,event.getPlayer().getDisplayName() + " вышел из игры " + getLocString(event.getPlayer()) + getLogWorld(event.getPlayer()));
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        plugin.saveLogs(TypeLog.BLOCKS, event.getPlayer().getDisplayName() + " поставил " + event.getBlock().getType().name() + getLocString(event.getPlayer()) + getLocString(event.getBlock()) + getLogWorld(event.getPlayer()));
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        plugin.saveLogs(TypeLog.BLOCKS, event.getPlayer().getDisplayName() + " сломал " + event.getBlock().getType().name() + getLocString(event.getPlayer()) + getLocString(event.getBlock()) + getLogWorld(event.getPlayer()));
    }

    @EventHandler
    public void onDropItem(PlayerDropItemEvent event) {
        plugin.saveLogs(TypeLog.ITEMS, event.getPlayer().getDisplayName() + " выбросил " + event.getItemDrop().getItemStack().getType().name() + " x " + event.getItemDrop().getItemStack().getAmount() + getLocString(event.getPlayer()) + getLocString(event.getItemDrop()) + getLogWorld(event.getPlayer()));
    }

    @EventHandler
    public void onPickupItem(PlayerPickupItemEvent event) {
        plugin.saveLogs(TypeLog.ITEMS, event.getPlayer().getDisplayName() + " поднял " + event.getItem().getItemStack().getType().name() + " x " + event.getItem().getItemStack().getAmount() + getLocString(event.getPlayer()) + getLocString(event.getItem()) + getLogWorld(event.getPlayer()));
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        String positionKiller = "";
        if(event.getEntity().getKiller() != null) {
            positionKiller = getLocString(event.getEntity().getKiller());
        }
        plugin.saveLogs(TypeLog.PLAYERS,event.getEntity().getDisplayName() + " " + event.getDeathMessage() + getLocString(event.getEntity()) + positionKiller + getLogWorld(event.getEntity()));
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        plugin.saveLogs(TypeLog.PLAYERS,event.getPlayer().getDisplayName() + " возродился" + getLocString(event.getRespawnLocation()) + getLogWorld(event.getPlayer()));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onChatMessage(AsyncPlayerChatEvent event) {
        plugin.saveLogs(TypeLog.PLAYERS,event.getPlayer().getDisplayName() + ": " + event.getMessage() + getLocString(event.getPlayer()) + getLogWorld(event.getPlayer()));
    }

    @EventHandler
    public void onCommandUse(PlayerCommandPreprocessEvent event) {
        plugin.saveLogs(TypeLog.PLAYERS,event.getPlayer().getDisplayName() + ": " + event.getMessage() + getLocString(event.getPlayer()) + getLogWorld(event.getPlayer()));
    }

    public static String getLocString(Location location) {
        return "(" + Math.round(location.getX()) + ";" + Math.round(location.getY()) + ";" + Math.round(location.getZ()) + ")";
    }

    public static String getLocString(Player player) {
        return getLocString(player.getLocation());
    }

    public static String getLocString(Entity entity) {
        return getLocString(entity.getLocation());
    }

    public static String getLocString(Block block) {
        return getLocString(block.getLocation());
    }

    public static String getLocString(Item item) {
        return getLocString(item.getLocation());
    }

    public static String getLogWorld(Player player) {
        return "(" + player.getWorld().getName() + ")";
    }

}
