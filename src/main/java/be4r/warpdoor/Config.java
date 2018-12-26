package be4r.warpdoor;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 *
 * @author Be4rJP
 */
public class Config {
    
    
    private FileConfiguration config;
    
   
    
    public synchronized void LoadConfig(){
        config = YamlConfiguration.loadConfiguration(new File("plugins/WarpDoor", "points.yml"));
        
    }
    
    public synchronized void SaveConfig(){
        try{
        config.save(new File("plugins/MovePoint", "points.yml"));
        }catch(Exception e){
        }
    }
    
    public FileConfiguration getConfig(){
        return config;
    }
}
