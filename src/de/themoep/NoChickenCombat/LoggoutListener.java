package de.themoep.NoChickenCombat;

import org.bukkit.event.Listener;

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

    
}
