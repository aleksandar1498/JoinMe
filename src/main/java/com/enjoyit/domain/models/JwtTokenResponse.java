package com.enjoyit.domain.models;

import java.io.Serializable;

/**
 * @author AStefanov
 *
 */
public class JwtTokenResponse implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = -5089290570663960299L;


    private String jwtToken;


    public JwtTokenResponse(final String jwtToken) {
        this.jwtToken = jwtToken;
    }


    public String getJwtToken() {
        return jwtToken;
    }


    public void setJwtToken(final String jwtToken) {
        this.jwtToken = jwtToken;
    }



}
