package com.ldtteam.datagenerators.loot_table.pool;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ldtteam.datagenerators.IJsonSerializable;
import com.ldtteam.datagenerators.loot_table.pool.conditions.IPoolCondition;
import com.ldtteam.datagenerators.loot_table.pool.conditions.PoolConditions;
import com.ldtteam.datagenerators.loot_table.pool.entry.EntryJson;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@Deprecated(forRemoval = true)
public class PoolJson implements IJsonSerializable
{

    /**
     * Determines conditions for this pool to be used.
     * If multiple conditions are specified, all must pass.
     *
     * Conditions are registered in {@link com.ldtteam.datagenerators.loot_table.pool.conditions.PoolConditions#POOL_CONDITIONS}
     */
    @Nullable
    private List<IPoolCondition> conditions;

    /**
     *  Specifies the exact number of rolls on the pool.
     */
    private int rolls;

    // Either or, not both \\

    /**
     * Specifies a random number of rolls within a range.
     */
    @Nullable
    private PoolRollsJson rollsJson;

    /**
     * Specifies the exact number of bonus rolls on the pool per point of luck.
     * Rounded down after multiplying.
     */
    private float bonusRolls;

    // Either or, not both \\

    /**
     * Specifies a random number of bonus rolls within a range.
     * Rounded down after multiplying.
     */
    @Nullable
    private PoolBonusRollsJson bonusRollsJson;

    /**
     * A list of all things that can be produced by this pool.
     * One entry is chosen per roll as a weighted random selection from all entries without failing conditions.
     */
    @NotNull
    private List<EntryJson> entries = new ArrayList<>();

    public PoolJson() {}

    public PoolJson(@Nullable final List<IPoolCondition> conditions, @Nullable final PoolRollsJson rollsJson, @Nullable final PoolBonusRollsJson bonusRollsJson, @NotNull final List<EntryJson> entries)
    {
        this.conditions = conditions;
        this.rollsJson = rollsJson;
        this.bonusRollsJson = bonusRollsJson;
        this.entries = entries;
    }

    public PoolJson(@Nullable final List<IPoolCondition> conditions, final int rolls, final float bonusRolls, @NotNull final List<EntryJson> entries)
    {
        this.conditions = conditions;
        this.rolls = rolls;
        this.bonusRolls = bonusRolls;
        this.entries = entries;
    }

    public PoolJson(@Nullable final List<IPoolCondition> conditions, @Nullable final PoolRollsJson rollsJson, final float bonusRolls, @NotNull final List<EntryJson> entries)
    {
        this.conditions = conditions;
        this.rollsJson = rollsJson;
        this.bonusRolls = bonusRolls;
        this.entries = entries;
    }

    public PoolJson(@Nullable final List<IPoolCondition> conditions, final int rolls, @Nullable final PoolBonusRollsJson bonusRollsJson, @NotNull final List<EntryJson> entries)
    {
        this.conditions = conditions;
        this.rolls = rolls;
        this.bonusRollsJson = bonusRollsJson;
        this.entries = entries;
    }

    @NotNull
    @Override
    public JsonElement serialize()
    {
        final JsonObject returnValue = new JsonObject();

        if (this.conditions != null && !this.conditions.isEmpty())
        {
            final JsonArray conditionsJson = new JsonArray();
            for (IPoolCondition condition : this.conditions)
            {
                conditionsJson.add(condition.serialize());
            }
            returnValue.add("conditions", conditionsJson);
        }

        if (this.rolls != 0)
            returnValue.addProperty("rolls", this.rolls);
        else if (this.rollsJson != null)
            returnValue.add("rolls", this.rollsJson.serialize());

        if (this.bonusRolls != 0)
            returnValue.addProperty("bonus_rolls", this.bonusRolls);
        else if (this.bonusRollsJson != null)
            returnValue.add("bonus_rolls", this.bonusRollsJson.serialize());

        final JsonArray entriesJson = new JsonArray();
        for (EntryJson entry : this.entries)
        {
            entriesJson.add(entry.serialize());
        }
        returnValue.add("entries", entriesJson);

        return returnValue;
    }

    @Override
    public void deserialize(@NotNull final JsonElement jsonElement)
    {
        final JsonObject poolJson = jsonElement.getAsJsonObject();

        if (poolJson.has("conditions"))
        {
            if (this.conditions == null)
                this.conditions = new ArrayList<>();

            final JsonArray conditionsJson = poolJson.getAsJsonArray("conditions");
            for (JsonElement condition : conditionsJson)
            {
                final IPoolCondition poolCondition = PoolConditions.deserializeCondition(condition);
                if (poolCondition != null)
                    this.conditions.add(poolCondition);
            }
        }

        if (poolJson.has("rolls"))
        {
            if (poolJson.get("rolls").isJsonObject())
            {
                this.rollsJson = new PoolRollsJson();
                this.rollsJson.deserialize(poolJson.get("rolls"));
            }
            else
            {
                this.rolls = poolJson.get("rolls").getAsInt();
            }
        }

        if (poolJson.has("bonus_rolls"))
        {
            if (poolJson.get("bonus_rolls").isJsonObject())
            {
                this.bonusRollsJson = new PoolBonusRollsJson();
                this.bonusRollsJson.deserialize(poolJson.get("bonus_rolls"));
            }
            else
            {
                this.bonusRolls = poolJson.get("bonus_rolls").getAsFloat();
            }
        }

        final JsonArray entriesJson = poolJson.getAsJsonArray("entries");
        for (JsonElement entry : entriesJson)
        {
            final EntryJson entryJson = new EntryJson();
            entryJson.deserialize(entry);
            this.entries.add(entryJson);
        }

    }

    @Nullable
    public List<IPoolCondition> getConditions()
    {
        return conditions;
    }

    public void setConditions(@Nullable final List<IPoolCondition> conditions)
    {
        this.conditions = conditions;
    }

    public int getRolls()
    {
        return rolls;
    }

    public void setRolls(final int rolls)
    {
        this.rolls = rolls;
    }

    @Nullable
    public PoolRollsJson getRollsJson()
    {
        return rollsJson;
    }

    public void setRollsJson(@Nullable final PoolRollsJson rollsJson)
    {
        this.rollsJson = rollsJson;
    }

    public float getBonusRolls()
    {
        return bonusRolls;
    }

    public void setBonusRolls(final float bonusRolls)
    {
        this.bonusRolls = bonusRolls;
    }

    @Nullable
    public PoolBonusRollsJson getBonusRollsJson()
    {
        return bonusRollsJson;
    }

    public void setBonusRollsJson(@Nullable final PoolBonusRollsJson bonusRollsJson)
    {
        this.bonusRollsJson = bonusRollsJson;
    }

    @NotNull
    public List<EntryJson> getEntries()
    {
        return entries;
    }

    public void setEntries(@NotNull final List<EntryJson> entries)
    {
        this.entries = entries;
    }
}
