package net.ntrdeal.realapi.util;

public final class FlagUtil {
    private FlagUtil() {throw new AssertionError("Utility Class");}

    public static boolean isSet(long flags, int flag) {checkFlag(flag, Long.SIZE); return (flags & (1L << flag)) != 0;}
    public static boolean isSet(int flags, int flag) {checkFlag(flag, Integer.SIZE); return (flags & (1 << flag)) != 0;}
    public static boolean isSet(short flags, int flag) {checkFlag(flag, Short.SIZE); return (flags & (1 << flag)) != 0;}
    public static boolean isSet(byte flags, int flag) {checkFlag(flag, Byte.SIZE); return (flags & (1 << flag)) != 0;}

    public static long set(long flags, int flag) {checkFlag(flag, Long.SIZE); return flags | (1L << flag);}
    public static int set(int flags, int flag) {checkFlag(flag, Integer.SIZE); return flags | (1 << flag);}
    public static short set(short flags, int flag) {checkFlag(flag, Short.SIZE); return (short) (flags | (1 << flag));}
    public static byte set(byte flags, int flag) {checkFlag(flag, Byte.SIZE); return (byte) (flags | (1 << flag));}

    public static long unset(long flags, int flag) {checkFlag(flag, Long.SIZE); return flags & ~(1L << flag);}
    public static int unset(int flags, int flag) {checkFlag(flag, Integer.SIZE); return flags & ~(1 << flag);}
    public static short unset(short flags, int flag) {checkFlag(flag, Short.SIZE); return (short) (flags & ~(1 << flag));}
    public static byte unset(byte flags, int flag) {checkFlag(flag, Byte.SIZE); return (byte) (flags & ~(1 << flag));}
    
    public static long toggle(long flags, int flag) {checkFlag(flag, Long.SIZE); return flags ^ (1L << flag);}
    public static int toggle(int flags, int flag) {checkFlag(flag, Integer.SIZE); return flags ^ (1 << flag);}
    public static short toggle(short flags, int flag) {checkFlag(flag, Short.SIZE); return (short) (flags ^ (1 << flag));}
    public static byte toggle(byte flags, int flag) {checkFlag(flag, Byte.SIZE); return (byte) (flags ^ (1 << flag));}

    private static void checkFlag(int flag, int size) {if (flag < 0 || flag >= size) throw new IndexOutOfBoundsException("Flag is out of range: " + flag);}
}
