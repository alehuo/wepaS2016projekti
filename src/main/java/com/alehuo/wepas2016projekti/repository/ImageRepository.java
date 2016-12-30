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

import com.alehuo.wepas2016projekti.domain.Image;
import com.alehuo.wepas2016projekti.domain.UserAccount;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Kuvarepo
 *
 * @author alehuo
 */
@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    /**
     * Palauttaa kymmenen uusinta kuvaa
     * @return Kymmenen uusinta kuvaa
     */
    List<Image> findTop10ByOrderByIdDesc();
    
    /**
     * Palauttaa kymmenen uusinta kuvaa, jotka on asetettu näkyviksi
     * @return Kymmenen uusinta kuvaa, jotka on asetettu näkyviksi
     */
    List<Image> findTop10ByVisibleTrueOrderByIdDesc();

    /**
     * Palauttaa yhden kuvan UUID:n perusteella
     * @param uuid UUID
     * @return Kuva
     */
    Image findOneByUuid(String uuid);

    /**
     * Palauttaa kaikki näkyvissä olevat kuvat lähetysjärjestyksessä, jotka käyttäjä on lähettänyt
     * @param u Käyttäjä
     * @return Kuvat
     */
    List<Image> findAllByVisibleTrueAndImageOwnerOrderByIdDesc(UserAccount u);
}
