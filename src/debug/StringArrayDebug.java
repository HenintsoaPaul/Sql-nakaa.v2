package debug;

@SuppressWarnings("unused")
public abstract class StringArrayDebug {

    public static void sysout(String[] array) {

        for (String str: array)
            System.out.println(str);
    }
}
