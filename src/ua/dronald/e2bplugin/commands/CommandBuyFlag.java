package ua.dronald.e2bplugin.commands;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.protection.flags.*;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ua.dronald.e2bplugin.E2BPlugin;

import static com.sk89q.worldguard.protection.flags.DefaultFlag.fuzzyMatchFlag;

public class CommandBuyFlag implements CommandExecutor {

    E2BPlugin plugin;

    public CommandBuyFlag(E2BPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(!commandSender.hasPermission("e2bplugin.buyflag")) {
            commandSender.sendMessage(plugin.NAMEWS + ChatColor.GREEN + "У вас недостаточно прав!");
            return true;
        }
        if(!(commandSender instanceof Player)) {
            plugin.logger.info(plugin.NAMEWS + ChatColor.GREEN + "It command for player!");
            return true;
        }
        Player player = (Player) commandSender;
        LocalPlayer localPlayer = plugin.wg.wrapPlayer(player);
        if(args.length == 3) {
            ProtectedRegion region = plugin.wg.getRegionManager(player.getWorld()).getRegion(args[0]);
            if(region == null) {
                commandSender.sendMessage(plugin.NAMEWS + ChatColor.GREEN + "Этого привата не существует в этом мире.");
                return true;
            }

            if(!region.isOwner(localPlayer)) {
                commandSender.sendMessage(plugin.NAMEWS + ChatColor.GREEN + "Вы не владелец этого привата!");
                return true;
            }

            if(!commandSender.hasPermission("e2bplugin.buyflag." + args[1])) {
                commandSender.sendMessage(plugin.NAMEWS + ChatColor.GREEN + "У вас недостаточно прав!");
                return true;
            }

            if(plugin.economy.getBalance(Bukkit.getOfflinePlayer(player.getUniqueId())) < plugin.getConfig().getDouble("flag." + args[1])) {
                commandSender.sendMessage(plugin.NAMEWS + ChatColor.GREEN + "У вас недостаточно денег чтобы приобрести данный флаг");
                return true;
            }

            plugin.economy.withdrawPlayer(Bukkit.getOfflinePlayer(player.getUniqueId()), plugin.getConfig().getDouble("flag." + args[1]));
            FlagRegistry flagRegistry = plugin.wg.getFlagRegistry();
            Flag flag = fuzzyMatchFlag(flagRegistry, args[1]);
            try {
                region.setFlag(flag, flag.parseInput(FlagContext.create().setInput(args[2]).setSender(commandSender).build()));
            } catch (InvalidFlagFormat invalidFlagFormat) {
                commandSender.sendMessage(plugin.NAMEWS + ChatColor.GREEN + "Вы ввели неправильно флаг.");
                return false;
            }
            commandSender.sendMessage(plugin.NAMEWS + ChatColor.GREEN + "Вы успешно приобрели флаг: " + ChatColor.WHITE + args[1] + ChatColor.GREEN + ". С вашего счета было снято: " + ChatColor.WHITE + plugin.getConfig().getDouble("flag." + args[1]));
            return true;

        }
        return false;
    }
}
