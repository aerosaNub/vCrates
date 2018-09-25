package net.vcrates.nathan;

import net.vcrates.nathan.listener.CrateListener;
import net.vcrates.nathan.listener.PlayerListener;
import net.vcrates.nathan.manager.CratesManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class vCrates extends JavaPlugin {

    private static vCrates instance;

    public void onEnable() {

        instance = this;

        load();
    }

    private void load() {
        new CratesManager(this);

        registerEvents();
    }

    private void registerEvents() {
        PluginManager pm = Bukkit.getPluginManager();

        pm.registerEvents(new CrateListener(), this);
        pm.registerEvents(new PlayerListener(), this);
    }

    public void onDisable() {

        instance = null;
    }

    public static vCrates getInstance() {
        return instance;
    }
}
