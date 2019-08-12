package com.ldtteam.datagenerators.models;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.ldtteam.datagenerators.IJsonSerializable;
import org.jetbrains.annotations.NotNull;

public class XYZIntListJson implements IJsonSerializable
{
    /**
     * X Value.
     */
    private int x = 0;

    /**
     * Y Value.
     */
    private int y = 0;

    /**
     * Z Value
     */
    private int z = 0;

    public XYZIntListJson()
    {
    }

    public XYZIntListJson(final int x, final int y, final int z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public boolean isEmtpy()
    {
        return this.x == 0 && this.y == 0 && this.z == 0;
    }

    @NotNull
    @Override
    public JsonElement serialize()
    {
        final JsonArray returnValue = new JsonArray();

        returnValue.add(x);
        returnValue.add(y);
        returnValue.add(z);

        return returnValue;
    }

    @Override
    public void deserialize(@NotNull final JsonElement jsonElement)
    {
        if (jsonElement.isJsonArray())
        {
            final JsonArray xyzListJson = jsonElement.getAsJsonArray();

            if (xyzListJson.size() == 3)
            {
                this.x = xyzListJson.get(0).getAsInt();
                this.y = xyzListJson.get(1).getAsInt();
                this.z = xyzListJson.get(2).getAsInt();
            }
        }
    }
}
