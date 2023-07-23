package com.minsoft.moviepedia.APIKeys;

public class APIKey {
    private static APIKey instance;

    private APIKey() {

    }

    public static APIKey getInstance() {
        if (instance == null) {
            instance = new APIKey();
        }
        return instance;
    }

    public String getYOUTUBE_API_KEY() {
        return "Your Youtube API KEY";
    }

    public String getTMDB_API_KEY() {
        return "Your TMDB API KEY";
    }
}
