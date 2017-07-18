package bean;

/**
 * Created by david on 17-7-12.
 */
public class Urls {
    private String url;

    public Urls( String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Urls) {
            Urls url1 = (Urls) o;
            return this.url.equals(url1.getUrl());
        }
        return super.equals(o);
    }

    @Override
    public String toString(){
        return url;
    }
}
