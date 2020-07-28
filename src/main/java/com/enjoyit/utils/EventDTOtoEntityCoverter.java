package com.enjoyit.utils;


import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.enjoyit.domain.dto.EventDTO;
import com.enjoyit.persistence.Event;
import com.enjoyit.persistence.repositories.EventRepository;

@Component
public class EventDTOtoEntityCoverter implements Converter<EventDTO, Event> {

    @Autowired
    private EventRepository eventRepo;

    @Override
    public Event convert(final MappingContext<EventDTO, Event> context) {
        if(context.getSource() == null) {
            return null;
        }
        return this.eventRepo.findById(context.getSource().getId()).orElse(null);
    }

}
