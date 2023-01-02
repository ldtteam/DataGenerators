package com.ldtteam.datagenerators.models.element;

@Deprecated(forRemoval = true)
public enum ElementRotationAxisEnum
{

    X("x"),
    Y("y"),
    Z("z");

    private final String name;

    ElementRotationAxisEnum(final String name)
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

    public static ElementRotationAxisEnum getFromName(final String name)
    {
        for (ElementRotationAxisEnum value : values())
        {
            if (value.getName().equalsIgnoreCase(name))
            {
                return value;
            }
        }
        return null;
    }
}
