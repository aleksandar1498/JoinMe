package com.enjoyit.domain.dto;

public class JoinUserDTO {
    private UserDTO user;

    public JoinUserDTO() {
        // TODO Auto-generated constructor stub
    }


    public JoinUserDTO(final UserDTO user) {
        this.user = user;
    }


    public UserDTO getUser() {
        return user;
    }

    public void setUser(final UserDTO user) {
        this.user = user;
    }


}
