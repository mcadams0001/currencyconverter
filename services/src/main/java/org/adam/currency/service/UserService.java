package org.adam.currency.service;

import org.adam.currency.command.UserCommand;
import org.adam.currency.common.RoleNameEnum;
import org.adam.currency.domain.Role;
import org.adam.currency.domain.User;

/**
 * User service for performing operations on user.
 */
public interface UserService {
    /**
     * Finds user by user name.
     * @param name the user name.
     * @return user instance.
     */
    User findUserByName(String name);

    /**
     * Creates a new user base on command passed from registration page.
     * @param command the command object from registration page.
     * @return a newly created instance of user with default role ROLE_USER.
     */
    User createUser(UserCommand command);

    /**
     * Finds role by role enumeration.
     * @param name the role enumeration value.
     * @return role instance retrieved from database.
     */
    Role findRoleByName(RoleNameEnum name);
}
