package de.themoep.NoChickenCombat;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

public class NoChickenCombat extends JavaPlugin {

    private boolean pvp = false;
    private boolean mobs = false;
    private boolean all = false;

    HashMap<UUID, Long> timeoutmap = new HashMap<UUID, Long>();

    public void onEnable() {
        this.getLogger().log(Level.INFO, "Loading Config...");
        this.saveDefaultConfig();
        List<String> enabled = this.getConfig().getStringList("enabled");
        for(String s : enabled){
            if(s.equalsIgnoreCase("pvp")){
                pvp = true;
            } else if(s.equalsIgnoreCase("mobs")){
                mobs = true;
            } else if(s.equalsIgnoreCase("all")){
                all = true;
            }
        }

        this.getLogger().log(Level.INFO, "Registering Listeners...");
        this.getServer().getPluginManager().registerEvents(new LoggoutListener(), this);
        if(all){
            pvp = false;
            mobs = false;
            this.getServer().getPluginManager().registerEvents(new EverythingListener(), this);
        } else {
            if(pvp)
                this.getServer().getPluginManager().registerEvents(new PvPListener(), this);
            if(mobs)
                this.getServer().getPluginManager().registerEvents(new MobListener(), this);
        }
    }

    public static NoChickenCombat getPlugin(){
        return this;
    }

}
