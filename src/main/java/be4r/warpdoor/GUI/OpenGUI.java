package be4r.warpdoor.GUI;

import static be4r.warpdoor.Main.conf;

import static be4r.warpdoor.Main.PlayerData;
import java.util.ArrayList;
import java.util.List;
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
            if (slotnum <= 44){
                inv.setItem(slotnum, item);
                if (slotnum == 44){
                    fulled = true;
                }
            }
        }
        
        
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
        
        p.playNote(p.getLocation(), Instrument.STICKS, Note.flat(1, Note.Tone.C));
        p.openInventory(inv);
    }
    
    public void AddPoint(Player p){
        if (fulled != true){
            Inventory inv = Bukkit.createInventory(p, InventoryType.ANVIL);
            ItemStack item = new ItemStack(Material.ENDER_EYE);
            ItemMeta itemm = item.getItemMeta();
            itemm.setDisplayName("ポイント名を入力してください");
            item.setItemMeta(itemm);
            inv.setItem(0, item);
            p.openInventory(inv);
        }
    }
    
    
}
