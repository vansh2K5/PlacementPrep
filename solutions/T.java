import common.Check;
import java.lang.reflect.*;

/** Calls pNNNN.Solution.method(args) reflectively, since every problem uses the class name Solution. */
public class T {
    public static Object call(String pkg, String method, Object... args) throws Exception {
        Class<?> c = Class.forName(pkg + ".Solution");
        Constructor<?> ctor = c.getDeclaredConstructor();
        ctor.setAccessible(true);
        Object inst = ctor.newInstance();
        for (Method m : c.getDeclaredMethods()) {
            if (m.getName().equals(method) && m.getParameterCount() == args.length) {
                m.setAccessible(true);
                return m.invoke(inst, args);
            }
        }
        throw new NoSuchMethodException(pkg + "." + method);
    }

    public static void done() {
        System.out.println("passed " + Check.passed + ", failed " + Check.failed);
        if (Check.failed > 0) System.exit(1);
    }
}
