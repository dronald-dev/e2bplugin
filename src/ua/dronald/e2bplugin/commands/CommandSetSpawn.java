package ua.dronald.e2bplugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ua.dronald.e2bplugin.E2BPlugin;

public class CommandSetSpawn implements CommandExecutor {

    E2BPlugin plugin;

    public CommandSetSpawn(E2BPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(!commandSender.hasPermission("e2bplugin.setspawn")) {
            commandSender.sendMessage(plugin.NAMEWS + ChatColor.GREEN + "У вас недостаточно прав!");
            return true;
        }
        Player player = (Player) commandSender;
        Location location = player.getLocation();
        plugin.getConfig().set("spawn.world", location.getWorld().getName());
        plugin.getConfig().set("spawn.x", location.getX());
        plugin.getConfig().set("spawn.y", location.getY());
        plugin.getConfig().set("spawn.z", location.getZ());
        plugin.getConfig().set("spawn.pitch", (double) location.getPitch());
        plugin.getConfig().set("spawn.yaw", (double) location.getYaw());
        plugin.saveConfig();
        return true;
    }

}
