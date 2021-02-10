package com.ldtteam.datagenerators.blockstate;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ldtteam.datagenerators.IJsonSerializable;
import com.ldtteam.datagenerators.Utils;
import com.ldtteam.datagenerators.blockstate.multipart.MultipartCaseJson;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class BlockstateJson implements IJsonSerializable
{
    /**
     * Holds the names of all the variants of the block.
     */
    @Nullable
    private Map<String, BlockstateVariantJson> variants;

    // one or the other \\

    /**
     * Used instead of variants to combine models based on block state attributes.
     */
    @Nullable
    private List<MultipartCaseJson> multipartCases;

    public BlockstateJson()
    {
    }

    public BlockstateJson(@Nullable final Map<String, BlockstateVariantJson> variants)
    {
        this.variants = Utils.assertTreeMap(variants);
    }

    public BlockstateJson(@Nullable final List<MultipartCaseJson> multipartCases)
    {
        this.multipartCases = multipartCases;
    }

    @Override
    public void deserialize(@NotNull final JsonElement jsonElement)
    {
        final JsonObject blockstateJson = jsonElement.getAsJsonObject();
        if (blockstateJson.has("multipart"))
        {
            if (this.multipartCases == null)
                this.multipartCases = new ArrayList<>();

            for (JsonElement element : jsonElement.getAsJsonArray())
            {
                final MultipartCaseJson caseJson = new MultipartCaseJson();
                caseJson.deserialize(element);
                this.multipartCases.add(caseJson);
            }
        }
        else
        {
            if (this.variants == null)
                this.variants = new TreeMap<>();

            for (Map.Entry<String, JsonElement> jsonElementEntry : blockstateJson.getAsJsonObject("variants").entrySet())
            {
                final BlockstateVariantJson variantJson = new BlockstateVariantJson();
                variantJson.deserialize(jsonElementEntry.getValue());
                this.variants.put(jsonElementEntry.getKey(), variantJson);
            }
        }
    }

    @NotNull
    @Override
    public JsonElement serialize()
    {
        final JsonObject returnValue = new JsonObject();

        if (variants != null && (multipartCases == null || multipartCases.isEmpty()))
        {
            final JsonObject variantsJson = new JsonObject();
            for (Map.Entry<String, BlockstateVariantJson> variantJsonEntry : variants.entrySet())
            {
                variantsJson.add(variantJsonEntry.getKey(), variantJsonEntry.getValue().serialize());
            }
            returnValue.add("variants", variantsJson);
        }
        else if (multipartCases != null)
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

    @Nullable
    public Map<String, BlockstateVariantJson> getVariants()
    {
        return variants;
    }

    public void setVariants(@Nullable final Map<String, BlockstateVariantJson> variants)
    {
        this.variants = Utils.assertTreeMap(variants);
    }

    @Nullable
    public List<MultipartCaseJson> getMultipartCases()
    {
        return multipartCases;
    }

    public void setMultipartCases(@Nullable final List<MultipartCaseJson> multipartCases)
    {
        this.multipartCases = multipartCases;
    }
}
