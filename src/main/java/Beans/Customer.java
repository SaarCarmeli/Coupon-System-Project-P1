package Beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Customer {
    private Integer id;
    private String firstName, lastName, email, password;
    private List<Coupon> coupons;

    public Customer(String firstName, String lastName, String email, String password) {
        this.id = null;
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        setPassword(password);
        this.coupons = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public List<Coupon> getCoupons() {
        return coupons;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return id == customer.id && Objects.equals(firstName, customer.firstName) && Objects.equals(lastName, customer.lastName) && Objects.equals(email, customer.email) && Objects.equals(password, customer.password) && Objects.equals(coupons, customer.coupons);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, password, coupons);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.
                append("Company{").
                append("id=").append(id).
                append(", firstName='").append(firstName).append('\'').
                append(", lastName='").append(lastName).append('\'').
                append(", email='").append(email).append('\'').
                append(", coupons=").append(coupons.size() == 0 ? "No coupons" : coupons).append('}');
        return stringBuilder.toString();
    }
}
