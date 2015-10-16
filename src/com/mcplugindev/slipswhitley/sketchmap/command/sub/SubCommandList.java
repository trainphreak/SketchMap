package com.mcplugindev.slipswhitley.sketchmap.command.sub;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.mcplugindev.slipswhitley.sketchmap.SketchMapUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.mcplugindev.slipswhitley.sketchmap.command.SketchMapSubCommand;
import com.mcplugindev.slipswhitley.sketchmap.map.SketchMap;

public class SubCommandList extends SketchMapSubCommand {
	@Override
	public String getSub() {
		return "list";
	}

	@Override
	public String getPermission() {
		return "sketchmap.list";
	}

	@Override
	public String getDescription() {
		return "List all current loaded maps";
	}

	@Override
	public String getSyntax() {
		return "/sketchmap list";
	}

	@Override
	public void onCommand(final CommandSender sender, final String[] args, final String prefix) {
		//sender.sendMessage(ChatColor.GREEN + "List of SketchMaps loaded!");
		final List<String> maps = new ArrayList<String>();
		if (SketchMap.getLoadedMaps().isEmpty()) {
			sender.sendMessage(ChatColor.RED + prefix + "No maps have been loaded to list.");
			return;
		}
		for (final SketchMap map : SketchMap.getLoadedMaps()) {
			if (map.isPublicProtected()) {
				continue;
			}
			maps.add(map.getID());
		}

        Collections.sort(maps);

		int pageSize = 15;
        int numPages = (maps.size() / pageSize) + (maps.size() % pageSize == 0 ? 0 : 1); // This works the same as array length (last page number is one less than number of pages, yay zero!)

        int pageToShow = 0;
        if (args.length > 0)
            pageToShow = Integer.parseInt(args[0]) - 1;
        if (pageToShow < 0)
            pageToShow = 0;
        if (pageToShow >= numPages)
            pageToShow = numPages - 1;

        int pageStart = pageSize * pageToShow;
        int pageEnd = pageSize * (pageToShow + 1) - 1;
        if (pageEnd > maps.size())
            pageEnd = maps.size() - 1;

        sender.sendMessage(ChatColor.RED + prefix + ChatColor.GRAY + "Displaying page " + (pageToShow + 1) + " of " + numPages);
        for (int mapIndex = pageStart; mapIndex <= pageEnd; mapIndex++)
        {
            sender.sendMessage(ChatColor.GREEN + "- " + ChatColor.AQUA + maps.get(mapIndex));
        }
        if (pageToShow+1 < numPages)
            sender.sendMessage(ChatColor.RED + "Type " + ChatColor.BLUE + "/sketchmap list " + (pageToShow + 2) + ChatColor.RED + " to see the next page");
	}
}
