package net.vcrates.nathan.files;

import net.vcrates.nathan.vCrates;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.InputStream;

public class CratesConfig {

    private File file;
    private YamlConfiguration configuration;

    public CratesConfig() {
        this.file = new File(vCrates.getInstance().getDataFolder(), "crates_settings.yml");
        this.configuration = YamlConfiguration.loadConfiguration(file);

        if (!this.file.exists()) {
            try {
                InputStream preFile = vCrates.getInstance().getResource("crates_settings.yml");
                YamlConfiguration c = YamlConfiguration.loadConfiguration(preFile);
                this.configuration.setDefaults(c);
                this.configuration.options().copyDefaults(true);
                this.configuration.save(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public FileConfiguration c() {
        return this.configuration;
    }
}
