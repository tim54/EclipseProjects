package home.filecopier;

import org.junit.Before;
import org.junit.BeforeClass;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class utilityTest
extends TestCase
{
	/**
	 * Create the test case
	 *
	 * @param testName name of the test case
	 */
	public utilityTest( String testName )
	{
		super( testName );
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite()
	{
		return new TestSuite( utilityTest.class );
	}

	/**
	 * Rigourous Test :-)
	 */
	public void testApp()
	{
		System.out.println("in test");
		assertTrue( true );
	}
}
