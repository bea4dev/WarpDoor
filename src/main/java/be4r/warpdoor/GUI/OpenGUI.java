package be4r.warpdoor.GUI;

import be4r.warpdoor.Glow;
import static be4r.warpdoor.Main.conf;

import static be4r.warpdoor.Main.PlayerData;
import static be4r.warpdoor.Main.glow;
import java.util.ArrayList;
import java.util.List;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Instrument;
import org.bukkit.Material;
import org.bukkit.Note;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

/**
 *
 * @author Be4rJP
 */
public class OpenGUI {
  
    public static Boolean fulled;
    
    public void MainMenu(Player p, String listname){
        Inventory inv = Bukkit.createInventory(null, 54, listname);
        int slotnum = -1;
        for (String PointName : conf.getConfig().getConfigurationSection("Points").getKeys(false)){
            ItemStack item = new ItemStack(Material.ENDER_EYE);
            ItemMeta itemm = item.getItemMeta();
            itemm.setDisplayName(PointName);
            List lores = new ArrayList();
            lores.add("クリックでテレポート");
            lores.add("ワールド名: " + conf.getConfig().getString("Points." + PointName + ".WorldName") + "  座標: " + conf.getConfig().getString("Points." + PointName + ".X") + "," + conf.getConfig().getString("Points." + PointName + ".Y") + "," + conf.getConfig().getString("Points." + PointName + ".Z"));
            itemm.setLore(lores);
            item.setItemMeta(itemm);
            slotnum++;
            if (slotnum <= 53){
                inv.setItem(slotnum, item);
                if (slotnum == 53){
                    fulled = true;
                }
            }
        }
        
        /*
        ItemStack book = new ItemStack(Material.BOOK);
        ItemMeta bookm = book.getItemMeta();
        bookm.setDisplayName("§c§aポイント名を変更");
        book.setItemMeta(bookm);
        inv.setItem(51, book);
        
        ItemStack del = new ItemStack(Material.BARRIER);
        ItemMeta delm = del.getItemMeta();
        delm.setDisplayName("§c§nポイントを削除");
        del.setItemMeta(delm);
        inv.setItem(53, del);
        */
        p.playNote(p.getLocation(), Instrument.STICKS, Note.flat(1, Note.Tone.C));
        p.openInventory(inv);
        
    }
    
    public void DoorMenu(Player p, String listname){
        Inventory inv = Bukkit.createInventory(null, 27, listname);
        
        ItemStack book = new ItemStack(Material.BOOK);
        ItemMeta bookm = book.getItemMeta();
        bookm.setDisplayName("§a§nポイント名を変更");
        book.setItemMeta(bookm);
        
        ItemStack del = new ItemStack(Material.BARRIER);
        ItemMeta delm = del.getItemMeta();
        delm.setDisplayName("§c§nポイントを削除");
        del.setItemMeta(delm);
        
        new Glow().enchantGlow(book);
        new Glow().enchantGlow(del);
        
        book.addEnchantment(glow, 1);
        del.addEnchantment(glow, 1);
        
        inv.setItem(11, book);
        inv.setItem(15, del);
        
        p.openInventory(inv);
    }
    
    
}
