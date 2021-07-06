package com.fb208.jcode.util;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class LambdaTool {
    // 工具方法
    public static <T> Consumer<T> consumerWithIndex(BiConsumer<T, Integer> consumer) {
        class Obj {
            int i;
        }
        Obj obj = new Obj();
        return t -> {
            int index = obj.i++;
            consumer.accept(t, index);
        };
    }
}
