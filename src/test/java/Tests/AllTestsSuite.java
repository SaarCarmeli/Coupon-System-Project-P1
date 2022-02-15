package Tests;


import Tests.BeansTests.CompanyTest;
import Tests.BeansTests.CouponTest;
import Tests.BeansTests.CustomerTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CompanyTest.class, CouponTest.class, CustomerTest.class
})
public class AllTestsSuite {

}
