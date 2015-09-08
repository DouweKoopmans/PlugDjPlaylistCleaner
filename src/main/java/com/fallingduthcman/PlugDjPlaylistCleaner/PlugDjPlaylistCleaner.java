package com.fallingduthcman.PlugDjPlaylistCleaner;

import com.fallingduthcman.PlugDjPlaylistCleaner.Data.Playlist;
import com.fallingduthcman.PlugDjPlaylistCleaner.Data.Song;
import com.fallingduthcman.PlugDjPlaylistCleaner.Parsers.HtmlParser;
import com.fallingduthcman.PlugDjPlaylistCleaner.Parsers.JsonParser;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by FallingDutchman on 2015-09-04.
 */
public class PlugDjPlaylistCleaner
{
    private final File JSON_LOC;
    private String BlUrl = "http://www.umcookies.com/blacklist";

    private static final ArrayList<Result> results = new ArrayList<>();

    public PlugDjPlaylistCleaner(File jsonLoc)
    {
        this.JSON_LOC = jsonLoc;
        try
        {
            BlUrl = System.getProperty("PlaylistCleaner.BlUrl");
        } catch (Exception ignored){}

    }

    public static void main(String[] args)
    {
        File file;
        try
        {
            file = new File(System.getProperty("PlaylistCleaner.location"));
            if (!file.exists())
            {
                System.out.println("the given file location is incorrect, please run this program again with the correct" +
                        "location");
                System.out.println("use \"java PlugDjPlaylistCleaner <file location>\" to launch this program");
                return;
            } else System.out.println("found the playlist file, launching program");
        } catch (Exception ex)
        {
            System.out.println("please pass the location of a JSON file containing Plug.dj playlist info");
            return;
        }

        new PlugDjPlaylistCleaner(file).go();
    }

    private void go()
    {
        URL bl = null;
        try
        {
            bl = new URL(BlUrl);
        } catch (MalformedURLException ex)
        {
            System.out.println("there was an error whilst trying to parse the URL for blacklisted songs:");
            ex.printStackTrace();
        }
        ArrayList<Thread> threads = new ArrayList<>();
        threads.add(new Thread(new JsonParser(JSON_LOC)));
        threads.add(new Thread(new HtmlParser(bl)));

        threads.forEach(java.lang.Thread::start);

        try
        {
            for (Thread thread : threads)
                thread.join();
        } catch (Exception ignored){}

        for (Map.Entry<String, Playlist> entry : Utils.getUserPlaylists().entrySet())
        {
            final Playlist playlist = entry.getValue();
            for (Song song : playlist)
            {
                for (Map.Entry<String, Playlist> blEntry : Utils.getBlPlaylists().entrySet())
                {
                    final Playlist blPlaylist = blEntry.getValue();
                    if (blPlaylist.hasSong(song) || blPlaylist.hasUrl(song.getUrl()))
                    {
                        results.add(new Result(song, blPlaylist.toString(), playlist.toString()));
                    }
                }
            }
        }

        printResults();
    }

    private static void printResults()
    {
        System.out.println("---------------------------------------------------------------");
        System.out.println("---------------------------Results!----------------------------");
        System.out.println("---------------------------------------------------------------");
        System.out.println(String.format("total number of blacklisted songs in your playlits: %s\n", results.size()));

        System.out.println("----------------------------songs------------------------------");

        for (Result result : results)
        {
            System.out.println(String.format("Playlist= '%s', blacklist category= '%s', song info: %s", result.getPlaylist(), result.blType, result.song.toString()));
        }
    }

    private class Result
    {
        private final Song song;
        private final String blType;
        private final String playlist;

        public Result(Song song, String blType, String playlist)
        {
            this.song = song;
            this.blType = blType;
            this.playlist = playlist;
        }

        public Song getSong()
        {
            return song;
        }

        public String getBlType()
        {
            return blType;
        }

        public String getPlaylist()
        {
            return playlist;
        }
    }
}
