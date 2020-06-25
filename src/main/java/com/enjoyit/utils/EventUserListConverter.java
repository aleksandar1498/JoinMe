package com.enjoyit.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.spi.MappingContext;

import com.enjoyit.domain.dto.UserDTO;
import com.enjoyit.persistence.EventUser;

public class EventUserListConverter extends ListConverter<EventUser, UserDTO> {

    public EventUserListConverter() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public List<UserDTO> convert(final MappingContext<List<EventUser>, List<UserDTO>> context) {
        //TODO attention when retrieved by user
        System.out.println(context.getSource()+" "+context.getDestination());
        if (context.getSource() == null) {
            return new ArrayList<UserDTO>();
        }
        return context.getSource().stream().map(u -> {
           return new UserDTO(u.getUser().getId(), u.getUser().getUsername());
        }).collect(Collectors.toList());
    }

}
