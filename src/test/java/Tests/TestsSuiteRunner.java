package Tests;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestsSuiteRunner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(AllTestsSuite.class);
        for (Failure fail : result.getFailures()) {
            System.out.println(fail.toString());
        }

        System.out.println();
        System.out.println("Success? " + result.wasSuccessful());
    }
}
