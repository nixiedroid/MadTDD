import java.lang.instrument.Instrumentation;

public class Premain {
    public static void agentmain(final String agentArgs, final Instrumentation inst){
        System.out.println("OK");
    }
}
