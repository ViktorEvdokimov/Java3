package HW7;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RunTests {
    public static void runTests (Class testedClass)  {
        try {
            Object test = testedClass.newInstance();
            Method[] methods = testedClass.getDeclaredMethods();
            int methodsLength = methods.length;
            int numberOfMethod = foundMethod(methods, 0, methodsLength);
            if (numberOfMethod != -1) {
                methods[numberOfMethod].invoke(test);
                methods[numberOfMethod] = null;
            }
            numberOfMethod = foundMethod(methods, 0, methodsLength);
            if (numberOfMethod != -1) {
                throw new RuntimeException("Class have two methods \"BeforeSuite\"");
            }

            for (int i = 1; i <= 10; i++) {
                do {
                    numberOfMethod = foundMethod(methods, i, methodsLength);
                    if (numberOfMethod!=-1) {
                        methods[numberOfMethod].invoke(test);
                        methods[numberOfMethod] = null;
                    }
                } while (numberOfMethod != -1);
            }

            numberOfMethod = foundMethod(methods, 11, methodsLength);
            if (numberOfMethod != -1) {
                methods[numberOfMethod].invoke(test);
                methods[numberOfMethod] = null;
            }
            numberOfMethod = foundMethod(methods, 11, methodsLength);
            if (numberOfMethod != -1) {
                throw new RuntimeException("Class have two methods \"AfterSuite\"");
            }
        } catch (InvocationTargetException | IllegalAccessException | InstantiationException e){
            throw new RuntimeException("SWW ", e);
        }

    }


    private static int foundMethod (Method[] methods, int priority, int length){
        if (priority==0){
            for(int i=0; i< length; i++){
                try {
                    if (methods[i].isAnnotationPresent(BeforeSuite.class)) {
                        return i;
                    }
                } catch (NullPointerException e){
                }
            }
        }
        if (priority==11){
            for(int i=0; i< length; i++){
                try {
                    if (methods[i].isAnnotationPresent(AfterSuite.class)){
                        return i;
                    }
                } catch (NullPointerException e){
                }
            }
        }
        for(int i=0; i< length; i++){
            try {
                if (methods[i].isAnnotationPresent(Test.class)) {
                    Test an = methods[i].getAnnotation(Test.class);
                    if (an.value() == priority) {
                        return i;
                    }
                }
            } catch (NullPointerException e){

        }
        }
        return -1;
    }


}
