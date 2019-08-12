package com.ldtteam.datagenerators.models.block;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ldtteam.datagenerators.IJsonSerializable;
import com.ldtteam.datagenerators.models.ModelDisplayPositionJson;
import com.ldtteam.datagenerators.models.ModelDisplayPositionsEnum;
import com.ldtteam.datagenerators.models.element.ModelElementJson;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlockModelJson implements IJsonSerializable
{

    /**
     * Loads a different model from the given path, starting in assets/<namespace>/models.
     * If both "parent" and "elements" are set, the "elements" tag overrides the "elements" tag from the previous model.
     * <p>
     * Can be set to "builtin/generated" to use a model that is created out of the specified icon.
     * Note that only the first layer is supported, and rotation can only be achieved using block states files.
     */
    @Nullable
    private String parent;

    /**
     * Whether to use ambient occlusion.
     */
    private boolean ambientOcclusion = true;

    /**
     * Holds the different places where item models are displayed.
     */
    @Nullable
    private Map<ModelDisplayPositionsEnum, ModelDisplayPositionJson> display;

    /**
     * Holds the textures of the model. Each texture starts in assets/<namespace>/textures or can be another texture variable.
     * <p>
     * (make sure to set the `particle` key, this is the particles displayed when then block is broken)
     */
    @Nullable
    private Map<String, String> textures;

    /**
     * Contains all the elements of the model.
     * they can only have cubic forms.
     * If both "parent" and "elements" are set, the "elements" tag overrides the "elements" tag from the previous model.
     */
    @Nullable
    private List<ModelElementJson> elements;

    @NotNull
    @Override
    public JsonElement serialize()
    {
        final JsonObject returnValue = new JsonObject();

        if (this.parent != null)
            returnValue.addProperty("parent", this.parent);

        if (!ambientOcclusion)
            returnValue.addProperty("ambientocclusion", false);

        if (this.display != null && !this.display.isEmpty())
        {
            final JsonObject displayValue = new JsonObject();
            for (Map.Entry<ModelDisplayPositionsEnum, ModelDisplayPositionJson> displayEntry : this.display.entrySet())
            {
                displayValue.add(displayEntry.getKey().toString(), displayEntry.getValue().serialize());
            }
            returnValue.add("display", displayValue);
        }

        if (this.textures != null && !this.textures.isEmpty())
        {
            final JsonObject texturesValue = new JsonObject();
            for (Map.Entry<String, String> texturesEntry : this.textures.entrySet())
            {
                texturesValue.addProperty(texturesEntry.getKey(), texturesEntry.getValue());
            }
            returnValue.add("textures", texturesValue);
        }

        if (this.elements != null && !this.elements.isEmpty())
        {
            final JsonArray elementArray = new JsonArray();
            for (ModelElementJson element : this.elements)
            {
                elementArray.add(element.serialize());
            }
            returnValue.add("elements", elementArray);
        }

        return returnValue;
    }

    @Override
    public void deserialize(@NotNull final JsonElement jsonElement)
    {
        final JsonObject modelJson = jsonElement.getAsJsonObject();

        if (modelJson.has("parent"))
            this.parent = modelJson.get("parent").getAsString();

        if (modelJson.has("ambientocclusion"))
            this.ambientOcclusion = modelJson.get("ambientocclusion").getAsBoolean();

        if (modelJson.has("display"))
        {
            if (this.display == null)
                this.display = new HashMap<>();

            final JsonObject displayObject = modelJson.getAsJsonObject("display");

            for (Map.Entry<String, JsonElement> displayEntry : displayObject.entrySet())
            {
                final ModelDisplayPositionsEnum displayPosition = ModelDisplayPositionsEnum.getFromName(displayEntry.getKey());

                if (displayPosition != null)
                {
                    final ModelDisplayPositionJson displayJson = new ModelDisplayPositionJson();
                    displayJson.deserialize(displayEntry.getValue());

                    this.display.put(displayPosition, displayJson);
                }
            }
        }

        if (modelJson.has("textures"))
        {
            if (this.textures == null)
                this.textures = new HashMap<>();

            final JsonObject texturesObject = modelJson.getAsJsonObject("textures");

            for (Map.Entry<String, JsonElement> texturesEntry : texturesObject.entrySet())
            {
                this.textures.put(texturesEntry.getKey(), texturesEntry.getValue().getAsString());
            }
        }

        if (modelJson.has("elements"))
        {
            if (this.elements == null)
                this.elements = new ArrayList<>();

            final JsonArray elementsArray = modelJson.getAsJsonArray("elements");

            for (JsonElement element : elementsArray)
            {
                final ModelElementJson elementJson = new ModelElementJson();
                elementJson.deserialize(element);
                this.elements.add(elementJson);
            }
        }

    }

    @Nullable
    public String getParent()
    {
        return parent;
    }

    public void setParent(@Nullable final String parent)
    {
        this.parent = parent;
    }

    public boolean isAmbientOcclusion()
    {
        return ambientOcclusion;
    }

    public void setAmbientOcclusion(final boolean ambientOcclusion)
    {
        this.ambientOcclusion = ambientOcclusion;
    }

    @Nullable
    public Map<ModelDisplayPositionsEnum, ModelDisplayPositionJson> getDisplay()
    {
        return display;
    }

    public void setDisplay(@Nullable final Map<ModelDisplayPositionsEnum, ModelDisplayPositionJson> display)
    {
        this.display = display;
    }

    @Nullable
    public Map<String, String> getTextures()
    {
        return textures;
    }

    public void setTextures(@Nullable final Map<String, String> textures)
    {
        this.textures = textures;
    }

    @Nullable
    public List<ModelElementJson> getElements()
    {
        return elements;
    }

    public void setElements(@Nullable final List<ModelElementJson> elements)
    {
        this.elements = elements;
    }
}
