package com.company.Util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexPatternMatcher {

    public static boolean patternMatch(String regex, String text) {
        return patternMatch(regex, 0, text);
    }

    public static boolean patternMatch(String regex, int regexOptions, String text) {
        return findPatternMatch(regex, regexOptions, text, -1) != null;
    }

    public static Float findFloatPatternMatch(String regex, int regexoptions, String text, int bracesNumber) {
        String pattern = findPatternMatch(regex, regexoptions, text, bracesNumber);
        try {
            return pattern == null ? null: Float.parseFloat(pattern);
        } catch(NumberFormatException e) {
            return null;
        }
    }


    private static String findPatternMatch(String regex, int regexoptions, String text, int bracesNumber) {
        if (text == null) {
            return null;
        }
        Pattern pattern = Pattern.compile(regex, regexoptions);
        Matcher matcher = pattern.matcher(text);
        String result = null;

        if (matcher.find()) {
            if(bracesNumber > 0) {
                result = matcher.group(2);
            } else {
                result = matcher.group();
            }
        }
        return result;
    }
}
