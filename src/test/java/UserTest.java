import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class UserTest {
    static MethodHandles.Lookup l;
    static Class<?> userClass;
    @BeforeAll
    public static void testUserClass(){
        Main.createUser();
        try {
            userClass = Class.forName("User");
            l = MethodHandles.privateLookupIn(userClass,MethodHandles.lookup());
        } catch (ClassNotFoundException e) {
            Assertions.fail("User class not exists");
        } catch (IllegalAccessException e) {
            Assertions.fail("Should not have thrown any exception");
        }
    }

    private Object getUser(){
        try {
            MethodHandle ctor = l.findConstructor(userClass, MethodType.methodType(void.class));
            return ctor.invoke();
        } catch (Throwable e) {
            Assertions.fail("Should not have thrown any exception");
            throw new Error();
        }
    }

    @Test
    public void testInit(){
        try {
            MethodHandle ctor = l.findConstructor(userClass, MethodType.methodType(void.class));
            ctor.invoke();
        } catch (Throwable e) {
            Assertions.fail("Should not have thrown any exception");
        }
    }
    @Test
    public void testGetName(){
        Object user = getUser();
        try {
            MethodHandle getName = l.findVirtual(userClass,"getName",MethodType.methodType(String.class));
            String name = (String) getName.invoke(user);
            Assertions.assertEquals("Imaginary",name);
        } catch (Throwable e) {
            Assertions.fail("Should not have thrown any exception");
        }

    }
}
