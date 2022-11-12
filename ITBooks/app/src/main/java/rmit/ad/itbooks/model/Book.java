package rmit.ad.itbooks.model;

public class Book {
    private String isbn13;
    private String title;
    private String subtitle;
    private String imageURL;
    private String bookURL;
    private String price;

    public Book(String isbn13, String title, String subtitle, String bookURL, String imageURL, String price) {
        this.isbn13 = isbn13;
        this.title = title;
        this.subtitle = subtitle;
        this.bookURL = bookURL;
        this.imageURL = imageURL;
        this.price = price;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getBookURL() {
        return bookURL;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getPrice() {
        return price;
    }
}
