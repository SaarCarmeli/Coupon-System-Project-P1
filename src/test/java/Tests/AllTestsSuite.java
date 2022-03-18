package Tests;


import Tests.LoginTests.AdminFacadeTest;
import Tests.LoginTests.CompanyFacadeTest;
import Tests.LoginTests.CustomerFacadeTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AdminFacadeTest.class, CompanyFacadeTest.class//, CustomerFacadeTest.class
})
public class AllTestsSuite {

}
