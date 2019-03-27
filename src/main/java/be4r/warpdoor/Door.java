package be4r.warpdoor;

import static be4r.warpdoor.Main.glow;
import static be4r.warpdoor.Main.conf;
import static be4r.warpdoor.Main.PlayerData;
import be4r.warpdoor.GUI.OpenGUI;
import static org.bukkit.Bukkit.getServer;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


/**
 *
 * @author Be4rJP
 */
public class Door  implements Listener{
    @EventHandler
    public void Door(BlockPlaceEvent e){
        if (e.getItemInHand().getItemMeta().hasEnchant(glow)){
            if(e.getItemInHand().getItemMeta().getDisplayName() != null){
                Player p = (Player)e.getPlayer();
                new AnvilGUI(Main.getInstance(), p, "<ポイント名>", (player, reply) -> {
                    if (conf.getConfig().contains("Points." + reply + ".WorldName") == false) {
                        conf.getConfig().set("Points." + reply + ".WorldName", e.getPlayer().getLocation().getWorld().getName());
                        conf.getConfig().set("Points." + reply + ".X", e.getBlockPlaced().getLocation().getX());
                        conf.getConfig().set("Points." + reply + ".Y", e.getBlockPlaced().getLocation().getY());
                        conf.getConfig().set("Points." + reply + ".Z", e.getBlockPlaced().getLocation().getZ());
                        return null;
                    }else{
                        p.sendMessage("§c§nポイント '" + reply + "' は既に存在します");
                    }
                    return "Incorrect.";
                });
                
            }
        }
        
        if(e.getItemInHand().getType() == Material.HEAVY_WEIGHTED_PRESSURE_PLATE){
            int X = e.getBlockPlaced().getLocation().getBlockX();
            int Y = e.getBlockPlaced().getLocation().getBlockY();
            int Z = e.getBlockPlaced().getLocation().getBlockZ();
            World w = e.getBlockPlaced().getLocation().getWorld();
            Location l1 = new Location(w, X+1, Y, Z);
            Location l2 = new Location(w, X, Y, Z+1);
            Location l3 = new Location(w, X-1, Y, Z);
            Location l4 = new Location(w, X, Y, Z-1);
            Block b1 = w.getBlockAt(l1);
            Block b2 = w.getBlockAt(l2);
            Block b3 = w.getBlockAt(l3);
            Block b4 = w.getBlockAt(l4);
            if((b1.getType() == Material.IRON_DOOR) || (b2.getType() == Material.IRON_DOOR) || (b3.getType() == Material.IRON_DOOR) || (b4.getType() == Material.IRON_DOOR)){
            }else{
                e.setCancelled(true);
            }
        }
    }
    
    @EventHandler
    public void DoorBreak(BlockBreakEvent e){
        if (e.getBlock().getBlockData().getMaterial().equals(Material.IRON_DOOR)){
            Location bbl = e.getBlock().getLocation();
            Player p = e.getPlayer();
            for (String PointName : conf.getConfig().getConfigurationSection("Points").getKeys(false)){
                String WorldName = conf.getConfig().getString("Points." + PointName + ".WorldName");
                World w = getServer().getWorld(WorldName);
                int x = conf.getConfig().getInt("Points." + PointName + ".X");
                int y = conf.getConfig().getInt("Points." + PointName + ".Y");
                int z = conf.getConfig().getInt("Points." + PointName + ".Z");
                Location bl = new Location(w, x, y, z);
                if (bbl.getBlockX() == bl.getBlockX() && bbl.getBlockZ() == bl.getBlockZ()){
                    e.setCancelled(true);
                }
            }
        }
    }
    
    @EventHandler
    public void Plate (PlayerInteractEvent e) {
        if(e.getAction().equals(Action.PHYSICAL)){
            if(e.getClickedBlock().getType() == Material.HEAVY_WEIGHTED_PRESSURE_PLATE){
                Player p = (Player)e.getPlayer();
                if (PlayerData.containsKey(p)){
                }else{
                    PlayerData.put(p, "GUI");
                    new OpenGUI().MainMenu(p, "ポイントリスト");
                }
            }
        }
    }
    
    
   
}
