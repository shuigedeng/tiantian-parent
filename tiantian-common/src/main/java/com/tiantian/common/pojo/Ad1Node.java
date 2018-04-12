package com.tiantian.common.pojo;

/**
 * @author 戴礼明
 * @create 2018/4/11 15:33
 */
public class Ad1Node {
    private String srcB;
    private Integer height;
    private String alt;
    private Integer width;
    private String src;
    private Integer widthB;
    private String href;
    private Integer heightB;

    public Ad1Node(){}

    public Ad1Node(String srcB, Integer height, String alt, Integer width, String src, Integer widthB, String href, Integer heightB) {
        this.srcB = srcB;
        this.height = height;
        this.alt = alt;
        this.width = width;
        this.src = src;
        this.widthB = widthB;
        this.href = href;
        this.heightB = heightB;
    }

    public String getSrcB() {
        return srcB;
    }

    public void setSrcB(String srcB) {
        this.srcB = srcB;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public Integer getWidthB() {
        return widthB;
    }

    public void setWidthB(Integer widthB) {
        this.widthB = widthB;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public Integer getHeightB() {
        return heightB;
    }

    public void setHeightB(Integer heightB) {
        this.heightB = heightB;
    }
}
