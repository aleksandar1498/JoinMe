/**
 *
 */
package com.enjoyit.persistence.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.enjoyit.enums.LocationCategory;
import com.enjoyit.persistence.Event;
import com.enjoyit.persistence.Location;

/**
 * @author AStefanov
 */
@Entity
@Table(name = "locations")
public class JpaLocation extends BaseEntity implements Location {


    @Column
    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @Column
    @NotEmpty(message = "City cannot be empty")
    private String city;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "LocationCategory cannot be empty")
    private LocationCategory locationCategory;

    @OneToMany(mappedBy = "location",fetch = FetchType.LAZY,targetEntity = JpaEvent.class)
    private List<Event> events;

    public JpaLocation() {
    }

    public JpaLocation(final String name, final String city, final LocationCategory locationCategory) {
        this.name = name;
        this.city = city;
        this.locationCategory = locationCategory;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getCity() {
        return this.city;
    }

    @Override
    public List<Event> getEvents() {
        return this.events;
    }

    @Override
    public LocationCategory getLocationCategory() {
        return this.locationCategory;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public void setEvents(final List<Event> events) {
        this.events = events;
    }

    public void setLocationCategory(final LocationCategory locationCategory) {
        this.locationCategory = locationCategory;
    }



}
