package JunitTestCases;

import Controller.ControllerSaveTest;
import Controller.ControllerTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * author: JJ Lindsay
 * version: 1.0
 * Course: ITEC 3150 Fall 2014
 * Written: 12/6/2014
 *
 * This class represents JUnit Test Suite
 *
 * Purpose: To call and run all 3 JUnit test classes.
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({
        InventoryTest.class,
        ControllerTest.class,
        ControllerSaveTest.class
})
public class JunitTestSuite {
}
