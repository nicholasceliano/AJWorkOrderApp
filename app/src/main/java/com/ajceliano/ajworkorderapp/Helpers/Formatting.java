package com.ajceliano.ajworkorderapp.Helpers;

/**
 * Created by Nick on 2/27/2015.
 */
public class Formatting {
    public static String FormatIncomingNET_JSON(String json){
        return json.substring(0, json.length() -1).substring(1).replace("\\", "");
    }

    public static String RemoveQuotes(String val) {
        return val.replace("\"", "");
    }
}
