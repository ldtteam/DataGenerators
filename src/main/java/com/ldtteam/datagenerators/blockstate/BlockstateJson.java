package com.ldtteam.datagenerators.blockstate;

import com.google.gson.*;
import com.ldtteam.datagenerators.IJsonSerializable;
import com.ldtteam.datagenerators.blockstate.multipart.MultipartCaseJson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlockstateJson implements IJsonSerializable
{
    /**
     *  Holds the names of all the variants of the block.
     */
    private Map<String, BlockstateVariantJson> variants = new HashMap<>();

    // one or the other \\

    /**
     * Used instead of variants to combine models based on block state attributes.
     */
    private List<MultipartCaseJson> multipartCases = new ArrayList<>();

    public BlockstateJson() {}

    public BlockstateJson(final Map<String, BlockstateVariantJson> variants)
    {
        this.variants = variants;
    }

    public BlockstateJson(final List<MultipartCaseJson> multipartCases)
    {
        this.multipartCases = multipartCases;
    }

    @Override
    public void deserialize(JsonElement jsonElement)
    {
        final JsonObject blockstateJson = jsonElement.getAsJsonObject();
        if (blockstateJson.has("multipart"))
        {
            for (JsonElement element : jsonElement.getAsJsonArray())
            {
                final MultipartCaseJson caseJson = new MultipartCaseJson();
                caseJson.deserialize(element);
                this.multipartCases.add(caseJson);
            }
        }
        else
        {
            for (Map.Entry<String, JsonElement> jsonElementEntry : blockstateJson.getAsJsonObject("variants").entrySet())
            {
                final BlockstateVariantJson variantJson = new BlockstateVariantJson();
                variantJson.deserialize(jsonElementEntry.getValue());
                this.variants.put(jsonElementEntry.getKey(), variantJson);
            }
        }
    }

    @Override
    public JsonElement serialize()
    {
        final JsonObject returnValue = new JsonObject();

        if (multipartCases.isEmpty())
        {
            final JsonObject variantsJson = new JsonObject();
            for (Map.Entry<String, BlockstateVariantJson> variantJsonEntry : variants.entrySet())
            {
                variantsJson.add(variantJsonEntry.getKey(), variantJsonEntry.getValue().serialize());
            }
            returnValue.add("variants", variantsJson);
        }
        else
        {
            final JsonArray multipartArray = new JsonArray();

            for (MultipartCaseJson multipartCase : multipartCases)
            {
                multipartArray.add(multipartCase.serialize());
            }

            returnValue.add("multipart", multipartArray);
        }

        return returnValue;
    }
}
