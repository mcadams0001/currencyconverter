package org.adam.currency.service;

import org.adam.currency.command.UserCommand;
import org.adam.currency.common.RoleNameEnum;
import org.adam.currency.domain.Role;
import org.adam.currency.domain.User;

/**
 * User service for performing operations on user.
 */
public interface UserService {
    User findUserByName(String name);
    User createUser(UserCommand command);
    Role findRoleByName(RoleNameEnum name);
}
