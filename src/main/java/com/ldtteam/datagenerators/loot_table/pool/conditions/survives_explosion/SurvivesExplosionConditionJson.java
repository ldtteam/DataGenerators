package com.ldtteam.datagenerators.loot_table.pool.conditions.survives_explosion;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ldtteam.datagenerators.loot_table.pool.conditions.IPoolCondition;
import org.jetbrains.annotations.NotNull;

public class SurvivesExplosionConditionJson implements IPoolCondition
{

    public static final String NAME = "minecraft:survives_explosion";

    public SurvivesExplosionConditionJson() {}

    @NotNull
    @Override
    public JsonElement serialize()
    {
        final JsonObject returnValue = new JsonObject();

        returnValue.addProperty("condition", NAME);

        return returnValue;
    }

    @Override
    public void deserialize(@NotNull final JsonElement jsonElement)
    {
        //Nothing to deserialize, name is static
    }
}
