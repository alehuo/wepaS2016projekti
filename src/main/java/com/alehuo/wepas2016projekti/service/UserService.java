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
package com.alehuo.wepas2016projekti.service;

import com.alehuo.wepas2016projekti.domain.Role;
import com.alehuo.wepas2016projekti.domain.UserAccount;
import com.alehuo.wepas2016projekti.repository.UserAccountRepository;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Palvelu, joka käsittelee käyttäjätunnuksia
 *
 * @author alehuo
 */
@Service("userService")
public class UserService {

    @Autowired
    private UserAccountRepository userRepo;

    /**
     * Uuden käyttäjän luominen
     *
     * @param username Käyttäjätunnus
     * @param password Salasana
     * @param email Sähköpostiosoite
     * @param role
     * @return Luotu käyttäjätunnus
     */
    @Transactional
    public UserAccount createNewUser(String username, String password, String email, Role role) {

        UserAccount u = new UserAccount();
        u.setUsername(username);
        u.setPassword(new BCryptPasswordEncoder().encode(password));
        u.setEmail(email);
        u.setRole(role);

        return saveUser(u);
    }

    /**
     * Palauttaa kaikki käyttäjät
     * @return Kaikki käyttäjät
     */
    public List<UserAccount> getAllUsers() {
        return userRepo.findAll();
    }

    /**
     * Palauttaa käyttäjän sen ID:n perusteella
     * @param id Käyttäjän ID
     * @return Käyttäjä
     */
    public UserAccount getUserById(Long id) {
        return userRepo.findOne(id);
    }

    /**
     * Palauttaa käyttäjän sen käyttäjänimen perusteella
     * @param username Käyttäjänimi
     * @return Käyttäjä
     */
    public UserAccount getUserByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    /**
     * Palauttaa käyttäjän sen käyttäjänimen perusteella
     * @param username Käyttäjänimi (non case sensitive)
     * @return Käyttäjä
     */
    public UserAccount getUserByUsernameIgnoreCase(String username) {
        return userRepo.findByUsernameIgnoreCase(username);
    }

    /**
     * Tallentaa käyttäjän
     * @param u Käyttäjä
     * @return Juuri tallennettu käyttäjä
     */
    public UserAccount saveUser(UserAccount u) {
        return userRepo.save(u);
    }

    /**
     * Poistaa kaikki käyttäjät
     */
    public void deleteAllUsers() {
        userRepo.deleteAll();
    }

    /**
     * Palauttaa käyttäjät jotka vastaavat hakusanaa
     * @param username Hakusana
     * @return Käyttäjät
     */
    public List<UserAccount> getUsersByUsernameLike(String username) {
        return userRepo.findByUsernameIgnoreCaseContaining(username);
    }

}
