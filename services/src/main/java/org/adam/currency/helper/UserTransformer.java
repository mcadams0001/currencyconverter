package org.adam.currency.helper;

import org.adam.currency.domain.User;
import org.adam.currency.dto.UserDTO;
import org.apache.commons.collections4.Transformer;

public class UserTransformer implements Transformer<User, UserDTO> {
    @Override
    public UserDTO transform(User user) {
        if(user == null) {
            return new UserDTO();
        }
        UserDTO dto = new UserDTO();
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        return dto;
    }
}
