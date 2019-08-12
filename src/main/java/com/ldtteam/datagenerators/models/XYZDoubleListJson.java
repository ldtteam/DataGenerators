package com.ldtteam.datagenerators.models;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.ldtteam.datagenerators.IJsonSerializable;
import org.jetbrains.annotations.NotNull;

public class XYZDoubleListJson implements IJsonSerializable
{
    /**
     * X Value.
     */
    private double x = 0;

    /**
     * Y Value.
     */
    private double y = 0;

    /**
     * Z Value
     */
    private double z = 0;

    public XYZDoubleListJson()
    {
    }

    public XYZDoubleListJson(final int x, final int y, final int z)
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
                this.x = xyzListJson.get(0).getAsDouble();
                this.y = xyzListJson.get(1).getAsDouble();
                this.z = xyzListJson.get(2).getAsDouble();
            }
        }
    }
}
