package com.ldtteam.datagenerators.loot_table.pool.entry;

public enum EntryTypeEnum
{
    ITEM("minecraft:item"),
    TAG("minecraft:tag"),
    LOOT_TABLE("minecraft:loot_Table"),
    GROUP("minecraft:group"),
    ALTERNATIVES("minecraft:alternatives"),
    SEQUENCE("minecraft:sequence"),
    DYNAMIC("minecraft:dynamic"),
    EMPTY("minecraft:empty");

    final String name;

    EntryTypeEnum(final String name)
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

    public static EntryTypeEnum getFromName(final String name)
    {
        for (EntryTypeEnum value : values())
        {
            if (value.getName().equalsIgnoreCase(name))
            {
                return value;
            }
        }
        return null;
    }
}
