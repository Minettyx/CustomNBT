package me.minettyx.customNBT;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class NBTItem
{
  private ItemStack storedstack;
  
  public NBTItem(ItemStack storeditem) { this.storedstack = storeditem; }


  
  public NBTItem setCustomMeta(String key, Object value) {
    List<String> lore = this.storedstack.getItemMeta().getLore();
    if (lore != null) {
      if (!iscorrectformat((String)lore.get(lore.size() - 1))) {
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
      String jsontext = getStringFromBinColorCodes((String)lore.get(lore.size() - 1));
      
      JSONParser parser = new JSONParser();
      try {
        JSONObject json = (JSONObject)parser.parse(jsontext);
        
        json.put(key, value);
        
        ItemMeta meta = this.storedstack.getItemMeta();
        List<String> lores = meta.getLore();
        lores.set(lores.size() - 1, StringToBinColorCodes(json.toString()));
        meta.setLore(lores);
        this.storedstack.setItemMeta(meta);
      }
      catch (ParseException e) {
        
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
    String lore = (String)this.storedstack.getItemMeta().getLore().get(this.storedstack.getItemMeta().getLore().size() - 1);
    String jsontext = getStringFromBinColorCodes(lore);
    
    JSONParser parser = new JSONParser();
    try {
      JSONObject json = (JSONObject)parser.parse(jsontext);
      
      return json.get(key);
    } catch (ParseException e) {
      
      e.printStackTrace();
      
      return null;
    } 
  }
  private String StringToBinColorCodes(String s) {
    String result = "";
    
    byte[] bytes = s.getBytes();
    StringBuilder binary = new StringBuilder(); byte b1; int j; byte[] arrayOfByte;
    for (j = arrayOfByte = bytes.length, b1 = 0; b1 < j; ) { byte b = arrayOfByte[b1];
      byte b2 = b;
      for (int i = 0; i < 8; i++) {
        binary.append(((b2 & 0x80) == 0) ? 0 : 1);
        b2 <<= 1;
      } 
      binary.append('-'); b1++; }
    
    char[] arrayOfChar;
    for (j = arrayOfChar = binary.toString().toCharArray().length, b1 = 0; b1 < j; ) { char c = arrayOfChar[b1];
      String cc = Character.toString(c);
      if (cc.equals("0")) {
        result = String.valueOf(result) + ChatColor.WHITE;
      } else if (cc.equals("1")) {
        result = String.valueOf(result) + ChatColor.BLACK;
      } else if (cc.equals("-")) {
        result = String.valueOf(result) + ChatColor.BLUE;
      } 
      b1++; }
    
    return result;
  }
  
  private String getStringFromBinColorCodes(String colorname) {
    String result = ""; byte b; int i;
    char[] arrayOfChar;
    for (i = arrayOfChar = colorname.toCharArray().length, b = 0; b < i; ) { char c = arrayOfChar[b];
      String cc = Character.toString(c);
      if (cc.equals("f")) {
        result = String.valueOf(result) + "0";
      } else if (cc.equals("0")) {
        result = String.valueOf(result) + "1";
      } else if (cc.equals("9")) {
        result = String.valueOf(result) + "-";
      } 
      b++; }
    
    String reresult = ""; String[] arrayOfString;
    for (int j = arrayOfString = result.split("-").length, i = 0; i < j; ) { String binchar = arrayOfString[i];
      int numchar = Integer.parseInt(binchar, 2);
      reresult = String.valueOf(reresult) + (new Character((char)numchar)).toString(); i++; }
    
    return reresult;
  }

  
  public ItemStack getItem() { return this.storedstack; }

  
  private boolean iscorrectformat(String string) {
    String res = string.replace("�", "").replace("0", "").replace("f", "").replace("9", "");
    
    if (res.equals("")) {
      System.out.println("true");
      return true;
    } 
    System.out.println("false");
    return false;
  }



  
  public List<String> getLore() {
    List<String> lores = this.storedstack.getItemMeta().getLore();
    if (iscorrectformat((String)lores.get(lores.size() - 1))) {
      lores.remove(lores.size() - 1);
    }
    return lores;
  }
  
  public void setLore(List<String> lores) {
    String storageBytes = "";
    
    List<String> oldlore = this.storedstack.getItemMeta().getLore();
    if (iscorrectformat((String)oldlore.get(oldlore.size() - 1))) {
      storageBytes = (String)oldlore.get(oldlore.size() - 1);
    }
    
    List<String> newlore = new ArrayList<String>();
    newlore.addAll(lores);
    newlore.add(storageBytes);
    
    ItemMeta meta = this.storedstack.getItemMeta();
    meta.setLore(newlore);
    this.storedstack.setItemMeta(meta);
  }
}
