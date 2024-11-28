package com.github.JoseAngelGiron.utils;

public class Utils {
    /**
     * Converts a boolean value to an integer.
     *
     * @param bool The boolean value to be converted.
     * @return 1 if the boolean value is true, 0 if it's false.
     */
    public static int booleanToInt(boolean bool) {
        return bool ? 1 : 0;
    }

    /**
     * Converts an integer to a boolean value.
     * <p>
     * Conversion rule:
     * - Returns `true` if the number is not 0.
     * - Returns `false` if the number is 0.
     *
     * @param number the integer to convert.
     * @return `true` if the number is not 0, `false` if the number is 0.
     */
    public static boolean intToBoolean(int number) {
        return number != 0;
    }

}
