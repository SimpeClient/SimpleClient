package simpleclient.math;

public class Vector {
    private float x;
    private float y;
    private float z;

    public Vector(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector(double x, double y, double z) {
        this.x = (float) x;
        this.y = (float) y;
        this.z = (float) z;
    }

    public Vector(int xyz) {
        this(xyz, xyz, xyz);
    }

    public Vector(float xyz) {
        this(xyz, xyz, xyz);
    }

    public Vector(double xyz) {
        this(xyz, xyz, xyz);
    }

    public Vector() {
        this(0);
    }

    public Vector add(float x, float y, float z) {
        return new Vector(this.x + x, this.y + y, this.z + z);
    }

    public Vector subtract(float x, float y, float z) {
        return new Vector(this.x - x, this.y - y, this.z - z);
    }

    public Vector multiply(float x, float y, float z) {
        return new Vector(this.x * x, this.y * y, this.z * z);
    }

    public Vector divide(float x, float y, float z) {
        return new Vector(this.x / x, this.y / y, this.z / z);
    }

    public float getX() {
        return x;
    }

    public Vector setX(float x) {
        this.x = x;
        return this;
    }

    public float getY() {
        return y;
    }

    public Vector setY(float y) {
        this.y = y;
        return this;
    }

    public float getZ() {
        return z;
    }

    public Vector setZ(float z) {
        this.z = z;
        return this;
    }
}