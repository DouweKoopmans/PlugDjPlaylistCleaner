package com.fallingduthcman.PlugDjPlaylistCleaner;

import com.fallingduthcman.PlugDjPlaylistCleaner.Data.Playlist;

import java.util.Map;

/**
 * Created by FallingDutchman on 2015-09-04.
 */
public class Utils
{
    private static Map<String, Playlist> blPlaylists;

    public static Map<String, Playlist> getBlPlaylists()
    {
        return blPlaylists;
    }

    private static Map<String, Playlist> userPlaylists;

    public static Map<String, Playlist> getUserPlaylists()
    {
        return userPlaylists;
    }
}
