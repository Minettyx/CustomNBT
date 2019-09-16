package me.minettyx.customNBT;

import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class NBTMob {
	private UUID id;

	public NBTMob(Entity entity) {
		this.id = entity.getUniqueId();
	}

	public NBTMob(UUID uuid) {
		this.id = uuid;
	}

	public NBTMob setCustomMeta(String key, String value) {
		Plugin plugin = Main.getPlugin(Main.class);
		FileConfiguration config = plugin.getConfig();

		if (config.get("mobs." + this.id) != null) {
			JsonParser parser = new JsonParser();
			JsonObject json = (JsonObject) parser.parse(config.getString("mobs." + this.id));
			json.addProperty(key, value);
			config.set("mobs." + this.id, json.toString());
			plugin.saveConfig();
		} else {
			JsonObject json = new JsonObject();
			json.addProperty(key, value);
			config.set("mobs." + this.id, json.toString());
			plugin.saveConfig();
		}

		return this;
	}

	public String getCustomMeta(String key) {
		Plugin plugin = Main.getPlugin(Main.class);
		FileConfiguration config = plugin.getConfig();

		JsonParser parser = new JsonParser();
		JsonObject json = (JsonObject) parser.parse(config.getString("mobs." + this.id));

		if (json.has(key)) {
			return json.get(key).getAsString();
		} else {
			return null;
		}
	}

	public JsonObject getCustomMetas() {
		Plugin plugin = Main.getPlugin(Main.class);
		FileConfiguration config = plugin.getConfig();

		JsonParser parser = new JsonParser();
		JsonObject json = (JsonObject) parser.parse(config.getString("mobs." + this.id));

		return json;
	}
}
