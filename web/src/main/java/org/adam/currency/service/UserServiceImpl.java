package org.adam.currency.service;

import org.adam.currency.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private GenericService genericService;

    @Override
    public User findUserByName(String name) {
        return genericService.findByName(User.class, "name", name);
    }

}
