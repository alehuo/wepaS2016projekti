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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author alehuo
 */
public class UserAccountTest {

    @Test
    public void kayttajatunnuksenAsettaminenToimii() {
        UserAccount u = new UserAccount();
        String username = UUID.randomUUID().toString().substring(0, 8);
        u.setUsername(username);
        assertEquals(username, u.getUsername());
    }

    @Test
    public void sahkopostiosoitteenAsettaminenToimii() {
        UserAccount u = new UserAccount();
        String email = UUID.randomUUID().toString().substring(0, 8) + "@test.com";
        u.setEmail(email);
        assertEquals(email, u.getEmail());
    }

    @Test
    public void kayttajaTasonAsettaminenToimii() {
        UserAccount u = new UserAccount();
        u.setRole(Role.USER);
        assertEquals(Role.USER, u.getRole());
        u.setRole(Role.ADMINISTRATOR);
        assertEquals(Role.ADMINISTRATOR, u.getRole());
    }

    @Test
    public void salasananAsettaminenToimii() {
        UserAccount u = new UserAccount();
        String password = UUID.randomUUID().toString().substring(0, 8);
        u.setPassword(password);
        assertEquals(password, u.getPassword());
    }

    @Test
    public void hashCodeJaEqualsToimii() {
        UserAccount u = new UserAccount();
        u.setUsername("abc");
        u.setPassword("dev");
        u.setEmail("ghi@jkl.com");
        u.setRole(Role.USER);
        
        UserAccount u2 = new UserAccount();
        u2.setUsername("abc");
        u2.setPassword("dev");
        u2.setEmail("ghi@jkl.com");
        u2.setRole(Role.USER);
        
        assertEquals(u, u2);
        assertTrue(u.hashCode() == u2.hashCode());
    }
}
