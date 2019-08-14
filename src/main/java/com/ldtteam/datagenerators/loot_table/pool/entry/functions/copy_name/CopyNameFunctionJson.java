package com.ldtteam.datagenerators.loot_table.pool.entry.functions.copy_name;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ldtteam.datagenerators.loot_table.pool.entry.functions.IEntryFunction;
import org.jetbrains.annotations.NotNull;

public class CopyNameFunctionJson implements IEntryFunction
{
    public static final String NAME = "minecraft:copy_name";

    private static final String SOURCE = "block_entity";

    @NotNull
    @Override
    public JsonElement serialize()
    {
        final JsonObject returnValue = new JsonObject();

        returnValue.addProperty("function", NAME);
        returnValue.addProperty("source", SOURCE);

        return returnValue;
    }

    @Override
    public void deserialize(@NotNull final JsonElement jsonElement)
    {
        //Nothing to deserialize, everything is static
    }
}
