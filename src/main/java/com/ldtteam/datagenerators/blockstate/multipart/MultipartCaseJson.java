package com.ldtteam.datagenerators.blockstate.multipart;

import com.google.gson.*;
import com.ldtteam.datagenerators.IJsonSerializable;
import com.ldtteam.datagenerators.blockstate.BlockstateVariantJson;

public class MultipartCaseJson implements IJsonSerializable
{

    /**
     * A list of cases that have to be met for the model to be applied.
     * If unset, the model always applies.
     */
    private MultipartWhenJson when;

    /**
     * A variant: Name of a variant, which consists of the relevant block states separated by commas.
     * A block with just one variant uses "" as a name for its variant.
     * Each variant can have one model or an array of models and contains their properties.
     * If set to an array, the model will randomly be chosen from the options given, with each option being specified in separate subsidiary -tags.
     * Item frames are treated as blocks and will use "map=false" for a map-less item frame, and "map=true" for item frames with maps.
     */
    private BlockstateVariantJson apply;

    public MultipartCaseJson() {}

    public MultipartCaseJson(final BlockstateVariantJson apply)
    {
        this(apply, null);
    }

    public MultipartCaseJson(final BlockstateVariantJson apply, final MultipartWhenJson when)
    {
        this.when = when;
        this.apply = apply;
    }

    @Override
    public void deserialize(final JsonElement json)
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
}
