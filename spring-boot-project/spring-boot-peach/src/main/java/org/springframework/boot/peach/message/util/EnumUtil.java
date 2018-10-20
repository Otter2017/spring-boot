package org.springframework.boot.peach.message.util;

public class EnumUtil {

    private static <T> T parse(Class<? extends Enum> enums, int index) throws NullPointerException, IllegalArgumentException {
        if (enums == null)
            throw new NullPointerException("First parameter can't be null.");
        if (!enums.isEnum())
            throw new IllegalArgumentException("First parameter must be an enum.");

        T[] consts = (T[]) enums.getEnumConstants();
        int enumSize = consts.length;
        if (index < 0 || index > enumSize - 1)
            throw new ArrayIndexOutOfBoundsException("Enum size is between 0 and " + enumSize + ".");

        return consts[index];
    }

    /**
     * 将字节转换成对应的枚举类型
     *
     * @param enums 枚举类型类
     * @param index 枚举类型的下标
     */
    public static <T> T parse(Class<? extends Enum> enums, byte index) {
        return parse(enums, (int) index);
    }


    // TODO 是否删除该方法（多余？）
    /**
     * 将枚举值转换成字节
     *
     * @param enums 枚举值
     */
    public static <T extends Enum> byte toByte(T enums) {
        return (byte) enums.ordinal();
    }
}
