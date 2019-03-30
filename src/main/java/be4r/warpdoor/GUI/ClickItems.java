package be4r.warpdoor.GUI;

import net.wesjd.anvilgui.AnvilGUI;
import static be4r.warpdoor.Main.conf;
import static be4r.warpdoor.Main.glow;
import be4r.warpdoor.GUI.OpenGUI;
import static be4r.warpdoor.GUI.OpenGUI.fulled;
import be4r.warpdoor.Glow;
import be4r.warpdoor.Main;
import org.bukkit.Bukkit;
import static org.bukkit.Bukkit.getServer;
import static be4r.warpdoor.Main.PlayerData;
import static be4r.warpdoor.Main.conf;
import static be4r.warpdoor.Main.glow;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.PlayerInteractEvent;
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
                    p.getLocation().getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
                    p.getLocation().getWorld().playEffect(p.getLocation(), Effect.ENDER_SIGNAL, 0);
                    p.getLocation().getWorld().playEffect(p.getLocation(), Effect.ENDER_SIGNAL, 0);
                    p.getLocation().getWorld().playEffect(p.getLocation(), Effect.ENDER_SIGNAL, 0);
                    p.getLocation().getWorld().playEffect(p.getLocation(), Effect.ENDER_SIGNAL, 0);
                    e.setCancelled(true);
                    p.closeInventory();
                    
                }
                /*
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
                }*/
                
                
                
            }
            
            ItemStack i = e.getCurrentItem();
                if (i.getItemMeta().hasEnchant(glow)){
                    if (i.getType().equals(Material.BOOK)){
                        e.setCancelled(true);
                        p.closeInventory();
                        
                            new AnvilGUI(Main.getInstance(), p, "<ポイント名>", (player, reply) -> {
                            if (conf.getConfig().contains("Points." + reply + ".WorldName") == false && conf.getConfig().getInt("Points." + e.getInventory().getTitle() + ".Y") != 0) {
                                int x = conf.getConfig().getInt("Points." + e.getInventory().getTitle() + ".X");
                                int y = conf.getConfig().getInt("Points." + e.getInventory().getTitle() + ".Y");
                                int z = conf.getConfig().getInt("Points." + e.getInventory().getTitle() + ".Z");
                                conf.getConfig().set("Points." + e.getInventory().getTitle(), null);
                                conf.getConfig().set("Points." + reply + ".WorldName", p.getLocation().getWorld().getName());
                                conf.getConfig().set("Points." + reply + ".X", x);
                                conf.getConfig().set("Points." + reply + ".Y", y);
                                conf.getConfig().set("Points." + reply + ".Z", z);
                                return null;
                            }else{
                        p.sendMessage("§c§nポイント '" + reply + "' は既に存在するか削除されています");
                        }
                        return null;
                        });
                    }
                    if (i.getType().equals(Material.BARRIER)){
                        e.setCancelled(true);
                        p.closeInventory();
                        ItemStack door = new ItemStack(Material.IRON_DOOR);
                        ItemMeta itemMeta = door.getItemMeta();
                        itemMeta.setDisplayName("WarpDoor");
                        new Glow().enchantGlow(door);
        
                        door.setItemMeta(itemMeta);
                        door.addEnchantment(glow, 1);
                        
                        String WorldName = conf.getConfig().getString("Points." + e.getInventory().getTitle() + ".WorldName");
                        World w = getServer().getWorld(WorldName);
                        
                        int x = conf.getConfig().getInt("Points." + e.getInventory().getTitle() + ".X");
                        int y = conf.getConfig().getInt("Points." + e.getInventory().getTitle() + ".Y");
                        int z = conf.getConfig().getInt("Points." + e.getInventory().getTitle() + ".Z");
                        
                        Block db = w.getBlockAt(new Location(w, x, y, z));
                        db.setType(Material.AIR);
                        db.breakNaturally();
                        db.getDrops().clear();
                        
                        w.dropItemNaturally(new Location(w, x, y, z), door);
                        conf.getConfig().set("Points." + e.getInventory().getTitle(), null);
                    }
                }
            
        }
    }
    
    @EventHandler
    public void ClickDoor(PlayerInteractEvent e){
        Block door = e.getClickedBlock();
        Player p = e.getPlayer();
        Action action = e.getAction();
        if (door.getBlockData().getMaterial().equals(Material.IRON_DOOR) && action.equals(Action.RIGHT_CLICK_BLOCK)){
            Location cbl = door.getLocation();
            for (String PointName : conf.getConfig().getConfigurationSection("Points").getKeys(false)){
                String WorldName = conf.getConfig().getString("Points." + PointName + ".WorldName");
                World w = getServer().getWorld(WorldName);
                int x = conf.getConfig().getInt("Points." + PointName + ".X");
                int y = conf.getConfig().getInt("Points." + PointName + ".Y");
                int z = conf.getConfig().getInt("Points." + PointName + ".Z");
                Location bl = new Location(w, x, y, z);
                if (cbl.getBlockX() == bl.getBlockX() && cbl.getBlockZ() == bl.getBlockZ()){
                    if(cbl.getBlockY() == bl.getBlockY() || cbl.getBlockY() == bl.getBlockY() + 1.0D){
                        new OpenGUI().DoorMenu(p, PointName);
                    }
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
