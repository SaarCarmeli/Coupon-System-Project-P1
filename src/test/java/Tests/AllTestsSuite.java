package Tests;


import Tests.FacadeTests.AdminFacadeTest;
import Tests.FacadeTests.CompanyFacadeTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AdminFacadeTest.class, CompanyFacadeTest.class//, CustomerFacadeTest.class
})
public class AllTestsSuite {

}
