package simpleclient.text;

public class TextColor {
    public static TextColor BLACK = deserialize("#FF000000");
    public static TextColor DARK_BLUE = deserialize("#FF000055");
    public static TextColor DARK_GREEN = deserialize("#FF005500");
    public static TextColor DARK_AQUA = deserialize("#FF005555");
    public static TextColor DARK_RED = deserialize("#FF550000");
    public static TextColor PURPLE = deserialize("#FF550055");
    public static TextColor GOLD = deserialize("#FFAA00FF");
    public static TextColor GRAY = deserialize("#FFAAAAAA");
    public static TextColor DARK_GRAY = deserialize("#FF555555");
    public static TextColor BLUE = deserialize("#FF5555FF");
    public static TextColor GREEN = deserialize("#FF55FF55");
    public static TextColor AQUA = deserialize("#FF55FFFF");
    public static TextColor RED = deserialize("#FFFF5555");
    public static TextColor PINK = deserialize("#FFFF55FF");
    public static TextColor YELLOW = deserialize("#FFFFFF55");
    public static TextColor WHITE = deserialize("#FFFFFFFF");
    private int r;
    private int g;
    private int b;
    private int a;

    public TextColor(int r, int g, int b) {
        this(255, r, g, b);
    }

    public TextColor(int a, int r, int g, int b) {
        this.a = a;
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public TextColor(int argb) {
        setARGB(argb);
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public int getARGB() {
        return (a << 24) | ((r & 255) << 16) | ((g & 255) << 8) | (b & 255);
    }

    public void setARGB(int argb) {
        this.a = (argb >> 24) & 0xFF;
        this.r = (argb >> 16) & 0xFF;
        this.g = (argb >> 8) & 0xFF;
        this.b = argb & 0xFF;
    }

    public String serialize() {
        return "#" + Integer.toHexString(getARGB());
    }

    public static TextColor deserialize(String serialized) {
        if (serialized.length() < 9) return TextColor.WHITE;
        return new TextColor(
                Integer.valueOf(serialized.substring(1, 3), 16),
                Integer.valueOf(serialized.substring(3, 5), 16),
                Integer.valueOf(serialized.substring(5, 7), 16),
                Integer.valueOf(serialized.substring(7, 9), 16)
        );
    }
}