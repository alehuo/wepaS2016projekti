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
package com.alehuo.wepas2016projekti.test;

import com.alehuo.wepas2016projekti.domain.form.ImageUploadFormData;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author alehuo
 */
public class FormDomainTest {

    /**
     *
     */
    @Test
    public void parametrienAsetusToimii() {
        ImageUploadFormData fd = new ImageUploadFormData();
        
        fd.setDescription("test");
        
        assertEquals("Kuvausta ei aseteta oikein", "test", fd.getDescription());
        
        MultipartFile mf = new MultipartFile() {
            @Override
            public String getName() {
                return "";
            }

            @Override
            public String getOriginalFilename() {
                return "";
            }

            @Override
            public String getContentType() {
                return "";
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public long getSize() {
                return 0;
            }

            @Override
            public byte[] getBytes() throws IOException {
                return new byte[8];
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return null;
            }

            @Override
            public void transferTo(File file) throws IOException, IllegalStateException {

            }
        };
        
        fd.setFile(mf);
        
        assertEquals("Tiedostoa ei aseteta oikein", mf, fd.getFile());
    }
}
