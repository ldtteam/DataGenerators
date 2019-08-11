package com.ldtteam.datagenerators.blockstate.multipart;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ldtteam.datagenerators.IJsonSerializable;
import net.minecraft.util.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MultipartWhenJson implements IJsonSerializable
{

    /**
     * Matches if any of the contained cases return true.
     */
    private List<MultipartOrJson> or = new ArrayList<>();

    // One or the other, but not both \\

    /**
     * Name of a block state.
     * A single case that has to match one of the block states.
     * It can be set to a list separated by | to allow multiple values to match.
     * Cannot be set along side the OR-tag
     */
    private Tuple<String, String> state = null;

    public MultipartWhenJson() {}

    public MultipartWhenJson(final List<MultipartOrJson> or)
    {
        this.or = or;
    }

    public MultipartWhenJson(final Tuple<String, String> state)
    {
        this.state = state;
    }

    @Override
    public void deserialize(JsonElement jsonElement)
    {
        final JsonObject whenJson = jsonElement.getAsJsonObject();

        if (whenJson.has("OR"))
        {
            for (JsonElement element : whenJson.getAsJsonArray("OR"))
            {
                final MultipartOrJson orJson = new MultipartOrJson();
                orJson.deserialize(element);
                this.or.add(orJson);
            }
        }
        else
        {
            for (Map.Entry<String, JsonElement> jsonElementEntry : whenJson.entrySet())
            {
                if (!jsonElementEntry.getKey().equals("OR"))
                {
                    this.state = new Tuple<>(jsonElementEntry.getKey(), jsonElementEntry.getValue().getAsString());
                    break;
                }
            }
        }
    }

    @Override
    public JsonElement serialize()
    {

        final JsonObject returnValue = new JsonObject();

        if (this.or.isEmpty())
        {
            returnValue.addProperty(this.state.getA(), this.state.getB());
        }
        else
        {
            final JsonArray orArray = new JsonArray();
            for (MultipartOrJson multipartOrJson : this.or)
            {
                orArray.add(multipartOrJson.serialize());
            }
            returnValue.add("OR", orArray);
        }

        return returnValue;
    }
}
