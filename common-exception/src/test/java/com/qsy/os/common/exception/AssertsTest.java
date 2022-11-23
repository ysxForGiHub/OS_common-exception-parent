package com.qsy.os.common.exception;

import cn.hutool.core.util.RandomUtil;
import com.qsy.os.common.exception.fixture.BaseExceptionFixture;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AssertsTest {

    @Spy
    private IAsserts asserts;

    @Nested
    class NewExceptionReturnSupplierTest {

        @Test
        void should_new_exception_supplier_successfully() {
            final Object arg = RandomUtil.randomString(5);
            final BaseException baseException = BaseExceptionFixture.given();

            doReturn(baseException).when(asserts).newE(arg);

            Supplier<BaseException> exceptionSupplier = asserts.newESupplier(arg);

            assertThat(exceptionSupplier).isNotNull();
            assertThat(exceptionSupplier.get()).isEqualTo(baseException);
        }

        @Test
        void should_new_exception_with_cause_supplier_successfully() {
            final Throwable cause = new RuntimeException();
            final Object arg = RandomUtil.randomString(5);
            final BaseException baseException = BaseExceptionFixture.given();

            doReturn(baseException).when(asserts).newEWithCause(cause, arg);

            Supplier<BaseException> exceptionSupplier = asserts.newEWithCauseSupplier(cause, arg);

            assertThat(exceptionSupplier).isNotNull();
            assertThat(exceptionSupplier.get()).isEqualTo(baseException);
        }

        @Test
        void should_new_exception_with_data_supplier_successfully() {
            final Object data = new Object();
            final Object arg = RandomUtil.randomString(5);
            final BaseException baseException = BaseExceptionFixture.given();

            doReturn(baseException).when(asserts).newEWithData(data, arg);

            Supplier<BaseException> exceptionSupplier = asserts.newEWithDataSupplier(data, arg);

            assertThat(exceptionSupplier).isNotNull();
            assertThat(exceptionSupplier.get()).isEqualTo(baseException);
        }

        @Test
        void should_new_exception_with_cause_and_data_supplier_successfully() {
            final Throwable cause = new RuntimeException();
            final Object data = new Object();
            final Object arg = RandomUtil.randomString(5);
            final BaseException baseException = BaseExceptionFixture.given();

            doReturn(baseException).when(asserts).newEWithCauseAndData(cause, data, arg);

            Supplier<BaseException> exceptionSupplier = asserts.newEWithCauseAndDataSupplier(cause, data, arg);

            assertThat(exceptionSupplier).isNotNull();
            assertThat(exceptionSupplier.get()).isEqualTo(baseException);
        }
    }

    @Nested
    class AssertNotNullTest {

        @Test
        void should_throw_exception_given_null_target() {
            final Object arg = RandomUtil.randomString(5);
            final BaseException baseException = BaseExceptionFixture.given();

            doReturn(baseException).when(asserts).newE(arg);

            BaseException returnedException = assertThrows(BaseException.class,
                () -> asserts.assertNotNull(null, arg));

            assertThat(returnedException).isEqualTo(baseException);
        }

        @Test
        void should_not_throw_exception_given_non_null_target() {
            asserts.assertNotNull(new Object());
            verify(asserts, never()).newE(any());
        }

        @Test
        void should_throw_exception_given_null_target_with_data() {
            final Object data = new Object();
            final Object arg = RandomUtil.randomString(5);
            final BaseException baseException = BaseExceptionFixture.given();

            doReturn(baseException).when(asserts).newEWithData(data, arg);

            BaseException returnedException = assertThrows(BaseException.class,
                () -> asserts.assertNotNullWithData(null, data, arg));

            assertThat(returnedException).isEqualTo(baseException);
        }

        @Test
        void should_not_throw_exception_given_non_null_target_with_data() {
            asserts.assertNotNullWithData(new Object(), new Object());
            verify(asserts, never()).newEWithData(any());
        }

        @Test
        void should_throw_exception_given_null_target_not_return() {
            final Object arg = RandomUtil.randomString(5);
            final BaseException baseException = BaseExceptionFixture.given();

            doReturn(baseException).when(asserts).newE(arg);

            BaseException returnedException = assertThrows(BaseException.class,
                () -> asserts.assertNotNullAndReturn(null, arg));

            assertThat(returnedException).isEqualTo(baseException);
        }

        @Test
        void should_return_data_given_non_null_target() {
            Object target = new Object();
            Object returnedTarget = asserts.assertNotNullAndReturn(target);
            assertThat(returnedTarget).isEqualTo(target);
            verify(asserts, never()).newEWithData(any());
        }

        @Test
        void should_throw_exception_given_null_target_with_data_not_return() {
            final Object data = new Object();
            final Object arg = RandomUtil.randomString(5);
            final BaseException baseException = BaseExceptionFixture.given();

            doReturn(baseException).when(asserts).newEWithData(data, arg);

            BaseException returnedException = assertThrows(BaseException.class,
                () -> asserts.assertNotNullWithDataAndReturn(null, data, arg));

            assertThat(returnedException).isEqualTo(baseException);
        }

        @Test
        void should_return_data_given_non_null_target_and_data() {
            Object target = new Object();
            Object returnedTarget = asserts.assertNotNullWithDataAndReturn(target, null);
            assertThat(returnedTarget).isEqualTo(target);
            verify(asserts, never()).newEWithData(any());
        }

        @Test
        void should_throw_exception_given_null_target_with_data_supplier_not_return() {
            final Object data = new Object();
            final Supplier<Object> dataSupplier = () -> data;
            final Object arg = RandomUtil.randomString(5);
            final BaseException baseException = BaseExceptionFixture.given();

            doReturn(baseException).when(asserts).newEWithData(data, arg);

            BaseException returnedException = assertThrows(BaseException.class,
                () -> asserts.assertNotNullWithDataSupplierAndReturn(null, dataSupplier, arg));

            assertThat(returnedException).isEqualTo(baseException);
        }

        @Test
        void should_return_data_given_non_null_target_and_data_supplier() {
            final Supplier<Object> dataSupplier = Object::new;
            Object target = new Object();
            Object returnedTarget = asserts.assertNotNullWithDataSupplierAndReturn(target, dataSupplier);
            assertThat(returnedTarget).isEqualTo(target);
            verify(asserts, never()).newEWithData(any());
        }
    }

    @Nested
    class AssertNullTest {

        @Test
        void should_throw_exception_given_non_null_target() {
            final Object arg = RandomUtil.randomString(5);
            final BaseException baseException = BaseExceptionFixture.given();

            doReturn(baseException).when(asserts).newE(arg);

            BaseException returnedException = assertThrows(BaseException.class,
                () -> asserts.assertNull(new Object(), arg));

            assertThat(returnedException).isEqualTo(baseException);
        }

        @Test
        void should_not_throw_exception_given_null_target() {
            asserts.assertNull(null);
            verify(asserts, never()).newE(any());
        }

        @Test
        void should_throw_exception_given_non_null_target_with_data() {
            final Object data = new Object();
            final Object arg = RandomUtil.randomString(5);
            final BaseException baseException = BaseExceptionFixture.given();

            doReturn(baseException).when(asserts).newEWithData(data, arg);

            BaseException returnedException = assertThrows(BaseException.class,
                () -> asserts.assertNullWithData(new Object(), data, arg));

            assertThat(returnedException).isEqualTo(baseException);
        }

        @Test
        void should_not_throw_exception_given_null_target_with_data() {
            asserts.assertNullWithData(null, null);
            verify(asserts, never()).newEWithData(any());
        }
    }

    @Nested
    class AssertTrueTest {

        @Test
        void should_throw_exception_given_target_not_true() {
            final Object arg = RandomUtil.randomString(5);
            final BaseException baseException = BaseExceptionFixture.given();

            doReturn(baseException).when(asserts).newE(arg);

            BaseException returnedException = assertThrows(BaseException.class,
                () -> asserts.assertTrue(false, arg));

            assertThat(returnedException).isEqualTo(baseException);

            returnedException = assertThrows(BaseException.class,
                () -> asserts.assertTrue(null, arg));

            assertThat(returnedException).isEqualTo(baseException);
        }

        @Test
        void should_not_throw_exception_given_target_true() {
            asserts.assertTrue(true);
            verify(asserts, never()).newE(any());
        }

        @Test
        void should_throw_exception_given_target_not_true_with_data() {
            final Object data = new Object();
            final Object arg = RandomUtil.randomString(5);
            final BaseException baseException = BaseExceptionFixture.given();

            doReturn(baseException).when(asserts).newEWithData(data, arg);

            BaseException returnedException = assertThrows(BaseException.class,
                () -> asserts.assertTrueWithData(false, data, arg));

            assertThat(returnedException).isEqualTo(baseException);

            returnedException = assertThrows(BaseException.class,
                () -> asserts.assertTrueWithData(null, data, arg));

            assertThat(returnedException).isEqualTo(baseException);
        }

        @Test
        void should_not_throw_exception_given_target_true_with_data() {
            asserts.assertTrueWithData(true, null);
            verify(asserts, never()).newEWithData(any(), any());
        }
    }

    @Nested
    class AssertFalseTest {

        @Test
        void should_throw_exception_given_target_not_false() {
            final Object arg = RandomUtil.randomString(5);
            final BaseException baseException = BaseExceptionFixture.given();

            doReturn(baseException).when(asserts).newE(arg);

            BaseException returnedException = assertThrows(BaseException.class,
                () -> asserts.assertFalse(true, arg));

            assertThat(returnedException).isEqualTo(baseException);

            returnedException = assertThrows(BaseException.class,
                () -> asserts.assertFalse(null, arg));

            assertThat(returnedException).isEqualTo(baseException);
        }

        @Test
        void should_not_throw_exception_given_target_false() {
            asserts.assertFalse(false);
            verify(asserts, never()).newE(any());
        }

        @Test
        void should_throw_exception_given_target_not_false_with_data() {
            final Object data = new Object();
            final Object arg = RandomUtil.randomString(5);
            final BaseException baseException = BaseExceptionFixture.given();

            doReturn(baseException).when(asserts).newEWithData(data, arg);

            BaseException returnedException = assertThrows(BaseException.class,
                () -> asserts.assertFalseWithData(true, data, arg));

            assertThat(returnedException).isEqualTo(baseException);

            returnedException = assertThrows(BaseException.class,
                () -> asserts.assertFalseWithData(null, data, arg));

            assertThat(returnedException).isEqualTo(baseException);
        }

        @Test
        void should_not_throw_exception_given_target_false_with_data() {
            asserts.assertFalseWithData(false, null);
            verify(asserts, never()).newEWithData(any(), any());
        }
    }

    @Nested
    class AssertEqualTest {

        @Test
        void should_throw_exception_given_target_not_equal_to_source() {
            final Object source = new Object();
            final Object target = new Object();
            final Object arg = RandomUtil.randomString(5);
            final BaseException baseException = BaseExceptionFixture.given();

            doReturn(baseException).when(asserts).newE(arg);

            BaseException returnedException = assertThrows(BaseException.class,
                () -> asserts.assertEqual(source, target, arg));

            assertThat(returnedException).isEqualTo(baseException);
        }

        @Test
        void should_not_throw_exception_given_target_equal_to_source() {
            Object source = new Object();
            asserts.assertEqual(source, source);
            verify(asserts, never()).newE(any());
        }

        @Test
        void should_throw_exception_given_target_not_equal_to_source_with_data() {
            final Object source = new Object();
            final Object target = new Object();
            final Object data = new Object();
            final Object arg = RandomUtil.randomString(5);
            final BaseException baseException = BaseExceptionFixture.given();

            doReturn(baseException).when(asserts).newEWithData(data, arg);

            BaseException returnedException = assertThrows(BaseException.class,
                () -> asserts.assertEqualWithData(source, target, data, arg));

            assertThat(returnedException).isEqualTo(baseException);
        }

        @Test
        void should_not_throw_exception_given_target_equal_to_source_with_data() {
            Object source = new Object();
            asserts.assertEqualWithData(source, source, null);
            verify(asserts, never()).newEWithData(any(), any());
        }
    }

    @Nested
    class AssertNotEqualTest {

        @Test
        void should_throw_exception_given_target_equal_to_source() {
            final Object source = new Object();
            final Object arg = RandomUtil.randomString(5);
            final BaseException baseException = BaseExceptionFixture.given();

            doReturn(baseException).when(asserts).newE(arg);

            BaseException returnedException = assertThrows(BaseException.class,
                () -> asserts.assertNotEqual(source, source, arg));

            assertThat(returnedException).isEqualTo(baseException);
        }

        @Test
        void should_not_throw_exception_given_target_not_equal_to_source() {
            Object source = new Object();
            Object target = new Object();
            asserts.assertNotEqual(source, target);
            verify(asserts, never()).newE(any());
        }

        @Test
        void should_throw_exception_given_target_equal_to_source_with_data() {
            final Object source = new Object();
            final Object data = new Object();
            final Object arg = RandomUtil.randomString(5);
            final BaseException baseException = BaseExceptionFixture.given();

            doReturn(baseException).when(asserts).newEWithData(data, arg);

            BaseException returnedException = assertThrows(BaseException.class,
                () -> asserts.assertNotEqualWithData(source, source, data, arg));

            assertThat(returnedException).isEqualTo(baseException);
        }

        @Test
        void should_not_throw_exception_given_target_not_equal_to_source_with_data() {
            Object source = new Object();
            Object target = new Object();
            asserts.assertNotEqualWithData(source, target, null);
            verify(asserts, never()).newEWithData(any(), any());
        }
    }

    @Nested
    class AssertNotBlankTest {

        @Test
        void should_throw_exception_given_blank_target() {
            final Object arg = RandomUtil.randomString(5);
            final BaseException baseException = BaseExceptionFixture.given();

            doReturn(baseException).when(asserts).newE(arg);

            BaseException returnedException = assertThrows(BaseException.class,
                () -> asserts.assertNotBlank(null, arg));

            assertThat(returnedException).isEqualTo(baseException);

            returnedException = assertThrows(BaseException.class,
                () -> asserts.assertNotBlank("   ", arg));

            assertThat(returnedException).isEqualTo(baseException);
        }

        @Test
        void should_not_throw_exception_given_non_blank_target() {
            asserts.assertNotBlank(new Object());
            asserts.assertNotBlank("A");
            verify(asserts, never()).newE(any());
        }

        @Test
        void should_throw_exception_given_blank_target_with_data() {
            final Object data = new Object();
            final Object arg = RandomUtil.randomString(5);
            final BaseException baseException = BaseExceptionFixture.given();

            doReturn(baseException).when(asserts).newEWithData(data, arg);

            BaseException returnedException = assertThrows(BaseException.class,
                () -> asserts.assertNotBlankWithData(null, data, arg));

            assertThat(returnedException).isEqualTo(baseException);

            returnedException = assertThrows(BaseException.class,
                () -> asserts.assertNotBlankWithData("", data, arg));

            assertThat(returnedException).isEqualTo(baseException);
        }

        @Test
        void should_not_throw_exception_given_non_blank_target_with_data() {
            asserts.assertNotBlankWithData(new Object(), null);
            asserts.assertNotBlankWithData("A", null);
            verify(asserts, never()).newEWithData(any());
        }

        @Test
        void should_throw_exception_given_blank_target_not_return() {
            final Object arg = RandomUtil.randomString(5);
            final BaseException baseException = BaseExceptionFixture.given();

            doReturn(baseException).when(asserts).newE(arg);

            BaseException returnedException = assertThrows(BaseException.class,
                () -> asserts.assertNotBlankAndReturn(null, arg));

            assertThat(returnedException).isEqualTo(baseException);

            returnedException = assertThrows(BaseException.class,
                () -> asserts.assertNotBlankAndReturn("   ", arg));

            assertThat(returnedException).isEqualTo(baseException);
        }

        @Test
        void should_throw_exception_given_not_char_sequence_target_not_return() {
            final Object arg = RandomUtil.randomString(5);

            assertThrows(ClassCastException.class,
                () -> asserts.assertNotBlankAndReturn(new Object(), arg));
        }

        @Test
        void should_return_data_given_non_blank_target() {
            String target = RandomUtil.randomString(5);
            Object returnedTarget = asserts.assertNotBlankAndReturn(target);
            assertThat(returnedTarget).isEqualTo(target);
            verify(asserts, never()).newEWithData(any());
        }

        @Test
        void should_throw_exception_given_blank_target_with_data_not_return() {
            final Object data = new Object();
            final Object arg = RandomUtil.randomString(5);
            final BaseException baseException = BaseExceptionFixture.given();

            doReturn(baseException).when(asserts).newEWithData(data, arg);

            BaseException returnedException = assertThrows(BaseException.class,
                () -> asserts.assertNotBlankWithDataAndReturn(null, data, arg));

            assertThat(returnedException).isEqualTo(baseException);

            returnedException = assertThrows(BaseException.class,
                () -> asserts.assertNotBlankWithDataAndReturn("   ", data, arg));

            assertThat(returnedException).isEqualTo(baseException);
        }

        @Test
        void should_throw_exception_given_not_char_sequence_target_with_data_not_return() {
            assertThrows(ClassCastException.class,
                () -> asserts.assertNotBlankWithDataAndReturn(new Object(), null));
        }

        @Test
        void should_return_data_given_non_blank_target_and_data() {
            String target = RandomUtil.randomString(5);
            Object returnedTarget = asserts.assertNotBlankWithDataAndReturn(target, null);
            assertThat(returnedTarget).isEqualTo(target);
            verify(asserts, never()).newEWithData(any());
        }

        @Test
        void should_throw_exception_given_blank_target_with_data_supplier_not_return() {
            final Object data = new Object();
            final Supplier<Object> dataSupplier = () -> data;
            final Object arg = RandomUtil.randomString(5);
            final BaseException baseException = BaseExceptionFixture.given();

            doReturn(baseException).when(asserts).newEWithData(data, arg);

            BaseException returnedException = assertThrows(BaseException.class,
                () -> asserts.assertNotBlankWithDataSupplierAndReturn(null, dataSupplier, arg));

            assertThat(returnedException).isEqualTo(baseException);

            returnedException = assertThrows(BaseException.class,
                () -> asserts.assertNotBlankWithDataSupplierAndReturn("  ", dataSupplier, arg));

            assertThat(returnedException).isEqualTo(baseException);
        }

        @Test
        void should_throw_exception_given_not_char_sequence_target_with_data_supplier_not_return() {
            assertThrows(ClassCastException.class,
                () -> asserts.assertNotBlankWithDataSupplierAndReturn(new Object(), null));
        }

        @Test
        void should_return_data_given_non_blank_target_and_data_supplier() {
            final Supplier<Object> dataSupplier = Object::new;
            String target = RandomUtil.randomString(5);
            Object returnedTarget = asserts.assertNotBlankWithDataSupplierAndReturn(target, dataSupplier);
            assertThat(returnedTarget).isEqualTo(target);
            verify(asserts, never()).newEWithData(any());
        }
    }

    @Nested
    class AssertBlankTest {

        @Test
        void should_throw_exception_given_not_blank_target() {
            final String target = RandomUtil.randomString(16);
            final Object arg = RandomUtil.randomString(5);
            final BaseException baseException = BaseExceptionFixture.given();

            doReturn(baseException).when(asserts).newE(arg);

            BaseException returnedException = assertThrows(BaseException.class,
                () -> asserts.assertBlank(target, arg));

            assertThat(returnedException).isEqualTo(baseException);

            returnedException = assertThrows(BaseException.class,
                () -> asserts.assertBlank(RandomUtil.randomInt(), arg));

            assertThat(returnedException).isEqualTo(baseException);
        }

        @Test
        void should_not_throw_exception_given_blank_target() {
            asserts.assertBlank(null);
            asserts.assertBlank("   ");
            verify(asserts, never()).newE(any());
        }

        @Test
        void should_throw_exception_given_not_blank_target_with_data() {
            final String target = RandomUtil.randomString(16);
            final Object data = new Object();
            final Object arg = RandomUtil.randomString(5);
            final BaseException baseException = BaseExceptionFixture.given();

            doReturn(baseException).when(asserts).newEWithData(data, arg);

            BaseException returnedException = assertThrows(BaseException.class,
                () -> asserts.assertBlankWithData(target, data, arg));

            assertThat(returnedException).isEqualTo(baseException);

            returnedException = assertThrows(BaseException.class,
                () -> asserts.assertBlankWithData(RandomUtil.randomInt(), data, arg));

            assertThat(returnedException).isEqualTo(baseException);
        }

        @Test
        void should_not_throw_exception_given_blank_target_with_data() {
            asserts.assertBlankWithData(null, null);
            asserts.assertBlankWithData("   ", null);
            verify(asserts, never()).newEWithData(any());
        }

        @Test
        void should_throw_exception_given_not_blank_target_not_return() {
            final String target = RandomUtil.randomString(16);
            final Object arg = RandomUtil.randomString(5);
            final BaseException baseException = BaseExceptionFixture.given();

            doReturn(baseException).when(asserts).newE(arg);

            BaseException returnedException = assertThrows(BaseException.class,
                () -> asserts.assertBlankAndReturn(target, arg));

            assertThat(returnedException).isEqualTo(baseException);

            returnedException = assertThrows(BaseException.class,
                () -> asserts.assertBlankAndReturn(RandomUtil.randomInt(), arg));

            assertThat(returnedException).isEqualTo(baseException);
        }

        @Test
        void should_return_data_given_blank_target() {
            String target = "   ";
            Object returnedTarget = asserts.assertBlankAndReturn(target);
            assertThat(returnedTarget).isEqualTo(target);
            verify(asserts, never()).newEWithData(any());
        }

        @Test
        void should_throw_exception_given_not_blank_target_with_data_not_return() {
            final String target = RandomUtil.randomString(16);
            final Object data = new Object();
            final Object arg = RandomUtil.randomString(5);
            final BaseException baseException = BaseExceptionFixture.given();

            doReturn(baseException).when(asserts).newEWithData(data, arg);

            BaseException returnedException = assertThrows(BaseException.class,
                () -> asserts.assertBlankWithDataAndReturn(target, data, arg));

            assertThat(returnedException).isEqualTo(baseException);

            returnedException = assertThrows(BaseException.class,
                () -> asserts.assertBlankWithDataAndReturn(RandomUtil.randomInt(), data, arg));

            assertThat(returnedException).isEqualTo(baseException);
        }

        @Test
        void should_return_data_given_blank_target_and_data() {
            String target = "   ";
            Object returnedTarget = asserts.assertBlankWithDataAndReturn(target, null);
            assertThat(returnedTarget).isEqualTo(target);
            verify(asserts, never()).newEWithData(any());
        }

        @Test
        void should_throw_exception_given_not_blank_target_with_data_supplier_not_return() {
            final String target = RandomUtil.randomString(15);
            final Object data = new Object();
            final Supplier<Object> dataSupplier = () -> data;
            final Object arg = RandomUtil.randomString(5);
            final BaseException baseException = BaseExceptionFixture.given();

            doReturn(baseException).when(asserts).newEWithData(data, arg);

            BaseException returnedException = assertThrows(BaseException.class,
                () -> asserts.assertBlankWithDataSupplierAndReturn(target, dataSupplier, arg));

            assertThat(returnedException).isEqualTo(baseException);

            returnedException = assertThrows(BaseException.class,
                () -> asserts.assertBlankWithDataSupplierAndReturn(RandomUtil.randomInt(), dataSupplier, arg));

            assertThat(returnedException).isEqualTo(baseException);
        }

        @Test
        void should_return_data_given_blank_target_and_data_supplier() {
            final Supplier<Object> dataSupplier = Object::new;
            String target = "   ";
            Object returnedTarget = asserts.assertBlankWithDataSupplierAndReturn(target, dataSupplier);
            assertThat(returnedTarget).isEqualTo(target);
            verify(asserts, never()).newEWithData(any());
        }
    }

    @Nested
    class AssertMatchesTest {

        private final Pattern PATTERN = Pattern.compile("\\d+");
        private final CharSequence MATCHED = String.valueOf(RandomUtil.randomInt(0, 10000));
        private final CharSequence NOT_MATCHED = "A";

        @Test
        void should_throw_exception_given_not_matches() {
            final Object arg = RandomUtil.randomString(5);
            final BaseException baseException = BaseExceptionFixture.given();

            doReturn(baseException).when(asserts).newE(arg);

            BaseException returnedException = assertThrows(BaseException.class,
                () -> asserts.assertMatches(NOT_MATCHED, PATTERN, arg));

            assertThat(returnedException).isEqualTo(baseException);
        }

        @Test
        void should_not_throw_exception_given_matched() {
            asserts.assertMatches(MATCHED, PATTERN);
            verify(asserts, never()).newE(any());
        }

        @Test
        void should_throw_exception_given_not_matches_with_data() {
            final Object data = new Object();
            final Object arg = RandomUtil.randomString(5);
            final BaseException baseException = BaseExceptionFixture.given();

            doReturn(baseException).when(asserts).newEWithData(data, arg);

            BaseException returnedException = assertThrows(BaseException.class,
                () -> asserts.assertMatchesWithData(NOT_MATCHED, PATTERN, data, arg));

            assertThat(returnedException).isEqualTo(baseException);
        }

        @Test
        void should_not_throw_exception_given_matches_with_data() {
            asserts.assertMatchesWithData(MATCHED, PATTERN, null);
            verify(asserts, never()).newEWithData(any());
        }

        @Test
        void should_throw_exception_given_not_matches_not_return() {
            final Object arg = RandomUtil.randomString(5);
            final BaseException baseException = BaseExceptionFixture.given();

            doReturn(baseException).when(asserts).newE(arg);

            BaseException returnedException = assertThrows(BaseException.class,
                () -> asserts.assertMatchesAndReturn(NOT_MATCHED, PATTERN, arg));

            assertThat(returnedException).isEqualTo(baseException);
        }

        @Test
        void should_return_data_given_matches() {
            Object returnedTarget = asserts.assertMatchesAndReturn(MATCHED, PATTERN);
            assertThat(returnedTarget).isEqualTo(MATCHED);
            verify(asserts, never()).newEWithData(any());
        }

        @Test
        void should_throw_exception_given_not_matches_with_data_not_return() {
            final Object data = new Object();
            final Object arg = RandomUtil.randomString(5);
            final BaseException baseException = BaseExceptionFixture.given();

            doReturn(baseException).when(asserts).newEWithData(data, arg);

            BaseException returnedException = assertThrows(BaseException.class,
                () -> asserts.assertMatchesWithDataAndReturn(NOT_MATCHED, PATTERN, data, arg));

            assertThat(returnedException).isEqualTo(baseException);
        }

        @Test
        void should_return_data_given_matches_and_data() {
            Object returnedTarget = asserts.assertMatchesWithDataAndReturn(MATCHED, PATTERN, null);
            assertThat(returnedTarget).isEqualTo(MATCHED);
            verify(asserts, never()).newEWithData(any());
        }

        @Test
        void should_throw_exception_given_not_matches_with_data_supplier_not_return() {
            final Object data = new Object();
            final Supplier<Object> dataSupplier = () -> data;
            final Object arg = RandomUtil.randomString(5);
            final BaseException baseException = BaseExceptionFixture.given();

            doReturn(baseException).when(asserts).newEWithData(data, arg);

            BaseException returnedException = assertThrows(BaseException.class,
                () -> asserts.assertMatchesWithDataSupplierAndReturn(NOT_MATCHED, PATTERN, dataSupplier, arg));

            assertThat(returnedException).isEqualTo(baseException);
        }

        @Test
        void should_return_data_given_matches_and_data_supplier() {
            final Supplier<Object> dataSupplier = Object::new;
            Object returnedTarget = asserts.assertMatchesWithDataSupplierAndReturn(MATCHED, PATTERN, dataSupplier);
            assertThat(returnedTarget).isEqualTo(MATCHED);
            verify(asserts, never()).newEWithData(any());
        }
    }

    @Nested
    class AssertNotEmptyTest {

        @Test
        void should_throw_exception_given_empty_target() {
            final Object arg = RandomUtil.randomString(5);
            final BaseException baseException = BaseExceptionFixture.given();

            doReturn(baseException).when(asserts).newE(arg);

            BaseException returnedException = assertThrows(BaseException.class,
                () -> asserts.assertNotEmpty((Iterable<?>) null, arg));

            assertThat(returnedException).isEqualTo(baseException);

            returnedException = assertThrows(BaseException.class,
                () -> asserts.assertNotEmpty(Collections.emptyList(), arg));

            assertThat(returnedException).isEqualTo(baseException);
        }

        @Test
        void should_not_throw_exception_given_non_empty_target() {
            asserts.assertNotEmpty(Collections.singleton(new Object()));
            verify(asserts, never()).newE(any());
        }

        @Test
        void should_throw_exception_given_empty_target_with_data() {
            final Object data = new Object();
            final Object arg = RandomUtil.randomString(5);
            final BaseException baseException = BaseExceptionFixture.given();

            doReturn(baseException).when(asserts).newEWithData(data, arg);

            BaseException returnedException = assertThrows(BaseException.class,
                () -> asserts.assertNotEmptyWithData((Iterable<?>) null, data, arg));

            assertThat(returnedException).isEqualTo(baseException);

            returnedException = assertThrows(BaseException.class,
                () -> asserts.assertNotEmptyWithData(Collections.emptyList(), data, arg));

            assertThat(returnedException).isEqualTo(baseException);
        }

        @Test
        void should_not_throw_exception_given_non_empty_target_with_data() {
            asserts.assertNotEmptyWithData(Collections.singleton(new Object()), new Object());
            verify(asserts, never()).newEWithData(any());
        }

        @Test
        void should_throw_exception_given_empty_target_not_return() {
            final Object arg = RandomUtil.randomString(5);
            final BaseException baseException = BaseExceptionFixture.given();

            doReturn(baseException).when(asserts).newE(arg);

            BaseException returnedException = assertThrows(BaseException.class,
                () -> asserts.assertNotEmptyAndReturn((Iterable<?>) null, arg));

            assertThat(returnedException).isEqualTo(baseException);

            returnedException = assertThrows(BaseException.class,
                () -> asserts.assertNotEmptyAndReturn(Collections.emptyList(), arg));

            assertThat(returnedException).isEqualTo(baseException);
        }

        @Test
        void should_return_data_given_non_empty_target() {
            List<Object> target = Collections.singletonList(new Object());
            Object returnedTarget = asserts.assertNotEmptyAndReturn(target);
            assertThat(returnedTarget).isEqualTo(target);
            verify(asserts, never()).newEWithData(any());
        }

        @Test
        void should_throw_exception_given_empty_target_with_data_not_return() {
            final Object data = new Object();
            final Object arg = RandomUtil.randomString(5);
            final BaseException baseException = BaseExceptionFixture.given();

            doReturn(baseException).when(asserts).newEWithData(data, arg);

            BaseException returnedException = assertThrows(BaseException.class,
                () -> asserts.assertNotEmptyWithDataAndReturn((Iterable<?>) null, data, arg));

            assertThat(returnedException).isEqualTo(baseException);

            returnedException = assertThrows(BaseException.class,
                () -> asserts.assertNotEmptyWithDataAndReturn(Collections.emptyList(), data, arg));

            assertThat(returnedException).isEqualTo(baseException);
        }

        @Test
        void should_return_data_given_non_empty_target_and_data() {
            List<Object> target = Collections.singletonList(new Object());
            Object returnedTarget = asserts.assertNotEmptyWithDataAndReturn(target, null);
            assertThat(returnedTarget).isEqualTo(target);
            verify(asserts, never()).newEWithData(any());
        }

        @Test
        void should_throw_exception_given_empty_target_with_data_supplier_not_return() {
            final Object data = new Object();
            final Supplier<Object> dataSupplier = () -> data;
            final Object arg = RandomUtil.randomString(5);
            final BaseException baseException = BaseExceptionFixture.given();

            doReturn(baseException).when(asserts).newEWithData(data, arg);

            BaseException returnedException = assertThrows(BaseException.class,
                () -> asserts.assertNotEmptyWithDataSupplierAndReturn((Iterable<?>) null, dataSupplier, arg));

            assertThat(returnedException).isEqualTo(baseException);

            returnedException = assertThrows(BaseException.class,
                () -> asserts.assertNotEmptyWithDataSupplierAndReturn(Collections.emptyList(), dataSupplier, arg));

            assertThat(returnedException).isEqualTo(baseException);
        }

        @Test
        void should_return_data_given_non_empty_target_and_data_supplier() {
            final Supplier<Object> dataSupplier = Object::new;
            List<Object> target = Collections.singletonList(new Object());
            Object returnedTarget = asserts.assertNotEmptyWithDataSupplierAndReturn(target, dataSupplier);
            assertThat(returnedTarget).isEqualTo(target);
            verify(asserts, never()).newEWithData(any());
        }
    }

    @Nested
    class AssertEmptyTest {

        private final Iterable<Object> NON_EMPTY = Collections.singletonList(new Object());
        private final Iterable<Object> EMPTY = Collections.emptyList();

        @Test
        void should_throw_exception_given_non_empty_target() {
            final Object arg = RandomUtil.randomString(5);
            final BaseException baseException = BaseExceptionFixture.given();

            doReturn(baseException).when(asserts).newE(arg);

            BaseException returnedException = assertThrows(BaseException.class,
                () -> asserts.assertEmpty(NON_EMPTY, arg));

            assertThat(returnedException).isEqualTo(baseException);
        }

        @Test
        void should_not_throw_exception_given_empty_target() {
            asserts.assertEmpty(EMPTY);
            verify(asserts, never()).newE(any());
        }

        @Test
        void should_throw_exception_given_non_empty_target_with_data() {
            final Object data = new Object();
            final Object arg = RandomUtil.randomString(5);
            final BaseException baseException = BaseExceptionFixture.given();

            doReturn(baseException).when(asserts).newEWithData(data, arg);

            BaseException returnedException = assertThrows(BaseException.class,
                () -> asserts.assertEmptyWithData(NON_EMPTY, data, arg));

            assertThat(returnedException).isEqualTo(baseException);
        }

        @Test
        void should_not_throw_exception_given_empty_target_with_data() {
            asserts.assertEmptyWithData(EMPTY, null);
            verify(asserts, never()).newEWithData(any());
        }
    }

    @Nested
    class AssertNotEmptyArrayTest {

        private final Object[] EMPTY = new Object[]{};
        private final Object[] NON_EMPTY = new Object[]{new Object()};

        @Test
        void should_throw_exception_given_empty_target() {
            final Object arg = RandomUtil.randomString(5);
            final BaseException baseException = BaseExceptionFixture.given();

            doReturn(baseException).when(asserts).newE(arg);

            BaseException returnedException = assertThrows(BaseException.class,
                () -> asserts.assertNotEmpty(EMPTY, arg));

            assertThat(returnedException).isEqualTo(baseException);
        }

        @Test
        void should_not_throw_exception_given_non_empty_target() {
            asserts.assertNotEmpty(NON_EMPTY);
            verify(asserts, never()).newE(any());
        }

        @Test
        void should_throw_exception_given_empty_target_with_data() {
            final Object data = new Object();
            final Object arg = RandomUtil.randomString(5);
            final BaseException baseException = BaseExceptionFixture.given();

            doReturn(baseException).when(asserts).newEWithData(data, arg);

            BaseException returnedException = assertThrows(BaseException.class,
                () -> asserts.assertNotEmptyWithData(EMPTY, data, arg));

            assertThat(returnedException).isEqualTo(baseException);
        }

        @Test
        void should_not_throw_exception_given_non_empty_target_with_data() {
            asserts.assertNotEmptyWithData(NON_EMPTY, new Object());
            verify(asserts, never()).newEWithData(any());
        }

        @Test
        void should_throw_exception_given_empty_target_not_return() {
            final Object arg = RandomUtil.randomString(5);
            final BaseException baseException = BaseExceptionFixture.given();

            doReturn(baseException).when(asserts).newE(arg);

            BaseException returnedException = assertThrows(BaseException.class,
                () -> asserts.assertNotEmptyAndReturn(EMPTY, arg));

            assertThat(returnedException).isEqualTo(baseException);
        }

        @Test
        void should_return_data_given_non_empty_target() {
            Object returnedTarget = asserts.assertNotEmptyAndReturn(NON_EMPTY);
            assertThat(returnedTarget).isEqualTo(NON_EMPTY);
            verify(asserts, never()).newEWithData(any());
        }

        @Test
        void should_throw_exception_given_empty_target_with_data_not_return() {
            final Object data = new Object();
            final Object arg = RandomUtil.randomString(5);
            final BaseException baseException = BaseExceptionFixture.given();

            doReturn(baseException).when(asserts).newEWithData(data, arg);

            BaseException returnedException = assertThrows(BaseException.class,
                () -> asserts.assertNotEmptyWithDataAndReturn(EMPTY, data, arg));

            assertThat(returnedException).isEqualTo(baseException);
        }

        @Test
        void should_return_data_given_non_empty_target_and_data() {
            Object returnedTarget = asserts.assertNotEmptyWithDataAndReturn(NON_EMPTY, null);
            assertThat(returnedTarget).isEqualTo(NON_EMPTY);
            verify(asserts, never()).newEWithData(any());
        }

        @Test
        void should_throw_exception_given_empty_target_with_data_supplier_not_return() {
            final Object data = new Object();
            final Supplier<Object> dataSupplier = () -> data;
            final Object arg = RandomUtil.randomString(5);
            final BaseException baseException = BaseExceptionFixture.given();

            doReturn(baseException).when(asserts).newEWithData(data, arg);

            BaseException returnedException = assertThrows(BaseException.class,
                () -> asserts.assertNotEmptyWithDataSupplierAndReturn(EMPTY, dataSupplier, arg));

            assertThat(returnedException).isEqualTo(baseException);

            returnedException = assertThrows(BaseException.class,
                () -> asserts.assertNotEmptyWithDataSupplierAndReturn(Collections.emptyList(), dataSupplier, arg));

            assertThat(returnedException).isEqualTo(baseException);
        }

        @Test
        void should_return_data_given_non_empty_target_and_data_supplier() {
            final Supplier<Object> dataSupplier = Object::new;
            Object returnedTarget = asserts.assertNotEmptyWithDataSupplierAndReturn(NON_EMPTY, dataSupplier);
            assertThat(returnedTarget).isEqualTo(NON_EMPTY);
            verify(asserts, never()).newEWithData(any());
        }
    }

    @Nested
    class ReturnOrWrapperExceptionTest {

        @Test
        void should_throw_exception_given_supplier_throw_exception() {
            RuntimeException exception = new RuntimeException();
            Supplier<Object> supplier = () -> {
                throw exception;
            };
            final Object arg = RandomUtil.randomString(5);
            final BaseException baseException = BaseExceptionFixture.given();

            doReturn(baseException).when(asserts).newEWithCause(exception, arg);

            BaseException returnedException = assertThrows(BaseException.class,
                () -> asserts.returnOrWrapperE(supplier, arg));

            assertThat(returnedException).isEqualTo(baseException);
        }

        @Test
        void should_return_given_supplier_return_successfully() {
            Object suppliedData = new Object();
            Supplier<Object> supplier = () -> suppliedData;
            final Object arg = RandomUtil.randomString(5);

            Object returnedData = asserts.returnOrWrapperE(supplier, arg);

            assertThat(returnedData).isEqualTo(suppliedData);
        }

        @Test
        void should_throw_exception_given_supplier_throw_exception_with_data() {
            RuntimeException exception = new RuntimeException();
            Supplier<Object> supplier = () -> {
                throw exception;
            };
            Object data = new Object();
            final Object arg = RandomUtil.randomString(5);
            final BaseException baseException = BaseExceptionFixture.given();

            doReturn(baseException).when(asserts).newEWithCauseAndData(exception, data, arg);

            BaseException returnedException = assertThrows(BaseException.class,
                () -> asserts.returnOrWrapperEWithData(supplier, data, arg));

            assertThat(returnedException).isEqualTo(baseException);
        }

        @Test
        void should_return_given_supplier_return_successfully_with_data() {
            Object suppliedData = new Object();
            Supplier<Object> supplier = () -> suppliedData;
            Object data = new Object();
            final Object arg = RandomUtil.randomString(5);

            Object returnedData = asserts.returnOrWrapperEWithData(supplier, data, arg);

            assertThat(returnedData).isEqualTo(suppliedData);
        }
    }
}
