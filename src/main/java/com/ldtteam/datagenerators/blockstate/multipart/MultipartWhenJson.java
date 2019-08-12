package com.ldtteam.datagenerators.blockstate.multipart;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ldtteam.datagenerators.IJsonSerializable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MultipartWhenJson implements IJsonSerializable
{

    /**
     * Matches if any of the contained cases return true.
     */
    @Nullable
    private List<MultipartOrJson> or;

    // One or the other two, but not both \\

    /**
     * Name of a block state.
     */
    @Nullable
    private String state;

    /**
     * A single case that has to match one of the block states. It can be set to a list separated by | to allow multiple values to match.
     */
    @Nullable
    private String cases;

    public MultipartWhenJson()
    {
    }

    public MultipartWhenJson(@Nullable final List<MultipartOrJson> or)
    {
        this.or = or;
    }

    public MultipartWhenJson(@Nullable final String state, @Nullable final String cases)
    {
        this.state = state;
        this.cases = cases;
    }

    @Override
    public void deserialize(@NotNull final JsonElement jsonElement)
    {
        final JsonObject whenJson = jsonElement.getAsJsonObject();

        if (whenJson.has("OR"))
        {
            if (this.or == null)
                this.or = new ArrayList<>();
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
                    this.state = jsonElementEntry.getKey();
                    this.cases = jsonElementEntry.getValue().getAsString();
                    break;
                }
            }
        }
    }

    @NotNull
    @Override
    public JsonElement serialize()
    {
        final JsonObject returnValue = new JsonObject();

        if ((this.or == null || this.or.isEmpty()) && (this.state != null && this.cases != null))
        {
            returnValue.addProperty(this.state, this.cases);
        }
        else if (this.or != null)
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

    @Nullable
    public List<MultipartOrJson> getOr()
    {
        return or;
    }

    public void setOr(@Nullable List<MultipartOrJson> or)
    {
        this.or = or;
    }

    @Nullable
    public String getState()
    {
        return state;
    }

    public void setState(@Nullable String state)
    {
        this.state = state;
    }

    @Nullable
    public String getCases()
    {
        return cases;
    }

    public void setCases(@Nullable String cases)
    {
        this.cases = cases;
    }
}
