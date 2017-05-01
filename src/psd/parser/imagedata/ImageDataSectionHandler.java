package psd.parser.imagedata;

public interface ImageDataSectionHandler {
    public void channelLoaded(int channelId, byte[] channelData);
}
