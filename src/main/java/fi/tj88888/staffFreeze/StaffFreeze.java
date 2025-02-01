package fi.tj88888.staffFreeze;

import org.bukkit.plugin.java.JavaPlugin;

public final class StaffFreeze extends JavaPlugin {
    private FreezeCommand freezeCommand;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        freezeCommand = new FreezeCommand(this);
        this.getCommand("freeze").setExecutor(freezeCommand);
        this.getCommand("stafffreezereload").setExecutor(new ReloadCommand(this));
        this.getCommand("lastfreeze").setExecutor(new LastFreezeCommand(freezeCommand, this));
        getServer().getPluginManager().registerEvents(new FreezeListener(freezeCommand, this), this);
    }

    @Override
    public void onDisable() {

    }
}