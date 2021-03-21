package com.ldtteam.datagenerators.loot_table.pool.entry.functions;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

/**
 * Function to set the count of an item stack
 */
public class SetCountFunctionJson implements IEntryFunction
{
    public static final String NAME = "minecraft:set_count";

    private int count = 1;

    public SetCountFunctionJson()
    {
    }

    public SetCountFunctionJson(final int count)
    {
        this.count = count;
    }

    public int getCount()
    {
        return count;
    }

    public void setCount(final int count)
    {
        this.count = count;
    }

    @NotNull
    @Override
    public JsonElement serialize()
    {
        final JsonObject returnValue = new JsonObject();

        returnValue.addProperty("function", NAME);
        returnValue.addProperty("count", count);

        return returnValue;
    }

    @Override
    public void deserialize(@NotNull JsonElement jsonElement)
    {
        final JsonObject jsonObject = jsonElement.getAsJsonObject();
        if (jsonObject.has("count"))
        {
            setCount(jsonObject.get("count").getAsInt());
        }
    }
}
