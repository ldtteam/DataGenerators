package com.ldtteam.datagenerators.loot_table.pool.entry.functions;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

/**
 * Function to set the NBT of an item
 */
public class SetNbtFunctionJson implements IEntryFunction
{
    public static final String NAME = "minecraft:set_nbt";

    private String tag;

    public SetNbtFunctionJson()
    {
    }

    public SetNbtFunctionJson(final String tag)
    {
        this.tag = tag;
    }

    public String getTag()
    {
        return tag;
    }

    public void setTag(final String tag)
    {
        this.tag = tag;
    }

    @NotNull
    @Override
    public JsonElement serialize()
    {
        final JsonObject returnValue = new JsonObject();

        returnValue.addProperty("function", NAME);
        returnValue.addProperty("tag", tag);

        return returnValue;
    }

    @Override
    public void deserialize(@NotNull JsonElement jsonElement)
    {
        final JsonObject jsonObject = jsonElement.getAsJsonObject();
        if (jsonObject.has("tag"))
        {
            setTag(jsonObject.get("tag").getAsString());
        }
    }
}
