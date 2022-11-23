package com.qsy.os.common.exception;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;

import java.util.Optional;
import java.util.function.Supplier;
import java.util.regex.Pattern;

@SuppressWarnings({"unchecked", "UnusedReturnValue"})
public interface IAsserts {

    // new exception

    <E extends BaseException> E newE(Object... args);

    <E extends BaseException> E newEWithCause(Throwable cause, Object... args);

    <E extends BaseException> E newEWithData(Object data, Object... args);

    <E extends BaseException> E newEWithCauseAndData(Throwable cause, Object data, Object... args);

    // new exception, return supplier

    default Supplier<BaseException> newESupplier(Object... args) {
        return () -> newE(args);
    }

    default Supplier<BaseException> newEWithCauseSupplier(Throwable cause, Object... args) {
        return () -> newEWithCause(cause, args);
    }

    default Supplier<BaseException> newEWithDataSupplier(Object data, Object... args) {
        return () -> newEWithData(data, args);
    }

    default Supplier<BaseException> newEWithCauseAndDataSupplier(Throwable cause, Object data, Object... args) {
        return () -> newEWithCauseAndData(cause, data, args);
    }

    // assert not null

    default <E extends BaseException> void assertNotNull(Object target, Object... args) {
        Optional.ofNullable(target).<E>orElseThrow(() -> newE(args));
    }

    default <E extends BaseException> void assertNotNullWithData(Object target, Object data, Object... args) {
        Optional.ofNullable(target).<E>orElseThrow(() -> newEWithData(data, args));
    }

    default <T, E extends BaseException> T assertNotNullAndReturn(T target, Object... args) {
        return Optional.ofNullable(target).<E>orElseThrow(() -> newE(args));
    }

    default <T, E extends BaseException> T assertNotNullWithDataAndReturn(T target, Object data, Object... args) {
        return Optional.ofNullable(target).<E>orElseThrow(() -> newEWithData(data, args));
    }

    default <T, E extends BaseException> T assertNotNullWithDataSupplierAndReturn(T target, Supplier<Object> dataSupplier, Object... args) {
        return Optional.ofNullable(target).<E>orElseThrow(() -> newEWithData(dataSupplier.get(), args));
    }

    // assert null

    default void assertNull(Object target, Object... args) {
        Optional.ofNullable(target).ifPresent(nonNullTarget -> {
            throw newE(args);
        });
    }

    default void assertNullWithData(Object target, Object data, Object... args) {
        Optional.ofNullable(target).ifPresent(nonNullTarget -> {
            throw newEWithData(data, args);
        });
    }

    // assert true

    default void assertTrue(Boolean target, Object... args) {
        if (!Boolean.TRUE.equals(target)) {
            throw newE(args);
        }
    }

    default void assertTrueWithData(Boolean target, Object data, Object... args) {
        if (!Boolean.TRUE.equals(target)) {
            throw newEWithData(data, args);
        }
    }

    // assert false

    default void assertFalse(Boolean target, Object... args) {
        if (!Boolean.FALSE.equals(target)) {
            throw newE(args);
        }
    }

    default void assertFalseWithData(Boolean target, Object data, Object... args) {
        if (!Boolean.FALSE.equals(target)) {
            throw newEWithData(data, args);
        }
    }

    // assert equal

    default void assertEqual(Object source, Object target, Object... args) {
        if (ObjectUtil.notEqual(source, target)) {
            throw newE(args);
        }
    }

    default void assertEqualWithData(Object source, Object target, Object data, Object... args) {
        if (ObjectUtil.notEqual(source, target)) {
            throw newEWithData(data, args);
        }
    }

    // assert not equal

    default void assertNotEqual(Object source, Object target, Object... args) {
        if (ObjectUtil.equal(source, target)) {
            throw newE(args);
        }
    }

    default void assertNotEqualWithData(Object source, Object target, Object data, Object... args) {
        if (ObjectUtil.equal(source, target)) {
            throw newEWithData(data, args);
        }
    }

    // assert not blank

    default void assertNotBlank(Object target, Object... args) {
        if (isBlank(target)) {
            throw newE(args);
        }
    }

    default void assertNotBlankWithData(Object target, Object data, Object... args) {
        if (isBlank(target)) {
            throw newEWithData(data, args);
        }
    }

    default <T extends CharSequence> T assertNotBlankAndReturn(Object target, Object... args) {
        if (isBlank(target)) {
            throw newE(args);
        }
        return (T) target;
    }

    default <T extends CharSequence> T assertNotBlankWithDataAndReturn(Object target, Object data, Object... args) {
        if (isBlank(target)) {
            throw newEWithData(data, args);
        }
        return (T) target;
    }

    default <T extends CharSequence> T assertNotBlankWithDataSupplierAndReturn(
        Object target, Supplier<Object> dataSupplier, Object... args) {
        if (isBlank(target)) {
            throw newEWithData(dataSupplier.get(), args);
        }
        return (T) target;
    }

    // assert blank

    default void assertBlank(Object target, Object... args) {
        if (!isBlank(target)) {
            throw newE(args);
        }
    }

    default void assertBlankWithData(Object target, Object data, Object... args) {
        if (!isBlank(target)) {
            throw newEWithData(data, args);
        }
    }

    default <T extends CharSequence> T assertBlankAndReturn(Object target, Object... args) {
        if (!isBlank(target)) {
            throw newE(args);
        }
        return (T) target;
    }

    default <T extends CharSequence> T assertBlankWithDataAndReturn(Object target, Object data, Object... args) {
        if (!isBlank(target)) {
            throw newEWithData(data, args);
        }
        return (T) target;
    }

    default <T extends CharSequence> T assertBlankWithDataSupplierAndReturn(
        Object target, Supplier<Object> dataSupplier, Object... args) {
        if (!isBlank(target)) {
            throw newEWithData(dataSupplier.get(), args);
        }
        return (T) target;
    }

    // assert matches

    default void assertMatches(CharSequence target, Pattern pattern, Object... args) {
        if (!pattern.matcher(target).matches()) {
            throw newE(args);
        }
    }

    default void assertMatchesWithData(CharSequence target, Pattern pattern, Object data, Object... args) {
        if (!pattern.matcher(target).matches()) {
            throw newEWithData(data, args);
        }
    }

    default <T extends CharSequence> T assertMatchesAndReturn(CharSequence target, Pattern pattern, Object... args) {
        if (!pattern.matcher(target).matches()) {
            throw newE(args);
        }
        return (T) target;
    }

    default <T extends CharSequence> T assertMatchesWithDataAndReturn(
        CharSequence target, Pattern pattern, Object data, Object... args) {
        if (!pattern.matcher(target).matches()) {
            throw newEWithData(data, args);
        }
        return (T) target;
    }

    default <T extends CharSequence> T assertMatchesWithDataSupplierAndReturn(
        CharSequence target, Pattern pattern, Supplier<Object> dataSupplier, Object... args) {
        if (!pattern.matcher(target).matches()) {
            throw newEWithData(dataSupplier.get(), args);
        }
        return (T) target;
    }

    // assert not empty

    default void assertNotEmpty(Iterable<?> target, Object... args) {
        if (CollectionUtil.isEmpty(target)) {
            throw newE(args);
        }
    }

    default void assertNotEmptyWithData(Iterable<?> target, Object data, Object... args) {
        if (CollectionUtil.isEmpty(target)) {
            throw newEWithData(data, args);
        }
    }

    default <T extends Iterable<?>> T assertNotEmptyAndReturn(Iterable<?> target, Object... args) {
        if (CollectionUtil.isEmpty(target)) {
            throw newE(args);
        }
        return (T) target;
    }

    default <T extends Iterable<?>> T assertNotEmptyWithDataAndReturn(Iterable<?> target, Object data, Object... args) {
        if (CollectionUtil.isEmpty(target)) {
            throw newEWithData(data, args);
        }
        return (T) target;
    }

    default <T extends Iterable<?>> T assertNotEmptyWithDataSupplierAndReturn(Iterable<?> target, Supplier<Object> dataSupplier, Object... args) {
        if (CollectionUtil.isEmpty(target)) {
            throw newEWithData(dataSupplier.get(), args);
        }
        return (T) target;
    }

    // assert empty

    default void assertEmpty(Iterable<?> target, Object... args) {
        if (CollectionUtil.isNotEmpty(target)) {
            throw newE(args);
        }
    }

    default void assertEmptyWithData(Iterable<?> target, Object data, Object... args) {
        if (CollectionUtil.isNotEmpty(target)) {
            throw newEWithData(data, args);
        }
    }

    // assert not empty

    default void assertNotEmpty(Object[] target, Object... args) {
        if (ArrayUtil.isEmpty(target)) {
            throw newE(args);
        }
    }

    default void assertNotEmptyWithData(Object[] target, Object data, Object... args) {
        if (ArrayUtil.isEmpty(target)) {
            throw newEWithData(data, args);
        }
    }

    default <T> T[] assertNotEmptyAndReturn(Object[] target, Object... args) {
        if (ArrayUtil.isEmpty(target)) {
            throw newE(args);
        }
        return (T[]) target;
    }

    default <T> T[] assertNotEmptyWithDataAndReturn(Object[] target, Object data, Object... args) {
        if (ArrayUtil.isEmpty(target)) {
            throw newEWithData(data, args);
        }
        return (T[]) target;
    }

    default <T> T[] assertNotEmptyWithDataSupplierAndReturn(Object[] target, Supplier<Object> dataSupplier, Object... args) {
        if (ArrayUtil.isEmpty(target)) {
            throw newEWithData(dataSupplier.get(), args);
        }
        return (T[]) target;
    }

    // return or wrapper exception

    default <T> T returnOrWrapperE(Supplier<T> supplier, Object... args) {
        try {
            return supplier.get();
        } catch (Exception e) {
            throw newEWithCause(e, args);
        }
    }

    default <T> T returnOrWrapperEWithData(Supplier<T> supplier, Object data, Object... args) {
        try {
            return supplier.get();
        } catch (Exception e) {
            throw newEWithCauseAndData(e, data, args);
        }
    }

    default boolean isBlank(Object target) {
        return target == null || target instanceof CharSequence && StrUtil.isBlank((CharSequence) target);
   }
}
