package co.id.bankjateng.cabutforce.validation

import jakarta.validation.ConstraintViolationException
import jakarta.validation.Validator
import org.springframework.stereotype.Component

/**
 * @author Samuel Mareno
 * @Date 06/12/22
 */

@Component
class ValidationUtil(val validator: Validator) {

    fun validate(any: Any) {
        val result = validator.validate(any)

        if (result.size != 0) {
            throw ConstraintViolationException(result)
        }
    }
}