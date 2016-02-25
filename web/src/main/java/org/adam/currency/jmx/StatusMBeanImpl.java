package org.adam.currency.jmx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@ManagedResource(
        objectName = "org.adam.currency:name=statusBean",
        description = "Application Status Bean")
@Component("statusMBean")
public class StatusMBeanImpl implements StatusMBean {
    @Autowired
    @Qualifier("sessionRegistry")
    private SessionRegistry sessionRegistry;

    @ManagedAttribute(description="Currently Active Users", currencyTimeLimit=15)
    @Override
    public List<String> getCurrentActiveUsers() {
        List<Object> principals = sessionRegistry.getAllPrincipals();
        List<String> userNames = new ArrayList<>();

        for (Object principal: principals) {
            if (principal instanceof User) {
                userNames.add(((User) principal).getUsername());
            }
        }
        return userNames;
    }
}
