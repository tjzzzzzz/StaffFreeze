package fi.tj88888.staffFreeze;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class FreezeListener implements Listener {
    private final FreezeCommand freezeCommand;

    private final StaffFreeze plugin;

    public FreezeListener(FreezeCommand freezeCommand, StaffFreeze plugin) {
        this.freezeCommand = freezeCommand;
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (freezeCommand.isPlayerFrozen(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (freezeCommand.isPlayerFrozen(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();

        if (freezeCommand.isPlayerFrozen(player.getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (freezeCommand.isPlayerFrozen(player.getUniqueId())) {
            String logoutMessage = plugin.getConfig().getString("messages.frozen-logout")
                    .replace("%player%", player.getName());
            freezeCommand.broadcastToStaff(logoutMessage);
        }
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (freezeCommand.isPlayerFrozen(player.getUniqueId())) {
            String originalFormat = event.getFormat();
            for (Player recipient : event.getRecipients()) {
                if (recipient.hasPermission("stafffreeze.use")) {
                    event.setFormat(ChatColor.AQUA + "[Frozen] " + ChatColor.RESET + originalFormat);
                }
            }
        }
    }
}