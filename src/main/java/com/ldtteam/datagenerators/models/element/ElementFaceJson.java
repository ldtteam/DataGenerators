package com.ldtteam.datagenerators.models.element;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ldtteam.datagenerators.IJsonSerializable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ElementFaceJson implements IJsonSerializable
{

    /**
     * Defines the area of the texture to use according to the scheme [x1, y1, x2, y2].
     * If unset, it defaults to values equal to xyz position of the element.
     * The texture behavior will be inconsistent if UV extends below 0 or above 16.
     * If the numbers of x1 and x2 are swapped (e.g. from 0, 0, 16, 16 to 16, 0, 0, 16), the texture will be flipped.
     * UV is optional, and if not supplied it will automatically generate based on the element's position.
     */
    @Nullable
    private ElementFaceUVJson uv;

    /**
     * Specifies the texture in form of the texture variable prepended with a #.
     */
    @NotNull
    private String texture = "particle";

    /**
     * Specifies whether a face does not need to be rendered when there is a block touching it in the specified position.
     * The position can be: down, up, north, south, west, or east.
     * It will also determine which side of the block to use the light level from for lighting the face, and if unset, defaults to the side.
     */
    @Nullable
    private ElementFaceEnum cullface;

    /**
     * Rotates the texture by the specified number of degrees.
     * Can be 0, 90, 180, or 270. Defaults to 0.
     * Rotation does not affect which part of the texture is used.
     * Instead, it amounts to permutation of the selected texture vertexes (selected implicitly, or explicitly though uv).
     */
    private int rotation;

    /**
     * Determines whether to tint the texture using a hardcoded tint index.
     * The default is not using the tint, and any number causes it to use tint.
     * Note that only certain blocks have a tint index, all others will be unaffected.
     */
    private int tintindex;

    public ElementFaceJson()
    {
    }

    public ElementFaceJson(@Nullable final ElementFaceUVJson uv, @NotNull final String texture, @Nullable ElementFaceEnum cullface, final int rotation, final int tintindex)
    {
        this.uv = uv;
        this.texture = texture;
        this.cullface = cullface;
        this.rotation = rotation;
        this.tintindex = tintindex;
    }

    @NotNull
    @Override
    public JsonElement serialize()
    {
        final JsonObject returnValue = new JsonObject();

        if (this.uv != null)
            returnValue.add("uv", this.uv.serialize());

        returnValue.addProperty("texture", this.texture);

        if (this.cullface != null)
            returnValue.addProperty("cullface", this.cullface.toString());

        if (this.rotation != 0)
            returnValue.addProperty("rotation", this.rotation);

        if (this.tintindex != 0)
            returnValue.addProperty("tintindex", this.tintindex);

        return returnValue;
    }

    @Override
    public void deserialize(@NotNull JsonElement jsonElement)
    {
        final JsonObject faceJson = jsonElement.getAsJsonObject();

        if (faceJson.has("uv"))
        {
            this.uv = new ElementFaceUVJson();
            this.uv.deserialize(faceJson.get("uv"));
        }

        if (faceJson.has("texture"))
            this.texture = faceJson.get("texture").getAsString();

        if (faceJson.has("cullface"))
            this.cullface = ElementFaceEnum.getFromName(faceJson.get("cullface").getAsString());

        if (faceJson.has("rotation"))
            this.rotation = faceJson.get("rotation").getAsInt();

        if (faceJson.has("tintindex"))
            this.tintindex = faceJson.get("tintindex").getAsInt();
    }
}
