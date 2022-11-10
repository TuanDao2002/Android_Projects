package rmit.ad.itbooks;

public class Book {
    private String isbn13;
    private String title;
    private String subtitle;
    private String imageURL;
    private String price;

    public Book(String isbn13, String title, String subtitle, String imageURL, String price) {
        this.isbn13 = isbn13;
        this.title = title;
        this.subtitle = subtitle;
        this.imageURL = imageURL;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getPrice() {
        return price;
    }
}
