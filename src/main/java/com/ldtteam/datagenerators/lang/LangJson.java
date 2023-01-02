package com.ldtteam.datagenerators.lang;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ldtteam.datagenerators.IJsonSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

@Deprecated(forRemoval = true)
public class LangJson implements IJsonSerializable
{

    @NotNull
    private Map<String, String> lang = new HashMap<>();

    public LangJson() {}

    public LangJson(@NotNull final Map<String, String> lang)
    {
        this.lang = lang;
    }

    @NotNull
    @Override
    public JsonElement serialize()
    {
        final JsonObject returnValue = new JsonObject();

        for (Map.Entry<String, String> langEntry : this.lang.entrySet())
        {
            returnValue.addProperty(langEntry.getKey(), langEntry.getValue());
        }

        return returnValue;
    }

    @Override
    public void deserialize(@NotNull final JsonElement jsonElement)
    {
        final JsonObject langJson = jsonElement.getAsJsonObject();

        for (Map.Entry<String, JsonElement> langEntry : langJson.entrySet())
        {
            this.lang.put(langEntry.getKey(), langEntry.getValue().getAsString());
        }
    }

    @NotNull
    public Map<String, String> getLang()
    {
        return lang;
    }

    public void setLang(@NotNull final Map<String, String> lang)
    {
        this.lang = lang;
    }

    public String put(final String key, final String value)
    {
        return lang.put(key, value);
    }

    public void putAll(@NotNull final Map<? extends String, ? extends String> m)
    {
        lang.putAll(m);
    }
}
