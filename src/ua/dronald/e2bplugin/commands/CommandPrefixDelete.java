package ua.dronald.e2bplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ua.dronald.e2bplugin.E2BPlugin;

public class CommandPrefixDelete implements CommandExecutor {

    private E2BPlugin plugin;

    public CommandPrefixDelete(E2BPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(!commandSender.hasPermission("e2bplugin.prefix")) {
            commandSender.sendMessage("[E2BPlugin] You don't have permission");
            return true;
        }

        if(!(commandSender instanceof Player)) {
            plugin.logger.info("[E2BPlugin] It command for player");
            return true;
        }

        if(args.length > 0)
            return false;

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + commandSender.getName() + " prefix \"\"");
        commandSender.sendMessage(ChatColor.GOLD + "Ваш префикс удален");
        return true;
    }
}
