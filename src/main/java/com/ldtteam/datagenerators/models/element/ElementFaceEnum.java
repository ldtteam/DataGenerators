package com.ldtteam.datagenerators.models.element;

public enum ElementFaceEnum
{

    DOWN("down"),
    UP("up"),
    NORTH("north"),
    SOUTH("south"),
    EAST("east"),
    WEST("west");

    final String name;

    ElementFaceEnum(final String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }

    @Override
    public String toString()
    {
        return getName();
    }

    public static ElementFaceEnum getFromName(final String name)
    {
        for (ElementFaceEnum value : values())
        {
            if (value.getName().equalsIgnoreCase(name))
            {
                return value;
            }
        }
        return null;
    }
}
