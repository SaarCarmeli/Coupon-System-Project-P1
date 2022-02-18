package Tests.BeansTests;

import Beans.Category;
import Beans.Coupon;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class CouponTest {
    public static Coupon coupon;

    @BeforeClass
    public static void initiation() {
        System.out.println("BeansTests.CouponTest");
        coupon = new Coupon(
                1,
                2,
                150,
                30.00,
                Category.ELECTRICITY,
                "blabla",
                "keev rosh keev rosh",
                "nooroofen.png",
                Date.valueOf(LocalDate.now()),
                Date.valueOf(LocalDate.now().plusDays(12)));
    }

//    @Test
//    public void getIdTest() {
//        assertEquals(1, coupon.getId());
//    }

    @Test
    public void getCompanyIdTest() {
        assertEquals(2, coupon.getCompanyID());
    }

    @Test
    public void getAmountTest() {
        assertEquals(150, coupon.getAmount());
    }

    @Test
    public void getPriceTest() {
        assertEquals(30.00, coupon.getPrice(), 0);
    }

    @Test
    public void getCategoryTest() {
        assertEquals(Category.ELECTRICITY, coupon.getCategory());// todo fix
    }

    @Test
    public void getTitleTest() {
        assertEquals("blabla", coupon.getTitle());
    }

    @Test
    public void getDescriptionTest() {
        assertEquals("keev rosh keev rosh", coupon.getDescription());
    }

    @Test
    public void getImageTest() {
        assertEquals("nooroofen.png", coupon.getImage());
    }

    @Test
    public void getStartDateTest() {
        assertEquals(Date.valueOf(LocalDate.now()), coupon.getStartDate());
    }

    @Test
    public void getEndDateTest() {
        assertEquals(Date.valueOf(LocalDate.now().plusDays(12)), coupon.getEndDate());
    }

    @Test
    public void printToString() {
        System.out.println(coupon.toString());
    }

    @AfterClass
    public static void finalization(){
        System.out.println("BeansTests.CouponTest finished...");
        System.out.println();
    }
}
