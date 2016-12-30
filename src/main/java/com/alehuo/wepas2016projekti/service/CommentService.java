/*
 * Copyright (C) 2016 Pivotal Software, Inc.
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
package com.alehuo.wepas2016projekti.service;

import com.alehuo.wepas2016projekti.domain.Comment;
import com.alehuo.wepas2016projekti.domain.UserAccount;
import com.alehuo.wepas2016projekti.repository.CommentRepository;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Kommenttipalvelu
 * @author Jusaa
 */
@Service
public class CommentService {

    /**
     * Kommenttirepo
     */
    @Autowired
    private CommentRepository commentRepository;

    /**
     * Käyttäjätietopalvelu
     */
    @Autowired
    private UserService userService;

    /**
     * Lisää kommentti
     * @param body Sisältö
     * @param u Käyttäjätunnus
     * @return Juuri lisätty kommentti
     */
    @Transactional
    public Comment addComment(String body, UserAccount u) {
        Comment c = new Comment();
        c.setBody(body);
        c.setUser(u);
        u.addComment(c);
        return commentRepository.save(c);
    }
}
