package com.mcplugindev.slipswhitley.sketchmap.command.sub;

import com.mcplugindev.slipswhitley.sketchmap.SketchMapUtils;
import com.mcplugindev.slipswhitley.sketchmap.command.SketchMapSubCommand;
import com.mcplugindev.slipswhitley.sketchmap.map.SketchMap;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SubCommandList extends SketchMapSubCommand
{
    @Override
    public String getSub()
    {
        return "list";
    }

    @Override
    public String getPermission()
    {
        return "sketchmap.list";
    }

    @Override
    public String getDescription()
    {
        return "List all current loaded maps";
    }

    @Override
    public String getSyntax()
    {
        return "/sketchmap list";
    }

    @Override
    public void onCommand(final CommandSender sender, final String[] args, final String prefix)
    {
        final List<String> maps = new ArrayList<>();
        if (SketchMap.getLoadedMaps().isEmpty())
        {
            sender.sendMessage(ChatColor.RED + prefix + "No maps have been loaded to list.");
            return;
        }

        if (sender instanceof Player)
        {
            final Player player = (Player) sender;
            player.sendMessage(ChatColor.GREEN + "Loading list of sketchmaps...");
            for (final SketchMap map : SketchMap.getLoadedMaps())
            {
                if ((map.getPrivacyLevel() == SketchMap.PrivacyLevel.PRIVATE &&
                        !map.getOwnerUUID().equals(player.getUniqueId()) &&
                        !map.getAllowedUUID().contains(player.getUniqueId()) &&
                        !SketchMapUtils.hasPermission(player, "sketchmap.privacy.admin")) ||
                        map.isPublicProtected())
                {
                    continue;
                }
                maps.add(map.getID());
            }
        }
        else
        {
            for (final SketchMap map : SketchMap.getLoadedMaps())
            {
                if (map.isPublicProtected())
                {
                    continue;
                }
                maps.add(map.getID());
            }
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
        if (pageToShow + 1 < numPages)
            sender.sendMessage(ChatColor.RED + "Type " + ChatColor.BLUE + "/sketchmap list " + (pageToShow + 2) + ChatColor.RED + " to see the next page");
    }
}
