package me.minettyx.customNBT;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class NBTItem {
	private ItemStack storedstack;

	public NBTItem(ItemStack storeditem) {
		this.storedstack = storeditem;
	}

	@SuppressWarnings("unchecked")
	public NBTItem setCustomMeta(String key, Object value) {
		//Plugin plugin = Main.getPlugin(Main.class);
		
		List<String> lore = this.storedstack.getItemMeta().getLore();
		if (lore != null) {
			if (!iscorrectformat(lore.get(lore.size() - 1))) {
				JSONObject json = new JSONObject();
				json.put(key, value);

				ItemMeta meta = this.storedstack.getItemMeta();
				List<String> lores = meta.getLore();
				List<String> newlore = new ArrayList<String>();
				newlore.addAll(lores);
				newlore.add(StringToBinColorCodes(json.toString()));

				meta.setLore(newlore);
				this.storedstack.setItemMeta(meta);
				return this;
			}
			String jsontext = getStringFromBinColorCodes(lore.get(lore.size() - 1));

			JSONParser parser = new JSONParser();
			try {
				JSONObject json = (JSONObject) parser.parse(jsontext);

				json.put(key, value);

				ItemMeta meta = this.storedstack.getItemMeta();
				List<String> lores = meta.getLore();
				lores.set(lores.size() - 1, StringToBinColorCodes(json.toString()));
				meta.setLore(lores);
				this.storedstack.setItemMeta(meta);

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			JSONObject json = new JSONObject();
			json.put(key, value);

			ItemMeta meta = this.storedstack.getItemMeta();
			List<String> newlore = new ArrayList<String>();
			newlore.add(StringToBinColorCodes(json.toString()));
			meta.setLore(newlore);
			this.storedstack.setItemMeta(meta);
		}
		return this;
	}

	public Object getCustomMeta(String key) {
		String lore = this.storedstack.getItemMeta().getLore().get(this.storedstack.getItemMeta().getLore().size() - 1);
		String jsontext = getStringFromBinColorCodes(lore);

		JSONParser parser = new JSONParser();
		try {
			JSONObject json = (JSONObject) parser.parse(jsontext);

			return json.get(key);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public JSONObject getCustomMetas() {
		String lore = this.storedstack.getItemMeta().getLore().get(this.storedstack.getItemMeta().getLore().size() - 1);
		String jsontext = getStringFromBinColorCodes(lore);

		JSONParser parser = new JSONParser();
		try {
			JSONObject json = (JSONObject) parser.parse(jsontext);

			return json;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private String StringToBinColorCodes(String s) {
		String result = "";

		byte[] bytes = s.getBytes();
		StringBuilder binary = new StringBuilder();
		for (byte b : bytes) {
			int val = b;
			for (int i = 0; i < 8; i++) {
				binary.append((val & 128) == 0 ? 0 : 1);
				val <<= 1;
			}
			binary.append('-');
		}

		for (char c : binary.toString().toCharArray()) {
			String cc = Character.toString(c);
			if (cc.equals("0")) {
				result += ChatColor.WHITE;
			} else if (cc.equals("1")) {
				result += ChatColor.BLACK;
			} else if (cc.equals("-")) {
				result += ChatColor.BLUE;
			}
		}

		return result;
	}

	private String getStringFromBinColorCodes(String colorname) {
		String result = "";

		for (char c : colorname.toCharArray()) {
			String cc = Character.toString(c);
			if (cc.equals("f")) {
				result += "0";
			} else if (cc.equals("0")) {
				result += "1";
			} else if (cc.equals("9")) {
				result += "-";
			}
		}

		String reresult = "";
		for (String binchar : result.split("-")) {
			int numchar = Integer.parseInt(binchar, 2);
			reresult += new Character((char) numchar).toString();
		}
		return reresult;
	}

	public ItemStack getItem() {
		return this.storedstack;
	}

	private boolean iscorrectformat(String string) {
		String res = string.replace("§", "").replace("0", "").replace("f", "").replace("9", "");

		if (res.equals("")) {
			System.out.println("true");
			return true;
		} else {
			System.out.println("false");
			return false;
		}
	}

	// LORE THINGS

	public List<String> getLore() {
		List<String> lores = this.storedstack.getItemMeta().getLore();
		if (iscorrectformat(lores.get(lores.size() - 1))) {
			lores.remove(lores.size() - 1);
		}
		return lores;
	}

	public void setLore(List<String> lores) {
		String storageBytes = "";
		
		List<String> oldlore = this.storedstack.getItemMeta().getLore();
		if (iscorrectformat(oldlore.get(oldlore.size() - 1))) {
			storageBytes = oldlore.get(oldlore.size() - 1);
		}
		
		List<String> newlore = new ArrayList<>();
		newlore.addAll(lores);
		newlore.add(storageBytes);
		
		ItemMeta meta = this.storedstack.getItemMeta();
		meta.setLore(newlore);
		this.storedstack.setItemMeta(meta);
	}
	
	public Boolean isNBTItem() {
		if(this.storedstack.hasItemMeta()) {
			if(this.storedstack.getItemMeta().hasLore()) {
				String string = this.storedstack.getItemMeta().getLore().get(this.storedstack.getItemMeta().getLore().size()-1);
				String res = string.replace("§", "").replace("0", "").replace("f", "").replace("9", "");
		
				if (res.equals("")) {
					System.out.println("true");
					return true;
				} else {
					System.out.println("false");
					return false;
				}
			}
		}
		return false;
	}
}
