package com.fallingduthcman.PlugDjPlaylistCleaner.Data;

import java.net.URL;

/**
 * Created by FallingDutchman on 2015-09-04.
 */
public class Song
{
    private final String title;
    private final String author;
    private final URL url;

    public Song(String title, String author, URL url)
    {
        this.title = title;
        this.author = author;
        this.url = url;
    }

    public String getTitle()
    {
        return title;
    }

    public String getAuthor()
    {
        return author;
    }

    public URL getUrl()
    {
        return url;
    }

    @Override
    protected Song clone()
    {
        return new Song(title, author, url);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Song)) return false;

        Song song = (Song) o;

        if (!title.equals(song.title)) return false;
        if (!author.equals(song.author)) return false;
        if (!url.equals(song.url)) return false;
        return true;

    }

    @Override
    public int hashCode()
    {
        int result = title.hashCode();
        result = 31 * result + author.hashCode();
        result = 31 * result + url.hashCode();
        return result;
    }

    @Override
    public String toString()
    {
        return String.format("title= '%s', author= '%s' , url= '%s'", title, author, url);
    }
}
