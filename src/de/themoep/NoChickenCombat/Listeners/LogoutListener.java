package de.themoep.NoChickenCombat.Listeners;

import de.themoep.NoChickenCombat.NoChickenCombat;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.logging.Level;

/**
 * Created by Phoenix616 on 07.11.2014.
 */
public class LogoutListener implements Listener {

    NoChickenCombat plugin = null;

    public LogoutListener(){
        this.plugin = NoChickenCombat.getPlugin();
        this.plugin.getLogger().log(Level.INFO, "Registered LogoutListener");
    }

    @EventHandler
    public void onPlayerLoggout(PlayerQuitEvent event){
        this.plugin.getLogger().log(Level.INFO, "PlayerQuitEvent");
        if(this.plugin.kickset.contains(event.getPlayer().getUniqueId()))
            this.plugin.kickset.remove(event.getPlayer().getUniqueId());
        else if(event.getPlayer().getGameMode() != GameMode.CREATIVE
                && !event.getPlayer().hasPermission("nochickencombat.exempt")
                && this.plugin.isTagged(event.getPlayer())){
            EntityDamageEvent.DamageCause dc = event.getPlayer().getLastDamageCause().getCause();
            if(dc == null)
                dc = EntityDamageEvent.DamageCause.SUICIDE;
            EntityDamageEvent ede = new EntityDamageEvent(event.getPlayer(), dc, 1000);
            this.plugin.getServer().getPluginManager().callEvent(ede);
            event.getPlayer().damage(100.0D);
        }
    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent event){
        this.plugin.kickset.add(event.getPlayer().getUniqueId());
    }
}
