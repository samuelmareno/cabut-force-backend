package co.id.bankjateng.cabutforce.configurations

import co.id.bankjateng.cabutforce.interceptors.HttpInterceptor
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/**
 * @author Samuel Mareno
 * @Date 12/12/22
 */

//@Configuration  // cross origin issue
class WebMvcConfig(private val httpInterceptor: HttpInterceptor) : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(httpInterceptor).addPathPatterns("/**")
        super.addInterceptors(registry)
    }
}