package org.exp.math.utils;

import org.exp.math.debug.*;
import java.io.File;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Debug {

    private static final int PAGESIZE = 4096;

    public static String classLoaderInfo(Class<?> cls) {
        ClassLoader cl = cls.getClassLoader();
        int hc = cl.hashCode();
        String cp = findClassPath(cls);
        String loaderName = cl.getClass().getName();
        String info = cl.toString();
        String fmt = "{hash=\"%d\", classLoader=\"%s\", classPath=\"%s\", info=\"%s\"}";
        String out = String.format(fmt, hc, loaderName, cp, info);
        return out;
    }

    public static String findClassPath(Class<?> cls) {
        try {
            String className = cls.getName();
            String mangledName = "/" + className.replace(".", "/") + ".class";
            URL loc = cls.getResource(mangledName);
            String classPath = loc.getPath();
            return classPath;
        } catch (Exception ex) {
            String st = getEST(ex);
            return st;
        }
    }

    public static String findClassPath(String className) throws ClassNotFoundException {
        Class classIn = Class.forName(className);
        return findClassPath(classIn);
    }

    public static String printClassPaths(String... classNames) {
        int i = 0;
        int length = classNames.length;
        StringBuilder sb = new StringBuilder();

        for (i = 0; i < length; i++) {
            String className = classNames[i];
            String classPath = "";
            try {
                classPath = findClassPath(className);
            } catch (Exception ex) {
                classPath = String.format("Exception %s\n", getEST(ex));
            }
            sb.append(String.format("\"%s\" -> \"%s\"\n", className, classPath));
        }
        return sb.toString();
    }

    public static String getExtendedStackTrace(Throwable th) {
        Throwable t;
        StringBuilder sb = new StringBuilder(PAGESIZE);
        Exception currEx;
        String msg;

        t = th;
        while (t != null) {
            if (t instanceof Exception) {
                currEx = (Exception) t;
                sb.append(String.format("\"%s\":\"%s\"\n", currEx.getClass().getName(), currEx.getMessage()));
                for (StackTraceElement se : currEx.getStackTrace()) {
                    sb.append(String.format("%s\n", se.toString()));
                }
                sb.append("\n");
                t = t.getCause();
            }
        }
        return sb.toString();
    }

    public static String getEST(Throwable th) {
        return getExtendedStackTrace(th);
    }

    public static int nCpus() {
        return Runtime.getRuntime().availableProcessors();
    }

    public static long freeMem() {
        return Runtime.getRuntime().freeMemory();
    }

    public static long totalMem() {
        return Runtime.getRuntime().totalMemory();
    }

    public static long usedMem() {
        return totalMem() - freeMem();
    }

    public static long maxMem() {
        return Runtime.getRuntime().maxMemory();
    }

    public static void gc() {
        Runtime.getRuntime().gc();
    }

    //For jython since
    public static String getClassName(Object obj) {
        if (obj instanceof Class) {
            Class c = (Class) obj;
            return c.getName();
        } else {
            return obj.getClass().getName();
        }
    }

    public static String getProgName(Class inClass) {
        int li;
        String sep;
        String path;
        String prog;
        URI uri;
        File file;

        try {
            uri = inClass.getProtectionDomain().
                    getCodeSource().
                    getLocation().
                    toURI();
            file = new File(uri);
            path = file.getAbsolutePath();
            sep = File.separator;
            li = path.lastIndexOf(sep) + 1;
            prog = path.substring(li, path.length());
            prog = String.format("java -jar %s", prog);
        } catch (Exception ex) {
            prog = "prog";
        }
        return prog;
    }

    public static Object getField(Object obj, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Field f = obj.getClass().getDeclaredField(fieldName);
        f.setAccessible(true);
        return f.get(obj);
    }

    // For jython
    public static byte[] newByteArray(int n) {
        return new byte[n];
    }

    public static String lpadLong(long val, String pad, int npad) {
        return lpad(Long.toString(val), pad, npad);
    }

    public static String lpad(String val, String pad, int npad) {
        StringBuilder sb = new StringBuilder();
        int nspaces = npad - val.length();
        for (int i = 0; i < nspaces; i++) {
            sb.append(pad);
        }
        sb.append(val);
        return sb.toString();
    }

    public static String showMem() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("  MaxMemory: %s\n", lpadLong(maxMem(), " ", 20)));
        sb.append(String.format("TotalMemory: %s\n", lpadLong(totalMem(), " ", 20)));
        sb.append(String.format(" UsedMemory: %s\n", lpadLong(usedMem(), " ", 20)));
        sb.append(String.format(" FreeMemory: %s\n", lpadLong(freeMem(), " ", 20)));
        return sb.toString();
    }
}
