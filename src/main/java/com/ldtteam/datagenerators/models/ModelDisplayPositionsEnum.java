package com.ldtteam.datagenerators.models;

@Deprecated(forRemoval = true)
public enum ModelDisplayPositionsEnum
{
    /**
     * Place where an item model is displayed. Holds its rotation, translation and scale for the specified situation.
     * fixed refers to item frames, while the rest are as their name states. Note that translations are applied to the model before rotations.
     */
    THIRD_PERSON_RIGHT_HAND("thirdperson_righthand"),
    THIRD_PERSON_LEFT_HAND("thirdperson_lefthand"),
    FIRST_PERSON_RIGHT_HAND("firstperson_righthand"),
    FIRST_PERSON_LEFT_HAND("firstperson_lefthand"),
    GUI("gui"),
    HEAD("head"),
    GROUND("ground"),
    FIXED("fixed");

    private final String name;

    ModelDisplayPositionsEnum(final String name)
    {
        this.name = name;
    }

    /**
     * Get the JSON reference for this position.
     *
     * @return Name
     */
    public String getName()
    {
        return this.name;
    }

    @Override
    public String toString()
    {
        return getName();
    }

    public static ModelDisplayPositionsEnum getFromName(final String name)
    {
        for (ModelDisplayPositionsEnum value : values())
        {
            if (value.getName().equalsIgnoreCase(name))
            {
                return value;
            }
        }
        return null;
    }
}
