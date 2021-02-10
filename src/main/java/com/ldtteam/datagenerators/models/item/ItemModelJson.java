package com.ldtteam.datagenerators.models.item;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ldtteam.datagenerators.IJsonSerializable;
import com.ldtteam.datagenerators.Utils;
import com.ldtteam.datagenerators.models.ModelDisplayPositionJson;
import com.ldtteam.datagenerators.models.ModelDisplayPositionsEnum;
import com.ldtteam.datagenerators.models.element.ModelElementJson;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.List;
import java.util.Map;

public class ItemModelJson implements IJsonSerializable
{
    /**
     * parent: Loads a different model from the given path, starting in assets/<namespace>/models.
     * If both "parent" and "elements" are set, the "elements" tag overrides the "elements" tag from the previous model.
     * <p>
     * Can be set to "item/generated" to use a model that is created out of the specified icon.
     * <p>
     * Can be set to "builtin/entity" to load a model from an entity file.
     * As you cannot specify the entity, this does not work for all items (only for chests, ender chests, mob heads, shields and banners).
     */
    @Nullable
    private String parent;

    /**
     * Holds the different places where item models are displayed.
     */
    @Nullable
    private Map<ModelDisplayPositionsEnum, ModelDisplayPositionJson> display;

    /**
     * Holds the textures of the model. Each texture starts in assets/<namespace>/textures or can be another texture variable.
     * <p>
     * layer#: Only used to specify the icon of the item used in the inventory.
     * There can be more than just one layer (e.g. for spawn eggs), but the amount of possible layers is hardcoded for each item.
     * Only works in combination with "builtin/generated".
     * <p>
     * (make sure to set the `particle` key, this is the particles displayed when then block is broken)
     */
    @Nullable
    private Map<String, String> textures;

    /**
     * Contains all the elements of the model.
     * They can only have cubic forms.
     * If both "parent" and "elements" are set, the "elements" tag overrides the "elements" tag from the previous model.
     */
    @Nullable
    private List<ModelElementJson> elements;

    /**
     * Determines cases which a different model should be used based on item tags.
     * All cases are evaluated in order from top to bottom and last predicate that matches will override.
     * However, overrides are ignored if it has been already overridden once, for example this avoids recursion on overriding to the same model.
     */
    @Nullable
    private List<OverrideCaseJson> overrides;

    /**
     * An optional loader used to load the model from json.
     */
    @Nullable
    private String loader;

    public ItemModelJson() {}

    public ItemModelJson(@Nullable final String parent,
                         @Nullable final Map<ModelDisplayPositionsEnum, ModelDisplayPositionJson> display,
                         @Nullable final Map<String, String> textures,
                         @Nullable final List<ModelElementJson> elements,
                         @Nullable final List<OverrideCaseJson> overrides,
                         @Nullable final String loader)
    {
        this.parent = parent;
        this.display = Utils.ensureTreeMap(display);
        this.textures = Utils.ensureTreeMap(textures);
        this.elements = elements;
        this.overrides = overrides;
        this.loader = loader;
    }

    @NotNull
    @Override
    public JsonElement serialize()
    {
        final JsonObject returnValue = new JsonObject();

        if (this.parent != null)
            returnValue.addProperty("parent", this.parent);

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

        if (this.overrides != null && !this.overrides.isEmpty())
        {
            final JsonArray overrideArray = new JsonArray();
            for (OverrideCaseJson override : this.overrides)
            {
                overrideArray.add(override.serialize());
            }
            returnValue.add("overrides", overrideArray);
        }

        if (this.loader != null)
        {
            returnValue.addProperty("loader", loader);
        }

        return returnValue;
    }

    @Override
    public void deserialize(@NotNull final JsonElement jsonElement)
    {
        final JsonObject modelJson = jsonElement.getAsJsonObject();

        if (modelJson.has("parent"))
            this.parent = modelJson.get("parent").getAsString();

        if (modelJson.has("display"))
        {
            if (this.display == null)
                this.display = new TreeMap<>();

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
                this.textures = new TreeMap<>();

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

        if (modelJson.has("overrides"))
        {
            if (this.overrides == null)
                this.overrides = new ArrayList<>();

            final JsonArray elementsArray = modelJson.getAsJsonArray("overrides");

            for (JsonElement element : elementsArray)
            {
                final OverrideCaseJson overrideJson = new OverrideCaseJson();
                overrideJson.deserialize(element);
                this.overrides.add(overrideJson);
            }
        }

        if (modelJson.has("loader"))
        {
            this.loader = modelJson.get("loader").getAsString();
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

    @Nullable
    public Map<ModelDisplayPositionsEnum, ModelDisplayPositionJson> getDisplay()
    {
        return display;
    }

    public void setDisplay(@Nullable final Map<ModelDisplayPositionsEnum, ModelDisplayPositionJson> display)
    {
        this.display = Utils.ensureTreeMap(display);
    }

    @Nullable
    public Map<String, String> getTextures()
    {
        return textures;
    }

    public void setTextures(@Nullable final Map<String, String> textures)
    {
        this.textures = Utils.ensureTreeMap(textures);
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

    @Nullable
    public List<OverrideCaseJson> getOverrides()
    {
        return overrides;
    }

    public void setOverrides(@Nullable final List<OverrideCaseJson> overrides)
    {
        this.overrides = overrides;
    }

    @Nullable
    public String getLoader()
    {
        return loader;
    }

    public void setLoader(@Nullable final String loader)
    {
        this.loader = loader;
    }
}
