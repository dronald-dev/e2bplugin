package ua.dronald.e2bplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ua.dronald.e2bplugin.E2BPlugin;

public class CommandSpawn implements CommandExecutor {

    E2BPlugin plugin;

    public CommandSpawn(E2BPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(!commandSender.hasPermission("e2bplugin.spawn")) {
            commandSender.sendMessage(plugin.NAMEWS + ChatColor.GREEN + "У вас недостаточно прав!");
            return true;
        }
        if(plugin.getConfig().getDouble("spawn.x") == 0.0) {
            commandSender.sendMessage(plugin.NAMEWS + ChatColor.GREEN + "Спавн не установлен!");
            return true;
        }
        Player player = (Player) commandSender;
        player.teleport(new Location(Bukkit.getWorld(plugin.getConfig().getString("spawn.world")), plugin.getConfig().getDouble("spawn.x"), plugin.getConfig().getDouble("spawn.y"), plugin.getConfig().getDouble("spawn.z"), (float) plugin.getConfig().getDouble("spawn.yaw"), (float) plugin.getConfig().getDouble("spawn.pitch")));
        return true;
    }

}
