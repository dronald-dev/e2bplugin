package ua.dronald.e2bplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ua.dronald.e2bplugin.E2BPlugin;

public class CommandColorNick implements CommandExecutor {

    private E2BPlugin plugin;
    private String symbols;

    public CommandColorNick(E2BPlugin plugin) {
        this.plugin = plugin;
        this.symbols = "b9826acekmo031547dfln";
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

        if(args.length == 0)
            return false;

        if(args.length == 1) {
            if(args[0].toCharArray().length > 2) {
                commandSender.sendMessage(ChatColor.GOLD + "Введите цвет ника");
                return false;
            }
            if(!(args[0].toCharArray()[0] == '&')) {
                commandSender.sendMessage(ChatColor.GOLD + "Введите цвет ника");
                return false;
            }
            for(int i = 0; i < this.symbols.toCharArray().length; i++) {
                if(this.symbols.toCharArray()[i] == args[0].toCharArray()[1]) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "nick " + commandSender.getName() + " " + args[0] + commandSender.getName());
                    return true;
                }
            }
            return false;
        }

        return false;
    }

}
