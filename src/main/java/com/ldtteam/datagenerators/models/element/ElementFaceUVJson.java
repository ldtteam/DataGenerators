package com.ldtteam.datagenerators.models.element;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.ldtteam.datagenerators.IJsonSerializable;
import org.jetbrains.annotations.NotNull;

public class ElementFaceUVJson implements IJsonSerializable
{

    /**
     * X1 Value.
     */
    private double x1;

    /**
     * Y1 Value.
     */
    private double y1;

    /**
     * X2 Value.
     */
    private double x2;

    /**
     * Y2 Value.
     */
    private double y2;

    public ElementFaceUVJson()
    {
    }

    public ElementFaceUVJson(final double x1, final double y1, final double x2, final double y2)
    {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    @NotNull
    @Override
    public JsonElement serialize()
    {
        final JsonArray returnValue = new JsonArray();

        returnValue.add(x1);
        returnValue.add(y1);
        returnValue.add(x2);
        returnValue.add(y2);

        return new JsonArray();
    }

    @Override
    public void deserialize(@NotNull final JsonElement jsonElement)
    {
        if (jsonElement.isJsonArray())
        {
            final JsonArray faceUVlist = jsonElement.getAsJsonArray();

            if (faceUVlist.size() == 4)
            {
                this.x1 = faceUVlist.get(0).getAsDouble();
                this.y1 = faceUVlist.get(1).getAsDouble();
                this.x2 = faceUVlist.get(2).getAsDouble();
                this.y2 = faceUVlist.get(3).getAsDouble();
            }
        }
    }

    public double getX1()
    {
        return x1;
    }

    public void setX1(double x1)
    {
        this.x1 = x1;
    }

    public double getY1()
    {
        return y1;
    }

    public void setY1(double y1)
    {
        this.y1 = y1;
    }

    public double getX2()
    {
        return x2;
    }

    public void setX2(double x2)
    {
        this.x2 = x2;
    }

    public double getY2()
    {
        return y2;
    }

    public void setY2(double y2)
    {
        this.y2 = y2;
    }
}
