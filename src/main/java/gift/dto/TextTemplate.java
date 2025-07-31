package gift.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TextTemplate {
    private final String object_type = "text";
    private String text;
    private TextLink link;
    private String button_title;

    public TextTemplate(String text, TextLink link, String button_title) {
        this.text = text;
        this.link = link;
        this.button_title = button_title;
    }

    public String getObject_type() {
        return object_type;
    }

    public String getText() {
        return text;
    }

    public TextLink getLink() {
        return link;
    }

    public String getButton_title() {
        return button_title;
    }
}
