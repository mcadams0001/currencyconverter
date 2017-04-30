package org.adam.currency.command;

import org.adam.currency.helper.EqualsTestHelper;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class UserCommandTest {
    private UserCommand userCommand = createUserCommand("user_name");
    private UserCommand userCommand2 = createUserCommand("user_name");
    private UserCommand userCommand3 = createUserCommand("different_name");

    @Test
    public void userCommandEquals() throws Exception {
        EqualsTestHelper.verifyEquals(userCommand, userCommand2, userCommand3);
    }

    @Test
    public void userCommandHashCode() throws Exception {
        EqualsTestHelper.verifyEquals(userCommand, userCommand2, userCommand3);
    }

    @Test
    public void userToString() throws Exception {
        assertThat(userCommand.toString(), equalTo("UserCommand{name='user_name'}"));
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