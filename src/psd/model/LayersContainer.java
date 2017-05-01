package psd.model;

public interface LayersContainer<T extends AbstractLayer<?>> {
    public T getLayer(int index);
    public int indexOfLayer(T layer);
    public int getLayersCount();
}
