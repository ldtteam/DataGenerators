package com.ldtteam.datagenerators.recipes.shaped;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.ldtteam.datagenerators.IJsonSerializable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ShapedPatternJson implements IJsonSerializable
{

    /**
     * All 3/2 parts of a ShapedPattern
     */
    @NotNull
    private String partA = "";
    @NotNull
    private String partB = "";
    @Nullable
    private String partC = "";

    public ShapedPatternJson() {}

    public ShapedPatternJson(@NotNull final String partA, @NotNull final String partB, @Nullable final String partC)
    {
        this.partA = partA;
        this.partB = partB;
        this.partC = partC;
    }

    @NotNull
    @Override
    public JsonElement serialize()
    {
        final JsonArray jsonArray = new JsonArray();

        jsonArray.add(this.partA);
        jsonArray.add(this.partB);

        if (this.partC != null)
            jsonArray.add(this.partC);

        return jsonArray;
    }

    @Override
    public void deserialize(@NotNull final JsonElement jsonElement)
    {
        final JsonArray patternJson = jsonElement.getAsJsonArray();

        this.partA = patternJson.get(0).getAsString();
        this.partB = patternJson.get(1).getAsString();

        if (patternJson.size() > 2)
            this.partC = patternJson.get(2).getAsString();
    }

    @NotNull
    public String getPartA()
    {
        return partA;
    }

    public void setPartA(@NotNull final String partA)
    {
        this.partA = partA;
    }

    @NotNull
    public String getPartB()
    {
        return partB;
    }

    public void setPartB(@NotNull final String partB)
    {
        this.partB = partB;
    }

    @Nullable
    public String getPartC()
    {
        return partC;
    }

    public void setPartC(@Nullable final String partC)
    {
        this.partC = partC;
    }
}
