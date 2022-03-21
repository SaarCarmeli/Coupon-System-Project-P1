package Beans;

import java.util.List;
import java.util.Objects;

public class Company {
    private Integer id;
    private String name, email, password;
    private List<Coupon> coupons;

    /**
     * Initiates an instance of Company. From MySQL database.
     *
     * @param id    ID number of the Company
     * @param name  Name of the Company
     * @param email Company Email
     */
    public Company(Integer id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    /**
     * Initiates an instance of Company. Before insertion to MySQL database.
     *
     * @param name     Name of the Company
     * @param email    Company Email
     * @param password Login password for the Company
     */
    public Company(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
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
     * You can not set this parameter.
     *
     * @param id Company ID number
     */
    public void setId(Integer id) {
        this.id = id;
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
     * You can not set this parameter.
     *
     * @param name Company Name
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
        return password;
    }

    /**
     * You can not set this parameter.
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

    //todo javadoc
    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", coupons=" + coupons +
                '}';
    }
}
