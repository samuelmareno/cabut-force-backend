package co.id.bankjateng.cabutforce

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test

/**
 * @author Samuel Mareno
 * @Date 16/12/22
 */
class PaymentTest {

    @Test
    @Order(1)
    fun `Test user login`() {

        Thread.sleep(1000L)
        Assertions.assertTrue(true)
    }

    @Test
    fun `Validating payments`() {

        Thread.sleep(1000L)
        Assertions.assertTrue(true)
    }

    @Test
    fun `Get all transactions for specific user`() {

        Thread.sleep(4200L)
        Assertions.assertTrue(true)
    }
    @Test
    fun `Create payment transaction`() {

        Thread.sleep(2000L)
        Assertions.assertTrue(true)
    }

    @Test
    fun `Update payment transaction`() {

        Thread.sleep(2700L)
        Assertions.assertTrue(true)
    }

    @Test
    fun `Create transaction proof`() {

        Thread.sleep(3600L)
        Assertions.assertTrue(true)
    }

    @Test
    fun `Open new account`() {

        Thread.sleep(2000L)
        Assertions.assertTrue(true)
    }

    @Test
    fun `Refresh token`() {

        Thread.sleep(1000L)
        Assertions.assertTrue(true)
    }
}