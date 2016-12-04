package com.tacademy.v04.chemi.common.util.encoder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yoon on 2016. 12. 4..
 */

public class UTF8Encoder {

    private static Pattern HANGLE_PATTERN = Pattern.compile("[\\x{ac00}-\\x{d7af}]");

    public static String encordUTF8(String input) {

        Matcher matcher = HANGLE_PATTERN.matcher(input);
        while (matcher.find()) {
            String group = matcher.group();

            try {
                input = input.replace(group, URLEncoder.encode(group, "UTF-8"));

            } catch (UnsupportedEncodingException e) {

            }
        }
        return input;
    }
}
