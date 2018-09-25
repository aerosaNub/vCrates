package net.vcrates.nathan.files;

import com.sk89q.worldedit.util.YAMLConfiguration;
import net.vcrates.nathan.objects.Crate;
import net.vcrates.nathan.vCrates;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

public class DataConfig {

    private File file;
    private YamlConfiguration configuration;
    private ArrayList<Crate> crates = new ArrayList<>();

    public DataConfig() {
        this.file = new File(vCrates.getInstance().getDataFolder(), "data.yml");
        this.configuration = YamlConfiguration.loadConfiguration(file);

        if (file.exists()) {
            if (configuration.get("Crate_Locations") != null) {
                for (String keys : this.configuration.getConfigurationSection("Crate_Locations").getKeys(false)) {
                    this.crates.add(new Crate(this.configuration.getInt(keys),
                            this.configuration.getString("Crate_Locations." + keys + ".crate"), this.configuration.getString("Crate_Locations." + keys + ".world"),
                            this.configuration.getDouble("Crate_Locations." + keys + ".x"),
                            this.configuration.getDouble("Crate_Locations." + keys + ".y"),
                            this.configuration.getDouble("Crate_Locations." + keys + ".z"),
                            (float) this.configuration.getDouble("Crate_Locations." + keys + ".yaw"),
                            (float) this.configuration.getDouble("Crate_Locations." + keys + ".pitch")));
                }
            }
        }
    }

    public int getVirtualKeys(Player player) {
        return this.configuration.getInt(player.getUniqueId().toString());
    }

    public void createCratesProfile(Player p) {
            this.configuration.set(p.getUniqueId().toString(), 0);
            saveData();
    }

    public void addChestLocation(String chestName, Location loc) {
        int keyNumber = this.crates.size() + 1;

        Crate createdCrates = new Crate(keyNumber, chestName,  loc.getWorld().getName(),
                loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());


        this.crates.add(createdCrates);

        this.configuration.set("Crate_Locations." + keyNumber + ".chest", chestName);
        this.configuration.set("Crate_Locations." + keyNumber + ".world", loc.getWorld().getName());
        this.configuration.set("Crate_Locations." + keyNumber + ".x", loc.getX());
        this.configuration.set("Crate_Locations." + keyNumber + ".y", loc.getY());
        this.configuration.set("Crate_Locations." + keyNumber + ".z", loc.getZ());
        this.configuration.set("Crate_Locations." + keyNumber + ".yaw", loc.getYaw());
        this.configuration.set("Crate_Locations." + keyNumber + ".pitch", loc.getPitch());

        saveData();
    }

    public FileConfiguration c() {
        return this.configuration;
    }

    public void saveData() {
        try {
            this.configuration.save(file);
        } catch (Exception e) { e.printStackTrace(); }
    }
}
