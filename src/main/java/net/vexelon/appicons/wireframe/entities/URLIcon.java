package net.vexelon.appicons.wireframe.entities;

public class URLIcon {

    private String url;
    private String type;
    private int width;
    private int height;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "URLIcon{" +
                "url='" + url + '\'' +
                ", type='" + type + '\'' +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
