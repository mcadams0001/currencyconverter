package org.adam.currency.service;

import org.adam.currency.domain.User;

/**
 * User service for performing operations on user.
 */
public interface UserService {
    User findUserByName(String name);
}
