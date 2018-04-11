package com.company.Util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexPatternMatcher {

    public static boolean patternMatch(String regex, String text, int bracesNumber) {
        return patternMatch(regex, 0, text, bracesNumber);
    }

    public static boolean patternMatch(String regex, int regexOptions, String text, int bracesNumber) {
        Pattern pattern = Pattern.compile(regex, regexOptions);
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            return true;
        }
        return false;
    }
}
