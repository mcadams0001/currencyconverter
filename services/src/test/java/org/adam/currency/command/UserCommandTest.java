package org.adam.currency.command;

import org.adam.currency.helper.EqualsTestHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserCommandTest {
    private UserCommand userCommand = createUserCommand("user_name");
    private UserCommand userCommand2 = createUserCommand("user_name");
    private UserCommand userCommand3 = createUserCommand("different_name");

    @Test
    void userCommandEquals() {
        EqualsTestHelper.verifyEquals(userCommand, userCommand2, userCommand3);
    }

    @Test
    void userCommandHashCode() throws Exception {
        EqualsTestHelper.verifyHashCode(userCommand, userCommand2, userCommand3);
    }

    @Test
    void userToString() throws Exception {
        assertEquals("UserCommand{name='user_name'}", userCommand.toString());
    }

    private UserCommand createUserCommand(String userName) {
        UserCommand command = new UserCommand();
        command.setName(userName);
        command.setFirstName("firstName");
        command.setLastName("lastName");
        command.setBirthDate("01-Apr-2016");
        command.setEmail("email@email.com");
        command.setCountry("UK");
        command.setStreet("Road");
        command.setPostCode("W7 3RW");
        command.setPassword("password1");
        command.setRepeatPassword("password1");
        return command;
    }
}