package com.enjoyit.domain.dto;

import java.util.ArrayList;
import java.util.List;

public class JoinEventDTO {
    private List<UserDTO> users;

    public JoinEventDTO() {
        this.users = new ArrayList<UserDTO>();
        // TODO Auto-generated constructor stub
    }

    public List<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(final List<UserDTO> users) {
        this.users = users;
    }



}
