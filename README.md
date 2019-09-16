# CustomNBT
CustomNBT is a bukkit api plugin that can manage custom NBT tags on items
This is possible by adding a lore line that looks empty but in this line there are stored all the tags (soo it's better don't remove it).
The tags are stored in json using color codes as memory (white for 0 and black for 1).

For mobs the tags are stored in the config file.

## Download
- [GitHub](https://github.com/MinettyxDev/customNBT/releases)

## Usage
Add the jar file to your Java build path and add CustomNBT as a dependency to your plugin.yml
```
depend: [customNBT]
```

### NBT Items
Create an NBTItem object by doing:
```
NBTItem nbtitem = new NBTItem(youritem);
```
Setting tags:
```
nbtitem.setCustomMeta("key", "value"); //add the key and values
nbtitem.getItem(); //get the result item back
```
Getting tags:
```
String value = nbtitem.getCustomMeta("key");
```
Getting all tags:
```
JsonObject json = nbtitem.getCustomMetas();
//you can use the JsonObject
```
If you have problem changing the lore you can use our api to be sure to don't touch the tags by accident
```
List<String> lore = nbtitem.getLore(); //Get the lore without the storage line
lore.add("TEST VALUE"); //you can do whatever you want with the lore
nbtitem.setLore(lore); //set to the new lore
```

### NBT Mobs
Create an NBTMob object by doing:
```
NBTMob nbtmob = new NBTMob(yourlivingentity);
```
Set tags:
```
nbtmob.setCustomMeta("key", "value");
```
Getting tags:
```
String value = nbtmob.getCustomMeta("key");
```
Getting all tags:
```
JsonObject json = nbtmob.getCustomMetas();
//you can use the JsonObject
```
