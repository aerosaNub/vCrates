package net.vcrates.nathan.manager;

import net.vcrates.nathan.files.CratesConfig;
import net.vcrates.nathan.files.DataConfig;
import net.vcrates.nathan.vCrates;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CratesManager {

    private static CratesManager cratesManager;

    public static CratesManager init() {
        if (cratesManager == null) {
            cratesManager = new CratesManager(vCrates.getInstance());
        }
        return  cratesManager;
    }

    private vCrates instance;
    private CratesConfig cratesConfig;
    private DataConfig locationConfig;

    public CratesManager(vCrates instance) {
        this.instance = instance;
        this.cratesConfig = new CratesConfig();
        this.locationConfig = new DataConfig();
    }

    public CratesConfig getCratesConfig() {
        return cratesConfig;
    }

    public DataConfig getDataConfig() {
        return locationConfig;
    }

    public Location crateLocation(String key) {
        Location location = new Location(Bukkit.getWorld(getDataConfig().c().getString("Crate_Locations." + key + ".world")),
                getDataConfig().c().getDouble("Crate_Locations." + key + ".x"),
                getDataConfig().c().getDouble("Crate_Locations." + key + ".y"),
                getDataConfig().c().getDouble("Crate_Locations." + key + ".z"),
                (float) getDataConfig().c().getDouble("Crate_Locations." + key + ".yaw"),
                (float) getDataConfig().c().getDouble("Crate_Locations." + key + ".pitch"));
        return location;
    }

    public void openFilledGUI(Player player, String crateName) {
        Inventory inv = Bukkit.createInventory(null, 27, ChatColor.YELLOW + crateName);

        for (int i = 0; i<inv.getContents().length; i++) {
            ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.YELLOW + "Select any slot for 4 prize total!");
            item.setItemMeta(meta);

            inv.setItem(i, item);
        }

        player.openInventory(inv);

    }

    public void setNewGlass(Inventory inv, String name, int num, int slot) {
        ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) num);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);

        inv.setItem(slot, item);
    }
}
