package com.cct.util;

import java.util.Base64;

public final class AutoEvolutionUtils {

    private AutoEvolutionUtils() {
    }

    public static String encodeId(String id) {
        return Base64.getUrlEncoder().encodeToString(id.getBytes());
    }

    public static String decodeId(String id) {
        return new String(Base64.getUrlDecoder().decode(id));
    }

}
