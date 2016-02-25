package org.adam.currency.jmx;

import java.util.List;

/**
 * JMX MBean with application status.
 */
public interface StatusMBean {
    /**
     * Gets a list of currently active users.
     * @return list of user names being currently logged in to the application.
     */
    List<String> getCurrentActiveUsers();
}
