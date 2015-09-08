package com.fallingduthcman.PlugDjPlaylistCleaner.Parsers;

import com.fallingduthcman.PlugDjPlaylistCleaner.Parser;
import com.google.gson.JsonElement;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by FallingDutchman on 2015-09-04.
 */
public class JsonParser extends Parser<File, JsonElement>
{
    private final com.google.gson.JsonParser parser = new com.google.gson.JsonParser();

    public JsonParser(File jsonFile)
    {
        super(jsonFile);
    }

    @Override
    protected void parse()
    {

    }

    @Override
    protected void initData(File data) throws IOException
    {
        final FileReader in = new FileReader(data);
        final JsonReader jsonReader = new JsonReader(in);
        setData(parser.parse(jsonReader));
        in.close();
        jsonReader.close();
    }
}
