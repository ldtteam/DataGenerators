package com.ldtteam.datagenerators.loot_table;

public enum LootTableTypeEnum
{

    EMPTY("minecraft:empty"),
    ENTITY("minecraft:entity"),
    BLOCK("minecraft:block"),
    CHEST("minecraft:chest"),
    FISHING("minecraft:fishing"),
    ADVANCEMENT_REWARD("minecraft:advancement_reward"),
    GENERIC("minecraft:generic");

    final String name;

    LootTableTypeEnum(final String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    @Override
    public String toString()
    {
        return getName();
    }

    public static LootTableTypeEnum getFromName(final String name)
    {
        for (LootTableTypeEnum value : values())
        {
            if (value.getName().equalsIgnoreCase(name))
            {
                return value;
            }
        }
        return null;
    }
}
