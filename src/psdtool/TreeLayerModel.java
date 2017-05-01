package psdtool;

import psd.model.AbstractLayer;
import psd.model.LayersContainer;
import psd.model.Psd;
import psd.parser.layer.LayerType;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import java.util.LinkedList;

public class TreeLayerModel<T extends AbstractLayer<T>> implements TreeModel {

    private Psd psd;
    private LinkedList<TreeModelListener> listeners = new LinkedList<TreeModelListener>();

    public TreeLayerModel() {
    }

    public void setPsd(Psd psd) {
        this.psd = psd;
        if (!listeners.isEmpty()) {
            TreeModelEvent event = new TreeModelEvent(this, new TreePath(psd));
            for (TreeModelListener l : listeners) {
                l.treeStructureChanged(event);
            }
        }
    }

    @Override
    public Object getRoot() {
        return psd;
    }

    @Override
    public Object getChild(Object parent, int index) {
        return ((LayersContainer<?>) parent).getLayer(index);
    }

    @Override
    public int getChildCount(Object parent) {
        return ((LayersContainer<?>) parent).getLayersCount();
    }

    @Override
    public boolean isLeaf(Object node) {
        return node instanceof AbstractLayer && ((AbstractLayer<?>) node).getType() == LayerType.NORMAL;
    }

    @Override
    public void valueForPathChanged(TreePath path, Object newValue) {
    }

	@Override
    @SuppressWarnings("unchecked")
    public int getIndexOfChild(Object parent, Object child) {
        return ((LayersContainer<T>) parent).indexOfLayer((T)child);
    }

	@Override
    public void addTreeModelListener(TreeModelListener l) {
        listeners.add(l);
    }

    @Override
    public void removeTreeModelListener(TreeModelListener l) {
        listeners.remove(l);
    }
}
