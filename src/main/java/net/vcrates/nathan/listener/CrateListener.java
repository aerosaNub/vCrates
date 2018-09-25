package net.vcrates.nathan.listener;

import net.vcrates.nathan.manager.CratesManager;
import net.vcrates.nathan.objects.Crate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class CrateListener implements Listener {

    @EventHandler
    public void onCratePlace(BlockPlaceEvent e) {
        Block block = e.getBlock();

        if (block == null) return;
        if (block.getType() == Material.AIR) return;

        Set<String> keys = CratesManager.init().getCratesConfig().c().getConfigurationSection("Crates").getKeys(false);

        for (String crates : keys) {
            if (block.getType() == Material.getMaterial(CratesManager.init().getCratesConfig().c().getString("Crates." + crates + ".blockType"))) {

                CratesManager.init().getDataConfig().addChestLocation("default", block.getLocation());

            }
        }
    }

    @EventHandler
    public void onCrateInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Block block = e.getClickedBlock();

        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (block == null) return;
            if (block.getType() == Material.AIR) return;

            Set<String> keys = CratesManager.init().getCratesConfig().c().getConfigurationSection("Crates").getKeys(false);

           for (String crates : keys) {
               if (block.getType() == Material.getMaterial(CratesManager.init().getCratesConfig().c().getString("Crates." + crates + ".blockType"))) {

                   for (String dataCrates : CratesManager.init().getDataConfig().c().getConfigurationSection("Crate_Locations").getKeys(false)) {

                       boolean isEqual = block.getLocation().equals(CratesManager.init().crateLocation(dataCrates));


                       if (isEqual) {

                           e.setCancelled(true);

                           CratesManager.init().openFilledGUI(p, crates);

                       }
                   }
               }
           }
        }
    }

    @EventHandler
    public void onCrateInventory(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        ItemStack item = e.getCurrentItem();

        if (e.getClickedInventory() == null) return;

        if (e.getClickedInventory().getName() == null) return;
        Set<String> keys = CratesManager.init().getCratesConfig().c().getConfigurationSection("Crates").getKeys(false);

        for (String crates : keys) {
            if (e.getClickedInventory().getName().equalsIgnoreCase(ChatColor.YELLOW + crates)) {
                if (item == null) return;

                if (item.getType() == Material.AIR) return;

                if (item.getType() == Material.STAINED_GLASS_PANE) {


                    if (item.getDurability() == (short) 10) {
                        e.setCancelled(true);
                    }

                    if (item.getDurability() == (short) 7) {
                        e.setCancelled(true);

                        for (String rewardKeys :  CratesManager.init().getCratesConfig().c().getConfigurationSection("Crates." + crates + ".rewards").getKeys(false)) {
                            CratesManager.init().setNewGlass(e.getClickedInventory(), ChatColor.GREEN + "Reward: " + ChatColor.GREEN + rewardKeys, 10, e.getSlot());


                                List<String> commandList = (CratesManager.init().getCratesConfig().c().getStringList("Crates." + crates + ".rewards." + rewardKeys));
                                int random = new Random().nextInt(commandList.size());
                                String command = commandList.get(random);
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("@user", p.getName()));
                                commandList.clear();

                        }
                    }
                }
            }
        }
    }
}
