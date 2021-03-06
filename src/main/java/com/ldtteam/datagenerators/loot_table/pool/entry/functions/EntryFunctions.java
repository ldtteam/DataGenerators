package com.ldtteam.datagenerators.loot_table.pool.entry.functions;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ldtteam.datagenerators.loot_table.pool.entry.functions.copy_name.CopyNameFunctionJson;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class EntryFunctions
{
    public final static Map<String, Supplier<? extends IEntryFunction>> ENTRY_FUNCTIONS = new HashMap<>();
    static
    {
        ENTRY_FUNCTIONS.put(CopyNameFunctionJson.NAME, CopyNameFunctionJson::new);
    }

    @Nullable
    public static IEntryFunction deserializeFunction(@NotNull final JsonElement element)
    {
        final JsonObject jsonObject = element.getAsJsonObject();

        if (ENTRY_FUNCTIONS.containsKey(jsonObject.get("function").getAsString()))
        {
            final IEntryFunction entryFunction = ENTRY_FUNCTIONS.get(jsonObject.get("function").getAsString()).get();
            entryFunction.deserialize(jsonObject);
            return entryFunction;
        }
        return null;
    }
}
