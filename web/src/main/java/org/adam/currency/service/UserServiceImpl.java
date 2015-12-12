package org.adam.currency.service;

import org.adam.currency.builder.AddressBuilder;
import org.adam.currency.builder.UserBuilder;
import org.adam.currency.command.UserCommand;
import org.adam.currency.common.RoleNameEnum;
import org.adam.currency.domain.Address;
import org.adam.currency.domain.Country;
import org.adam.currency.domain.Role;
import org.adam.currency.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Transactional
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private GenericService genericService;

    @Autowired
    private CountryService countryService;

    @Override
    public User findUserByName(String name) {
        return genericService.findByName(User.class, "name", name);
    }

    @Override
    public void createUser(UserCommand command) {
        Role defaultRole = findRoleByName(RoleNameEnum.ROLE_USER);
        Address address = toAddress(command);
        genericService.save(address);
        genericService.save(toUser(command, defaultRole, address));
    }

    @Override
    public Role findRoleByName(RoleNameEnum name) {
        return genericService.findByName(Role.class, "name", name);
    }

    private Address toAddress(UserCommand command) {
        Country country = countryService.findByCode(command.getCountry());
        return new AddressBuilder().withStreet(command.getStreet()).withCity(command.getCity()).withPostCode(command.getPostCode()).withCountry(country).build();
    }

    private User toUser(UserCommand command, Role defaultRole, Address address) {
        return new UserBuilder().withName(command.getUserName()).withEmailAddress(command.getEmail())
                .withFirstName(command.getFirstName()).withLastName(command.getLastName())
                .withPassword(command.getPassword()).withRoles(Collections.singletonList(defaultRole))
                .withAddress(address).build();

    }

}
