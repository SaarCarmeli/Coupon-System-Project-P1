package Beans;

import java.sql.Date;
import java.util.Objects;

public class Coupon {
    private int companyID, amount;
    private Integer id;
    private double price;
    private Category category;
    private String title, description, image;
    private Date startDate, endDate;

    /**
     * Initiates an instance of Coupon.
     *
     * @param id          Coupon ID number
     * @param companyID   Name of the issuing Company
     * @param amount      Worth of Coupon discount
     * @param price       Price to purchase the Coupon
     * @param category    Commercial category the Coupon gives discount in
     * @param title       Commercial name of the Coupon
     * @param description Description of the products the Coupon is valid for and additional info
     * @param image       File name for the Coupon's logo image
     * @param startDate   Coupon creation date
     * @param endDate     Date of Coupon expiration
     */
    public Coupon(Integer id, int companyID, int amount, double price, Category category, String title, String description, String image, Date startDate, Date endDate) {
        this.id = id;
        setCompanyID(companyID);
        setAmount(amount);
        setPrice(price);
        setCategory(category);
        setTitle(title);
        setDescription(description);
        setImage(image);
        this.startDate = startDate;
        setEndDate(endDate);
    }

    /**
     * Returns Coupon ID number.
     *
     * @return Coupon ID number
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the ID of the Company that issued the Coupon.
     *
     * @return Issuing Company's ID number
     */
    public int getCompanyID() {
        return companyID;
    }

    /**
     * Sets the ID of the Company that issues the Coupon.
     *
     * @param companyID Issuing Company's ID number
     */
    public void setCompanyID(int companyID) {
        this.companyID = companyID; // todo consider deleting
    }

    /**
     * Returns the amount of discount the Coupon gives.
     *
     * @return Coupon discount amount
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Sets the amount of discount the Coupon gives.
     *
     * @param amount Coupon discount amount
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * Returns the price for purchasing the Coupon.
     *
     * @return Price to purchase the Coupon
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price for purchasing the Coupon.
     *
     * @param price Price to purchase the Coupon
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Returns the commercial category the Coupon gives discount in.
     *
     * @return Coupon commercial category
     */
    public String getCategory() {
        return String.valueOf(category);
    }

    /**
     * Sets the commercial category the Coupon gives discount in.
     *
     * @param category Coupon commercial category
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * Returns the commercial name of the Coupon.
     *
     * @return Coupon commercial title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the commercial name of the Coupon.
     *
     * @param title Coupon commercial title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the description of the products the Coupon is valid for and additional info.
     *
     * @return Coupon product description and additional info
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the Coupon. Describe the products the Coupon is valid for and any additional info.
     *
     * @param description Coupon description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the file name for the Coupon's logo image.
     *
     * @return Logo image file name
     */
    public String getImage() {
        return image;
    }

    /**
     * Sets the file name for the Coupon's logo image.
     *
     * @param image Logo image file name
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Returns the date of the Coupon creation.
     *
     * @return Coupon creation date
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Returns the date of the Coupon expiration.
     *
     * @return Coupon expiration date
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Sets the date of the Coupon expiration.
     *
     * @param endDate Coupon expiration date
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * Method to compare an Object value to Coupon value.
     *
     * @param o Generic Object
     * @return True -> Objects are equal, else False
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coupon coupon = (Coupon) o;
        return id == coupon.id && companyID == coupon.companyID && amount == coupon.amount && Double.compare(coupon.price, price) == 0 && category == coupon.category && Objects.equals(title, coupon.title) && Objects.equals(description, coupon.description) && Objects.equals(image, coupon.image) && Objects.equals(startDate, coupon.startDate) && Objects.equals(endDate, coupon.endDate);
    }

    /**
     * Generates a distinct hashCode for Coupon.
     *
     * @return hashCode for Coupon
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, companyID, amount, price, category, title, description, image, startDate, endDate);
    }

    /**
     * Returns a String description of Coupon attributes.
     *
     * @return Attribute String for Coupon
     */
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
