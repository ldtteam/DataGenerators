package com.ldtteam.datagenerators.loot_table.pool;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ldtteam.datagenerators.IJsonSerializable;
import org.jetbrains.annotations.NotNull;

@Deprecated(forRemoval = true)
public class PoolRollsJson implements IJsonSerializable
{
    /**
     *  Maximum number of rolls. Inclusive.
     */
    private int max;

    /**
     *  Minimum number of rolls. Inclusive.
     */
    private int min;

    public PoolRollsJson() {}

    public PoolRollsJson(final int max, final int min)
    {
        this.max = max;
        this.min = min;
    }

    @NotNull
    @Override
    public JsonElement serialize()
    {
        final JsonObject returnValue = new JsonObject();

        returnValue.addProperty("max", this.max);
        returnValue.addProperty("min", this.min);

        return returnValue;
    }

    @Override
    public void deserialize(@NotNull final JsonElement jsonElement)
    {
        final JsonObject rollsJson = new JsonObject();

        this.max = rollsJson.get("max").getAsInt();
        this.min = rollsJson.get("min").getAsInt();
    }

    public int getMax()
    {
        return max;
    }

    public void setMax(final int max)
    {
        this.max = max;
    }

    public int getMin()
    {
        return min;
    }

    public void setMin(final int min)
    {
        this.min = min;
    }
}
