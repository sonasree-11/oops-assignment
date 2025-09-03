package testrunner;

/**
 * Example tests to demonstrate the runner.
 */
public class SampleTests {

    private String s;

	// A test that passes
    @Test
    public void testAdditionPass() {
        int a = 2 + 3;
        Assert.assertEquals(5, a);
    }

    // A test that fails an assertion
    @Test
    public void testAdditionFail() {
        int a = 2 + 2;
        Assert.assertEquals(5, a); // this will fail
    }

    // A test that throws an unexpected exception
    @Test
    public void testThrowsException() {
        s = null;
        // this will cause a NullPointerException -> test reported as failed/error
        s.length();
    }

    // Another passing test
    @Test
    public void testBoolean() {
        Assert.assertTrue(3 > 1);
    }
}
