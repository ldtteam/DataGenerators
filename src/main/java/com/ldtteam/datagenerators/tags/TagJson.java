package com.ldtteam.datagenerators.tags;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ldtteam.datagenerators.IJsonSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TagJson implements IJsonSerializable
{
    /**
     * Optional. Whether or not contents of this tag should completely replace tag contents from different lower priority data packs with the same namespaced ID.
     * When false the tag's content will append to the contents of the higher priority data packs instead.
     */
    private boolean replace = false;

    /**
     * A list of mix and match of object names and tag names.
     * For tags, recursive reference is possible, but a circular reference will cause a loading failure.
     *
     * If placing a tag inside, prepend with a # character.
     * e.g. #minecraft:dirt_like
     */
    @NotNull
    private List<String> values = new ArrayList<>();

    public TagJson() {}

    public TagJson(final boolean replace, @NotNull final List<String> values)
    {
        this.replace = replace;
        this.values = values;
    }

    @NotNull
    @Override
    public JsonElement serialize()
    {
        final JsonObject returnValue = new JsonObject();

        if (this.replace)
            returnValue.addProperty("replace", true);

        final JsonArray valuesArray = new JsonArray();
        for (String value : this.values)
        {
            valuesArray.add(value);
        }
        returnValue.add("values", valuesArray);

        return returnValue;
    }

    @Override
    public void deserialize(@NotNull final JsonElement jsonElement)
    {
        final JsonObject tagJson = jsonElement.getAsJsonObject();

        if (tagJson.has("replaec"))
            this.replace = tagJson.get("replace").getAsBoolean();

        for (JsonElement value : tagJson.getAsJsonArray("values"))
        {
            this.values.add(value.getAsString());
        }
    }

    public boolean isReplace()
    {
        return replace;
    }

    public void setReplace(final boolean replace)
    {
        this.replace = replace;
    }

    @NotNull
    public List<String> getValues()
    {
        return values;
    }

    public void setValues(@NotNull final List<String> values)
    {
        this.values = values;
    }
}
