package de.themoep.NoChickenCombat;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.logging.Level;

/**
 * Created by Phoenix616 on 07.11.2014.
 */
public class LoggoutListener implements Listener {

    NoChickenCombat plugin = null;

    public LoggoutListener(){
        this.plugin = NoChickenCombat.getPlugin();
        this.plugin.getLogger().log(Level.INFO, "Registered LoggoutListener");
    }

    @EventHandler
    public void onPlayerLoggout(PlayerQuitEvent event){
        if(event.getQuitMessage().contains("lost connection: Disconnected")
                && event.getPlayer().getGameMode() != GameMode.CREATIVE
                && !event.getPlayer().hasPermission("nochickencombat.exempt")
                && this.plugin.tagmap.containsKey(event.getPlayer().getUniqueId())
                && System.currentTimeMillis() < this.plugin.tagmap.get(event.getPlayer().getUniqueId()) + this.plugin.timeout){
            EntityDamageEvent ede = new EntityDamageEvent(event.getPlayer(), event.getPlayer().getLastDamageCause().getCause(), 1000);
            this.plugin.getServer().getPluginManager().callEvent(ede);
            event.getPlayer().damage(100.0D);
        }
    }
}
