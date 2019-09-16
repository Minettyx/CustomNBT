package me.minettyx.customNBT;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONObject;

public class Main extends JavaPlugin implements Listener {
	public void onEnable() {
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		String message = event.getMessage();
		Player player = event.getPlayer();
		
		if(message.startsWith("@nbt")) {
			event.setCancelled(true);
			String[] args = message.split(" ");
			
			if(args[1].equals("set")) {
				NBTItem item = new NBTItem(player.getItemInHand());
				item.setCustomMeta(args[2], args[3]);
				player.setItemInHand(item.getItem());
			}
			
			if(args[1].equals("get")) {
				NBTItem item = new NBTItem(player.getItemInHand());
				JSONObject metas = item.getCustomMetas();
				player.sendMessage(metas.toJSONString());
			}
			
			if(args[1].equals("mob")) {
				if(player.getNearbyEntities(2, 2, 2).size() > 0) {
					Entity entity = player.getNearbyEntities(2, 2, 2).get(0);
					
					if(args[2].equals("set")) {
						NBTMob mob = new NBTMob(entity);
						mob.setCustomMeta(args[3], args[4]);
					}
					if(args[2].equals("get")) {
						NBTMob mob = new NBTMob(entity);
						player.sendMessage(mob.getCustomMetas().toString());
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onDeath(EntityDeathEvent event) {
		Plugin plugin = Main.getPlugin(Main.class);
		FileConfiguration config = plugin.getConfig();
		LivingEntity entity = event.getEntity();
		
		if(config.getString("mobs."+entity.getUniqueId()) != null) {
			config.set("mobs."+entity.getUniqueId(), null);
			plugin.saveConfig();
		}
	}
}