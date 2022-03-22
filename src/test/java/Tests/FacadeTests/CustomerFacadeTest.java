package Tests.FacadeTests;

import Beans.Category;
import Beans.Company;
import Beans.Coupon;
import Beans.Customer;
import Beans.Util.TablePrinterUtil;
import DB.DatabaseInitializer;
import DB.Util.DBManager;
import DB.Util.DBTools;
import DBDAO.CouponDBDAO;
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
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static org.junit.Assert.*;

public class CustomerFacadeTest {
    public static AdminFacade adminFacade;
    public static CompanyFacade[] companyFacade;
    public static CustomerFacade[] customerFacade;
    public static Company macrohard, banana;
    public static List<Company> companyList;
    public static Customer jeff, jennifer, christopher, christine, expctedChristine;
    public static List<Customer> customerList;

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

    public static BiConsumer<Customer, Customer> customerAssertion = (expected, actual) -> {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getEmail(), actual.getEmail());
    };

    public static Consumer<Company> companyCreation = company -> {
        try {
            adminFacade.addCompany(company);
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
        companyList = List.of(macrohard, banana);
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

        expctedChristine = new Customer(
                4,
                "Christine",
                "Brown",
                "christy@gmail.com"
        );
        customerList = List.of(jeff, jennifer, christopher, christine);
    }

    @Before
    public void everyTestInitiation() throws EntityAlreadyExistException, EntityCrudException {
        // Database set-up:
        DatabaseInitializer.createTables();
        // In-SQL Company creation and login:
        adminFacade = (AdminFacade) LoginManager.getInstance().login("admin@admin.com", "admin", ClientType.valueOf("ADMINISTRATOR"));
        companyList.forEach(company -> companyCreation.accept(company));
        companyFacade[0] = (CompanyFacade) LoginManager.getInstance().login("MacroBusiness@coldmail.com", "secretlyMicrosoft", ClientType.COMPANY);
        companyFacade[1] = (CompanyFacade) LoginManager.getInstance().login("Banana.Business@bmail.com", "betterthanmacrohard", ClientType.COMPANY);
        // In-SQL Coupon creation:
        companyFacade[0].addCoupon(databaseMacrohardCoupons[0]);
        companyFacade[0].addCoupon(databaseMacrohardCoupons[1]);
        companyFacade[1].addCoupon(databaseBananaCoupons[0]);
        // In-SQL Customer creation and login:
        customerList.forEach(customer -> customerCreation.accept(customer));
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
        // Drop schema (database):
        System.out.println("Dropped schema: " + DBTools.runQuery(DBManager.DROP_SCHEMA));
        // Reset Coupon amount:
        expectedMacrohardCoupons[0].setAmount(2);
        expectedMacrohardCoupons[1].setAmount(2);
        expectedBananaCoupons[0].setAmount(3);
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
    public void deleteCouponPurchaseByDeleteCouponCascadeTest() throws Exception {
        Coupon coupon = customerFacade[0].readCouponById(1);
        customerFacade[0].purchaseCoupon(coupon);
        assertTrue(CouponDBDAO.getInstance().isPurchaseExistByIds(1, 1));
        companyFacade[0].deleteCoupon(1);
        assertFalse(CouponDBDAO.getInstance().isPurchaseExistByIds(1, 1));
    }

    @Test
    public void deleteCouponPurchaseByDeleteCompanyCascadeTest() throws Exception {
        Coupon coupon = customerFacade[0].readCouponById(1);
        customerFacade[0].purchaseCoupon(coupon);
        assertTrue(CouponDBDAO.getInstance().isPurchaseExistByIds(1, 1));
        adminFacade.deleteCompany(1);
        assertFalse(CouponDBDAO.getInstance().isPurchaseExistByIds(1, 1));
    }

    @Test
    public void readAllCustomerCouponsTest() throws Exception {
        Coupon coupon1 = customerFacade[1].readCouponById(1);
        Coupon coupon2 = customerFacade[1].readCouponById(2);
        customerFacade[1].purchaseCoupon(coupon1);
        customerFacade[1].purchaseCoupon(coupon2);
        Coupon coupon3 = customerFacade[2].readCouponById(3);
        customerFacade[2].purchaseCoupon(coupon3);
        ArrayList<Coupon> customer1Coupons = customerFacade[1].readAllCustomerCoupons();
        assertEquals(2, customer1Coupons.size());
        expectedMacrohardCoupons[0].setAmount(expectedMacrohardCoupons[0].getAmount() - 1);
        expectedMacrohardCoupons[1].setAmount(expectedMacrohardCoupons[1].getAmount() - 1);
        couponAssertion.accept(expectedMacrohardCoupons[0], customer1Coupons.get(0));
        couponAssertion.accept(expectedMacrohardCoupons[1], customer1Coupons.get(1));
    }

    @Test
    public void printAllCustomerCouponTest() throws Exception {
        Coupon coupon1 = customerFacade[1].readCouponById(1);
        Coupon coupon2 = customerFacade[1].readCouponById(2);
        customerFacade[1].purchaseCoupon(coupon1);
        customerFacade[1].purchaseCoupon(coupon2);
        Coupon coupon3 = customerFacade[2].readCouponById(3);
        customerFacade[2].purchaseCoupon(coupon3);
        ArrayList<Coupon> customer1Coupons = customerFacade[1].readAllCustomerCoupons();
        TablePrinterUtil.print(customer1Coupons);
    }

    @Test
    public void readCustomerCouponsByCategoryTest() throws Exception {
        Coupon coupon1 = customerFacade[1].readCouponById(1);
        Coupon coupon2 = customerFacade[1].readCouponById(2);
        customerFacade[1].purchaseCoupon(coupon1);
        customerFacade[1].purchaseCoupon(coupon2);
        Coupon coupon3 = customerFacade[2].readCouponById(3);
        customerFacade[2].purchaseCoupon(coupon3);
        ArrayList<Coupon> customer1Coupons = customerFacade[1].readCustomerCoupons(Category.SOFTWARE);
        assertEquals(1, customer1Coupons.size());
        expectedMacrohardCoupons[1].setAmount(expectedMacrohardCoupons[1].getAmount() - 1);
        couponAssertion.accept(expectedMacrohardCoupons[1], customer1Coupons.get(0));
    }

    @Test
    public void printCustomerCouponsByCategoryTest() throws Exception {
        Coupon coupon1 = customerFacade[1].readCouponById(1);
        Coupon coupon2 = customerFacade[1].readCouponById(2);
        customerFacade[1].purchaseCoupon(coupon1);
        customerFacade[1].purchaseCoupon(coupon2);
        Coupon coupon3 = customerFacade[2].readCouponById(3);
        customerFacade[2].purchaseCoupon(coupon3);
        ArrayList<Coupon> customer1Coupons = customerFacade[1].readCustomerCoupons(Category.SOFTWARE);
        TablePrinterUtil.print(customer1Coupons);
    }

    @Test
    public void readCustomerCouponsByMaxPriceTest() throws Exception {
        Coupon coupon1 = customerFacade[1].readCouponById(1);
        Coupon coupon2 = customerFacade[1].readCouponById(2);
        customerFacade[1].purchaseCoupon(coupon1);
        customerFacade[1].purchaseCoupon(coupon2);
        Coupon coupon3 = customerFacade[2].readCouponById(3);
        customerFacade[2].purchaseCoupon(coupon3);
        ArrayList<Coupon> customer1Coupons = customerFacade[1].readCustomerCoupons(50);
        assertEquals(1, customer1Coupons.size());
        expectedMacrohardCoupons[1].setAmount(expectedMacrohardCoupons[1].getAmount() - 1);
        couponAssertion.accept(expectedMacrohardCoupons[1], customer1Coupons.get(0));
    }

    @Test
    public void printCustomerCouponsByMaxPriceTest() throws Exception {
        Coupon coupon1 = customerFacade[1].readCouponById(1);
        Coupon coupon2 = customerFacade[1].readCouponById(2);
        customerFacade[1].purchaseCoupon(coupon1);
        customerFacade[1].purchaseCoupon(coupon2);
        Coupon coupon3 = customerFacade[2].readCouponById(3);
        customerFacade[2].purchaseCoupon(coupon3);
        ArrayList<Coupon> customer1Coupons = customerFacade[1].readCustomerCoupons(50);
        TablePrinterUtil.print(customer1Coupons);
    }

    @Test
    public void getCustomerDetailsTest() throws Exception {
        Customer actual = customerFacade[3].getCustomerDetails();
        Customer expected = expctedChristine;
        customerAssertion.accept(expected, actual);
    }

    @Test
    public void printCustomerDetailsTest() throws Exception {
        Customer customer = customerFacade[3].getCustomerDetails();
        Coupon coupon = customerFacade[3].readCouponById(3);
        customerFacade[3].purchaseCoupon(coupon);
        TablePrinterUtil.print(customer);
    }
}
