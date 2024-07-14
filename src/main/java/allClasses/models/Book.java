package allClasses.models;

import javax.validation.constraints.*;

public class Book {
    private long id;

    @NotEmpty(message = "Обязательное поле")
    @Size(min = 2, max = 30, message = "Название должно быть от 2 до 30 символов")
    private String name;

    @NotEmpty(message = "Обязательное поле")
    @Size(min = 2, max = 30, message = "Имя автора должно быть от 3 до 50 символов")
    private String author;

    @NotEmpty(message = "Язык не может быть пустым")
    private String language;

    @NotNull(message = "Обязательное поле")
    @Digits(integer = 4, fraction = 0, message = "Введите корректный год издания")
    private int year;

    @Size(max = 100, message = "Поле жанры не должно превышать 100 символов")
    private String genres;

    @Size(max = 1000, message = "Описание не должно превышать 1000 символов")
    private String description;

    @Pattern(regexp = "^$|\\d{3}-\\d{10}", message = "ISBN должен соответствовать формату 123-1234567890")
    private String ISBN;

    @Min(value = 0, message = "Количество страниц не может быть меньше 0")
    private int amountPages;

    @DecimalMin(value = "0.0", inclusive = true, message = "Рейтинг не может быть меньше 0")
    @DecimalMax(value = "10.0", inclusive = true, message = "Рейтинг не может быть больше 10")
    private double rate;

    private boolean isNew;

    private String imagePath;

    @NotNull(message = "Обязательное поле")
    @DecimalMin(value = "0.00", inclusive = true, message = "Цена не может быть меньше 0")
    private double price;

    @NotNull(message = "Обязательное поле")
    @Min(value = 0, message = "Количество не может быть меньше 0")
    private int amount;

    public Book(){
        imagePath = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQtUGZqmALjP_ea_LlkL-Jj5fPeP9FUM0Ol4A&s";
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getLanguage() {
        return language;
    }

    public int getYear() {
        return year;
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

    public boolean getIsNew() {
        return isNew;
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

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setYear(int year) {
        this.year = year;
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

    public void setIsNew(boolean isNew) {
        this.isNew = isNew;
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
