package simpleclient.util;

public class SharedVaribles {
    public static boolean hud_enabled = true;
    public static BUILD_STATUS buildStatus = BUILD_STATUS.DEV;

    public enum BUILD_STATUS {
        DEV("Dev"),
        EXPERIMENTAL("Experimental"),
        ALPHA("Alpha"),
        Beta("Beta"),
        RELEASE("Release");

        public java.lang.String name;

        private BUILD_STATUS(String name) {
            this.name = name;
        }
    }
}
