package ua.dronald.e2bplugin.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import ua.dronald.e2bplugin.E2BPlugin;

public class EventSpawn implements Listener {

    E2BPlugin plugin;

    public EventSpawn(E2BPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if(plugin.getConfig().getDouble("spawn.x") == 0.0) {
            event.getPlayer().sendMessage(plugin.NAMEWS + ChatColor.GREEN + "Спавн не установлен!");
            return;
        }
        if(!event.getPlayer().hasPlayedBefore()) {
            Player player = event.getPlayer();
            player.teleport(new Location(Bukkit.getWorld(plugin.getConfig().getString("spawn.world")), plugin.getConfig().getDouble("spawn.x"), plugin.getConfig().getDouble("spawn.y"), plugin.getConfig().getDouble("spawn.z"), (float) plugin.getConfig().getDouble("spawn.yaw"), (float) plugin.getConfig().getDouble("spawn.pitch")));
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        if(plugin.getConfig().getDouble("spawn.x") == 0.0) {
            event.getPlayer().sendMessage(plugin.NAMEWS + ChatColor.GREEN + "Спавн не установлен!");
            return;
        }
        Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, () -> event.getPlayer().teleport(new Location(Bukkit.getWorld(plugin.getConfig().getString("spawn.world")), plugin.getConfig().getDouble("spawn.x"), plugin.getConfig().getDouble("spawn.y"), plugin.getConfig().getDouble("spawn.z"), (float) plugin.getConfig().getDouble("spawn.yaw"), (float) plugin.getConfig().getDouble("spawn.pitch"))));
    }
}
