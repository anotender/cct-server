package com.cct.service.extractor;

import java.util.Collection;

@FunctionalInterface
public interface Extractor<T, F> {
    Collection<T> extract(F from);
}
