package psd.parser;

import java.io.*;

import psd.parser.header.HeaderSectionParser;
import psd.parser.imagedata.ImageDataSectionParser;
import psd.parser.imageresource.ImageResourceSectionParser;
import psd.parser.layer.LayersSectionParser;

public class PsdFileParser {
    private HeaderSectionParser headerParser;
    private ColorModeSectionParser colorModeSectionParser;
    private ImageResourceSectionParser imageResourceSectionParser;
    private LayersSectionParser layersSectionParser;
    private ImageDataSectionParser imageDataSectionParser;

    public PsdFileParser() {
        headerParser = new HeaderSectionParser();
        colorModeSectionParser = new ColorModeSectionParser();
        imageResourceSectionParser = new ImageResourceSectionParser();
        layersSectionParser = new LayersSectionParser();
        imageDataSectionParser = new ImageDataSectionParser(headerParser.getHeader());
    }

    public HeaderSectionParser getHeaderSectionParser() {
        return headerParser;
    }

    public ImageResourceSectionParser getImageResourceSectionParser() {
        return imageResourceSectionParser;
    }

    public LayersSectionParser getLayersSectionParser() {
        return layersSectionParser;
    }
    
    public ImageDataSectionParser getImageDataSectionParser() {
        return imageDataSectionParser;
    }

    public void parse(InputStream inputStream) throws IOException {
        PsdInputStream stream = new PsdInputStream(inputStream);
        headerParser.parse(stream);
        colorModeSectionParser.parse(stream);
        imageResourceSectionParser.parse(stream);
        layersSectionParser.parse(stream);
        imageDataSectionParser.parse(stream);
    }
}
