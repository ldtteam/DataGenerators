package com.ldtteam.datagenerators.loot_table.pool.entry;

public enum EntryTypeEnum
{
    ITEM("item"),
    TAG("tag"),
    LOOT_TABLE("loot_Table"),
    GROUP("group"),
    ALTERNATIVES("alternatives"),
    SEQUENCE("sequence"),
    DYNAMIC("dynamic"),
    EMPTY("empty");

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
