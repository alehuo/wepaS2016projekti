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
import com.alehuo.wepas2016projekti.domain.Image;
import com.alehuo.wepas2016projekti.domain.UserAccount;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author alehuo
 */
public class ImageTest {

    @Test
    public void testaaAsetaKaikki() {
        Comment c = new Comment();
        c.setBody("Hello world");
        c.setCreationDate(new Date(2016, 12, 30));
        UserAccount u = new UserAccount();
        u.setUsername("abc");
        c.setUser(u);

        Image i = new Image();
        i.setDescription("HelloWorld");
        byte[] imageData = new byte[1];
        String uuid = UUID.randomUUID().toString().substring(0, 2);
        imageData = uuid.getBytes();
        i.setImageData(imageData);
        i.setImageContentType("image/42");
        i.setImageOwner(u);
        i.addComment(c);

        List<Comment> comments = new ArrayList();
        comments.add(c);

        assertTrue("Kuvan dataa ei aseteta oikein", Arrays.equals(imageData, i.getImageData()));
        assertEquals("Tyyppiä ei aseteta oikein", "image/42", i.getContentType());
        assertEquals("Kuvausta ei aseteta oikein", "HelloWorld", i.getDescription());
        assertEquals("Käyttäjää ei aseteta oikein", u, i.getImageOwner());
        assertEquals("Kommenttia ei aseteta oikein", c, i.getComments().get(0));

        i.setComments(comments);

        assertEquals("Kommentteja ei aseteta oikein", comments, i.getComments());

        String uuid2 = "42424242-42424242";

        i.setUuid(uuid2);

        assertEquals("UUID:tä ei aseteta oikein", uuid2, i.getUuid());

    }

}
