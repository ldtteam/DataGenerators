package com.ldtteam.datagenerators;

import com.google.gson.JsonElement;

public interface IJsonSerializable
{
    JsonElement serialize();

    void deserialize(final JsonElement jsonElement);
}
