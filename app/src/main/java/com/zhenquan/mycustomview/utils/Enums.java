package com.zhenquan.mycustomview.utils;

import java.util.Random;

/**
 * 枚举类中随机获取枚举
 * Enums.random(WeekEnum.class)
 */
public class Enums {
    private static int random = (int) (Math.random() * 10);// 生成种子
    private static Random rand = new Random(random);

    public static <T extends Enum<T>> T random(Class<T> ec) {
        return random(ec.getEnumConstants());
    }

    public static <T> T random(T[] values) {
        return values[rand.nextInt(values.length)];
    }
}

