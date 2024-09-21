package allClasses.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.List;

@Entity
@Table (name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private long bookId;

    @NotBlank(message = "Обязательное поле")
    @Size(min = 2, max = 50, message = "Название должно быть от 2 до 50 символов")
    @Column(name = "title", length = 50, nullable = false)
    private String title;

    @NotBlank(message = "Обязательное поле")
    @Size(min = 2, max = 50, message = "Имя автора должно быть от 2 до 50 символов")
    @Column(name = "author", length = 50, nullable = false)
    private String author;

    @NotBlank(message = "Обязательное поле")
    @Size(max = 100, message = "Язык не должен превышать 100 символов")
    @Column(name = "language", length = 100, nullable = false)
    private String language;

    @NotNull(message = "Обязательное поле")
    @Digits(integer = 4, fraction = 0, message = "Введите корректный год издания")
    @Column(name = "release", length = 4, nullable = false)
    private int release;

    @Size(max = 100, message = "Поле жанры не должно превышать 100 символов")
    @Column(name = "genres", length = 100)
    private String genres;

    @Size(max = 1000, message = "Описание не должно превышать 1000 символов")
    @Column(name = "description", length = 1000)
    private String description;

    @Size(max = 20, message = "ISBN не должно превышать 20 символов")
    @Column(name = "isbn", length = 20)
    private String ISBN;

    @Min(value = 0, message = "Количество страниц не может быть меньше 0")
    @Column (name = "amount_pages")
    private int amountPages;

    @DecimalMin(value = "0.0", message = "Рейтинг не может быть меньше 0")
    @DecimalMax(value = "10.0", message = "Рейтинг не может быть больше 10")
    @Column(name = "rate", precision = 4)
    private double rate;

    @Column(name = "latest")
    private boolean latest;

    @Column (name = "image_path")
    private String imagePath;

    @NotNull(message = "Обязательное поле")
    @DecimalMin(value = "0.00", message = "Цена не может быть меньше 0")
    @Column(name = "price", nullable = false)
    private double price;

    @NotNull(message = "Обязательное поле")
    @Min(value = 0, message = "Количество не может быть меньше 0")
    @Column(name = "amount", nullable = false)
    private int amount;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<Cart> cart;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<OrderBook> orderBooks;

    public Book(){
        imagePath = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQtUGZqmALjP_ea_LlkL-Jj5fPeP9FUM0Ol4A&s";
    }

    public long getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getLanguage() {
        return language;
    }

    public int getRelease() {
        return release;
    }

    public String getGenres() {
        return genres;
    }

    public String getDescription() {
        return description;
    }

    public String getISBN() {
        return ISBN;
    }

    public int getAmountPages() {
        return amountPages;
    }

    public double getRate() {
        return rate;
    }

    public boolean isLatest() {
        return latest;
    }

    public String getImagePath() {
        return imagePath;
    }

    public double getPrice() {
        return price;
    }

    public int getAmount() {
        return amount;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setRelease(int release) {
        this.release = release;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public void setAmountPages(int amountPages) {
        this.amountPages = amountPages;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public void setLatest(boolean latest) {
        this.latest = latest;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public List<Cart> getCart() {
        return cart;
    }

    public void setCart(List<Cart> cart) {
        this.cart = cart;
    }

    public List<OrderBook> getOrderBooks() {
        return orderBooks;
    }

    public void setOrderBooks(List<OrderBook> orderBooks) {
        this.orderBooks = orderBooks;
    }

    public String getAmountView() {
        String result;
        if (amount > 5)
            result = "В наличии";
        else if (amount >= 1)
            result = "Осталось мало";
        else
            result = "Товар закончился";

        return result;
    }

}
