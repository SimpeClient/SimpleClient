package simpleclient.text;

public class TranslatableText extends Text {
    private String key;
    private Object[] args;

    public TranslatableText(String key, Object... args) {
        this.key = key;
        this.args = args;
    }

    public String getKey() {
        return key;
    }

    public TranslatableText setKey(String key) {
        this.key = key;
        return this;
    }

    public Object[] getArgs() {
        return args;
    }

    public TranslatableText setArgs(Object... args) {
        this.args = args;
        return this;
    }
}