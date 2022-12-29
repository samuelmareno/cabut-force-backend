package co.id.bankjateng.cabutforce.users.controller

import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author Samuel Mareno
 * @Date 29/12/22
 */
@RestController
@CrossOrigin
class HelloController {
    @GetMapping("/", "")
    fun hello(): String {
        return "Cabut Force by Bank Jateng"
    }
}