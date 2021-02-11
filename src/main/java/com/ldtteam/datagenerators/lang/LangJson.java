package com.ldtteam.datagenerators.lang;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ldtteam.datagenerators.IJsonSerializable;
import com.ldtteam.datagenerators.Utils;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class LangJson implements IJsonSerializable
{

    @NotNull
    private Map<String, String> lang = new TreeMap<>();
    private Map<String, String> oldLang = new LinkedHashMap<>();

    public LangJson() {}

    public LangJson(@NotNull final Map<String, String> lang)
    {
        this.lang = Utils.ensureTreeMap(lang);
    }

    @NotNull
    @Override
    public JsonElement serialize()
    {
        final JsonObject returnValue = new JsonObject();

        final Map<String, String> copyOld = new LinkedHashMap<>(oldLang);
        copyOld.keySet().removeAll(lang.keySet());

        // put datagen on top of lang file
        for (final Map.Entry<String, String> langEntry : this.lang.entrySet())
        {
            returnValue.addProperty(langEntry.getKey(), langEntry.getValue());
        }
        // put marker to distinguish datagen and normal
        returnValue.addProperty("__comment", "Datagen-only lang entries above");
        // put rest of old lang file
        for (final Map.Entry<String, String> langEntry : copyOld.entrySet())
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

    public void deserializeOldLang(@NotNull final JsonElement jsonElement)
    {
        for (final Map.Entry<String, JsonElement> langEntry : jsonElement.getAsJsonObject().entrySet())
        {
            this.oldLang.put(langEntry.getKey(), langEntry.getValue().getAsString());
        }
    }

    @NotNull
    public Map<String, String> getLang()
    {
        return lang;
    }

    public void setLang(@NotNull final Map<String, String> lang)
    {
        this.lang = Utils.ensureTreeMap(lang);
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
