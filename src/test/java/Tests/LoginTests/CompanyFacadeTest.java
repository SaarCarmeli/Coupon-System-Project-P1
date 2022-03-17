package Tests.LoginTests;

import Beans.Category;
import Beans.Company;
import Beans.Coupon;
import DB.DatabaseInitializer;
import DB.Util.DBManager;
import DB.Util.DBTools;
import Exceptions.EntityAlreadyExistException;
import Exceptions.EntityCrudException;
import Facades.AdminFacade;
import Facades.CompanyFacade;
import LoginManager.ClientType;
import LoginManager.LoginManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.function.Consumer;

import static org.junit.Assert.*;

public class CompanyFacadeTest {
    public static AdminFacade adminFacade;
    public static CompanyFacade[] companyFacade = new CompanyFacade[2];
    public static int couponIdCounter;
    public static int companyIdCounter;
    public static Consumer<Coupon> couponAssertion = coupon -> {
        try {
            Coupon dataCompany = companyFacade[companyIdCounter - 1].readCouponById(couponIdCounter);
            assertEquals(coupon.getId(), dataCompany.getId());
            assertEquals(coupon.getCompanyId(), dataCompany.getCompanyId());
            assertEquals(coupon.getAmount(), dataCompany.getAmount());
            assertEquals(coupon.getPrice(), dataCompany.getPrice(), 0);
            assertEquals(coupon.getCategory(), dataCompany.getCategory());
            assertEquals(coupon.getTitle(), dataCompany.getTitle());
            assertEquals(coupon.getDescription(), dataCompany.getDescription());
            assertEquals(coupon.getStartDate(), dataCompany.getStartDate());
            assertEquals(coupon.getEndDate(), dataCompany.getEndDate());
            couponIdCounter++;
        } catch (EntityCrudException e) {
            System.out.println(e.getMessage());
        }
    };

    @Before
    public void initiation() throws EntityAlreadyExistException, EntityCrudException {
        DatabaseInitializer.createTables();
        adminFacade = (AdminFacade) LoginManager.getInstance().login("admin@admin.com", "admin", ClientType.valueOf("ADMINISTRATOR"));
        Company macrohard = new Company("Macrohard Corporation", "MacroBusiness@coldmail.com", "secretlyMicrosoft");
        Company banana = new Company("Banana Inc", "Banana.Business@bmail.com", "betterthanmacrohard");
        adminFacade.addCompany(macrohard);
        adminFacade.addCompany(banana);
        companyFacade[0] = (CompanyFacade) LoginManager.getInstance().login("MacroBusiness@coldmail.com", "secretlyMicrosoft", ClientType.COMPANY);
        companyFacade[1] = (CompanyFacade) LoginManager.getInstance().login("Banana.Business@bmail.com", "betterthanmacrohard", ClientType.COMPANY);
        couponIdCounter = 1;
        companyIdCounter = 1;
    }

    @After
    public void finish() throws SQLException {
        System.out.println("Dropped schema: " + DBTools.runQuery(DBManager.DROP_SCHEMA));
    }

    @Test
    public void addCouponTest() throws Exception {
        Coupon coupon = new Coupon(
                companyIdCounter
                , 2
                , 399.99
                , Category.ELECTRICITY
                , "Macrohard PC Coupon"
                , "0.1% off on new Macrohard computers!"
                , "gloogle-pictures-generic-computer.jpg"
                , Date.valueOf(LocalDate.now())
                , Date.valueOf(LocalDate.now().plusDays(20))
        );
        Coupon newCoupon = new Coupon(
                couponIdCounter,
                companyIdCounter
                , 2
                , 399.99
                , Category.ELECTRICITY
                , "Macrohard PC Coupon"
                , "0.1% off on new Macrohard computers!"
                , "gloogle-pictures-generic-computer.jpg"
                , Date.valueOf(LocalDate.now())
                , Date.valueOf(LocalDate.now().plusDays(20))
        );
        companyFacade[companyIdCounter - 1].addCoupon(coupon);
        couponAssertion.accept(newCoupon);
    }

    @Test
    public void updateCouponTest() throws Exception {
        Coupon coupon = new Coupon(
                companyIdCounter
                , 2
                , 49.99
                , Category.ELECTRICITY
                , "Macrohard Doors OS Coupon"
                , "0.01% off on new Macrohard OS!"
                , "doors-os-logo.jpg"
                , Date.valueOf(LocalDate.now())
                , Date.valueOf(LocalDate.now().plusDays(20))
        );
        companyFacade[companyIdCounter - 1].addCoupon(coupon);
        coupon = companyFacade[companyIdCounter - 1].readCouponById(couponIdCounter);
        coupon.setAmount(3);
        coupon.setPrice(99.99);
        coupon.setDescription("1.0% off on new Macrohard OS!");
        companyFacade[companyIdCounter - 1].updateCoupon(coupon);
        couponAssertion.accept(coupon);
    }

    @Test
    public void deleteCouponTest() throws Exception {
        Coupon coupon = new Coupon(
                companyIdCounter
                , 2
                , 49.99
                , Category.ELECTRICITY
                , "Macrohard Doors OS Coupon"
                , "0.01% off on new Macrohard OS!"
                , "doors-os-logo.jpg"
                , Date.valueOf(LocalDate.now())
                , Date.valueOf(LocalDate.now().plusDays(20))
        );
        companyFacade[companyIdCounter - 1].addCoupon(coupon);
        assertTrue(TestDBMethods.isCouponExistById(couponIdCounter));
        companyFacade[companyIdCounter - 1].deleteCoupon(couponIdCounter);
        assertFalse(TestDBMethods.isCouponExistById(couponIdCounter));
    }

    @Test
    public void deleteCouponByCascadeTest() throws Exception {
        Coupon coupon = new Coupon(
                companyIdCounter
                , 2
                , 49.99
                , Category.ELECTRICITY
                , "Macrohard Doors OS Coupon"
                , "0.01% off on new Macrohard OS!"
                , "doors-os-logo.jpg"
                , Date.valueOf(LocalDate.now())
                , Date.valueOf(LocalDate.now().plusDays(20))
        );
        companyFacade[companyIdCounter - 1].addCoupon(coupon);
        assertTrue(TestDBMethods.isCouponExistById(couponIdCounter));
        adminFacade.deleteCompany(companyIdCounter);
        assertFalse(TestDBMethods.isCouponExistById(couponIdCounter));
    }

    @Test
    public void readAllCompanyCouponsTest() {
        /*
        Customer customer1 = new Customer("Jeffery", "Jefferson", "jeffjeff@gmail.com", "nosreffej4891");
        Customer customer2 = new Customer("Jennifer", "Jefferson", "jennjeff@gmail.com", "nosreffej6891");
        Customer customer3 = new Customer("Fred", "Friedman", "freddytheman@gmail.com", "19fredderf89");
        List<Customer> customerList = List.of(customer1, customer2, customer3);
        customerList.forEach(customer -> customerCreation.accept(customer));
        List<Customer> newCustomerList = adminFacade.readAllCustomers();
        newCustomerList.forEach(customer -> customerAssertion.accept(customer));
        * */

    }
}
