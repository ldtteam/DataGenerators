package com.ldtteam.datagenerators.blockstate.multipart;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ldtteam.datagenerators.IJsonSerializable;
import com.ldtteam.datagenerators.Utils;
import org.jetbrains.annotations.NotNull;

import java.util.TreeMap;
import java.util.Map;

public class MultipartOrJson implements IJsonSerializable
{
    /**
     * A list of cases that all have to match the block to return true.
     * <p>
     * Key is:
     * Name of a block state.
     * <p>
     * Value is:
     * A single case that has to match one of the block states.
     * It can be set to a list separated by | to allow multiple values to match.
     */
    @NotNull
    private Map<String, String> states = new TreeMap<>();

    public MultipartOrJson()
    {
    }

    public MultipartOrJson(@NotNull final Map<String, String> orElements)
    {
        this.states = Utils.ensureTreeMap(orElements);
    }

    @Override
    public void deserialize(@NotNull final JsonElement jsonElement)
    {
        final JsonObject orJson = jsonElement.getAsJsonObject();

        for (Map.Entry<String, JsonElement> jsonElementEntry : orJson.entrySet())
        {
            this.states.put(jsonElementEntry.getKey(), jsonElementEntry.getValue().getAsString());
        }
    }

    @NotNull
    @Override
    public JsonElement serialize()
    {
        final JsonObject returnValue = new JsonObject();
        for (Map.Entry<String, String> orElementEntry : this.states.entrySet())
        {
            returnValue.addProperty(orElementEntry.getKey(), orElementEntry.getValue());
        }

        return returnValue;
    }

    @NotNull
    public Map<String, String> getStates()
    {
        return states;
    }

    public void setStates(@NotNull final Map<String, String> states)
    {
        this.states = Utils.ensureTreeMap(states);
    }
}
