package simpleclient.cosmetics.option;

import java.awt.Color;

public class CosmeticColor {
    private int rgb = 0xFFFFFF;
    private int alpha = 255;
    private boolean rainbow = false;
    private float rainbowSpeed = 10.0F;

    public CosmeticColor(int argb) {
        this.rgb = (argb & 0x00FFFFFF) << 8;
        this.alpha = (argb & 0xFF000000) >> 24;
    }

    public CosmeticColor(int rgb, int alpha) {
        this.rgb = rgb;
        this.alpha = alpha;
    }

    public CosmeticColor(int r, int g, int b) {
        this.rgb = r << 16 + g << 8 + b;
        this.alpha = 255;
    }

    public CosmeticColor(int r, int g, int b, int alpha) {
        this.rgb = r << 16 + g << 8 + b;
        this.alpha = alpha;
    }

    public CosmeticColor(float rainbowSpeed) {
        this.rainbow = true;
        this.rainbowSpeed = rainbowSpeed;
    }

    public int getRGB() {
        if (rainbow) {
            float rainbowDuration = (1000 * rainbowSpeed);
            return Color.HSBtoRGB((System.currentTimeMillis() % rainbowDuration) / rainbowDuration, 1.0F, 1.0F);
        } else return rgb;
    }

    public int getRed() {
        return (rgb & 0xFF0000) >> 16;
    }

    public int getGreen() {
        return (rgb & 0xFF0000) >> 16;
    }

    public int getBlue() {
        return (rgb & 0xFF0000) >> 16;
    }

    public boolean isRainbow() {
        return rainbow;
    }

    public float getRainbowSpeed() {
        return rainbowSpeed;
    }
}