package com.ldtteam.datagenerators;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class DataGeneratorConstants
{

    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    // ResourcePack Directories \\

    public static final String EN_US_LANG = "assets/structurize/lang/en_us.json";
}
