package de.themoep.NoChickenCombat;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.logging.Level;

/**
 * Created by Phoenix616 on 07.11.2014.
 */
public class EverythingListener implements Listener {

    NoChickenCombat plugin = null;

    public EverythingListener(){
        this.plugin = NoChickenCombat.getPlugin();
        this.plugin.getLogger().log(Level.INFO, "Registered EverythingListener");
    }

    @EventHandler
    public void onDamagePlayer(EntityDamageEvent event){
        if(event.getEntityType() == EntityType.PLAYER)
            NoChickenCombat.getPlugin().tag((Player) event.getEntity());
    }

    @EventHandler
    public void onPlayerDamageEntity(EntityDamageByEntityEvent event){
        if(event.getDamager().getType() == EntityType.PLAYER){
            NoChickenCombat.getPlugin().tag((Player) event.getDamager());
        } else if(event.getDamager() instanceof Projectile){
            Projectile projectile = (Projectile) event.getDamager();
            if(projectile.getShooter().getType() == EntityType.PLAYER)
                NoChickenCombat.getPlugin().tag((Player) projectile.getShooter());
        }
    }
}
