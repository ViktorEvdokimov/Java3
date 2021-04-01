package HW7;

public class TestedClass {

    @Test(value = 3)
    public void treePriority(){
        System.out.println("treePriority");
    }

    @Test(value = 1)
    public void onePriority(){
        System.out.println("onePriority");
    }
    @Test(value = 9)
    public void ninePriority(){
        System.out.println("ninePriority");
    }
    @Test(value = 5)
    public void fivePriority(){
        System.out.println("fivePriority");
    }
    @Test(value = 3)
    public void treePriorityTwo(){
        System.out.println("treePriorityTwo");
    }

    @AfterSuite
    public void after(){
        System.out.println("after");
    }

    @BeforeSuite
    public void before(){
        System.out.println("before");
    }

    public static void main(String[] args) {
        TestedClass testedClass = new TestedClass();
        RunTests.runTests(testedClass.getClass());
    }
}
