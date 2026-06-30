package com.tsTech.practice.IMS_v2.common.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.OverridesAttribute;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {})
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)

///This all {field.<property>} is configured in resources/ValidationMessages.properties file,
///and in GlobalExceptionHandler class, it's been used, to populate error message with the field name, on which annotation is applied.
@NotBlank(message = "{field.required}")
//and as override attributes are defined, so no need to pass hard coded values here -> so removed max= 50 from here.
@Size(message = "{field.size}")
@Pattern(regexp = "^[a-zA-Z0-9 _]+$", message = "{field.alphanumeric}")
public @interface EntityStringValidation {
    String message() default "Invalid string input";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    //Start Jun 25, 2026 TaukirS (ER 1003 - validation and generalize response and error coding)
    /// if requires custom min and max contraint value, then it can be provided within annotation calling itself, just like message.
    ///here in constraint, have to define classname, that for which attribute, its used for.
    @OverridesAttribute(constraint = Size.class, name = "min")
    int min() default 0;

    @OverridesAttribute(constraint = Size.class, name = "max")
    int max() default 50;
    //End Jun 25, 2026 TaukirS (ER 1003 - validation and generalize response and error coding)
}
