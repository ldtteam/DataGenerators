package com.ldtteam.datagenerators.sounds;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ldtteam.datagenerators.IJsonSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SoundsJson implements IJsonSerializable
{

    @NotNull
    private List<String> workers = new ArrayList<>();

    @NotNull
    private List<String> soundTypes = new ArrayList<>();

    /**
     * Prefix.
     */
    private String prefix = "";

    public SoundsJson() {}

    public SoundsJson(@NotNull final List<String> soundTypes, @NotNull final List<String> workers, @NotNull final String prefix)
    {
        this.soundTypes.addAll(soundTypes);
        this.workers.addAll(workers);
        this.prefix = prefix;
    }

    @NotNull
    @Override
    public JsonElement serialize()
    {
        final JsonObject returnValue = new JsonObject();

        final JsonObject defaultValue = new JsonObject();
        defaultValue.addProperty("category", "neutral");

        final JsonArray sounds = new JsonArray();
        final JsonObject sound1 = new JsonObject();
        sound1.addProperty("name", "minecolonies:mob/citizen/male/say1");
        sound1.addProperty("stream", false);
        sounds.add(sound1);

        final JsonObject sound2 = new JsonObject();
        sound2.addProperty("name", "minecolonies:mob/citizen/male/say2");
        sound2.addProperty("stream", false);
        sounds.add(sound2);

        final JsonObject sound3 = new JsonObject();
        sound3.addProperty("name", "minecolonies:mob/citizen/male/say2");
        sound3.addProperty("stream", false);
        sounds.add(sound3);

        defaultValue.add("sounds", sounds);

        final JsonObject childDefault = new JsonObject();
        childDefault.addProperty("category", "neutral");

        final JsonArray childSounds = new JsonArray();
        final JsonObject childSound1 = new JsonObject();
        childSound1.addProperty("name", "minecolonies:mob/citizen/male/say1");
        childSound1.addProperty("stream", false);
        childSounds.add(childSound1);

        final JsonObject childSound2 = new JsonObject();
        childSound2.addProperty("name", "minecolonies:mob/citizen/male/say2");
        childSound2.addProperty("stream", false);
        childSounds.add(childSound2);

        childDefault.add("sounds", childSounds);

        
        /*
        "mob.barbarian.death": {
        "category": "hostile",
                      "sounds": [
        {
            "name": "minecolonies:mob/barbarian/death",
                      "stream": false
        }
    ]
    },
        "mob.barbarian.hurt": {
        "category": "hostile",
                      "sounds": [
        {
            "name": "minecolonies:mob/barbarian/hurt1",
                      "stream": false
        },
        {
            "name": "minecolonies:mob/barbarian/hurt2",
                      "stream": false
        },
        {
            "name": "minecolonies:mob/barbarian/hurt3",
                      "stream": false
        },
        {
            "name": "minecolonies:mob/barbarian/hurt4",
                      "stream": false
        }
    ]
    },
        "mob.barbarian.say": {
        "category": "hostile",
                      "sounds": [
        {
            "name": "minecolonies:mob/barbarian/say",
                      "stream": false
        }
    ]
    },
        "mob.citizen.snore": {
        "category": "neutral",
                      "sounds": [
        {
            "name": "minecolonies:mob/citizen/snore",
                      "stream": false
        }
    ]
    }
    */


        for (final String worker : this.workers)
        {
            for (final String soundType : this.soundTypes)
            {
                returnValue.add(prefix + "." + worker + ".male." + soundType, defaultValue);
                returnValue.add(prefix + "." + worker + ".female." + soundType, defaultValue);
            }
        }

        for (final String soundType : this.soundTypes)
        {
            returnValue.add(prefix + ".citizen.male." + soundType, defaultValue);
            returnValue.add(prefix + ".citizen.female." + soundType, defaultValue);
        }

        for (final String soundType : this.soundTypes)
        {
            returnValue.add(prefix + ".child.male." + soundType, childDefault);
            returnValue.add(prefix + ".child.female." + soundType, childDefault);
        }

        return returnValue;
    }

    @Override
    public void deserialize(@NotNull final JsonElement jsonElement)
    {
        final JsonObject soundsJson = jsonElement.getAsJsonObject();

        for (Map.Entry<String, JsonElement> soundEntry : soundsJson.entrySet())
        {
            if (soundEntry.getKey().length() == 3)
            {
                final String[] keySplit = soundEntry.getKey().split(".");
                if (!keySplit[1].contains("child") && !keySplit[1].contains("citizen"))
                {
                    if (!this.workers.contains(keySplit[1]))
                    {
                        this.workers.add(keySplit[1]);
                    }

                    if (!this.soundTypes.contains(keySplit[3]))
                    {
                        this.soundTypes.add(keySplit[3]);
                    }
                }
            }
        }
    }
}
