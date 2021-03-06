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

import com.alehuo.wepas2016projekti.domain.Image;
import com.alehuo.wepas2016projekti.domain.UserAccount;
import com.alehuo.wepas2016projekti.repository.ImageRepository;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Kuvapalvelu
 * @author alehuo
 */
@Service("imageService")
public class ImageService {

    /**
     * Kuvarepositorio
     */
    @Autowired
    private ImageRepository imageRepo;

    /**
     * Lisää kuva
     * @param u Käyttäjätili
     * @param imageData Kuvan data
     * @param imageType Kuvan tiedostomuoto
     * @param description Kuvaus
     * @return Juuri lisätty kuva
     */
    @Transactional
    public Image addImage(UserAccount u, byte[] imageData, String imageType, String description) {
        Image i = new Image();
        i.setImageData(imageData);
        i.setDescription(description);
        i.setImageOwner(u);
        i.setImageContentType(imageType);
        return saveImage(i);
    }

    /**
     * Palauttaa kuvan ID:n perusteella
     * @param imageId Kuvan ID
     * @return Kuva
     */
    @Transactional
    public Image findOneImageById(Long imageId) {
        return imageRepo.findOne(imageId);
    }

    /**
     * Palauttaa kuvan UUID:n perusteella
     * @param uuid UUID
     * @return Kuva
     */
    @Transactional
    public Image findOneImageByUuid(String uuid) {
        return imageRepo.findOneByUuid(uuid);
    }

    /**
     * Palauttaa kaikki kuvat jotka käyttäjä on lähettänyt palveluun
     * @param u Käyttäjätunnus
     * @return Kuvat
     */
    @Transactional
    public List<Image> findAllByUserAccount(UserAccount u) {
        return imageRepo.findAllByVisibleTrueAndImageOwnerOrderByIdDesc(u);
    }

    /**
     * Palauttaa kaikki palveluun lisätyt kuvat
     * @return Kaikki kuvat
     */
    @Transactional
    public List<Image> findAllImages() {
        return imageRepo.findAll();
    }

    /**
     * Tallentaa kuvan
     * @param i Kuva
     * @return Tallennettu kuva
     */
    public Image saveImage(Image i) {
        imageRepo.save(i);
        return i;
    }

    /**
     * Poistaa kaikki kuvat palvelusta
     */
    public void deleteAllImages() {
        imageRepo.deleteAll();
    }
}
