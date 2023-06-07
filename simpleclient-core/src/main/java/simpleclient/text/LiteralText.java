package simpleclient.text;

public class LiteralText extends Text {
    private String content;

    public LiteralText(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public LiteralText setContent(String content) {
        this.content = content;
        return this;
    }
}