package com.fallingduthcman.PlugDjPlaylistCleaner.Parsers;

import com.fallingduthcman.PlugDjPlaylistCleaner.Data.Playlist;
import com.fallingduthcman.PlugDjPlaylistCleaner.Data.Song;
import com.fallingduthcman.PlugDjPlaylistCleaner.Parser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.stream.Collectors;

/**
 * Created by FallingDutchman on 2015-09-04.
 */
public class HtmlParser extends Parser<URL, Document>
{
    private String[] sections = {"NSFW","JUNK","THEME","SEASONAL"};

    public HtmlParser(URL data)
    {
        super(data);
    }

    @Override
    protected void parse()
    {
        for (String section : sections)
        {
            parseSection(section);
        }
    }

    private Playlist parseSection(final String sectionName)
    {
        Elements elList = getData().select(String.format("table.blacklisttable.%s tr", sectionName));

        Playlist pl = new Playlist(elList.size() - 1, sectionName);
        pl.addAll(elList.stream().map(element -> new Song(
                extractText(element.select("td").get(0)),
                extractText(element.select("td").get(1)),
                extractUrl(element.select("td").get(2)))).collect(Collectors.toList()));
        return pl;
    }

    private synchronized String extractText(Element element)
    {
        final Elements td = element.select("td");
        if (td.hasText())
        {
            return td.text();
        }
        return "";
    }

    private synchronized URL extractUrl(Element element)
    {
        final String link = element.select("a").first().attr("href");

        try
        {
            return new URL(link);
        } catch (MalformedURLException ex)
        {
            System.out.println("a url found on umcookies.com/blacklist was incorrect: " + link);
            ex.printStackTrace();
        }
        return null; //TODO figure out if this won't cause NPE's down the line
    }

    @Override
    protected void initData(URL data) throws IOException
    {
        setData(Jsoup.connect(data.toString()).get());
    }
}
