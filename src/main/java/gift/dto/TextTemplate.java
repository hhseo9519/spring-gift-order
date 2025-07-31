package gift.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TextTemplate {

    @JsonProperty("object_type")
    private final String objectType = "text";

    private String text;
    private TextLink link;

    @JsonProperty("button_title")
    private String buttonTitle;

    public TextTemplate(String text, TextLink link, String buttonTitle) {
        this.text = text;
        this.link = link;
        this.buttonTitle = buttonTitle;
    }
}
