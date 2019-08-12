package com.ldtteam.datagenerators.blockstate.multipart;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ldtteam.datagenerators.IJsonSerializable;
import com.ldtteam.datagenerators.blockstate.BlockstateVariantJson;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MultipartCaseJson implements IJsonSerializable
{

    /**
     * A list of cases that have to be met for the model to be applied.
     * If unset, the model always applies.
     */
    @Nullable
    private MultipartWhenJson when;

    /**
     * A variant: Name of a variant, which consists of the relevant block states separated by commas.
     * A block with just one variant uses "" as a name for its variant.
     * Each variant can have one model or an array of models and contains their properties.
     * If set to an array, the model will randomly be chosen from the options given, with each option being specified in separate subsidiary -tags.
     * Item frames are treated as blocks and will use "map=false" for a map-less item frame, and "map=true" for item frames with maps.
     */
    @NotNull
    private BlockstateVariantJson apply = new BlockstateVariantJson();

    public MultipartCaseJson()
    {
    }

    public MultipartCaseJson(@NotNull final BlockstateVariantJson apply, @Nullable final MultipartWhenJson when)
    {
        this.when = when;
        this.apply = apply;
    }

    @Override
    public void deserialize(@NotNull final JsonElement json)
    {
        final JsonObject caseJson = json.getAsJsonObject();

        if (caseJson.has("when"))
        {
            final MultipartWhenJson whenJson = new MultipartWhenJson();
            whenJson.deserialize(caseJson.get("when"));
            this.when = whenJson;
        }
        final BlockstateVariantJson variantJson = new BlockstateVariantJson();
        variantJson.deserialize(caseJson.get("apply"));
        this.apply = variantJson;
    }

    @NotNull
    @Override
    public JsonElement serialize()
    {
        final JsonObject returnValue = new JsonObject();

        if (when != null)
        {
            returnValue.add("when", this.when.serialize());
        }
        returnValue.add("apply", this.apply.serialize());

        return returnValue;
    }

    @Nullable
    public MultipartWhenJson getWhen()
    {
        return when;
    }

    public void setWhen(@Nullable final MultipartWhenJson when)
    {
        this.when = when;
    }

    @NotNull
    public BlockstateVariantJson getApply()
    {
        return apply;
    }

    public void setApply(@NotNull final BlockstateVariantJson apply)
    {
        this.apply = apply;
    }
}
