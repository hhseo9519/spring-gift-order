package gift.dto;

public class TextLink {
    private String web_url;
    private String mobile_web_url;

    public TextLink(String url) {
        this.web_url = url;
        this.mobile_web_url = url;
    }
}