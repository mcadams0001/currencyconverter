package org.adam.currency.helper;

import org.adam.currency.domain.User;
import org.adam.currency.dto.UserDTO;

import java.util.function.Function;

public class UserTransformer implements Function<User, UserDTO> {
    @Override
    public UserDTO apply(User user) {
        if (user == null) {
            return new UserDTO();
        }
        UserDTO dto = new UserDTO();
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        return dto;
    }
}
