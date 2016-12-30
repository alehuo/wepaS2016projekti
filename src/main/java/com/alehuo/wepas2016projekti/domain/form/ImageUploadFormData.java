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
package com.alehuo.wepas2016projekti.domain.form;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

/**
 * Kuvan lähetyksessä käytetty Form -luokka.
 * Tämän avulla validoidaan kuvan lähetys.
 * 
 * @author alehuo
 */
public class ImageUploadFormData {

    /**
     * Kuvan kuvaus
     */
    @Length(max = 24)
    private String description;

    /**
     * Tiedosto
     */
    @NotNull
    private MultipartFile file;

    /**
     * Palauttaa kuvauksen
     * @return Kuvaus
     */
    public String getDescription() {
        return description;
    }

    /**
     * Asettaa kuvauksen
     * @param description Kuvaus
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Palauttaa tiedoston
     * @return Tiedosto
     */
    public MultipartFile getFile() {
        return file;
    }

    /**
     * Asettaa tiedoston
     * @param file Tiedosto
     */
    public void setFile(MultipartFile file) {
        this.file = file;
    }

    
}
