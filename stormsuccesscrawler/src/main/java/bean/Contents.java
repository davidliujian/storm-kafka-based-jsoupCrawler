package bean;

import java.sql.Timestamp;

/**
 * Created by david on 17-7-12.
 */
public class Contents {
    private String content;
    private String title;
    private String source;
    private String pub_time;
    private String url;
    private String identifier;

    public Contents(String content, String title, String source, String pub_time, String url, String identifier) {
        this.content = content;
        this.title = title;
        this.source = source;
        this.pub_time = pub_time;
        this.url = url;
        this.identifier = identifier;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPub_time() {
        return pub_time;
    }

    public void setPub_time(String pub_time) {
        this.pub_time = pub_time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
