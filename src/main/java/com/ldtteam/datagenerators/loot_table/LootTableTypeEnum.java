package com.ldtteam.datagenerators.loot_table;

public enum LootTableTypeEnum
{

    EMPTY("empty"),
    ENTITY("entity"),
    BLOCK("block"),
    CHEST("chest"),
    FISHING("fishing"),
    ADVANCEMENT_REWARD("advancement_reward"),
    GENERIC("generic");

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
