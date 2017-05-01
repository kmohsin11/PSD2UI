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

import psd.parser.BlendMode;
import psd.parser.layer.*;
import psd.parser.layer.additional.LayerSectionDividerHandler;
import psd.parser.layer.additional.LayerSectionDividerParser;
import psd.parser.layer.additional.LayerUnicodeNameHandler;
import psd.parser.layer.additional.LayerUnicodeNameParser;
import psd.parser.layer.additional.LayerTypeToolHandler;
import psd.parser.layer.additional.LayerTypeToolParser;
import psd.parser.object.PsdTextData;

import psd.util.BufferedImageBuilder;

import java.awt.image.*;
import java.util.*;
import psd.parser.layer.additional.LayerEffectsHandler;
import psd.parser.layer.additional.LayerEffectsParser;
import psd.parser.layer.additional.LayerIdHandler;
import psd.parser.layer.additional.LayerIdParser;
import psd.parser.layer.additional.LayerMetaDataHandler;
import psd.parser.layer.additional.LayerMetaDataParser;
import psd.parser.layer.additional.LayerSolidColorHandler;
import psd.parser.layer.additional.LayerSolidColorParser;
import psd.parser.layer.additional.LayerVogkHandler;
import psd.parser.layer.additional.LayerVogkParser;
import psd.parser.layer.additional.Matrix;
import psd.parser.object.PsdDescriptor;

public class AbstractLayer<T extends AbstractLayer<T>> implements LayersContainer<T> {
   
    private int top = 0;
    private int left = 0;
    private int bottom = 0;
    private int right = 0;

    private int alpha = 255;

    private boolean visible = true;

    private String name;

    private BufferedImage image;
    private LayerType type = LayerType.NORMAL;
    private int lyid;
     private PsdDescriptor descriptor1;
     private PsdDescriptor metadescriptor;
     private PsdDescriptor effectsdescriptor;

    private ArrayList<T> layers = new ArrayList<T>();
    
    List<Channel> channels;
    private PsdDescriptor vogkdescriptor;

    public AbstractLayer(LayerParser parser) {
        
        
        parser.setHandler(new LayerHandler() {
            @Override
            public void boundsLoaded(int left, int top, int right, int bottom) {
                AbstractLayer.this.left = left;
                AbstractLayer.this.top = top;
                AbstractLayer.this.right = right;
                AbstractLayer.this.bottom = bottom;
            }

            @Override
            public void blendModeLoaded(BlendMode blendMode) {
            }

            @Override
            public void blendingRangesLoaded(BlendingRanges ranges) {
            }

            @Override
            public void opacityLoaded(int opacity) {
                AbstractLayer.this.alpha = opacity;
            }

            @Override
            public void clippingLoaded(boolean clipping) {
            }

            @Override
            public void flagsLoaded(boolean transparencyProtected, boolean visible, boolean obsolete, boolean isPixelDataIrrelevantValueUseful, boolean pixelDataIrrelevant) {
                AbstractLayer.this.visible = visible;
            }

            @Override
            public void nameLoaded(String name) {
                AbstractLayer.this.name = name;
            }

            @Override
            public void channelsLoaded(List<Channel> channels) {
                BufferedImageBuilder imageBuilder = new BufferedImageBuilder(channels, getWidth(), getHeight());
                image = imageBuilder.makeImage();
                
                AbstractLayer.this.channels = channels;
            }

            @Override
            public void maskLoaded(Mask mask) {
            }

        });

        parser.putAdditionalInformationParser(LayerSectionDividerParser.TAG, new LayerSectionDividerParser(new LayerSectionDividerHandler() {
            @Override
            public void sectionDividerParsed(LayerType type) {
                AbstractLayer.this.type = type;
            }
        }));

        parser.putAdditionalInformationParser(LayerUnicodeNameParser.TAG, new LayerUnicodeNameParser(new LayerUnicodeNameHandler() {
            @Override
            public void layerUnicodeNameParsed(String unicodeName) {
                name = unicodeName;
            }
        }));
         parser.putAdditionalInformationParser(LayerIdParser.TAG, new LayerIdParser(new LayerIdHandler() {
            @Override
            public void layerIdParsed(int id) {
               // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            lyid=id;
            }
          
        }));
         
             parser.putAdditionalInformationParser(LayerTypeToolParser.TAG, new LayerTypeToolParser(new LayerTypeToolHandler() {
          
            @Override
            public void typeToolTransformParsed(Matrix transform) {
             //   throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void typeToolDescriptorParsed(int version, PsdDescriptor descriptor) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            descriptor1=descriptor;
            }
          
        }));
             
             parser.putAdditionalInformationParser(LayerSolidColorParser.TAG, new LayerSolidColorParser(new LayerSolidColorHandler() {
            @Override
            public void layerSolid(PsdDescriptor descriptor) {
                metadescriptor=descriptor;
            }
       
                 
        }));
             parser.putAdditionalInformationParser(LayerEffectsParser.TAG, new LayerEffectsParser(new LayerEffectsHandler() {
            @Override
            public void layerEffectsParsed(PsdDescriptor d) {
                   effectsdescriptor = d;
            }
        
       
                 
        }));
                    parser.putAdditionalInformationParser(LayerVogkParser.TAG, new LayerVogkParser(new LayerVogkHandler() {
          
            @Override
            public void layerVogkParsed(PsdDescriptor d) {
                vogkdescriptor =d;
            }
        
       
                 
        }));
         
         
        

    }
    
    public PsdDescriptor getmeta(){
        return metadescriptor;
    }
      public PsdDescriptor geteffects(){
        return effectsdescriptor;
    }
         public PsdDescriptor getvogk(){
        return vogkdescriptor;
    }
      

    public void addLayer(T layer) {
        layers.add(layer);
    }
    public int getId(){
        return lyid;
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
    
    public int getChannelsCount(){
        return channels.size();
    }

    public BufferedImage getImage() {
        return image;
    }

    public int getX() {
        return left;
    }

    public int getY() {
        return top;
    }

    public int getWidth() {
        return right - left;
    }

    public int getHeight() {
        return bottom - top;
    }

    public LayerType getType() {
        return type;
    }
    
    public String getName(){
        return name;
    }
    public PsdDescriptor getProperties(){
               return descriptor1;
      
    }

    public boolean isVisible() {
        return visible;
    }

    @Override
    public String toString() {
        return name;
    }

    public int getAlpha() {
        return alpha;
    }
}
