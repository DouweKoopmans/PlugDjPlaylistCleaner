package com.fallingduthcman.PlugDjPlaylistCleaner.Data;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by FallingDutchman on 2015-09-04.
 */
public class Playlist extends ArrayList<Song>
{
    private final String name;

    public Playlist(int initialCapacity, String name){
        super(initialCapacity);
        this.name = name;
    }
    public Playlist(Collection<? extends Song> c, String name){
        super(c);
        this.name = name;
    }

    public Playlist(String name)
    {
        this.name = name;
    }

    public boolean hasTitle(final String title)
    {
        for (Song song : this)
            if (song.getTitle().equals(title)) return true;
        return false;
    }

    public boolean hasUrl(URL url)
    {
        for (Song song : this)
            if (song.getUrl().equals(url)) return true;
        return false;
    }

    public boolean hasUrl(String url) throws MalformedURLException
    {
        return hasUrl(new URL(url));
    }

    public boolean hasSong(Song song)
    {
        for (Song song1 : this)
            if (song1.equals(song)) return true;
        return false;
    }

    public boolean hasAuthor(String author)
    {
        for (Song song : this)
            if (song.getAuthor().equals(author)) return true;
        return false;
    }

    /**
     * Creates a deep copy of these elements.
     * @return a deep copy
     */
    @Override
    public Playlist clone() {
        Playlist clone = new Playlist(size(), name);

        for(Song e : this)
            clone.add(e.clone());

        return clone;
    }

    /**
     * returns the name of the playlist
     * @return the name
     */
    @Override
    public String toString()
    {
        return name;
    }
}
