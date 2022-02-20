package Beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Company {
    private Integer id;
    private String name, email, password;
    private List<Coupon> coupons;

    /**
     * Initiates an instance of Company.
     *
     * @param id       Company ID number
     * @param name     Name of the Company
     * @param email    Company Email
     * @param password Login password for the Company
     */
    public Company(Integer id, String name, String email, String password) {
        this.id = id;
        setName(name);
        setEmail(email);
        setPassword(password);
        this.coupons = new ArrayList<>();
    }


    /**
     * Returns Company ID number.
     *
     * @return Company ID number
     */
    public int getId() {
        return id;
    }

    /**
     * Returns a List of the Coupons the Company has.
     *
     * @return Company List of Coupons
     */
    public List<Coupon> getCoupons() {
        return coupons;
    }

    /**
     * Sets the List of Company Coupons.
     *
     * @param coupons A List of Company Coupons
     */
    public void setCoupons(List<Coupon> coupons) {
        this.coupons = coupons;
    }

    /**
     * Returns the name of the Company.
     *
     * @return Company Name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the Company.
     *
     * @param name Company name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the email of the Company
     *
     * @return Company email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the Company
     *
     * @param email Company email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the login password of the Company
     *
     * @return Login password
     */
    public String getPassword() {
        return password; // todo consider deleting
    }

    /**
     * Sets the login password of the Company
     *
     * @param password Login password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Method to compare an Object value to Company value.
     *
     * @param o Generic Object
     * @return True -> Objects are equal, else False
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return id == company.id && Objects.equals(name, company.name) && Objects.equals(email, company.email) && Objects.equals(password, company.password) && Objects.equals(coupons, company.coupons);
    }

    /**
     * Generates a distinct hashCode for Company.
     *
     * @return hashCode for Company
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, password, coupons);
    }

    /**
     * Returns a String description of Company attributes.
     *
     * @return Attribute String for Company
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.
                append("Company{").
                append("id=").append(id).
                append(", name='").append(name).append('\'').
                append(", email='").append(email).append('\'').
                append(", coupons=").append(coupons.size() == 0 ? "No coupons" : coupons).append('}');
        return stringBuilder.toString();
    }
}
