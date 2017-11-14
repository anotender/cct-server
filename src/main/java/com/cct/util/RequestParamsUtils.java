package com.cct.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public final class RequestParamsUtils {

    private RequestParamsUtils() {
    }

    public static Collection<Long> extractLongIdsFromParam(String param) {
        return extractStringIdsFromParam(param)
                .stream()
                .map(Long::parseLong)
                .collect(Collectors.toSet());
    }

    public static Collection<String> extractStringIdsFromParam(String param) {
        return Arrays.asList(param.split(","));
    }

}
