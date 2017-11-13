package Util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class Helpers {

    private static DecimalFormat df = new DecimalFormat("#,###.##");

    /**
     * Returns a Double from a string that can be formatted with commas, but must be guaranteed to parse properly
     * @param guaranteedString - String to parse to double
     * @return - Double value of string
     */
    public static double guaranteedStringToDouble(String guaranteedString){
        double guaranteedDouble = 0;
        try {
            guaranteedDouble = NumberFormat.getNumberInstance(Locale.US).parse(guaranteedString).doubleValue();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return guaranteedDouble;
    }

    public static String formatDoubleToString(Double value){
        return df.format(value);
    }
}
