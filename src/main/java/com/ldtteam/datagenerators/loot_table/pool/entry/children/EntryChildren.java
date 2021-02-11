package com.ldtteam.datagenerators.loot_table.pool.entry.children;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ldtteam.datagenerators.loot_table.pool.entry.EntryTypeEnum;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.TreeMap;
import java.util.Map;
import java.util.function.Supplier;

public class EntryChildren
{
    public final static Map<EntryTypeEnum, Supplier<? extends IEntryChild>> ENTRY_CHILDREN = new TreeMap<>();
    static
    {
        // haven't made any classes for this yet.
    }

    @Nullable
    public static IEntryChild deserializeChild(@NotNull final EntryTypeEnum type, @NotNull final JsonElement element)
    {
        final JsonObject jsonObject = element.getAsJsonObject();

        if (ENTRY_CHILDREN.containsKey(type))
        {
            final IEntryChild entryChild = ENTRY_CHILDREN.get(type).get();
            entryChild.deserialize(jsonObject);
            return entryChild;
        }
        return null;
    }
}
