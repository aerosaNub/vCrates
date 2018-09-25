package net.vcrates.nathan.listener;

import net.vcrates.nathan.manager.CratesManager;
import net.vcrates.nathan.objects.Crate;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Set;

public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        CratesManager.init().getDataConfig().createCratesProfile(p);

        p.sendMessage(ChatColor.YELLOW + "You currently have " + CratesManager.init().getDataConfig().getVirtualKeys(p) + " crate key(s)!");

        Set<String> keys = CratesManager.init().getCratesConfig().c().getConfigurationSection("Crates").getKeys(false);

        for (String crates : keys) {
            ItemStack item = new ItemStack(Material.getMaterial(CratesManager.init().getCratesConfig().c().getString("Crates." + crates + ".blockType")));
            ItemMeta meta = item.getItemMeta();

            meta.setDisplayName("default");
            item.setItemMeta(meta);

            p.getInventory().addItem(item);
        }
    }
}
