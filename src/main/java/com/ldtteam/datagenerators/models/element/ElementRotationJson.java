package com.ldtteam.datagenerators.models.element;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ldtteam.datagenerators.IJsonSerializable;
import com.ldtteam.datagenerators.models.XYZDoubleListJson;
import org.jetbrains.annotations.NotNull;

public class ElementRotationJson implements IJsonSerializable
{

    /**
     * Sets the center of the rotation according to the scheme [x, y, z].
     */
    @NotNull
    private XYZDoubleListJson origin = new XYZDoubleListJson();

    /**
     * Specifies the direction of rotation.
     */
    @NotNull
    private ElementRotationAxisEnum axis = ElementRotationAxisEnum.X;

    /**
     * Specifies the angle of rotation. Can be 45 through -45 degrees in 22.5 degree increments.
     */
    private float angle;

    /**
     * Specifies whether or not to scale the faces across the whole block.
     */
    private boolean rescale = false;

    public ElementRotationJson()
    {
    }

    public ElementRotationJson(@NotNull final XYZDoubleListJson origin, @NotNull final ElementRotationAxisEnum axis, final float angle, final boolean rescale)
    {
        this.origin = origin;
        this.axis = axis;
        this.angle = angle;
        this.rescale = rescale;
    }

    @NotNull
    @Override
    public JsonElement serialize()
    {
        final JsonObject returnValue = new JsonObject();

        returnValue.add("origin", this.origin.serialize());
        returnValue.addProperty("axis", this.axis.toString());
        returnValue.addProperty("angle", this.angle);
        returnValue.addProperty("rescale", this.rescale);

        return returnValue;
    }

    @Override
    public void deserialize(@NotNull final JsonElement jsonElement)
    {
        final JsonObject rotationJson = new JsonObject();

        if (rotationJson.has("origin"))
        {
            this.origin = new XYZDoubleListJson();
            this.origin.deserialize(rotationJson.get("origin"));
        }

        if (rotationJson.has("axis"))
        {
            final ElementRotationAxisEnum axis = ElementRotationAxisEnum.getFromName(rotationJson.get("axis").getAsString());
            this.axis = axis == null ? ElementRotationAxisEnum.X : axis;
        }

        if (rotationJson.has("angle"))
            this.angle = rotationJson.get("angle").getAsFloat();

        if (rotationJson.has("rescale"))
            this.rescale = rotationJson.get("rescale").getAsBoolean();
    }

    @NotNull
    public XYZDoubleListJson getOrigin()
    {
        return origin;
    }

    public void setOrigin(@NotNull XYZDoubleListJson origin)
    {
        this.origin = origin;
    }

    @NotNull
    public ElementRotationAxisEnum getAxis()
    {
        return axis;
    }

    public void setAxis(@NotNull ElementRotationAxisEnum axis)
    {
        this.axis = axis;
    }

    public float getAngle()
    {
        return angle;
    }

    public void setAngle(float angle)
    {
        this.angle = angle;
    }

    public boolean isRescale()
    {
        return rescale;
    }

    public void setRescale(boolean rescale)
    {
        this.rescale = rescale;
    }
}
