package Beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Company {
    private Integer id;
    private String name, email, password;
    private List<Coupon> coupons;

    public Company(Integer id, String name, String email, String password) {
        this.id = id;
        setName(name);
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

    public void setCoupons(List<Coupon> coupons) {
        this.coupons = coupons;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        Company company = (Company) o;
        return id == company.id && Objects.equals(name, company.name) && Objects.equals(email, company.email) && Objects.equals(password, company.password) && Objects.equals(coupons, company.coupons);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, password, coupons);
    }

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
