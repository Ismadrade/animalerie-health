package br.com.ismadrade.authuser.validations;

import br.com.ismadrade.authuser.validations.impl.UsernameConstraintImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UsernameConstraintImpl.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UsernameConstraint {
    String message() default "Username Inválido!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
