package com.enjoyit.utils;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.enjoyit.domain.dto.InterestEventDTO;
import com.enjoyit.persistence.EventUser;
import com.enjoyit.persistence.composite.UserInterestEventKey;
import com.enjoyit.persistence.repositories.UserInterestEventRepository;

@Component
public class UserInterestEventDTOtoEntityConverter implements Converter<InterestEventDTO, EventUser> {
    @Autowired
    private UserInterestEventRepository userInterestRepo;


    @Override
    public EventUser convert(final MappingContext<InterestEventDTO, EventUser> context) {
        if(context.getSource() == null) {
            return null;
        }
        return this.userInterestRepo.findById(new UserInterestEventKey(context.getSource().getUser().getId(), context.getSource().getEvent().getId())).orElse(null);
    }

}
