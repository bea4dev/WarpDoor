/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be4r.warpdoor.GUI;

import net.wesjd.anvilgui.AnvilGUI;
import static be4r.warpdoor.Main.conf;
import be4r.warpdoor.GUI.OpenGUI;
import be4r.warpdoor.Main;
import org.bukkit.Bukkit;
import static org.bukkit.Bukkit.getServer;
import static be4r.warpdoor.Main.PlayerData;
import static be4r.warpdoor.Main.conf;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

/**
 *
 * @author Be4rJP
 */
public class ClickItems implements Listener{
    @EventHandler
    public void ClickInventory(InventoryClickEvent e){
        Player p = (Player)e.getWhoClicked();
        if(e.getCurrentItem().getItemMeta() != null){
            if (e.getInventory().getTitle().equals("ポイントリスト")){
                if(e.getCurrentItem().getType().equals(Material.BARRIER)){
                    p.closeInventory();
                    e.setCancelled(true);
                    new OpenGUI().MainMenu(p,"ポイントを削除");
                }
                if(e.getCurrentItem().getType().equals(Material.BOOK)){
                    p.closeInventory();
                    e.setCancelled(true);
                    new OpenGUI().MainMenu(p,"ポイント名を変更");
                }
            }
            if (e.getCurrentItem().getType().equals(Material.ENDER_EYE)){
                if (e.getInventory().getTitle().equals("ポイントリスト")){
                    String WorldName = conf.getConfig().getString("Points." + e.getCurrentItem().getItemMeta().getDisplayName() + ".WorldName");
                    World w = getServer().getWorld(WorldName);
                    int x = conf.getConfig().getInt("Points." + e.getCurrentItem().getItemMeta().getDisplayName() + ".X");
                    int y = conf.getConfig().getInt("Points." + e.getCurrentItem().getItemMeta().getDisplayName() + ".Y");
                    int z = conf.getConfig().getInt("Points." + e.getCurrentItem().getItemMeta().getDisplayName() + ".Z");
                    Location tpp = new Location(w, x, y, z);
                    tpp.setX(tpp.getX() + 0.5);
                    tpp.setZ(tpp.getZ() + 0.5);
                    tpp.setYaw(p.getLocation().getYaw());
                    p.teleport(tpp);
                    PlayerData.remove(p);
                    p.playEffect(tpp, Effect.ENDEREYE_LAUNCH, 1);
                    p.playSound(tpp, Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
                    e.setCancelled(true);
                    p.closeInventory();
                    
                }
                if (e.getInventory().getTitle().equals("ポイントを削除")){
                    e.setCancelled(true);
                    p.closeInventory();
                    conf.getConfig().set("Points." + e.getCurrentItem().getItemMeta().getDisplayName(), null);
                }
                if (e.getInventory().getTitle().equals("ポイント名を変更")){
                    e.setCancelled(true);
                    p.closeInventory();
                    int x = conf.getConfig().getInt("Points." + e.getCurrentItem().getItemMeta().getDisplayName() + ".X");
                    int y = conf.getConfig().getInt("Points." + e.getCurrentItem().getItemMeta().getDisplayName() + ".Y");
                    int z = conf.getConfig().getInt("Points." + e.getCurrentItem().getItemMeta().getDisplayName() + ".Z");
                    conf.getConfig().set("Points." + e.getCurrentItem().getItemMeta().getDisplayName(), null);
                    new AnvilGUI(Main.getInstance(), p, "<ポイント名>", (player, reply) -> {
                    if (conf.getConfig().contains("Points." + reply + ".WorldName") == false) {
                        conf.getConfig().set("Points." + reply + ".WorldName", p.getLocation().getWorld().getName());
                        conf.getConfig().set("Points." + reply + ".X", x);
                        conf.getConfig().set("Points." + reply + ".Y", y);
                        conf.getConfig().set("Points." + reply + ".Z", z);
                        return null;
                    }else{
                        p.sendMessage("§c§nポイント '" + reply + "' は既に存在します");
                    }
                    return "Incorrect.";
                    });
                }
                
            }
        }
    }
    
    @EventHandler
    public void Close(InventoryCloseEvent e) {
        Player p = (Player)e.getPlayer();
        PlayerData.remove(p);
    }
    
    
}
