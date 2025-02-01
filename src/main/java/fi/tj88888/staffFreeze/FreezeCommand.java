package fi.tj88888.staffFreeze;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public class FreezeCommand implements CommandExecutor {
    private final Set<UUID> frozenPlayers = new HashSet<>();
    private final Map<UUID, Long> lastFreezeTime = new HashMap<>();


    private final StaffFreeze plugin;

    public FreezeCommand(StaffFreeze plugin) {
        this.plugin = plugin;
    }

    private String getMessage(String path) {
        return ChatColor.translateAlternateColorCodes('&',
                plugin.getConfig().getString("messages." + path));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("stafffreeze.use")) {
            sender.sendMessage(getMessage("no-permission"));
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage(getMessage("usage"));
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(getMessage("player-not-found"));
            return true;
        }

        UUID targetId = target.getUniqueId();
        if (frozenPlayers.contains(targetId)) {
            frozenPlayers.remove(targetId);
            target.sendMessage(getMessage("unfrozen"));
            String broadcast = getMessage("unfrozen-broadcast")
                    .replace("%player%", target.getName())
                    .replace("%sender%", sender.getName());
            String youUnFroze = getMessage("you-unfroze")
                    .replace("%player%", target.getName());
            sender.sendMessage(youUnFroze);
            broadcastToStaff(broadcast);
        } else {
            frozenPlayers.add(targetId);
            lastFreezeTime.put(targetId, System.currentTimeMillis());
            target.sendMessage(getMessage("freeze-message"));
            String broadcast = getMessage("frozen-broadcast")
                    .replace("%player%", target.getName())
                    .replace("%sender%", sender.getName());
            String youFroze = getMessage("you-froze")
                    .replace("%player%", target.getName());
            sender.sendMessage(youFroze);
            broadcastToStaff(broadcast);
            startFreezeMessage(target);
        }

        return true;
    }


      void broadcastToStaff(String message) {
        String prefix = ChatColor.translateAlternateColorCodes('&',
                plugin.getConfig().getString("prefix", "&7[StaffFreeze] "));
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission("stafffreeze.use")) {
                player.sendMessage(prefix + message);
            }
        }
    }

   private void startFreezeMessage(Player player) {
        Bukkit.getScheduler().runTaskTimer(StaffFreeze.getPlugin(StaffFreeze.class), () -> {
            if (frozenPlayers.contains(player.getUniqueId())) {
                player.sendMessage(getMessage("freeze-message"));
            }
        }, 60L, 60L);
    }


    public boolean isPlayerFrozen(UUID playerId) {
        return frozenPlayers.contains(playerId);
    }

    public Long getLastFreezeTime(UUID playerId) {
        return lastFreezeTime.get(playerId);
    }
}