package com.ldtteam.datagenerators.recipes.shaped;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ldtteam.datagenerators.IJsonSerializable;
import com.ldtteam.datagenerators.recipes.RecipeIngredientKeyJson;
import com.ldtteam.datagenerators.recipes.RecipeResultJson;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class ShapedRecipeJson implements IJsonSerializable
{
    /**
     *  A namespaced ID indicating the type of serializer of the recipe.
     */
    @NotNull
    private static final String TYPE = "minecraft:crafting_shaped";

    /**
     * A string identifier.
     * Used to group multiple recipes together in the recipe book.
     */
    @Nullable
    private String group;

    /**
     *  A list of single-character keys used to describe a 2×2 or 3×3 pattern for shaped crafting.
     *  Each row in the crafting grid is one string in this list containing 3 or less keys.
     *  All strings in this list need to have the same amount of keys.
     *  A space can be used to indicate an empty spot.
     */
    @NotNull
    private ShapedPatternJson pattern = new ShapedPatternJson();

    /**
     * All keys used for this shaped crafting recipe.
     */
    @NotNull
    private Map<String, RecipeIngredientKeyJson> key = new HashMap<>();

    /**
     * The output item of the recipe.
     */
    @NotNull
    private RecipeResultJson result = new RecipeResultJson();

    /**
     * The type of the recipe.
     * By default defines a vanilla shaped recipe.
     * Use the overloaded constructor to define a custom type.
     */
    @NotNull
    private String recipeType = TYPE;

    public ShapedRecipeJson() {}

    public ShapedRecipeJson(@Nullable final String group,
                            @NotNull final ShapedPatternJson pattern,
                            @NotNull final Map<String, RecipeIngredientKeyJson> key,
                            @NotNull final RecipeResultJson result)
    {
        this.group = group;
        this.pattern = pattern;
        this.key = key;
        this.result = result;
    }

    public ShapedRecipeJson(
      @Nullable final String group,
      @NotNull final ShapedPatternJson pattern,
      @NotNull final Map<String, RecipeIngredientKeyJson> key, @NotNull final RecipeResultJson result, @NotNull final String recipeType)
    {
        this.group = group;
        this.pattern = pattern;
        this.key = key;
        this.result = result;
        this.recipeType = recipeType;
    }

    @NotNull
    @Override
    public JsonElement serialize()
    {
        final JsonObject returnValue = new JsonObject();

        returnValue.addProperty("type", recipeType);

        if (this.group != null)
            returnValue.addProperty("group", this.group);

        returnValue.add("pattern", this.pattern.serialize());

        final JsonObject keyObject = new JsonObject();
        for (Map.Entry<String, RecipeIngredientKeyJson> keyEntry : this.key.entrySet())
        {
            keyObject.add(keyEntry.getKey(), keyEntry.getValue().serialize());
        }
        returnValue.add("key", keyObject);

        returnValue.add("result", this.result.serialize());

        return returnValue;
    }

    @Override
    public void deserialize(@NotNull final JsonElement jsonElement)
    {
        final JsonObject recipeJson = jsonElement.getAsJsonObject();

        if (recipeJson.has("type"))
            this.recipeType = recipeJson.get("type").getAsString();

        if (recipeJson.has("group"))
            this.group = recipeJson.get("group").getAsString();

        this.pattern.deserialize(recipeJson.get("pattern"));

        for (Map.Entry<String, JsonElement> keyEntry : recipeJson.getAsJsonObject("key").entrySet())
        {
            final RecipeIngredientKeyJson ingredientKeyJson = new RecipeIngredientKeyJson();
            ingredientKeyJson.deserialize(keyEntry.getValue());
            this.key.put(keyEntry.getKey(), ingredientKeyJson);
        }

        this.result.deserialize(recipeJson.get("result"));
    }

    @NotNull
    public static String getDefaultType()
    {
        return TYPE;
    }

    @NotNull
    private String getRecipeType()
    {
        return recipeType;
    }

    public void setRecipeType(@NotNull final String recipeType)
    {
        this.recipeType = recipeType;
    }

    @Nullable
    public String getGroup()
    {
        return group;
    }

    public void setGroup(@Nullable final String group)
    {
        this.group = group;
    }

    @NotNull
    public ShapedPatternJson getPattern()
    {
        return pattern;
    }

    public void setPattern(@NotNull final ShapedPatternJson pattern)
    {
        this.pattern = pattern;
    }

    @NotNull
    public Map<String, RecipeIngredientKeyJson> getKey()
    {
        return key;
    }

    public void setKey(@NotNull final Map<String, RecipeIngredientKeyJson> key)
    {
        this.key = key;
    }

    @NotNull
    public RecipeResultJson getResult()
    {
        return result;
    }

    public void setResult(@NotNull final RecipeResultJson result)
    {
        this.result = result;
    }
}
