/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.commons.imaging.formats.gif;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Collection;
import java.util.List;

import org.apache.commons.imaging.ImageInfo;
import org.apache.commons.imaging.Imaging;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class GifReadTest extends GifBaseTest {

    private final File imageFile;

    @Parameterized.Parameters
    public static Collection<File> data() throws Exception {
        return getGifImages();
    }

    public GifReadTest(final File imageFile) {
        this.imageFile = imageFile;
    }

    @Ignore(value = "RoundtripTest has to be fixed befor implementation can throw UnsupportedOperationException")
    @Test(expected = UnsupportedOperationException.class)
    public void testMetadata() throws Exception {
        Imaging.getMetadata(imageFile);
    }

    @Test
    public void testImageInfo() throws Exception {
        final ImageInfo imageInfo = Imaging.getImageInfo(imageFile);
        assertNotNull(imageInfo);
        // TODO assert more
    }

    @Test
    public void testImageDimensions() throws Exception {
        final ImageInfo imageInfo = Imaging.getImageInfo(imageFile);
        final GifImageMetadata metadata = (GifImageMetadata) Imaging.getMetadata(imageFile);
        final List<BufferedImage> images = Imaging.getAllBufferedImages(imageFile);

        int width = 0;
        int height = 0;
        for(int i = 0; i < images.size(); i++) {
            final BufferedImage image = images.get(i);
            final GifImageMetadataItem metadataItem = metadata.getItems().get(i);
            final int xOffset = metadataItem.getLeftPosition();
            final int yOffset = metadataItem.getTopPosition();
            width = Math.max(width, image.getWidth() + xOffset);
            height = Math.max(height, image.getHeight() + yOffset);
        }

        assertEquals(width, metadata.getWidth());
        assertEquals(height, metadata.getHeight());
        assertEquals(width, imageInfo.getWidth());
        assertEquals(height, imageInfo.getHeight());
    }

    @Test
    public void testBufferedImage() throws Exception {
        final BufferedImage image = Imaging.getBufferedImage(imageFile);
        assertNotNull(image);
        // TODO assert more
    }

    @Test
    public void testBufferedImages() throws Exception {
        final List<BufferedImage> images = Imaging.getAllBufferedImages(imageFile);
        assertTrue(images.size() > 0);
        // TODO assert more
    }
}
