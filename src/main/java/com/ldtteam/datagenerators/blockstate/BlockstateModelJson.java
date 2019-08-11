package com.ldtteam.datagenerators.blockstate;

import com.google.gson.*;
import com.ldtteam.datagenerators.IJsonSerializable;
import net.minecraft.util.ResourceLocation;

public class BlockstateModelJson implements IJsonSerializable
{
    /**
     * Specifies the path to the model file of the block, starting in assets/<namespace>/models
     */
    private ResourceLocation model = null;

    /**
     * otation of the model on the x-axis in increments of 90 degrees.
     */
    private int x = 0;

    /**
     * Rotation of the model on the y-axis in increments of 90 degrees.
     */
    private int y = 0;

    /**
     * Locks the rotation of the texture of a block, if set to true. This way the texture will not rotate with the block when using the x and y-tags above.
     */
    private boolean uvlock = false;

    /**
     * Only applies when used in BlockStateVariant#models
     *
     * Sets the probability of the model for being used in the game, defaults to 1 (=100%).
     * If more than one model is used for the same variant, the probability will be calculated by dividing the individual model’s weight by the sum of the weights of all models.
     * (For example, if three models are used with weights 1, 1, and 2, then their combined weight would be 4 (1+1+2).
     * The probability of each model being used would then be determined by dividing each weight by 4: 1/4, 1/4 and 2/4, or 25%, 25% and 50%, respectively.)
     */
    private int weight = 0;

    public BlockstateModelJson() {}

    public BlockstateModelJson(final ResourceLocation model)
    {
        this(model, 0);
    }

    public BlockstateModelJson(final ResourceLocation model, final int x)
    {
        this(model, x, 0);
    }

    public BlockstateModelJson(final ResourceLocation model, final int x, final int y)
    {
        this(model, x, y, false);
    }

    public BlockstateModelJson(final ResourceLocation model, final int x, final int y, final boolean uvlock)
    {
        this(model, x, y, uvlock, 0);
    }

    public BlockstateModelJson(final ResourceLocation model, final int x, final int y, final boolean uvlock, final int weight)
    {
        this.model = model;
        this.x = x;
        this.y = y;
        this.uvlock = uvlock;
        this.weight = weight;
    }

    @Override
    public void deserialize(final JsonElement json)
    {
        final JsonObject modelJson = json.getAsJsonObject();

        if (modelJson.has("model"))
            this.model = new ResourceLocation(modelJson.get("model").getAsString());

        if (modelJson.has("x"))
            this.x = modelJson.get("x").getAsInt();

        if (modelJson.has("y"))
            this.y = modelJson.get("y").getAsInt();

        if (modelJson.has("uvlock"))
            this.uvlock = modelJson.get("uvlock").getAsBoolean();

        if (modelJson.has("weight"))
            this.weight = modelJson.get("weight").getAsInt();
    }

    @Override
    public JsonElement serialize()
    {
        final JsonObject returnValue = new JsonObject();

        if (this.model != null)
            returnValue.addProperty("model", this.model.toString());

        if (this.x != 0)
            returnValue.addProperty("x", this.x);

        if (this.y != 0)
            returnValue.addProperty("y", this.y);

        if (this.uvlock)
            returnValue.addProperty("uvlock", true);

        if (this.weight != 0)
            returnValue.addProperty("weight", this.weight);

        return returnValue;
    }
}