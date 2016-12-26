package com.progressoft.corpay.rules.validation;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RulesValidatorTest {
    private Payment payment;
    private PaymentValidator paymentValidator;
    private Calendar testedDate;

    @Before
    public void setUp() throws Exception {
        testedDate = Calendar.getInstance();
        payment = new Payment("xyz", BigDecimal.valueOf(100)
                , "JOD", new Date(), new Account("123456", new Rule("NO_RULE"))
                , "999999");
        paymentValidator = new PaymentValidator();
    }

    @After
    public void tearDown() throws Exception {
        payment = null;
    }

    @Test
    public void givenPaymentWithAccountHasNoRulesTypeAndDateIsFuture_thenReturnFalse() throws Exception {
        testedDate.add(Calendar.DAY_OF_MONTH, +1);
        payment.setSubmissionDate(testedDate.getTime());
        payment.getFromAccount().setRule(new Rule("NO_RULE"));
        assertFalse(paymentValidator.validate(payment));
    }
    @Test
    public void givenPaymentWithAccountHasSameDayTypeAndDateIsFuture_thenReturnTrue() throws Exception {
        payment.getFromAccount().setRule(new Rule("SAME_DAY"));
        assertTrue(paymentValidator.validate(payment));
    }

    @Test
    public void givenPaymentWithAccountHasSameMonthAndDifferntDayTypeAndDateIsFuture_thenReturnTrue() throws Exception {
        payment.getFromAccount().setRule(new Rule("SAME_MONTH"));
        assertTrue(paymentValidator.validate(payment));
    }

    @Test
    public void givenPaymentWithAccountHasSameYearAndDifferntMonthAndDifferntDayTypeAndDateIsFuture_thenReturnTrue() throws Exception {
        payment.getFromAccount().setRule(new Rule("SAME_YEAR"));
        assertTrue(paymentValidator.validate(payment));
    }

    @Test(expected = NullRuleException.class)
    public void givenAccountWithNullRules() throws Exception {
        payment.getFromAccount().setRule(null);
        paymentValidator.validate(payment);
    }

    @Test(expected = NullAccountException.class)
    public void givenPaymentWithAccountNull() throws Exception {
        payment.setFromAccount(null);
        paymentValidator.validate(payment);
    }

    @Test(expected = PaymentNullException.class)
    public void whenValidatePaymentIsNullThenThrowsException() throws Exception {
        paymentValidator.validate(null);
    }

    @Test
    public void whenValidatePaymentWithOldDateThenReturnsFalse() throws Exception {
        prevDay(Calendar.DAY_OF_MONTH, -1);
        payment.setSubmissionDate(testedDate.getTime());
        assertFalse(paymentValidator.validate(payment));
    }

    private void prevDay(int dayOfMonth, int amount) {
        testedDate.add(dayOfMonth, amount);
    }

    @Test
    public void passingPaymentWithDateHasDifferentPrevYearShouldReturnFalse() throws Exception {
        testedDate.add(Calendar.YEAR, -1);
        payment.setSubmissionDate(testedDate.getTime());
        assertFalse(paymentValidator.validate(payment));
    }

    @Test
    public void passingPaymentWithDateHasDifferentMonthShouldReturnFalse() throws Exception {
        testedDate.add(Calendar.MONTH, -1);
        payment.setSubmissionDate(testedDate.getTime());
        assertFalse(paymentValidator.validate(payment));
    }

    @Test
    public void passingPaymentWithCurrentDayDateLagTowSeconds_ShouldReturnTrue() throws Exception {
        payment.setSubmissionDate(new Date());
        Thread.sleep(1000);
        Assert.assertTrue(paymentValidator.validate(payment));
    }

    @Test
    public void passingPaymentWithDateHasNextYear_2_ShouldReturnFalse() throws Exception {
        testedDate.add(Calendar.YEAR, 1);
        testedDate.add(Calendar.MONTH, 0);
        payment.setSubmissionDate(testedDate.getTime());
        assertFalse(paymentValidator.validate(payment));
    }

    @Test
    public void initialTest() {
        Assert.assertNotNull(payment);
    }
}
