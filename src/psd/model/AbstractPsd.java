/*
 * This file is part of java-psd-library.
 * 
 * This library is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/>.
 */

package psd.model;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

import psd.parser.*;
import psd.parser.header.*;
import psd.parser.imagedata.ImageDataSectionHandler;
import psd.parser.layer.*;
import psd.util.BufferedImageBuilder;

public abstract class AbstractPsd<T extends AbstractLayer<T>> implements LayersContainer<T> {
    private Header header;
    private List<T> layers = new ArrayList<T>();
    private BufferedImage image;
    private String name;

    public AbstractPsd(File psdFile) throws IOException {
        name = psdFile.getName();
        final byte[][] channels = new byte[3][];

        PsdFileParser parser = new PsdFileParser();
        parser.getHeaderSectionParser().setHandler(new HeaderSectionHandler() {
            @Override
            public void headerLoaded(Header header) {
                AbstractPsd.this.header = header;
            }
        });

        final List<T> fullLayersList = new ArrayList<T>();
        parser.getLayersSectionParser().setHandler(new LayersSectionHandler() {
            @Override
            public void createLayer(LayerParser parser) {
                fullLayersList.add(AbstractPsd.this.createLayer(parser));
            }
        });

        parser.getImageDataSectionParser().setHandler(new ImageDataSectionHandler() {
            @Override
            public void channelLoaded(int channelId, byte[] channelData) {
                if (channelId >= 0 && channelId < 3) {
                    channels[channelId] = channelData;
                }
            }
        });
        configureParser(parser);
        
        BufferedInputStream stream = new BufferedInputStream(new FileInputStream(psdFile));
        parser.parse(stream);
        stream.close();

        layers = makeLayersHierarchy(fullLayersList);

        if (parser.getHeaderSectionParser().getHeader().getColorMode() == ColorMode.GRAYSCALE) {
            channels[1] = channels[2] = channels[0];
        }
        image = new BufferedImageBuilder(channels, header.getWidth(), header.getHeight()).makeImage();
    }
    
    protected abstract T createLayer(LayerParser parser);
    
    protected void configureParser(PsdFileParser parser) {
    }

    private List<T> makeLayersHierarchy(List<T> layers) {
        LinkedList<LinkedList<T>> layersStack = new LinkedList<LinkedList<T>>();
        ArrayList<T> rootLayers = new ArrayList<T>();
        for (T layer : layers) {
            switch (layer.getType()) {
            case HIDDEN: {
                layersStack.addFirst(new LinkedList<T>());
                break;
            }
            case FOLDER: {
                assert !layersStack.isEmpty();
                LinkedList<T> folderLayers = layersStack.removeFirst();
                for (T l : folderLayers) {
                    layer.addLayer(l);
                }
            }
                // break isn't needed
            case NORMAL: {
                if (layersStack.isEmpty()) {
                    rootLayers.add(layer);
                } else {
                    layersStack.getFirst().add(layer);
                }
                break;
            }
            default:
                assert false;
            }
        }
        return rootLayers;
    }

    public int getWidth() {
        return header.getWidth();
    }

    public int getHeight() {
        return header.getHeight();
    }
    
    public BufferedImage getImage() {
        return image;
    }

    @Override
    public T getLayer(int index) {
        return layers.get(index);
    }

    @Override
    public int indexOfLayer(T layer) {
        return layers.indexOf(layer);
    }

    @Override
    public int getLayersCount() {
        return layers.size();
    }

    @Override
    public String toString() {
        return name;
    }
}
