package rmit.ad.itbooks;

public class Book {
    private String isbn13;
    private String title;
    private String subtitle;

    public Book(String isbn13, String title, String subtitle) {
        this.isbn13 = isbn13;
        this.title = title;
        this.subtitle = subtitle;
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
}
