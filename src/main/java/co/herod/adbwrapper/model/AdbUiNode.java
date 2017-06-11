package co.herod.adbwrapper.model;

import co.herod.adbwrapper.util.UiHierarchyHelper;

public class AdbUiNode {

    private final String nodeString;
    private final long creationTimeMillis;

    private Integer[] bounds;

    public AdbUiNode(final String nodeString) {
        this.nodeString = nodeString;
        this.creationTimeMillis = System.currentTimeMillis();
    }

    public Integer[] getBounds() {
        if (bounds == null || bounds.length != 4) {
            bounds = UiHierarchyHelper.extractBoundsInts(nodeString).blockingSingle();
        }
        return bounds;
    }

    public int getWidth() {
        return UiHierarchyHelper.getWidth(getBounds());
    }

    public int getHeight() {
        return UiHierarchyHelper.getWidth(getBounds());
    }

    public long getCreationTimeMillis() {
        return creationTimeMillis;
    }

    @Override
    public String toString() {
        return "AdbUiNode{" +
                "nodeString='" + nodeString + '\'' +
                '}';
    }

}
