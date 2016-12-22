package com.progressoft.corpay.rules.validation;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by u550 on 12/21/2016.
 */
public class RulesValidatorTest {
    private Payment payment;
    private PaymentValidator paymentValidator;
    private Calendar instance;

    @Before
    public void setUp() throws Exception {
        instance = Calendar.getInstance();
        payment = new Payment("xyz", BigDecimal.valueOf(100)
                , "JOD", new Date(), "001120"
                , "999999");
        paymentValidator = new PaymentValidator();
    }

    @After
    public void tearDown() throws Exception {
        payment = null;
    }

    @Test(expected = PaymentNullException.class)
    public void whenValidatePaymentIsNullThenThrowsException() throws Exception {
        paymentValidator.validate(null);
    }

    @Test
    public void whenValidatePaymentWithOldDateThenReturnsFalse() throws Exception {
        prevDay(Calendar.DAY_OF_MONTH, -1);
        payment.setSubmissionDate(instance.getTime());
        Assert.assertFalse(paymentValidator.validate(payment));
    }

    private void prevDay(int dayOfMonth, int amount) {
        instance.add(dayOfMonth, amount);
    }

    @Test
    public void passingPaymentWithDateHasDifferentPrevYearShouldReturnFalse() throws Exception {
        instance.add(Calendar.YEAR, -1);
        payment.setSubmissionDate(instance.getTime());
        Assert.assertFalse(paymentValidator.validate(payment));
    }

    @Test
    public void passingPaymentWithDateHasDifferentMonthShouldReturnFalse() throws Exception {
        instance.add(Calendar.MONTH, -1);
        payment.setSubmissionDate(instance.getTime());
        Assert.assertFalse(paymentValidator.validate(payment));
    }

    @Test
    public void passingPaymentWithCurrentDayDateLagTowSeconds_ShouldReturnTrue() throws Exception{
        payment.setSubmissionDate(new Date());
        Thread.sleep(1000);
        Assert.assertTrue(paymentValidator.validate(payment));
    }

    @Test
    public void passingPaymentWithDateHasNextYear_2_ShouldReturnFalse() throws Exception {
        instance.add(Calendar.YEAR, 1);
        instance.add(Calendar.MONTH, 0);
        payment.setSubmissionDate(instance.getTime());
        Assert.assertFalse(paymentValidator.validate(payment));
    }

    @Test
    public void initialTest() {
        Assert.assertNotNull(payment);
    }
}
