package com.example.introduction.onlinebookapp2;

public class BookModel {

    // details for our book
    private String title;
    private String subtitle;
    private String authors;
    private String publisher;
    private String publishedDate;
    private String description;
    private int pageCount;
    private  String language;
    private String thumbnail;
    private String previewLink;
    private String buyLink;



    //get methods
    public String getTitle() { return title; }

    public String getSubtitle() { return subtitle; }

    public String getAuthors() { return authors; }

    public String getPublisher() { return publisher; }

    public String getPublishedDate() { return publishedDate; }

    public String getDescription() { return description; }

    public int getPageCount() { return pageCount; }

    public String getThumbnail() { return thumbnail; }

    public String getLanguage() { return language; }

    public String getPreviewLink() { return previewLink; }

    public String getBuyLink() { return buyLink; }

    //set methods

    public void setTitle(String title) { this.title = title; }

    public void setSubtitle(String subtitle) { this.subtitle = subtitle; }

    public void setAuthors(String authors) { this.authors = authors; }

    public void setPublisher(String publisher) { this.publisher = publisher; }

    public void setPublishedDate(String publishedDate) { this.publishedDate = publishedDate; }

    public void setDescription(String description) { this.description = description; }

    public void setPageCount(int pageCount) { this.pageCount = pageCount; }

    public void setThumbnail(String thumbnail) { this.thumbnail = thumbnail; }

    public void setLanguage(String language) { this.language = language; }

    public void setPreviewLink(String previewLink) { this.previewLink = previewLink; }

    public void setBuyLink(String buyLink) { this.buyLink = buyLink; }



    //constructor for 11 parameters
    public BookModel(String title, String subtitle,String authors, String publisher, String publishedDate,
                     String description, int pageCount, String thumbnail,String language, String previewLink, String buyLink)
    {
        this.title = title;
        this.subtitle = subtitle;
        this.authors = authors;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.description = description;
        this.pageCount = pageCount;
        this.thumbnail = thumbnail;
        this.language=language;
        this.previewLink = previewLink;
        this.buyLink = buyLink;
    }

    //constructor for 4 parameters
    public BookModel(String title, String authors, String thumbnail,String previewLink) {
        this.title = title;
        this.authors = authors;
        this.thumbnail = thumbnail;
        this.previewLink=previewLink;
    }
}
