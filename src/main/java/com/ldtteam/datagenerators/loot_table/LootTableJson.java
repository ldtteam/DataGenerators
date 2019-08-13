package com.ldtteam.datagenerators.loot_table;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ldtteam.datagenerators.IJsonSerializable;
import com.ldtteam.datagenerators.loot_table.pool.PoolJson;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class LootTableJson implements IJsonSerializable
{

    /**
     *  Optional type of the loot table.
     *  Must be one of {@link LootTableTypeEnum#EMPTY} if the loot table does not generate any loot,
     *  {@link LootTableTypeEnum#ENTITY} for loot an entity drops,
     *  {@link LootTableTypeEnum#BLOCK} for loot an block drops,
     *  {@link LootTableTypeEnum#CHEST} for a treasure chest,
     *  {@link LootTableTypeEnum#FISHING} for a fishing loot table,
     *  {@link LootTableTypeEnum#ADVANCEMENT_REWARD} if it's used as a reward for an advancement
     *  or {@link LootTableTypeEnum#GENERIC} if none of the above apply.
     */
    @Nullable
    private LootTableTypeEnum type;

    /**
     *  A list of all pools for this table.
     *  Each pool used will generate items from its list of items based on the number of rolls.
     *  Pools are applied in order.
     */
    @NotNull
    private List<PoolJson> pools = new ArrayList<>();

    public LootTableJson() {}

    public LootTableJson(@Nullable final LootTableTypeEnum type, @NotNull final List<PoolJson> pools)
    {
        this.type = type;
        this.pools = pools;
    }

    @NotNull
    @Override
    public JsonElement serialize()
    {
        final JsonObject returnValue = new JsonObject();

        if (this.type != null)
            returnValue.addProperty("type", this.type.toString());

        final JsonArray poolArray = new JsonArray();
        for (PoolJson pool : this.pools)
        {
            poolArray.add(pool.serialize());
        }
        returnValue.add("pools", poolArray);

        return returnValue;
    }

    @Override
    public void deserialize(@NotNull final JsonElement jsonElement)
    {
        final JsonObject tableJson = jsonElement.getAsJsonObject();

        if (tableJson.has("type"))
            this.type = LootTableTypeEnum.getFromName(tableJson.get("type").toString());

        final JsonArray poolArray = tableJson.getAsJsonArray("pools");
        for (JsonElement pool : poolArray)
        {
            final PoolJson poolJson = new PoolJson();
            poolJson.deserialize(pool);
            this.pools.add(poolJson);
        }
    }

    @Nullable
    public LootTableTypeEnum getType()
    {
        return type;
    }

    public void setType(@Nullable final LootTableTypeEnum type)
    {
        this.type = type;
    }

    @NotNull
    public List<PoolJson> getPools()
    {
        return pools;
    }

    public void setPools(@NotNull final List<PoolJson> pools)
    {
        this.pools = pools;
    }
}
