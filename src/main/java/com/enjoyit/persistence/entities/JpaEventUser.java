/**
 *
 */
package com.enjoyit.persistence.entities;

import com.enjoyit.persistence.Event;
import com.enjoyit.persistence.EventUser;
import com.enjoyit.persistence.User;

/**
 * @author AStefanov
 */

public abstract class JpaEventUser implements EventUser {

    protected User user;
    protected Event event;

    public JpaEventUser() {

    }

    protected JpaEventUser(final User user, final Event event) {
        this.event = event;
        this.user = user;
    }

}
