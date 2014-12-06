package JunitTestCases;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * author: JJ Lindsay
 * version: 1.0
 * Course: ITEC 3150 Fall 2014
 * Written: 12/5/2014
 *
 * This class represents a Junit Test
 *
 * Purpose: To run all the Junit Test in InventoryTest
 */
public class TestRunner
{
    public static void main(String[] args)
    {
        Result result = JUnitCore.runClasses(InventoryTest.class);
        for (Failure failure : result.getFailures())
        {
            System.out.println(failure.toString());
        }
        System.out.println(result.wasSuccessful());
    }
}
