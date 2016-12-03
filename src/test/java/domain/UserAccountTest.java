/*
 * Copyright (C) 2016 alehuo
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package domain;

import com.alehuo.wepas2016projekti.domain.Role;
import com.alehuo.wepas2016projekti.domain.UserAccount;
import com.alehuo.wepas2016projekti.service.UserService;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author alehuo
 */
/*@RunWith(SpringRunner.class)
@SpringBootTest(classes={UserService.class})*/
public class UserAccountTest {

//    @Autowired
//    private UserService userService;
//
//    public UserAccountTest() {
//
//    }
//
//    @Test
//    public void kayttajaTilinLisaysToimii() {
//        userService.createNewUser("administrator", "abc123", "administrator@localhost.com", Role.ADMINISTRATOR);
//        UserAccount u = userService.getUserByUsername("administrator");
//        assertNotNull(u);
//        assertEquals("administrator", u.getUsername());
//        assertEquals("administrator@localhost.com", u.getEmail());
//        assertEquals(Role.ADMINISTRATOR, u.getRole());
//        u = userService.getUserById(1L);
//        assertNotNull(u);
//        assertEquals("administrator", u.getUsername());
//        assertEquals("administrator@localhost.com", u.getEmail());
//        assertEquals(Role.ADMINISTRATOR, u.getRole());
////    }
}
