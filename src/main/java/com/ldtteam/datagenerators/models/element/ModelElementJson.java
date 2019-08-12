package com.ldtteam.datagenerators.models.element;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ldtteam.datagenerators.IJsonSerializable;
import com.ldtteam.datagenerators.models.XYZDoubleListJson;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class ModelElementJson implements IJsonSerializable
{

    /**
     * Not used by minecraft, however is nice to have for readability.
     */
    @Nullable
    private String name;

    /**
     * Start point of a cube according to the scheme [x, y, z]. Values must be between -16 and 32.
     */
    @NotNull
    private XYZDoubleListJson from = new XYZDoubleListJson();

    /**
     * Stop point of a cube according to the scheme [x, y, z]. Values must be between -16 and 32.
     */
    @NotNull
    private XYZDoubleListJson to = new XYZDoubleListJson();

    /**
     * Defines if shadows are rendered.
     */
    private boolean shade = true;

    /**
     * Defines the rotation of an element.
     */
    @Nullable
    private ElementRotationJson rotation;

    /**
     * Holds all the faces of the cube. If a face is left out, it will not be rendered.
     */
    @NotNull
    private Map<ElementFaceEnum, ElementFaceJson> faces = new HashMap<>();

    public ModelElementJson()
    {
    }

    public ModelElementJson(@Nullable final String name,
                            @NotNull final XYZDoubleListJson from,
                            @NotNull final XYZDoubleListJson to,
                            final boolean shade,
                            @Nullable final ElementRotationJson rotation,
                            @NotNull final Map<ElementFaceEnum, ElementFaceJson> faces)
    {
        this.name = name;
        this.from = from;
        this.to = to;
        this.shade = shade;
        this.rotation = rotation;
        this.faces = faces;
    }

    @NotNull
    @Override
    public JsonElement serialize()
    {
        final JsonObject returnValue = new JsonObject();

        if (this.name != null)
            returnValue.addProperty("name", this.name);

        returnValue.add("from", this.from.serialize());
        returnValue.add("to", this.to.serialize());

        if (!this.shade)
            returnValue.addProperty("shade", false);

        if (this.rotation != null)
            returnValue.add("rotation", this.rotation.serialize());

        if (!this.faces.isEmpty())
        {
            final JsonObject facesObject = new JsonObject();
            for (Map.Entry<ElementFaceEnum, ElementFaceJson> elementFaceEntry : this.faces.entrySet())
            {
                facesObject.add(elementFaceEntry.getKey().toString(), elementFaceEntry.getValue().serialize());
            }
            returnValue.add("faces", facesObject);
        }

        return returnValue;
    }

    @Override
    public void deserialize(@NotNull final JsonElement jsonElement)
    {
        final JsonObject elementJson = jsonElement.getAsJsonObject();

        if (elementJson.has("name"))
            this.name = elementJson.get("name").getAsString();

        if (elementJson.has("from"))
            this.from.deserialize(elementJson.get("from"));

        if (elementJson.has("to"))
            this.to.deserialize(elementJson.get("to"));

        if (elementJson.has("shade"))
            this.shade = elementJson.get("shade").getAsBoolean();

        if (elementJson.has("rotation"))
        {
            this.rotation = new ElementRotationJson();
            this.rotation.deserialize(elementJson.get("rotation"));
        }

        if (elementJson.has("faces"))
        {
            final JsonObject facesJson = elementJson.getAsJsonObject("faces");
            for (Map.Entry<String, JsonElement> facesEntry : facesJson.entrySet())
            {
                final ElementFaceEnum faceEnum = ElementFaceEnum.getFromName(facesEntry.getKey());

                if (faceEnum != null)
                {
                    final ElementFaceJson face = new ElementFaceJson();
                    face.deserialize(facesEntry.getValue());

                    this.faces.put(faceEnum, face);
                }
            }
        }
    }

    @Nullable
    public String getName()
    {
        return name;
    }

    public void setName(@Nullable String name)
    {
        this.name = name;
    }

    @NotNull
    public XYZDoubleListJson getFrom()
    {
        return from;
    }

    public void setFrom(@NotNull XYZDoubleListJson from)
    {
        this.from = from;
    }

    @NotNull
    public XYZDoubleListJson getTo()
    {
        return to;
    }

    public void setTo(@NotNull XYZDoubleListJson to)
    {
        this.to = to;
    }

    public boolean isShade()
    {
        return shade;
    }

    public void setShade(boolean shade)
    {
        this.shade = shade;
    }

    @Nullable
    public ElementRotationJson getRotation()
    {
        return rotation;
    }

    public void setRotation(@Nullable ElementRotationJson rotation)
    {
        this.rotation = rotation;
    }

    @NotNull
    public Map<ElementFaceEnum, ElementFaceJson> getFaces()
    {
        return faces;
    }

    public void setFaces(@NotNull Map<ElementFaceEnum, ElementFaceJson> faces)
    {
        this.faces = faces;
    }
}
