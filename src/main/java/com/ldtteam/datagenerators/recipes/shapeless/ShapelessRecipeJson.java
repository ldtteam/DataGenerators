package com.ldtteam.datagenerators.recipes.shapeless;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ldtteam.datagenerators.IJsonSerializable;
import com.ldtteam.datagenerators.recipes.RecipeIngredientKeyJson;
import com.ldtteam.datagenerators.recipes.RecipeResultJson;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ShapelessRecipeJson implements IJsonSerializable
{

    /**
     *  A namespaced ID indicating the type of serializer of the recipe.
     */
    @NotNull
    private static final String TYPE = "minecraft:crafting_shapeless";

    /**
     * A string identifier.
     * Used to group multiple recipes together in the recipe book.
     */
    @Nullable
    private String group;

    /**
     *  A list of entries for this shapeless crafting recipe.
     *  Must have 1 to 9 entries.
     */
    @NotNull
    private List<RecipeIngredientKeyJson> ingredients = new ArrayList<>();

    @NotNull
    private RecipeResultJson result = new RecipeResultJson();

    /**
     * The type of the recipe.
     * By default defines a vanilla shapeless recipe.
     * Use the overloaded constructor to define a custom type.
     */
    @NotNull
    private String recipeType = TYPE;

    public ShapelessRecipeJson() {}

    public ShapelessRecipeJson(@Nullable final String group,
                              @NotNull final List<RecipeIngredientKeyJson> ingredients,
                              @NotNull final RecipeResultJson result)
    {
        this.group = group;
        this.ingredients = ingredients;
        this.result = result;
    }

    private ShapelessRecipeJson(
      @Nullable final String group,
      @NotNull final List<RecipeIngredientKeyJson> ingredients,
      @NotNull final RecipeResultJson result,
      @NotNull final String recipeType)
    {
        this.group = group;
        this.ingredients = ingredients;
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

        final JsonArray ingredientsArray = new JsonArray();
        for (RecipeIngredientKeyJson ingredient : this.ingredients)
        {
            ingredientsArray.add(ingredient.serialize());
        }
        returnValue.add("ingredients", ingredientsArray);

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

        for (JsonElement ingredient : recipeJson.getAsJsonArray("ingredients"))
        {
            final RecipeIngredientKeyJson ingredientJson = new RecipeIngredientKeyJson();
            ingredientJson.deserialize(ingredient);
            this.ingredients.add(ingredientJson);
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
    public List<RecipeIngredientKeyJson> getIngredients()
    {
        return ingredients;
    }

    public void setIngredients(@NotNull final List<RecipeIngredientKeyJson> ingredients)
    {
        this.ingredients = ingredients;
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
