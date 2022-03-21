package Tests.FacadeTests;

import Beans.Category;
import Beans.Company;
import Beans.Coupon;
import Beans.Customer;
import DB.DatabaseInitializer;
import DB.Util.DBManager;
import DB.Util.DBTools;
import Exceptions.EntityAlreadyExistException;
import Exceptions.EntityCrudException;
import Facades.AdminFacade;
import Facades.CompanyFacade;
import Facades.CustomerFacade;
import LoginManager.ClientType;
import LoginManager.LoginManager;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static org.junit.Assert.assertEquals;

public class CustomerFacadeTest {
    public static AdminFacade adminFacade;
    public static CompanyFacade[] companyFacade;
    public static CustomerFacade[] customerFacade;
    public static Company macrohard, banana;
    public static Customer jeff, jennifer, christopher, christine;

    public static int couponIdCounter, companyIdCounter, customerIdCounter;
    public static Coupon[] databaseMacrohardCoupons, expectedMacrohardCoupons;
    public static Coupon[] databaseBananaCoupons, expectedBananaCoupons;

    public static BiConsumer<Coupon, Coupon> couponAssertion = (expected, actual) -> {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getCompanyId(), actual.getCompanyId());
        assertEquals(expected.getAmount(), actual.getAmount());
        assertEquals(expected.getPrice(), actual.getPrice(), 0);
        assertEquals(expected.getCategory(), actual.getCategory());
        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getStartDate(), actual.getStartDate());
        assertEquals(expected.getEndDate(), actual.getEndDate());
    };

    public static Consumer<Customer> customerCreation = customer -> {
        try {
            adminFacade.addCustomer(customer);
        } catch (EntityAlreadyExistException | EntityCrudException e) {
            System.out.println(e.getMessage());
        }
    };

    @BeforeClass
    public static void oneTimeInitialization() {
        // Array set-ups:
        companyFacade = new CompanyFacade[2];
        customerFacade = new CustomerFacade[4];
        databaseMacrohardCoupons = new Coupon[2];
        expectedMacrohardCoupons = new Coupon[2];
        databaseBananaCoupons = new Coupon[1];
        expectedBananaCoupons = new Coupon[1];
        // In-program Company creation:
        macrohard = new Company("Macrohard Corporation", "MacroBusiness@coldmail.com", "secretlyMicrosoft");
        banana = new Company("Banana Inc", "Banana.Business@bmail.com", "betterthanmacrohard");
        // In-program Coupon creation:
        databaseMacrohardCoupons[0] = new Coupon(
                1
                , 2
                , 399.99
                , Category.ELECTRICITY
                , "Macrohard PC Coupon"
                , "0.1% off on new Macrohard computers!"
                , "gloogle-generic-computer.jpg"
                , Date.valueOf(LocalDate.now())
                , Date.valueOf(LocalDate.now().plusDays(20))
        );
        expectedMacrohardCoupons[0] = new Coupon(
                1
                , 1
                , 2
                , 399.99
                , Category.ELECTRICITY
                , "Macrohard PC Coupon"
                , "0.1% off on new Macrohard computers!"
                , "gloogle-generic-computer.jpg"
                , Date.valueOf(LocalDate.now())
                , Date.valueOf(LocalDate.now().plusDays(20))
        );
        databaseMacrohardCoupons[1] = new Coupon(
                1
                , 2
                , 49.99
                , Category.SOFTWARE
                , "Macrohard Doors OS Coupon"
                , "0.01% off on new Macrohard OS!"
                , "doors-os-logo.jpg"
                , Date.valueOf(LocalDate.now())
                , Date.valueOf(LocalDate.now().plusDays(13))
        );
        expectedMacrohardCoupons[1] = new Coupon(
                2
                , 1
                , 2
                , 49.99
                , Category.SOFTWARE
                , "Macrohard Doors OS Coupon"
                , "0.01% off on new Macrohard OS!"
                , "doors-os-logo.jpg"
                , Date.valueOf(LocalDate.now())
                , Date.valueOf(LocalDate.now().plusDays(13))
        );
        databaseBananaCoupons[0] = new Coupon(
                2
                , 3
                , 999.99
                , Category.SOFTWARE
                , "Banana OS Coupon"
                , "0.001% off on new Banana OS!"
                , "banana-os-logo.jpg"
                , Date.valueOf(LocalDate.now())
                , Date.valueOf(LocalDate.now().plusDays(10))
        );
        expectedBananaCoupons[0] = new Coupon(
                3,
                2
                , 3
                , 999.99
                , Category.SOFTWARE
                , "Banana OS Coupon"
                , "0.001% off on new Banana OS!"
                , "banana-os-logo.jpg"
                , Date.valueOf(LocalDate.now())
                , Date.valueOf(LocalDate.now().plusDays(10))
        );
        // In-program Customer creation and login:
        jeff = new Customer(
                "Jeff",
                "Jefferson",
                "jeffyjeff@gmail.com",
                "12345678"
        );
        jennifer = new Customer(
                "Jennifer",
                "Davis",
                "jenny@gmail.com",
                "abc123"
        );
        christopher = new Customer(
                "Christopher",
                "Williams",
                "chris@gmail.com",
                "abcdefg"
        );
        christine = new Customer(
                "Christine",
                "Brown",
                "christy@gmail.com",
                "1Fsj9byG_oP%"
        );
    }

    @Before
    public void everyTestInitiation() throws EntityAlreadyExistException, EntityCrudException {
        // Database set-up:
        DatabaseInitializer.createTables();
        // In-SQL Company creation and login:
        adminFacade = (AdminFacade) LoginManager.getInstance().login("admin@admin.com", "admin", ClientType.valueOf("ADMINISTRATOR"));
        adminFacade.addCompany(macrohard);
        adminFacade.addCompany(banana);
        companyFacade[0] = (CompanyFacade) LoginManager.getInstance().login("MacroBusiness@coldmail.com", "secretlyMicrosoft", ClientType.COMPANY);
        companyFacade[1] = (CompanyFacade) LoginManager.getInstance().login("Banana.Business@bmail.com", "betterthanmacrohard", ClientType.COMPANY);
        // In-SQL Coupon creation:
        companyFacade[0].addCoupon(databaseMacrohardCoupons[0]);
        companyFacade[0].addCoupon(databaseMacrohardCoupons[1]);
        companyFacade[1].addCoupon(databaseBananaCoupons[0]);
        // In-SQL Customer creation and login:
        adminFacade.addCustomer(jeff);
        adminFacade.addCustomer(jennifer);
        adminFacade.addCustomer(christopher);
        adminFacade.addCustomer(christine);
        customerFacade[0] = (CustomerFacade) LoginManager.getInstance().login("jeffyjeff@gmail.com", "12345678", ClientType.CUSTOMER);
        customerFacade[1] = (CustomerFacade) LoginManager.getInstance().login("jenny@gmail.com", "abc123", ClientType.CUSTOMER);
        customerFacade[2] = (CustomerFacade) LoginManager.getInstance().login("chris@gmail.com", "abcdefg", ClientType.CUSTOMER);
        customerFacade[3] = (CustomerFacade) LoginManager.getInstance().login("christy@gmail.com", "1Fsj9byG_oP%", ClientType.CUSTOMER);
        // idCounter Set-up:
        couponIdCounter = 1;
        companyIdCounter = 1;
        customerIdCounter = 1;
    }

    @After
    public void finish() throws SQLException {
        System.out.println("Dropped schema: " + DBTools.runQuery(DBManager.DROP_SCHEMA));
    }

    @Test
    public void purchaseCouponTest() throws Exception {
        Coupon coupon = customerFacade[0].readCouponById(1);
        Coupon actual, expected;
        customerFacade[0].purchaseCoupon(coupon);
        expected = expectedMacrohardCoupons[0];
        expected.setAmount(expected.getAmount() - 1);
        actual = customerFacade[0].readAllCustomerCoupons().get(0);
        couponAssertion.accept(expected, actual);
    }

    @Test
    public void deleteCouponPurchaseByDeleteCouponCascade() throws Exception {

    }

    @Test
    public void deleteCouponPurchaseByDeleteCompanyCascade() throws Exception {

    }

    @Test
    public void readCouponByIdTest() throws Exception {

    }

    @Test
    public void readAllCustomerCouponsTest() throws Exception {

    }

    @Test
    public void printAllCustomerCouponTest() throws Exception {

    }

    @Test
    public void readCustomerCouponsByCategoryTest() throws Exception {

    }

    @Test
    public void printCustomerCouponsByCategoryTest() throws Exception {

    }

    @Test
    public void readCustomerCouponsByMaxPriceTest() throws Exception {

    }

    @Test
    public void printCustomerCouponsByMaxPriceTest() throws Exception {

    }

    @Test
    public void getCustomerDetailsTest() throws Exception {

    }

    @Test
    public void printCustomerDetailsTest() throws Exception {

    }
}
