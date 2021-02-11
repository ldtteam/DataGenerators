package com.ldtteam.datagenerators.models.item;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ldtteam.datagenerators.IJsonSerializable;
import com.ldtteam.datagenerators.Utils;
import org.jetbrains.annotations.NotNull;

import java.util.TreeMap;
import java.util.Map;

public class OverridePredicateJson implements IJsonSerializable
{

    /**
     *  Holds the cases.
     */
    @NotNull
    private Map<String, Float> cases = new TreeMap<>();

    public OverridePredicateJson() {}

    public OverridePredicateJson(@NotNull final Map<String, Float> cases)
    {
        this.cases = Utils.ensureTreeMap(cases);
    }

    @NotNull
    @Override
    public JsonElement serialize()
    {
        final JsonObject returnValue = new JsonObject();

        for (Map.Entry<String, Float> casesEntry : this.cases.entrySet())
        {
            returnValue.addProperty(casesEntry.getKey(), casesEntry.getValue());
        }

        return returnValue;
    }

    @Override
    public void deserialize(@NotNull final JsonElement jsonElement)
    {
        final JsonObject predicateJson = jsonElement.getAsJsonObject();

        for (Map.Entry<String, JsonElement> entry : predicateJson.entrySet())
        {
            this.cases.put(entry.getKey(), entry.getValue().getAsFloat());
        }
    }

    @NotNull
    public Map<String, Float> getCases()
    {
        return cases;
    }

    public void setCases(@NotNull final Map<String, Float> cases)
    {
        this.cases = Utils.ensureTreeMap(cases);
    }
}
