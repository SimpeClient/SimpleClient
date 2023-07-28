package simpleclient.cosmetics.model;

public class TextureInfo {
    private final String location;
    private final float x;
    private final float y;
    private final float width;
    private final float height;

    public TextureInfo(String location, float x, float y, float width, float height) {
        this.location = location;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public String getLocation() {
        return location;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}