package com.enjoyit.utils;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

public class ListConverter<S, D> implements Converter<List<S>, List<D>> {

    @Override
    public List<D> convert(final MappingContext<List<S>, List<D>> context) {
        if (context.getSource() == null || context.getDestination() == null) {
            return new ArrayList<D>();
        }
        return ObjectMapper.mapAll(context.getSource(), (Class<D>) context.getDestination().getClass());
    }

}
