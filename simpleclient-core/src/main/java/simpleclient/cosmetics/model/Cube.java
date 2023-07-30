package simpleclient.cosmetics.model;

import simpleclient.math.Vector;

public class Cube {
    private final String name;
    private final String parent;
    private Vector position;
    private Vector size;
    private Vector pivotPoint;
    private Vector rotation;
    private final TextureInfo textureInfo;

    public Cube(String name, String parent, Vector position, Vector size, Vector pivotPoint, Vector rotation, TextureInfo textureInfo) {
        this.name = name;
        this.parent = parent;
        this.position = position;
        this.size = size;
        this.pivotPoint = pivotPoint;
        this.rotation = rotation;
        this.textureInfo = textureInfo;
    }

    public Cube(String name, String parent) {
        this.name = name;
        this.parent = parent;
        this.position = new Vector();
        this.size = new Vector();
        this.pivotPoint = new Vector();
        this.rotation = new Vector();
        this.textureInfo = null;
    }

    public Cube position(double x, double y, double z) {
        position = new Vector(x, y, z);
        return this;
    }

    public Cube size(double width, double height, double depth) {
        size = new Vector(width, height, depth);
        return this;
    }

    public Cube pivotPoint(double x, double y, double z) {
        pivotPoint = new Vector(x, y, z);
        return this;
    }

    public Cube rotation(double x, double y, double z) {
        rotation = new Vector(x, y, z);
        return this;
    }

    public String getName() {
        return name;
    }

    public String getParent() {
        return parent;
    }

    public Vector getPosition() {
        return position;
    }

    public Vector getSize() {
        return size;
    }

    public Vector getPivotPoint() {
        return pivotPoint;
    }

    public Vector getRotation() {
        return rotation;
    }

    public TextureInfo getTextureInfo() {
        return textureInfo;
    }
}