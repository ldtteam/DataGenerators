package com.ldtteam.datagenerators.recipes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ldtteam.datagenerators.IJsonSerializable;
import org.jetbrains.annotations.NotNull;

@Deprecated(forRemoval = true)
public class RecipeIngredientJson implements IJsonSerializable
{

    /**
     * An item or tag ID.
     */
    @NotNull
    private String ID = "";

    /**
     * is this an item tag
     */
    private boolean tag = false;

    public RecipeIngredientJson() {}

    public RecipeIngredientJson(@NotNull final String ID, final boolean tag)
    {
        this.ID = ID;
        this.tag = tag;
    }

    @NotNull
    @Override
    public JsonElement serialize()
    {
        final JsonObject returnValue = new JsonObject();

        if (tag)
            returnValue.addProperty("tag", this.ID);
        else
            returnValue.addProperty("item", this.ID);

        return returnValue;
    }

    @Override
    public void deserialize(@NotNull final JsonElement jsonElement)
    {
        final JsonObject ingredientJson = jsonElement.getAsJsonObject();

        if (ingredientJson.has("tag"))
        {
            this.ID = ingredientJson.get("tag").getAsString();
            this.tag = true;
        }
        else
        {
            this.ID = ingredientJson.get("item").getAsString();
            this.tag = false;
        }
    }

    @NotNull
    public String getID()
    {
        return ID;
    }

    public void setID(@NotNull final String ID)
    {
        this.ID = ID;
    }

    public boolean isTag()
    {
        return tag;
    }

    public void setTag(final boolean tag)
    {
        this.tag = tag;
    }
}
