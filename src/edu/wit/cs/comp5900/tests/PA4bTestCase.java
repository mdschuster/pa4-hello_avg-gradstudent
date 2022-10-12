package edu.wit.cs.comp5900.tests;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.security.Permission;

import edu.wit.cs.comp5900.PA4b;
import junit.framework.TestCase;

public class PA4bTestCase extends TestCase {
	
	@SuppressWarnings("serial")
	private static class ExitException extends SecurityException {}
	

	
	@Override
    protected void setUp() throws Exception 
    {
        super.setUp();
    }
	
	@Override
    protected void tearDown() throws Exception 
    {
        super.tearDown();
    }
	
	private static final String S_NUMS = "Enter a stream of non-negative numbers (negative when finished): ";
	private static final String S_AGAIN = "Do you want to compute another average (y/n)? ";
	private static final String S_AVG = "The average is: %s%n";
	
	private void _test(String[] values, String[] prompts, String[] results) {
		final String input = String.join(" ", values);
		
		final String output = TestSuite.stringOutput(new String[] {
			String.join("", prompts) }, (Object[]) results);
		
		final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		
		System.setIn(new ByteArrayInputStream(input.getBytes()));
		System.setOut(new PrintStream(outContent));
		
		try {
			PA4b.main(new String[] { "foo" });
		} catch (ExitException e) {}
		assertEquals(output, outContent.toString());
		
		System.setIn(null);
		System.setOut(null);
	}
	
	public void testStop() {
		_test(new String[] {"-1", "n"}, new String[] {S_NUMS, S_AVG, S_AGAIN}, new String[] {"0.00"});
		_test(new String[] {"-1", "N"}, new String[] {S_NUMS, S_AVG, S_AGAIN}, new String[] {"0.00"});
		_test(new String[] {"-1", "y", "-2", "n"}, new String[] {S_NUMS, S_AVG, S_AGAIN, S_NUMS, S_AVG, S_AGAIN}, new String[] {"0.00", "0.00"});
		_test(new String[] {"-1", "y", "-2", "N"}, new String[] {S_NUMS, S_AVG, S_AGAIN, S_NUMS, S_AVG, S_AGAIN}, new String[] {"0.00", "0.00"});
		_test(new String[] {"-1", "Y", "-2", "n"}, new String[] {S_NUMS, S_AVG, S_AGAIN, S_NUMS, S_AVG, S_AGAIN}, new String[] {"0.00", "0.00"});
		_test(new String[] {"-1", "Y", "-2", "N"}, new String[] {S_NUMS, S_AVG, S_AGAIN, S_NUMS, S_AVG, S_AGAIN}, new String[] {"0.00", "0.00"});
	}
	
	public void testIntOne() {
		_test(new String[] {"1", "2", "3", "-1", "n"}, new String[] {S_NUMS, S_AVG, S_AGAIN}, new String[] {"2.00"});
		_test(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "-1", "n"}, new String[] {S_NUMS, S_AVG, S_AGAIN}, new String[] {"5.50"});
		_test(new String[] {"1", "1", "3", "-1", "n"}, new String[] {S_NUMS, S_AVG, S_AGAIN}, new String[] {"1.67"});
	}
	
	public void testDecOne() {
		_test(new String[] {"1.1", "1.9", "3", "-1", "n"}, new String[] {S_NUMS, S_AVG, S_AGAIN}, new String[] {"2.00"});
		_test(new String[] {"1.1", "2.2", "3.3", "-1", "n"}, new String[] {S_NUMS, S_AVG, S_AGAIN}, new String[] {"2.20"});
	}
	
}
