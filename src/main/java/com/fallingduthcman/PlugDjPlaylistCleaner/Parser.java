package com.fallingduthcman.PlugDjPlaylistCleaner;

import java.io.IOException;

/**
 * Created by FallingDutchman on 2015-09-04.
 */
public abstract class Parser<K, T> implements Runnable
{
    private T data;

    public T getData()
    {
        return data;
    }

    public void setData(T data)
    {
        this.data = data;
    }

    public Parser(K readable)
    {
        try
        {
            initData(readable);
        } catch (IOException ex)
        {
            System.out.println("unable to read readable: ");
            ex.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public void run()
    {
        parse();
    }

    protected abstract void parse();

    protected abstract void initData(K data) throws IOException;
}
