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
package com.alehuo.wepas2016projekti.repository;

import com.alehuo.wepas2016projekti.domain.UserAccount;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Käyttäjärepo
 *
 * @author alehuo
 */
@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

    /**
     * Palauttaa käyttäjän käyttäjänimen perusteella
     * @param username Käyttäjänimi
     * @return Käyttäjä
     */
    UserAccount findByUsername(String username);
    
    /**
     * Palauttaa käyttäjän käyttäjänimen perusteella
     * @param username Käyttäjänimi (Non case sensitive)
     * @return Käyttäjä
     */
    UserAccount findByUsernameIgnoreCase(String username);

    /**
     * Palauttaa kaikki käyttäjät joiden käyttäjätunnuksessa esiintyy merkkijono
     * @param username Merkkijono
     * @return Käyttäjät
     */
    List<UserAccount> findByUsernameIgnoreCaseContaining(String username);

}
