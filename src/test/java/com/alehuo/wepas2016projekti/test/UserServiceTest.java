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
import com.alehuo.wepas2016projekti.service.CommentService;
import com.alehuo.wepas2016projekti.service.ImageService;
import com.alehuo.wepas2016projekti.service.InitService;
import com.alehuo.wepas2016projekti.service.UserService;
import java.util.List;
import java.util.UUID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
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

    @Autowired
    private CommentService commentService;

    @Autowired
    private ImageService imageService;
    
    @Autowired
    private InitService initService;
    
    /**
     *
     */
    @Before
    public void setUp() {
        initService.resetApplicationState();
    }

    /**
     * Testi testaa, että käyttäjätilin lisäys toimii userServicen kautta
     */
    @Test
    public void kayttajaTilinLisaysToimiiUserServicenAvulla() {

        //Luodaan satunnainen käyttäjä
        UserAccount randomUser = generateAndSaveRandomUser();

        UserAccount u = userService.getUserByUsername(randomUser.getUsername());

        assertNotNull(u);
        assertEquals("Käyttäjätunnusta ei aseteta oikein", randomUser.getUsername(), u.getUsername());
        assertEquals("Sähköpostiosoitetta ei aseteta oikein", randomUser.getEmail(), u.getEmail());
        assertEquals("Käyttäjätunnuksen roolia ei aseteta oikein", randomUser.getRole(), u.getRole());
    }

    /**
     * Testaa käyttäjätilien haun toiminnallisuuden
     */
    @Test
    public void kayttajaTilinHakuToimiiUserServicenAvulla() {
        //Luodaan satunnainen käyttäjä
        UserAccount randomUser1 = generateAndSaveRandomUser();
        //Luodaan satunnainen käyttäjä
        UserAccount randomUser2 = generateAndSaveRandomAdmin();
        //Luodaan satunnainen käyttäjä
        UserAccount randomUser3 = generateAndSaveRandomUser();

        List<UserAccount> users = userService.getAllUsers();

        assertEquals("Käyttäjätilejä ei palautunut tarvittava määrä", 5, users.size());
        assertTrue(users.contains(randomUser1));
        assertTrue(users.contains(randomUser2));
        assertTrue(users.contains(randomUser3));
    }

    /**
     *
     */
    @Test
    public void kayttajaTilinHakuToimiiIdlläUserServicenAvulla() {
        //Luodaan satunnainen käyttäjä
        UserAccount randomUser1 = generateAndSaveRandomUser();

        assertEquals(randomUser1, userService.getUserById(randomUser1.getId()));
    }

    /**
     * Luo uuden satunnaisen käyttäjän
     *
     * @return Käyttäjä
     */
    public UserAccount generateAndSaveRandomUser() {
        String username = UUID.randomUUID().toString().substring(0, 8);
        String email = UUID.randomUUID().toString().substring(0, 8) + "@test.com";
        Role role = Role.USER;

        return userService.createNewUser(username, "helloworld", email, role);
    }

    /**
     * Luo uuden satunnaisen pääkäyttäjän
     *
     * @return Käyttäjä
     */
    public UserAccount generateAndSaveRandomAdmin() {
        String username = UUID.randomUUID().toString().substring(0, 8);
        String email = UUID.randomUUID().toString().substring(0, 8) + "@test.com";
        Role role = Role.ADMINISTRATOR;

        return userService.createNewUser(username, "helloworld", email, role);
    }
}
