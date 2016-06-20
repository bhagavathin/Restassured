package Restassured.Restassured;

import static com.jayway.restassured.RestAssured.get;

import static org.hamcrest.Matchers.equalTo;

import com.jayway.restassured.response.Response;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        
    	assertTrue( true );
        
    }
    
   
}
