package com.ldtteam.datagenerators.sounds;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ldtteam.datagenerators.IJsonSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SoundsJson implements IJsonSerializable
{
    @NotNull
    final Map<String[], List<String>> sounds = new HashMap<>();

    public SoundsJson() {}

    public SoundsJson(@NotNull final Map<String[], List<String>> sounds)
    {
        this.sounds.putAll(sounds);
    }

    @NotNull
    @Override
    public JsonElement serialize()
    {
        final JsonObject returnValue = new JsonObject();

        for (final Map.Entry<String[], List<String>> entry : sounds.entrySet())
        {
            final JsonObject defaultValue = new JsonObject();
            defaultValue.addProperty("category", entry.getKey()[1]);

            final JsonArray sounds = new JsonArray();
            for (final String value : entry.getValue())
            {
                final JsonObject sound1 = new JsonObject();
                sound1.addProperty("name", value);
                sound1.addProperty("stream", false);
                sounds.add(sound1);
            }

            defaultValue.add("sounds", sounds);
            returnValue.add(entry.getKey()[0], defaultValue);
        }

        return returnValue;
    }

    @Override
    public void deserialize(@NotNull final JsonElement jsonElement)
    {
        final JsonObject soundsJson = jsonElement.getAsJsonObject();

        for (Map.Entry<String, JsonElement> soundEntry : soundsJson.entrySet())
        {
            final String key = soundEntry.getKey();
            final JsonObject entryJson =  soundEntry.getValue().getAsJsonObject();

            final String category = entryJson.get("category").getAsString();

            final List<String> sounds = new ArrayList<>();
            final JsonArray array = entryJson.getAsJsonArray("sounds");
            for (int i = 0; i < array.size(); i++)
            {
                final JsonObject obj = array.get(i).getAsJsonObject();
                sounds.add(obj.get("name").getAsString());
            }

            this.sounds.put(new String[]{key, category}, sounds);
        }
    }
}
