package com.mcplugindev.slipswhitley.sketchmap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.mcplugindev.slipswhitley.sketchmap.command.SketchMapCommand;
import com.mcplugindev.slipswhitley.sketchmap.file.SketchMapLoader;
import com.mcplugindev.slipswhitley.sketchmap.listener.PlayerListener;

public class SketchMapPlugin extends JavaPlugin {
	private static SketchMapPlugin plugin;

	public void onEnable() {
		SketchMapPlugin.plugin = this;
		SketchMapUtils.setupPermissions();
		this.setupCommands();
		this.setupListeners();
		SketchMapLoader.loadMaps();
		this.sendEnabledMessage();
	}

	private void sendEnabledMessage() {
		SketchMapUtils.sendColoredConsoleMessage(ChatColor.GREEN + "|                                                   |");
		SketchMapUtils.sendColoredConsoleMessage(ChatColor.GREEN + "|        "
                                                         + ChatColor.AQUA + " SketchMap "
                                                     + this.getDescription().getVersion() + " has been Enabled!"
                                                                                          + ChatColor.GREEN + "         |");
		SketchMapUtils.sendColoredConsoleMessage(ChatColor.GREEN + "|        "
                                                         + ChatColor.AQUA + " Authors: SlipsWhitley & Fyrinlight "
                                                                                            + ChatColor.GREEN + "       |");
		SketchMapUtils.sendColoredConsoleMessage(ChatColor.GREEN + "|        "
                                                         + ChatColor.AQUA + " Updated and modified by Trainphreak"
                                                                                            + ChatColor.GREEN + "       |");
		SketchMapUtils.sendColoredConsoleMessage(ChatColor.GREEN + "|                                                   |");
	}

	private void setupCommands() {
		this.getCommand("sketchmap").setExecutor(new SketchMapCommand());
	}

	private void setupListeners() {
		Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
	}

	public static SketchMapPlugin getPlugin() {
		return SketchMapPlugin.plugin;
	}
}
