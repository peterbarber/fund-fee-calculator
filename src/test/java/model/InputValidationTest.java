package model;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.BeforeClass;
import org.junit.Test;

public class InputValidationTest {

	private static Validator validator;
	private InputValidation iv = new InputValidation(new BigDecimal(100000), new BigDecimal(1000), 40, new BigDecimal(2.0), new BigDecimal(8.0));

	@BeforeClass
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void testNotNull() {
		InputValidation iv = new InputValidation(null, null, null, null, null);
		Set<ConstraintViolation<InputValidation>> cv = validator.validate(iv);

		assertEquals(5, cv.size());
		assertEquals("may not be null", cv.iterator().next().getMessage());
		assertEquals("may not be null", cv.iterator().next().getMessage());
		assertEquals("may not be null", cv.iterator().next().getMessage());
		assertEquals("may not be null", cv.iterator().next().getMessage());
		assertEquals("may not be null", cv.iterator().next().getMessage());
	}

	@Test
	public void notNegative() {
		InputValidation iv = new InputValidation(new BigDecimal(-100), new BigDecimal(-2), -1, new BigDecimal(-12), new BigDecimal(-8));
		Set<ConstraintViolation<InputValidation>> cv = validator.validate(iv);

		assertEquals(5, cv.size());
		assertEquals("must be greater than or equal to 0", cv.iterator().next().getMessage());
		assertEquals("must be greater than or equal to 0", cv.iterator().next().getMessage());
		assertEquals("must be greater than or equal to 0", cv.iterator().next().getMessage());
		assertEquals("must be greater than or equal to 0", cv.iterator().next().getMessage());
		assertEquals("must be greater than or equal to 0", cv.iterator().next().getMessage());
	}

	@Test
	public void initialAmountGreaterThan() {
		iv.setInitialAmount(new BigDecimal(1000000001));
		Set<ConstraintViolation<InputValidation>> cv = validator.validate(iv);
		assertEquals(1, cv.size());
		assertEquals("must be less than or equal to 1000000000", cv.iterator().next().getMessage());
	}

	@Test
	public void contributionGreaterThan() {
		iv.setContributionAmount(new BigDecimal(1000000001));
		Set<ConstraintViolation<InputValidation>> cv = validator.validate(iv);
		assertEquals(1, cv.size());
		assertEquals("must be less than or equal to 1000000000", cv.iterator().next().getMessage());
	}

	@Test
	public void ageGreaterThan() {
		iv.setYears(101);
		Set<ConstraintViolation<InputValidation>> cv = validator.validate(iv);
		assertEquals(1, cv.size());
		assertEquals("must be less than or equal to 100", cv.iterator().next().getMessage());
	}
	
	@Test
	public void feesGreaterThan() {
		iv.setFees(new BigDecimal(10.1));
		Set<ConstraintViolation<InputValidation>> cv = validator.validate(iv);
		assertEquals(1, cv.size());
		assertEquals("must be less than or equal to 10", cv.iterator().next().getMessage());
	}
	
	@Test
	public void yieldGreaterThan() {
		iv.setYield(new BigDecimal(31));
		Set<ConstraintViolation<InputValidation>> cv = validator.validate(iv);
		assertEquals(1, cv.size());
		assertEquals("must be less than or equal to 30", cv.iterator().next().getMessage());
	}
	
	
	@Test
	public void valid() {
		Set<ConstraintViolation<InputValidation>> cv = validator.validate(iv);
		assertEquals(0, cv.size());
	}

}
