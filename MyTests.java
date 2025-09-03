package testrunner;

public class MyTests {
	@Test
    public void testAddition() {
        int result = 2 + 3;
        Assert.assertEquals(5, result);
    }

    @Test
    public void testCondition() {
        Assert.assertTrue(10 > 1);
    }

    @Test
    public void testFailure() {
        Assert.assertEquals(10, 20); // This will fail
    }

}
