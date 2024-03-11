package ru.ivanshirokov.poopapp.util.tgon;

import java.util.Arrays;
import java.util.List;

// tgon: /path/..._blood:i_mucus:i_...,    :i <---- val
public interface TgonParser<Model>{

    String mapToTgon(Model model);
    Model updateModelFromTgon(Model model, String tgon);

    default List<String> getValues(String tgon){
        return Arrays.stream(tgon.split("_"))
                .filter(s -> !s.contains("/"))
                .filter(s -> s.contains(":"))
                .map(s -> s.substring(s.indexOf(":") + 1))
                .toList();
    }
}
