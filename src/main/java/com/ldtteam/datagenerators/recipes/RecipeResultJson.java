package com.ldtteam.datagenerators.recipes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ldtteam.datagenerators.IJsonSerializable;
import org.jetbrains.annotations.NotNull;

@Deprecated(forRemoval = true)
public class RecipeResultJson implements IJsonSerializable
{

    /**
     * Optional.
     * The amount of the item. Will fallback to 1 if the field is absent.
     */
    private int count;

    /**
     * An item ID.
     */
    @NotNull
    private String item = "";

    public RecipeResultJson() {}

    public RecipeResultJson(final int count, @NotNull final String item)
    {
        this.count = count;
        this.item = item;
    }

    @NotNull
    @Override
    public JsonElement serialize()
    {
        final JsonObject returnValue = new JsonObject();

        if (this.count != 0)
            returnValue.addProperty("count", this.count);

        returnValue.addProperty("item", this.item);

        return returnValue;
    }

    @Override
    public void deserialize(@NotNull final JsonElement jsonElement)
    {
        final JsonObject resultJson = jsonElement.getAsJsonObject();

        if (resultJson.has("count"))
            this.count = resultJson.get("count").getAsInt();

        this.item = resultJson.get("item").getAsString();
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
    public String getItem()
    {
        return item;
    }

    public void setItem(@NotNull final String item)
    {
        this.item = item;
    }
}
