package com.ldtteam.datagenerators.loot_table.pool;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ldtteam.datagenerators.IJsonSerializable;
import org.jetbrains.annotations.NotNull;

public class PoolBonusRollsJson implements IJsonSerializable
{
    /**
     *  Maximum number of bonus rolls. Inclusive.
     */
    private float max;

    /**
     *  Minimum number of bonus rolls. Inclusive.
     */
    private float min;

    public PoolBonusRollsJson() {}

    public PoolBonusRollsJson(final float max, final float min)
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

        this.max = rollsJson.get("max").getAsFloat();
        this.min = rollsJson.get("min").getAsFloat();
    }

    public float getMax()
    {
        return max;
    }

    public void setMax(final float max)
    {
        this.max = max;
    }

    public float getMin()
    {
        return min;
    }

    public void setMin(final float min)
    {
        this.min = min;
    }
}
