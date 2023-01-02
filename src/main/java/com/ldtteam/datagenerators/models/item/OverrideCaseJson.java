package com.ldtteam.datagenerators.models.item;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ldtteam.datagenerators.IJsonSerializable;
import org.jetbrains.annotations.NotNull;

@Deprecated(forRemoval = true)
public class OverrideCaseJson implements IJsonSerializable
{

    /**
     * Holds the cases.
     */
    @NotNull
    private OverridePredicateJson predicate = new OverridePredicateJson();

    /**
     * The path to the model to use if the case is met, starting in assets/<namespace>/models/
     */
    @NotNull
    private String model = "";

    public OverrideCaseJson() {}

    public OverrideCaseJson(@NotNull final OverridePredicateJson predicate, @NotNull final String model)
    {
        this.predicate = predicate;
        this.model = model;
    }

    @NotNull
    @Override
    public JsonElement serialize()
    {
        final JsonObject returnValue = new JsonObject();

        returnValue.addProperty("model", model);
        returnValue.add("predicate", predicate.serialize());

        return returnValue;
    }

    @Override
    public void deserialize(@NotNull final JsonElement jsonElement)
    {
        final JsonObject overrideCase = jsonElement.getAsJsonObject();

        if (overrideCase.has("model"))
            this.model = overrideCase.get("mode").getAsString();

        if (overrideCase.has("predicate"))
            this.predicate.deserialize(overrideCase.get("predicate"));

    }

    @NotNull
    public OverridePredicateJson getPredicate()
    {
        return predicate;
    }

    public void setPredicate(@NotNull final OverridePredicateJson predicate)
    {
        this.predicate = predicate;
    }

    @NotNull
    public String getModel()
    {
        return model;
    }

    public void setModel(@NotNull final String model)
    {
        this.model = model;
    }
}
