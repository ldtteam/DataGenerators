package com.ldtteam.datagenerators.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ldtteam.datagenerators.IJsonSerializable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@Deprecated(forRemoval = true)
public class RecipeIngredientKeyJson implements IJsonSerializable
{

    /**
     * An entry made of a single ingredient.
     */
    @Nullable
    private RecipeIngredientJson ingredient;

    /**
     * An entry made of a list of acceptable ingredients.
     */
    @Nullable
    private List<RecipeIngredientJson> ingredients;

    public RecipeIngredientKeyJson() {}

    public RecipeIngredientKeyJson(@Nullable final RecipeIngredientJson ingredient)
    {
        this.ingredient = ingredient;
    }

    public RecipeIngredientKeyJson(@Nullable final List<RecipeIngredientJson> ingredients)
    {
        this.ingredients = ingredients;
    }

    @NotNull
    @Override
    public JsonElement serialize()
    {
        if (this.ingredient != null)
        {
            return this.ingredient.serialize();
        }

        if (this.ingredients != null && !this.ingredients.isEmpty())
        {
            final JsonArray jsonArray = new JsonArray();
            for (RecipeIngredientJson ingredient : this.ingredients)
            {
                jsonArray.add(ingredient.serialize());
            }
            return jsonArray;
        }

        return new JsonObject();
    }

    @Override
    public void deserialize(@NotNull final JsonElement jsonElement)
    {
        if (jsonElement.isJsonObject())
        {
            this.ingredient = new RecipeIngredientJson();
            this.ingredient.deserialize(jsonElement);
        }

        if (jsonElement.isJsonArray())
        {
            if (this.ingredients == null)
                this.ingredients = new ArrayList<>();

            for (JsonElement element : jsonElement.getAsJsonArray())
            {
                final RecipeIngredientJson ingredient = new RecipeIngredientJson();
                ingredient.deserialize(element);

                this.ingredients.add(ingredient);
            }
        }
    }

    @Nullable
    public RecipeIngredientJson getIngredient()
    {
        return ingredient;
    }

    public void setIngredient(@Nullable final RecipeIngredientJson ingredient)
    {
        this.ingredient = ingredient;
    }

    @Nullable
    public List<RecipeIngredientJson> getIngredients()
    {
        return ingredients;
    }

    public void setIngredients(@Nullable final List<RecipeIngredientJson> ingredients)
    {
        this.ingredients = ingredients;
    }
}
