package ru.ivanshirokov.poopapp.util.cache;

public interface ModelCache <Model>{

    boolean isCached(long id);
    void addToCache(Model model);
    void clearCache();
    Model getFromCache(long id);
}
