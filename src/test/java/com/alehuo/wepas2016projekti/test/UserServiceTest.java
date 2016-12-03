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
package com.alehuo.wepas2016projekti.test;

import com.alehuo.wepas2016projekti.domain.Role;
import com.alehuo.wepas2016projekti.domain.UserAccount;
import com.alehuo.wepas2016projekti.service.UserService;
import java.util.UUID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * UserServicen testailua
 *
 * @author alehuo
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    /**
     * Testi testaa, että käyttäjätilin lisäys toimii userServicen kautta
     */
    @Test
    public void kayttajaTilinLisaysToimiiUserServicenAvulla() {
        
        //Luodaan satunnainen data
        String username = UUID.randomUUID().toString().substring(0, 8);
        String email = UUID.randomUUID().toString().substring(0, 8) + "@test.com";
        Role role = Role.USER;

        userService.createNewUser(username, "helloworld", email, role);

        UserAccount u = userService.getUserByUsername(username);

        assertNotNull(u);
        assertEquals("Käyttäjätunnusta ei aseteta oikein", username, u.getUsername());
        assertEquals("Sähköpostiosoitetta ei aseteta oikein", email, u.getEmail());
        assertEquals("Käyttäjätunnuksen roolia ei aseteta oikein", role, u.getRole());
    }
}
