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

import com.alehuo.wepas2016projekti.domain.Comment;
import com.alehuo.wepas2016projekti.domain.UserAccount;
import java.util.Date;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author alehuo
 */
public class CommentDomainTest {

    @Test
    public void testaaAsetaKaikki() {
        Comment c = new Comment();
        c.setBody("Hello world");
        c.setCreationDate(new Date(2016, 12, 30));
        UserAccount u = new UserAccount();
        u.setUsername("abc");
        c.setUser(u);

        assertEquals("Sisältöä ei aseteta oikein", "Hello world", c.getBody());
        assertEquals("Päiväystä ei aseteta oikein", new Date(2016, 12, 30), c.getCreationDate());
        assertEquals("Käyttäjää ei aseteta oikein", u, c.getUser());
    }
}
