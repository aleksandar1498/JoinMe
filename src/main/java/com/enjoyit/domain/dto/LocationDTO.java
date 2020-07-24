package com.enjoyit.domain.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.enjoyit.enums.LocationCategory;

public class LocationDTO {
    private String id;
    @NotEmpty(message = "City cannot be empty")
    private String city;
    @NotEmpty(message = "Address cannot be empty")
    private String address;
    @NotNull(message = "Location Category cannot be not set")
    private LocationCategory locationCategory;

    public LocationDTO() {
    }

    public LocationDTO(final String id, final String city, final String address,
            final LocationCategory locationCategory) {
        this.id = id;
        this.city = city;
        this.address = address;
        this.locationCategory = locationCategory;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getId() {
        return id;
    }

    public LocationCategory getLocationCategory() {
        return locationCategory;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public void setLocationCategory(final LocationCategory locationCategory) {
        this.locationCategory = locationCategory;
    }

    @Override
    public String toString() {
        return "LocationDTO [id=" + id + ", city=" + city + ", address=" + address + ", locationCategory="
                + locationCategory + "]";
    }

}
