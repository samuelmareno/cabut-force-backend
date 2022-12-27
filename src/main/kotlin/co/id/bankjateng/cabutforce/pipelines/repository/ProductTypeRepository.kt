package co.id.bankjateng.cabutforce.pipelines.repository

import co.id.bankjateng.cabutforce.pipelines.entity.ProductType
import org.springframework.data.jpa.repository.JpaRepository

/**
 * @author Samuel Mareno
 * @Date 27/12/22
 */
interface ProductTypeRepository : JpaRepository<ProductType, Int> {
}