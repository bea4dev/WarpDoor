package be4r.warpdoor;

import be4r.warpdoor.GUI.ClickItems;
import be4r.warpdoor.Glow;
import be4r.warpdoor.GUI.OpenGUI;
import be4r.warpdoor.Door;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;


/**
 *
 * @author Be4rJP
 */
public class Main extends JavaPlugin implements CommandExecutor, Listener {
    
    public static Config conf = new Config();
    public static Glow glow = new Glow();
    static Main instance;
    public static HashMap<Player, String> PlayerData = new HashMap<Player, String>();
    
    @Override
    public void onEnable() {
        Main.instance = this;
        getCommand("wd").setExecutor(this);
        getLogger().info("");
        conf.LoadConfig();
        getServer().getPluginManager().registerEvents(new ClickItems(), this);
        getServer().getPluginManager().registerEvents(new Door(), this);
        
        Bukkit.getPluginManager().registerEvents(this, this);
        
        //new recipe
        ItemStack door = new ItemStack(Material.IRON_DOOR);
        ItemMeta itemMeta = door.getItemMeta();
        itemMeta.setDisplayName("WarpDoor");
        new Glow().enchantGlow(door);
        
        door.setItemMeta(itemMeta);
        door.addEnchantment(glow, 1);
        
        ShapedRecipe recipe = new ShapedRecipe(door);
        recipe.shape("AKA",
                     "KDK",
                     "AEA");
        recipe.setIngredient('D', Material.IRON_DOOR);
        recipe.setIngredient('E', Material.ENDER_EYE);
        recipe.setIngredient('K', Material.OBSIDIAN);
        recipe.setIngredient('A', Material.AIR);
        
        Bukkit.addRecipe(recipe);
    }
    
    @Override
    public void onDisable() {
        conf.SaveConfig();
    }
    
   
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
	if (commandLabel.equalsIgnoreCase("wd")) {
            Player p = (Player)sender;
            try{
            new OpenGUI().MainMenu(p,"ポイントリスト");
            }catch (Exception e){
                p.sendMessage("実行に失敗しました");
            }
            return true;
	}
	return false;
    }
    
    public static Main getInstance() {
	return instance;
    }
    
}
