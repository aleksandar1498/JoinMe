package com.enjoyit.utils;

public class GenericListConverter<S, D> extends ListConverter<S, D> {

    public GenericListConverter(final Class<?> destinationClass) {
        super(destinationClass);
    }

}
