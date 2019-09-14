package me.minettyx.customNBT;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
  public void onEnable() {
    getConfig().options().copyDefaults(true);
    saveConfig();
  }
}
