package Tests.BeansTests;

import Beans.Coupon;
import Beans.Customer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CustomerTest {
    public static Customer customer1;
//    public static Customer customer2;
//    public static Coupon coupon;

    @BeforeClass
    public static void initiation() {
        System.out.println("BeansTests.CustomerTest");
        customer1 = new Customer("Moshiko", "Berkowich", "mosh.berko@jmail.com", "`1234567890-=");
//        customer2 = new Customer("Super", "man", "emegencies.only@supermail.com", "clark-kent");
//        coupon = new Coupon(
//                2,
//                150,
//                30.00,
//                Category.ELECTRICITY,
//                "blabla",
//                "keev rosh keev rosh",
//                "nooroofen.png",
//                Date.valueOf(LocalDate.now()),
//                Date.valueOf(LocalDate.now().plusDays(12))); //todo add customer test with Coupon in List (after facade)
    }

//    @Test
//    public void getIdTest() {
//        assertEquals(1, customer1.getId());
//    }

    @Test
    public void getFirstNameTest() {
        assertEquals("Moshiko", customer1.getFirstName());
    }

    @Test
    public void getLastNameTest() {
        assertEquals("Berkowich", customer1.getLastName());
    }

    @Test
    public void getEmailTest() {
        assertEquals("mosh.berko@jmail.com", customer1.getEmail());
    }

    @Test
    public void getPasswordTest() {
        assertEquals("`1234567890-=", customer1.getPassword());
    }

    @Test
    public void listNotNullTest() {
        assertNotNull(customer1.getCoupons());
    }

    @Test
    public void printToString() {
        System.out.println(customer1.toString());
    }

    @AfterClass
    public static void finalization(){
        System.out.println("BeansTests.CustomerTest finished...");
        System.out.println();
    }
}
