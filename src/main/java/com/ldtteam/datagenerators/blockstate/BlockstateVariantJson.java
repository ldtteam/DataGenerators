package com.ldtteam.datagenerators.blockstate;

import com.google.gson.*;
import com.ldtteam.datagenerators.IJsonSerializable;

import java.util.ArrayList;
import java.util.List;

public class BlockstateVariantJson implements IJsonSerializable
{
    /**
     * Contains the properties of a model.
     */
    private BlockstateModelJson model;

    // One or the other, but not both \\

    /**
     * All specified models alternate in the game if in this list.
     */
    private List<BlockstateModelJson> models = new ArrayList<>();

    public BlockstateVariantJson() {}

    public BlockstateVariantJson(final BlockstateModelJson model)
    {
        this.model = model;
    }

    public BlockstateVariantJson(final List<BlockstateModelJson> models)
    {
        this.models = models;
    }

    @Override
    public void deserialize(final JsonElement json)
    {
        if (json.isJsonArray())
        {
            for (JsonElement jsonElement : json.getAsJsonArray())
            {
                BlockstateModelJson modelJson = new BlockstateModelJson();
                modelJson.deserialize(jsonElement);
                this.models.add(modelJson);
            }
        }
        else
        {
            BlockstateModelJson modelJson = new BlockstateModelJson();
            modelJson.deserialize(json);
            this.model = modelJson;
        }
    }

    @Override
    public JsonElement serialize()
    {
        if (this.models.isEmpty())
        {
            return this.model.serialize();
        }
        else
        {
            final JsonArray modelsJson = new JsonArray();

            for (final BlockstateModelJson modelJson : this.models)
            {
                modelsJson.add(modelJson.serialize());
            }

            return modelsJson;
        }
    }
}
