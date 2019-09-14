# customNBT
CustomNBT is a bukkit api plugin that can manage custom NBT tags on items (more support like blocks and mobs coming soon).
This is possible by adding a lore line that looks empty but in this line there are stored all the tags (soo it's better don't remove it).
The tags are stored in json using color codes as memory (white for 0 and black for 1).

## Download
- [GitHub](https://github.com/MinettyxDev/customNBT/releases)
- Spigot
- Bukkit

## Usage
Add the jar file to your Java build path and add CustomNBT as a dependency to your plugin.yml
```
depend: [CustomNBT]
```
Create an NBTItem object by doing:
```
NBTItem nbtitem = new NBTItem(youritem);
```
Now you can use it to add or get tags:
```
nbtitem.setCustomMeta("key", "value"); //add the key and values
nbtitem.getItem(); //get the result item back
```
If you have problem changing the lore you can use our api to be sure to don't touch the tags by accident
```
List<String> lore = nbtitem.getLore(); //Get the lore without the storage line
lore.add("TEST VALUE"); //you can do whatever you want with the lore
nbtitem.setLore(lore); //set to the new lore
```