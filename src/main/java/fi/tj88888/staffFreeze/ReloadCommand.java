package fi.tj88888.staffFreeze;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {
    private final StaffFreeze plugin;

    public ReloadCommand(StaffFreeze plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("stafffreeze.reload")) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfig().getString("messages.no-permission")));
            return true;
        }

        plugin.reloadConfig();
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                plugin.getConfig().getString("prefix") + "&aConfiguration reloaded!"));
        return true;
    }
}