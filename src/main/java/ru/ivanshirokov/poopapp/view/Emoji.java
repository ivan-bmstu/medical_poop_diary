package ru.ivanshirokov.poopapp.view;

import java.util.List;

public class Emoji {
    public final static String BLOOD = "\uD83E\uDE78";
    public final static String MUCUS = "\uD83E\uDDA0";
    public final static String CANCEL = "❌";
    public final static String TOILET = "\uD83D\uDEBD";
    public final static String BACK = "⬅️";
    public final static String CHECK_MARK = "✅";
    public final static String POOP = "\uD83D\uDCA9";
    public final static String SOLID = "\uD83D\uDFE4";
    public final static String SCORE = "◽\uFE0F";
    public final static String EMPTY_SCORE = "◾\uFE0F";


    public final static String ZERO = "0️⃣";
    public final static String ONE = "1️⃣";
    public final static String TWO = "2️⃣";
    public final static String THREE = "3️⃣";
    public final static String FOUR = "4️⃣";
    public final static String FIVE = "5️⃣";
    public final static String SIX = "6️⃣";
    public final static String SEVEN = "7️⃣";
    public final static String EIGHT = "8️⃣";
    public final static String NINE = "9️⃣";
    public final static List<String> numeralEmoji;
    
    static {
        numeralEmoji = List.of(
                ZERO, ONE, TWO, THREE, FOUR,
                FIVE, SIX, SEVEN, EIGHT, NINE);
    }

}
