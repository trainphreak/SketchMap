package com.mcplugindev.slipswhitley.sketchmap;

import com.mcplugindev.slipswhitley.sketchmap.command.SketchMapCommand;
import com.mcplugindev.slipswhitley.sketchmap.file.SketchMapLoader;
import com.mcplugindev.slipswhitley.sketchmap.listener.PlayerListener;
import com.mcplugindev.slipswhitley.sketchmap.map.SketchMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class SketchMapPlugin extends JavaPlugin
{
    private static SketchMapPlugin plugin;
    private FileConfiguration config = getConfig();

    private static int maxDimension;
    private static SketchMap.PrivacyLevel defaultPrivacyLevel;
    private static int maxOwnedMaps; // 0 is unlimited

    public void onEnable()
    {
        SketchMapPlugin.plugin = this;
        SketchMapUtils.setupPermissions();
        this.setupConfig();
        this.loadConfig();
        this.setupCommands();
        this.setupListeners();
        SketchMapLoader.loadMaps();
        this.sendEnabledMessage();
    }

    private void loadConfig()
    {
        maxDimension = config.getInt("default-max-dimension");
        SketchMapUtils.sendColoredConsoleMessage(ChatColor.YELLOW + "[SketchMap] Default max sketchmap dimensions: " + maxDimension + "x" + maxDimension);
        String tempPrivacyLevel = config.getString("default-privacy-level");
        if (tempPrivacyLevel.equalsIgnoreCase("private"))
        {
            defaultPrivacyLevel = SketchMap.PrivacyLevel.PRIVATE;
            SketchMapUtils.sendColoredConsoleMessage(ChatColor.YELLOW + "[SketchMap] Default privacy level: Private");
        }
        else
        {
            defaultPrivacyLevel = SketchMap.PrivacyLevel.PUBLIC;
            SketchMapUtils.sendColoredConsoleMessage(ChatColor.YELLOW + "[SketchMap] Default privacy level: Public");
        }
        maxOwnedMaps = config.getInt("default-max-owned-maps");
        SketchMapUtils.sendColoredConsoleMessage(ChatColor.YELLOW + "[SketchMap] Default max owned maps: " + maxOwnedMaps);
    }

    private void sendEnabledMessage()
    {
        SketchMapUtils.sendColoredConsoleMessage(ChatColor.GREEN + "|                                                   |");
        SketchMapUtils.sendColoredConsoleMessage(ChatColor.GREEN + "|        "
                + ChatColor.AQUA + " SketchMap "
                + this.getDescription().getVersion() + " has been Enabled!" // M.m.b, 5 characters total
                + ChatColor.GREEN + "         |");
        SketchMapUtils.sendColoredConsoleMessage(ChatColor.GREEN + "|    "
                + ChatColor.AQUA + "Original authors: SlipsWhitley & Fyrinlight"
                + ChatColor.GREEN + "    |");
        SketchMapUtils.sendColoredConsoleMessage(ChatColor.GREEN + "|        "
                + ChatColor.AQUA + "Updated and modified by Trainphreak"
                + ChatColor.GREEN + "        |");
        SketchMapUtils.sendColoredConsoleMessage(ChatColor.GREEN + "|                                                   |");
    }

    private void setupCommands()
    {
        this.getCommand("sketchmap").setExecutor(new SketchMapCommand());
    }

    private void setupConfig()
    {
        config.addDefault("default-max-dimension", 10);
        config.addDefault("default-privacy-level", "public");
        config.addDefault("default-max-owned-maps", 0);
        config.options().copyDefaults(true);
        saveConfig();
    }

    private void setupListeners()
    {
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
    }

    public static SketchMapPlugin getPlugin()
    {
        return SketchMapPlugin.plugin;
    }

    public static int getMaxDimension()
    {
        return maxDimension;
    }

    public static SketchMap.PrivacyLevel getDefaultPrivacyLevel()
    {
        return defaultPrivacyLevel;
    }

    public static int getMaxOwnedMaps()
    {
        return maxOwnedMaps;
    }
}
