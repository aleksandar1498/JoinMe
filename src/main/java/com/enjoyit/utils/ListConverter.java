    package com.enjoyit.utils;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

public abstract class ListConverter<S, D> implements Converter<List<S>, List<D>> {
    private Class<?> destinationClass ;

    public ListConverter() {
        // TODO Auto-generated constructor stub
    }
    public ListConverter(final Class<?> destinationClass2) {
        this.destinationClass = destinationClass2;
    }


    @Override
    public List<D> convert(final MappingContext<List<S>,List<D>> context) {
        System.out.println(context.getSource()+" "+this.destinationClass);
        if (context.getSource() == null || context.getSource().size() == 0) {
            return new ArrayList<D>();
        }
       context.getSource().forEach( x -> {
           System.out.println(x);
       });
        System.out.println("HERE "+ObjectMapper.mapAll(context.getSource(), this.destinationClass));
        return (List<D>) ObjectMapper.mapAll(context.getSource(), this.destinationClass);
    }

}
