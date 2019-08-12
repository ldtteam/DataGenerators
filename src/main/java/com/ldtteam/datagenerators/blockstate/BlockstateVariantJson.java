package com.ldtteam.datagenerators.blockstate;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ldtteam.datagenerators.IJsonSerializable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BlockstateVariantJson implements IJsonSerializable
{
    /**
     * Contains the properties of a model.
     */
    @Nullable
    private BlockstateModelJson model;

    // One or the other, but not both \\

    /**
     * All specified models alternate in the game if in this list.
     */
    @Nullable
    private List<BlockstateModelJson> models;

    public BlockstateVariantJson()
    {
    }

    public BlockstateVariantJson(@Nullable final BlockstateModelJson model)
    {
        this.model = model;
    }

    public BlockstateVariantJson(@Nullable final List<BlockstateModelJson> models)
    {
        this.models = models;
    }

    @Override
    public void deserialize(@NotNull final JsonElement json)
    {
        if (json.isJsonArray())
        {
            if (this.models == null)
                this.models = new ArrayList<>();

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

    @NotNull
    @Override
    public JsonElement serialize()
    {
        if (this.model != null && (this.models == null || this.models.isEmpty()))
        {
            return this.model.serialize();
        }
        else if (this.models != null)
        {
            final JsonArray modelsJson = new JsonArray();

            for (final BlockstateModelJson modelJson : this.models)
            {
                modelsJson.add(modelJson.serialize());
            }

            return modelsJson;
        }
        return new JsonObject();
    }

    @Nullable
    public BlockstateModelJson getModel()
    {
        return model;
    }

    public void setModel(@Nullable final BlockstateModelJson model)
    {
        this.model = model;
    }

    @Nullable
    public List<BlockstateModelJson> getModels()
    {
        return models;
    }

    public void setModels(@Nullable final List<BlockstateModelJson> models)
    {
        this.models = models;
    }
}
