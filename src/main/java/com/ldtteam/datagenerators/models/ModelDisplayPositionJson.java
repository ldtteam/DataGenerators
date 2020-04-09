package com.ldtteam.datagenerators.models;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ldtteam.datagenerators.IJsonSerializable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ModelDisplayPositionJson implements IJsonSerializable
{

    /**
     * Specifies the rotation of the model according to the scheme [x, y, z]
     */
    @Nullable
    private XYZIntListJson rotation;

    /**
     * Specifies the position of the model according to the scheme [x, y, z].
     * If the value is greater than 80, it is displayed as 80.
     * If the value is less then -80, it is displayed as -80.
     */
    @Nullable
    private XYZDoubleListJson translation;

    /***
     * Specifies the scale of the model according to the scheme [x, y, z].
     * If the value is greater than 4, it is displayed as 4.
     */
    @Nullable
    private XYZDoubleListJson scale;

    public ModelDisplayPositionJson()
    {
    }

    public ModelDisplayPositionJson(@Nullable final XYZIntListJson rotation, @Nullable final XYZDoubleListJson translation, @Nullable final XYZDoubleListJson scale)
    {
        this.rotation = rotation;
        this.translation = translation;
        this.scale = scale;
    }

    @NotNull
    @Override
    public JsonElement serialize()
    {
        final JsonObject returnValue = new JsonObject();

        if (rotation != null && !rotation.isEmtpy())
            returnValue.add("rotation", rotation.serialize());

        if (translation != null && !translation.isEmtpy())
            returnValue.add("translation", translation.serialize());

        if (scale != null && !scale.isEmtpy())
            returnValue.add("scale", scale.serialize());

        return returnValue;
    }

    @Override
    public void deserialize(@NotNull final JsonElement jsonElement)
    {
        final JsonObject positionJson = new JsonObject();

        if (positionJson.has("rotation") && positionJson.get("rotation").isJsonArray())
        {
            this.rotation = new XYZIntListJson();
            this.rotation.deserialize(positionJson.getAsJsonArray("rotation"));
        }

        if (positionJson.has("translation") && positionJson.get("translation").isJsonArray())
        {
            this.translation = new XYZDoubleListJson();
            this.translation.deserialize(positionJson.getAsJsonArray("translation"));
        }

        if (positionJson.has("scale") && positionJson.get("scale").isJsonArray())
        {
            this.scale = new XYZDoubleListJson();
            this.scale.deserialize(positionJson.getAsJsonArray("scale"));
        }
    }

    @Nullable
    public XYZIntListJson getRotation()
    {
        return rotation;
    }

    public void setRotation(@Nullable final XYZIntListJson rotation)
    {
        this.rotation = rotation;
    }

    @Nullable
    public XYZDoubleListJson getTranslation()
    {
        return translation;
    }

    public void setTranslation(@Nullable final XYZDoubleListJson translation)
    {
        this.translation = translation;
    }

    @Nullable
    public XYZDoubleListJson getScale()
    {
        return scale;
    }

    public void setScale(@Nullable final XYZDoubleListJson scale)
    {
        this.scale = scale;
    }
}
