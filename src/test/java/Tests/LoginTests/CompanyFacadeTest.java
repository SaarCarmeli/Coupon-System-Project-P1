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
    public static CompanyFacade companyFacade;
    public static int idCounter = 1;
    public static Consumer<Coupon> couponAssertion = coupon -> {
        try {
            Coupon dataCompany = companyFacade.getCouponById(idCounter);// todo maybe change to "by id and company id"
            assertEquals(coupon.getId(), dataCompany.getId());
            assertEquals(coupon.getCompanyId(), dataCompany.getCompanyId());
            assertEquals(coupon.getAmount(), dataCompany.getAmount());
            assertEquals(coupon.getPrice(), dataCompany.getPrice(), 0);
            assertEquals(coupon.getCategory(), dataCompany.getCategory());
            assertEquals(coupon.getTitle(), dataCompany.getTitle());
            assertEquals(coupon.getDescription(), dataCompany.getDescription());
            assertEquals(coupon.getStartDate(), dataCompany.getStartDate());
            assertEquals(coupon.getEndDate(), dataCompany.getEndDate());
        } catch (EntityCrudException e) {
            System.out.println(e.getMessage());
        }
    };

    @Before
    public void initiation() throws EntityAlreadyExistException, EntityCrudException {
        DatabaseInitializer.createTables();
        adminFacade = (AdminFacade) LoginManager.getInstance().login("admin@admin.com", "admin", ClientType.valueOf("ADMINISTRATOR"));
        Company company = new Company("Macrohard Corp.", "MacroBusiness@coldmail.com", "secretlyMicrosoft");
        adminFacade.addCompany(company);
        companyFacade = (CompanyFacade) LoginManager.getInstance().login("MacroBusiness@coldmail.com", "secretlyMicrosoft", ClientType.COMPANY);
        idCounter = 1;
    }

    @After
    public void finish() throws SQLException {
        System.out.println("Dropped schema: " + DBTools.runQuery(DBManager.DROP_SCHEMA));
    }

    @Test
    public void addCouponTest() throws Exception {
        Coupon coupon = new Coupon(
                idCounter
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
                idCounter,
                idCounter
                , 2
                , 399.99
                , Category.ELECTRICITY
                , "Macrohard PC Coupon"
                , "0.1% off on new Macrohard computers!"
                , "gloogle-pictures-generic-computer.jpg"
                , Date.valueOf(LocalDate.now())
                , Date.valueOf(LocalDate.now().plusDays(20))
        );
        companyFacade.addCoupon(coupon);
        couponAssertion.accept(newCoupon);
    }

    @Test
    public void updateCouponTest() throws Exception {
        Coupon coupon = new Coupon(
                idCounter
                , 2
                , 49.99
                , Category.ELECTRICITY
                , "Macrohard Doors OS Coupon"
                , "0.01% off on new Macrohard OS!"
                , "doors-os-logo.jpg"
                , Date.valueOf(LocalDate.now())
                , Date.valueOf(LocalDate.now().plusDays(20))
        );
        companyFacade.addCoupon(coupon);
        coupon = companyFacade.getCouponById(idCounter);
        coupon.setAmount(3);
        coupon.setPrice(99.99);
        coupon.setDescription("1.0% off on new Macrohard OS!");
        companyFacade.updateCoupon(coupon);
        couponAssertion.accept(coupon);
    }

    @Test
    public void deleteCouponTest() throws Exception {
        Coupon coupon = new Coupon(
                idCounter
                , 2
                , 49.99
                , Category.ELECTRICITY
                , "Macrohard Doors OS Coupon"
                , "0.01% off on new Macrohard OS!"
                , "doors-os-logo.jpg"
                , Date.valueOf(LocalDate.now())
                , Date.valueOf(LocalDate.now().plusDays(20))
        );
        companyFacade.addCoupon(coupon);
        assertTrue(TestDBMethods.isCouponExistById(idCounter));
        companyFacade.deleteCoupon(idCounter);
        assertFalse(TestDBMethods.isCouponExistById(idCounter));
    }

    @Test
    public void deleteCouponByCascadeTest() throws Exception {
        Coupon coupon = new Coupon(
                idCounter
                , 2
                , 49.99
                , Category.ELECTRICITY
                , "Macrohard Doors OS Coupon"
                , "0.01% off on new Macrohard OS!"
                , "doors-os-logo.jpg"
                , Date.valueOf(LocalDate.now())
                , Date.valueOf(LocalDate.now().plusDays(20))
        );
        companyFacade.addCoupon(coupon);
        assertTrue(TestDBMethods.isCouponExistById(idCounter));
        adminFacade.deleteCompany(idCounter);
        assertFalse(TestDBMethods.isCouponExistById(idCounter));
    }
}
