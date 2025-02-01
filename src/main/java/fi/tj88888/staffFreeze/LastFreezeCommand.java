package fi.tj88888.staffFreeze;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LastFreezeCommand implements CommandExecutor {
    private final FreezeCommand freezeCommand;
    private final StaffFreeze plugin;

    public LastFreezeCommand(FreezeCommand freezeCommand, StaffFreeze plugin) {
        this.freezeCommand = freezeCommand;
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("stafffreeze.use")) {
            sender.sendMessage(ChatColor.RED + "No permission!");
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "Usage: /lastfreeze <player>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(getMessage("last-freeze-player-not-found"));
            return true;
        }

        Long lastFreeze = freezeCommand.getLastFreezeTime(target.getUniqueId());
        if (lastFreeze == null) {
            sender.sendMessage(getMessage("last-freeze-never")
                    .replace("%player%", target.getName())
            );
            return true;
        }

        if (lastFreeze == 0) {
            sender.sendMessage(getMessage("last-freeze-never")
                    .replace("%player%", target.getName())
            );
        } else {
            long timeSince = System.currentTimeMillis() - lastFreeze;
            String timeAgo = formatTime(timeSince);
            sender.sendMessage(getMessage("last-freeze-message")
                    .replace("%player%", target.getName())
                    .replace("%time%", timeAgo));
        }

        return true;
    }

    private String formatTime(long milliseconds) {
        long seconds = milliseconds / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;

        if (days > 0) return days + " days";
        if (hours > 0) return hours + " hours";
        if (minutes > 0) return minutes + " minutes";
        return seconds + " seconds";
    }

    private String getMessage(String path) {
        String message = plugin.getConfig().getString("messages." + path);
        if (message == null) {
            return ChatColor.RED + "Message not found in config: " + path;
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
