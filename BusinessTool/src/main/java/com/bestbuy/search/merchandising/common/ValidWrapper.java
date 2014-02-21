
package com.bestbuy.search.merchandising.common;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.NotNull;

	/**
	 * Custom annotation for validation endDate greater than startDate
	 * @author Chanchal.Kumari
	 */

	@Documented
	@Constraint(validatedBy = { CustomValidator.class })
	@Target({ METHOD, TYPE, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
	@Retention(RUNTIME)
	@ReportAsSingleViolation
	@NotNull
	public @interface ValidWrapper{
		String message() default "";
		Class<?>[] groups() default {};
		Class<? extends Payload>[] payload() default {};

	}
	
	