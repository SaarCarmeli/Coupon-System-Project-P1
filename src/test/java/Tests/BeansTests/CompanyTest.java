package Tests.BeansTests;

import Beans.Category;
import Beans.Company;
import Beans.Coupon;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CompanyTest {
    public static Company company1;
//    public static Company company2;
//    public static Coupon coupon;

    @BeforeClass
    public static void initiation() {
        System.out.println("BeansTests.CompanyTest started...");
        company1 = new Company(1,"Itzik Inc.", "Itz@Itzmail.com", "Itzik_hamelech1980");
//        company2 = new Company("Motti n' Sons Corp.", "motti.sons@mbusiness.com", "92motti1.95motti2.98motti3");
//        coupon = new Coupon(
//                2,
//                150,
//                30.00,
//                Category.ELECTRICITY,
//                "blabla",
//                "keev rosh keev rosh",
//                "nooroofen.png",
//                Date.valueOf(LocalDate.now()),
//                Date.valueOf(LocalDate.now().plusDays(12))); //todo add company test with Coupon in List (after facade)
    }

//    @Test
//    public void getIdTest() {
//        assertEquals(1, company1.getId());
//    }

    @Test
    public void getNameTest() {
        assertEquals("Itzik Inc.", company1.getName());
    }

    @Test
    public void getEmailTest() {
        assertEquals("Itz@Itzmail.com", company1.getEmail());
    }

    @Test
    public void getPasswordTest() {
        assertEquals("Itzik_hamelech1980", company1.getPassword());
    }

    @Test
    public void listNotNullTest() {
        assertNotNull(company1.getCoupons());
    }

    @Test
    public void printToString() {
        System.out.println(company1.toString());
    }

    @AfterClass
    public static void finalization(){
        System.out.println("BeansTests.CompanyTest finished...");
        System.out.println();
    }
}
