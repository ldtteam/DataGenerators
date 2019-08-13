package com.ldtteam.datagenerators.loot_table.pool.entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ldtteam.datagenerators.IJsonSerializable;
import com.ldtteam.datagenerators.loot_table.pool.conditions.IPoolCondition;
import com.ldtteam.datagenerators.loot_table.pool.conditions.PoolConditions;
import com.ldtteam.datagenerators.loot_table.pool.entry.children.EntryChildren;
import com.ldtteam.datagenerators.loot_table.pool.entry.children.IEntryChild;
import com.ldtteam.datagenerators.loot_table.pool.entry.functions.EntryFunctions;
import com.ldtteam.datagenerators.loot_table.pool.entry.functions.IEntryFunction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class EntryJson implements IJsonSerializable
{
    /**
     *  Determines conditions for this entry to be used.
     *  If multiple conditions are specified, all must pass.
     *
     * Conditions are registered in {@link com.ldtteam.datagenerators.loot_table.pool.conditions.PoolConditions#POOL_CONDITIONS}
     */
    @Nullable
    private List<IPoolCondition> conditions;

    /**
     * Namespaced ID type of entry.
     * {@link EntryTypeEnum#ITEM} for item entries,
     * {@link EntryTypeEnum#TAG} for item tags,
     * {@link EntryTypeEnum#LOOT_TABLE} to produce items from another loot table,
     * {@link EntryTypeEnum#GROUP} for child entries,
     * {@link EntryTypeEnum#ALTERNATIVES} to select one sub-entry from a list,
     * {@link EntryTypeEnum#SEQUENCE} to select sub-entries until one entry cannot be granted,
     * {@link EntryTypeEnum#DYNAMIC} to generate block specific drops,
     * or {@link EntryTypeEnum#EMPTY} for an entry that generates nothing.
     */
    @NotNull
    private EntryTypeEnum type = EntryTypeEnum.ITEM;

    /**
     * For type {@link EntryTypeEnum#ITEM}, ID name of the item to be produced, e.g. diamond.
     * The default, if not changed by functions, is a stack of 1 of the default instance of the item.
     *
     * For type {@link EntryTypeEnum#TAG} item tag to be used, e.g. arrows.
     *
     * For type {@link EntryTypeEnum#LOOT_TABLE}, loot table to be used, e.g. gameplay/fishing/junk.
     *
     * For type {@link EntryTypeEnum#DYNAMIC}, can be contents for block entity contents or self for banners and player skulls.
     */
    @Nullable
    private String name;

    /**
     * For type {@link EntryTypeEnum#GROUP} a list of entries that are used to generate loot. Can be used for convinience,
     * e.g. if one condition applies for multiple entries.
     *
     * For type {@link EntryTypeEnum#ALTERNATIVES} a list of entries of which the first,
     * and only the first, succesful entry gets generated.
     *
     * For type '{@link EntryTypeEnum#SEQUENCE} a list of entries that are used until the first entry fails.
     * After an entry fails, no more entries of this list will be generated
     *
     * children are registered in {@link com.ldtteam.datagenerators.loot_table.pool.entry.children.EntryChildren#ENTRY_CHILDREN}
     */
    @Nullable
    private List<IEntryChild> children;

    /**
     *  For type {@link EntryTypeEnum#TAG}, if set to true,
     *  it chooses one item of the tag, each with the same weight and quality.
     *  If false, it uses all the items in the tag.
     */
    private boolean expand = false;

    /**
     * For type {@link EntryTypeEnum#ITEM}, applies functions to the item stack being produced.
     * Functions are applied in order, so for example looting_enchant must be after set_count to work correctly.
     */
    @Nullable
    private List<IEntryFunction> functions;

    /**
     * Determines how often this entry will be chosen out of all the entries in the pool.
     * Entries with higher weights will be used more often (chance is this entry's weight⁄total of all considered entries' weights).
     */
    private int weight;

    /**
     * Modifies the entry's weight based on the killing/opening/fishing player's luck attribute.
     * Formula is floor( weight + (quality * generic.luck)).
     */
    private int quality;

    public EntryJson() {}

    public EntryJson(@Nullable final List<IPoolCondition> conditions,
                     @NotNull final EntryTypeEnum type,
                     @Nullable final String name,
                     @Nullable final List<IEntryChild> children,
                     final boolean expand,
                     @Nullable final List<IEntryFunction> functions,
                     final int weight,
                     final int quality)
    {
        this.conditions = conditions;
        this.type = type;
        this.name = name;
        this.children = children;
        this.expand = expand;
        this.functions = functions;
        this.weight = weight;
        this.quality = quality;
    }

    @NotNull
    @Override
    public JsonElement serialize()
    {
        final JsonObject returnValue = new JsonObject();

        if (this.conditions != null && !this.conditions.isEmpty())
        {
            final JsonArray conditionsJson = new JsonArray();
            for (IPoolCondition condition : this.conditions)
            {
                conditionsJson.add(condition.serialize());
            }
            returnValue.add("conditions", conditionsJson);
        }

        returnValue.addProperty("type", this.type.toString());

        if (this.name != null && (this.type.equals(EntryTypeEnum.ITEM) ||
                this.type.equals(EntryTypeEnum.TAG) ||
                this.type.equals(EntryTypeEnum.LOOT_TABLE) ||
                this.type.equals(EntryTypeEnum.DYNAMIC)))
        {
            returnValue.addProperty("name", this.name);
        }

        if (this.children != null && !this.children.isEmpty() && (this.type.equals(EntryTypeEnum.ITEM) ||
                this.type.equals(EntryTypeEnum.TAG) ||
                this.type.equals(EntryTypeEnum.LOOT_TABLE) ||
                this.type.equals(EntryTypeEnum.DYNAMIC)))
        {
            final JsonArray childrenJson = new JsonArray();
            for (IEntryChild child : this.children)
            {
                childrenJson.add(child.serialize());
            }
            returnValue.add("children", childrenJson);
        }

        if (this.type.equals(EntryTypeEnum.TAG))
            returnValue.addProperty("expand", this.expand);

        if (this.functions != null && !this.functions.isEmpty() && this.type.equals(EntryTypeEnum.ITEM))
        {
            final JsonArray functionsJson = new JsonArray();
            for (IEntryFunction function : this.functions)
            {
                functionsJson.add(function.serialize());
            }
            returnValue.add("functions", functionsJson);
        }

        if (this.weight != 0)
            returnValue.addProperty("weight", this.weight);

        if (this.quality != 0)
            returnValue.addProperty("quality", this.quality);

        return returnValue;
    }

    @Override
    public void deserialize(@NotNull final JsonElement jsonElement)
    {
        final JsonObject entryJson = jsonElement.getAsJsonObject();
        
        if (entryJson.has("conditions"))
        {
            if (this.conditions == null)
                this.conditions = new ArrayList<>();

            final JsonArray conditionsJson = entryJson.getAsJsonArray("conditions");
            for (JsonElement condition : conditionsJson)
            {
                final IPoolCondition poolCondition = PoolConditions.deserializeCondition(condition);
                if (poolCondition != null)
                    this.conditions.add(poolCondition);
            }
        }

        final EntryTypeEnum type = EntryTypeEnum.getFromName(entryJson.get("type").getAsString());
        if (type != null)
            this.type = type;
        else
            this.type = EntryTypeEnum.ITEM;

        if (entryJson.has("name") && (this.type.equals(EntryTypeEnum.ITEM) ||
            this.type.equals(EntryTypeEnum.TAG) ||
            this.type.equals(EntryTypeEnum.LOOT_TABLE) ||
            this.type.equals(EntryTypeEnum.DYNAMIC)))
        {
            this.name = entryJson.get("name").getAsString();
        }

        if (entryJson.has("children") && (this.type.equals(EntryTypeEnum.GROUP) ||
                this.type.equals(EntryTypeEnum.ALTERNATIVES) ||
                this.type.equals(EntryTypeEnum.SEQUENCE)))
        {
            if (this.children == null)
                this.children = new ArrayList<>();

            final JsonArray childrenJson = entryJson.getAsJsonArray("children");
            for (JsonElement child : childrenJson)
            {
                final IEntryChild entryChild = EntryChildren.deserializeChild(this.type, child);
                if (entryChild != null)
                    this.children.add(entryChild);
            }
        }

        if (this.type.equals(EntryTypeEnum.TAG) && entryJson.has("expand"))
            this.expand = entryJson.get("expand").getAsBoolean();

        if (entryJson.has("functions") && this.type.equals(EntryTypeEnum.ITEM))
        {
            if (this.functions == null)
                this.functions = new ArrayList<>();

            final JsonArray functionsJson = entryJson.getAsJsonArray("functions");
            for (JsonElement function : functionsJson)
            {
                final IEntryFunction entryFunction = EntryFunctions.deserializeFunction(function);
                if (entryFunction != null)
                    this.functions.add(entryFunction);
            }
        }

        if (entryJson.has("weight"))
            this.weight = entryJson.get("weight").getAsInt();

        if (entryJson.has("quality"))
            this.quality = entryJson.get("quality").getAsInt();
    }

    @Nullable
    public List<IPoolCondition> getConditions()
    {
        return conditions;
    }

    public void setConditions(@Nullable final List<IPoolCondition> conditions)
    {
        this.conditions = conditions;
    }

    @NotNull
    public EntryTypeEnum getType()
    {
        return type;
    }

    public void setType(@NotNull final EntryTypeEnum type)
    {
        this.type = type;
    }

    @Nullable
    public String getName()
    {
        return name;
    }

    public void setName(@Nullable final String name)
    {
        this.name = name;
    }

    @Nullable
    public List<IEntryChild> getChildren()
    {
        return children;
    }

    public void setChildren(@Nullable final List<IEntryChild> children)
    {
        this.children = children;
    }

    public boolean isExpand()
    {
        return expand;
    }

    public void setExpand(final boolean expand)
    {
        this.expand = expand;
    }

    @Nullable
    public List<IEntryFunction> getFunctions()
    {
        return functions;
    }

    public void setFunctions(@Nullable final List<IEntryFunction> functions)
    {
        this.functions = functions;
    }

    public int getWeight()
    {
        return weight;
    }

    public void setWeight(final int weight)
    {
        this.weight = weight;
    }

    public int getQuality()
    {
        return quality;
    }

    public void setQuality(final int quality)
    {
        this.quality = quality;
    }
}
