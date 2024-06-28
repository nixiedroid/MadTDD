import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

public class Main {

    public static void main(String[] args) {
        try {
            createUser();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    static void createUser() {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        String className = "User";

        String sb = "class " + className + "{private String name; public String getName(){return this.name;} public " + className + "(){this.name = \"Imaginary\";}}";

        File f = new File(System.getProperty("java.io.tmpdir") + className + ".java");
        File c = new File(System.getProperty("java.io.tmpdir") + className + ".class");
        byte[] cB = new byte[0];
        try {
            f.createNewFile();
            f.deleteOnExit();
            c.deleteOnExit();
            Files.write(f.toPath(), sb.getBytes(StandardCharsets.UTF_8), StandardOpenOption.WRITE);
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            if (compiler.run(null, System.out, null, f.getAbsolutePath()) != 0) {
                throw new Error();
            }
            cB = Files.readAllBytes(c.toPath());
        } catch (IOException ignored) {
        }

        try {
            Class<?> clazz = lookup.defineClass(cB);
            System.out.println(clazz.getName());
        } catch (ReflectiveOperationException ignored) {
            throw new Error();
        }
    }
}
