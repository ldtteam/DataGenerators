package com.ldtteam.datagenerators.loot_table.pool.conditions;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ldtteam.datagenerators.loot_table.pool.conditions.survives_explosion.SurvivesExplosionConditionJson;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Deprecated(forRemoval = true)
public class PoolConditions
{
    public final static Map<String, Supplier<? extends IPoolCondition>> POOL_CONDITIONS = new HashMap<>();
    static
    {
        POOL_CONDITIONS.put(SurvivesExplosionConditionJson.NAME, SurvivesExplosionConditionJson::new);
    }

    @Nullable
    public static IPoolCondition deserializeCondition(@NotNull final JsonElement element)
    {
        final JsonObject jsonObject = element.getAsJsonObject();

        if (POOL_CONDITIONS.containsKey(jsonObject.get("condition").getAsString()))
        {
            final IPoolCondition poolCondition = POOL_CONDITIONS.get(jsonObject.get("condition").getAsString()).get();
            poolCondition.deserialize(jsonObject);
            return poolCondition;
        }
        return null;
    }
}
