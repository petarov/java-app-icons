package net.vexelon.appicons.wireframe.entities;

public class IconFile {

    private String path;
    private String extension;
    private int width;
    private int height;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
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

    @Override public String toString() {
        return "IconFile{" +
                "path='" + path + '\'' +
                ", extension='" + extension + '\'' +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
