package de.themoep.NoChickenCombat;

import de.themoep.NoChickenCombat.Listeners.EverythingListener;
import de.themoep.NoChickenCombat.Listeners.LogoutListener;
import de.themoep.NoChickenCombat.Listeners.MobListener;
import de.themoep.NoChickenCombat.Listeners.PvPListener;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

public class NoChickenCombat extends JavaPlugin {

    private static NoChickenCombat plugin;

    private boolean pvp = false;
    private boolean mobs = false;
    private boolean all = false;


    long timeout;

    HashMap<UUID, Long> tagmap = new HashMap<UUID, Long>();

    public HashSet<UUID> kickset = new HashSet<UUID>();

    private LogoutListener ll = null;
    private PvPListener pl = null;
    private MobListener ml = null;
    private EverythingListener el = null;

    public void onEnable(){
        this.plugin = this;

        this.getLogger().log(Level.INFO, "Loading Config...");
        this.saveDefaultConfig();

        timeout = this.getConfig().getInt("timeout") * 1000;
        this.getLogger().log(Level.INFO, "Combat Timeout: " + timeout + "ms");

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
        this.ll = new LogoutListener();
        this.getServer().getPluginManager().registerEvents(this.ll, this);
        if(all){
            pvp = false;
            mobs = false;
            this.el = new EverythingListener();
            this.getServer().getPluginManager().registerEvents(this.el, this);
        } else {
            if (pvp) {
                this.pl = new PvPListener();
                this.getServer().getPluginManager().registerEvents(this.pl, this);
            }
            if (mobs) {
                this.ml = new MobListener();
                this.getServer().getPluginManager().registerEvents(this.ml, this);
            }
        }
    }

    public static NoChickenCombat getPlugin(){
        return plugin;
    }

    public void onDisable(){
        HandlerList.unregisterAll(this.ll);
        if(this.el != null)
            HandlerList.unregisterAll(this.el);
        if(this.pl != null)
            HandlerList.unregisterAll(this.el);
        if(this.ml != null)
            HandlerList.unregisterAll(this.el);
    }

    public void tag(Player player) {
        if (player.hasPermission("nochickencombat.exempt")) return;
        this.tagmap.put(player.getUniqueId(), System.currentTimeMillis());
    }

    public boolean isTagged(Player player) {
        if(this.tagmap.containsKey(player.getUniqueId())){
            if(System.currentTimeMillis() < this.tagmap.get(player.getUniqueId()) + this.timeout)
                return true;
            else
                this.tagmap.remove(player.getUniqueId());
        }
        return false;
    }
}
