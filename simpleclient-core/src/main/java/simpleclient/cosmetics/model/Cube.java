package simpleclient.cosmetics.model;

import java.util.Vector;

public class Cube {
    private final String name;
    private final String parent;
    private final Vector<Float> position;
    private final Vector<Float> size;
    private final Vector<Float> pivotPoint;
    private final Vector<Float> rotation;
    private final TextureInfo textureInfo;
    private final String

    public Cube(String name, String parent, Vector<Float> position, Vector<Float> size, Vector<Float> pivotPoint, Vector<Float> rotation, TextureInfo textureInfo) {
        this.name = name;
        this.parent = parent;
        this.position = position;
        this.size = size;
        this.pivotPoint = pivotPoint;
        this.rotation = rotation;
        this.textureInfo = textureInfo;
    }

    public String getName() {
        return name;
    }

    public String getParent() {
        return parent;
    }

    public Vector<Float> getPosition() {
        return position;
    }

    public Vector<Float> getSize() {
        return size;
    }

    public Vector<Float> getPivotPoint() {
        return pivotPoint;
    }

    public Vector<Float> getRotation() {
        return rotation;
    }

    public TextureInfo getTextureInfo() {
        return textureInfo;
    }
}