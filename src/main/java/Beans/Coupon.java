package Beans;

import java.sql.Date;
import java.util.Objects;

public class Coupon {
    private int  companyID, amount;
    private Integer id;
    private double price;
    private Category category;
    private String title, description, image;
    private Date startDate, endDate;

    public Coupon(int companyID, int amount, double price, Category category, String title, String description, String image, Date startDate, Date endDate) {
        this.id = null;
        setCompanyID(companyID);
        setAmount(amount);
        setPrice(price);
        setCategory(category);
        setTitle(title);
        setDescription(description);
        setImage(image);
        setStartDate(startDate);
        setEndDate(endDate);
    }

    public int getId() {
        return id;
    }

    public int getCompanyID() {
        return companyID;
    }

    public void setCompanyID(int companyID) {
        this.companyID = companyID;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return String.valueOf(category);
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coupon coupon = (Coupon) o;
        return id == coupon.id && companyID == coupon.companyID && amount == coupon.amount && Double.compare(coupon.price, price) == 0 && category == coupon.category && Objects.equals(title, coupon.title) && Objects.equals(description, coupon.description) && Objects.equals(image, coupon.image) && Objects.equals(startDate, coupon.startDate) && Objects.equals(endDate, coupon.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, companyID, amount, price, category, title, description, image, startDate, endDate);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.
                append("Coupon{").
                append("id=").append(id).
                append(", companyID=").append(companyID).
                append(", amount=").append(amount).
                append(", price=").append(price).
                append(", category=").append(category).
                append(", title='").append(title).append('\'').
                append(", description='").append(description).append('\'').
                append(", image='").append(image).append('\'').
                append(", startDate=").append(startDate).
                append(", endDate=").append(endDate).append('}');
        return stringBuilder.toString();
    }

}
