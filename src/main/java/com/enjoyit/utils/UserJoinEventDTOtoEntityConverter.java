package com.enjoyit.utils;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.enjoyit.domain.dto.JoinEventDTO;
import com.enjoyit.persistence.EventUser;
import com.enjoyit.persistence.composite.UserJoinEventKey;
import com.enjoyit.persistence.repositories.UserJoinEventRepository;

@Component
public class UserJoinEventDTOtoEntityConverter implements Converter<JoinEventDTO, EventUser>{
    @Autowired
    private UserJoinEventRepository userJoinRepo;


    @Override
    public EventUser convert(final MappingContext<JoinEventDTO, EventUser> context) {
        if(context.getSource() == null) {
            return null;
        }
        return this.userJoinRepo.findById(new UserJoinEventKey(context.getSource().getUser().getId(), context.getSource().getEvent().getId())).orElse(null);
    }
}
