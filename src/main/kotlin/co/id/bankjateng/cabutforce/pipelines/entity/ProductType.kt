package co.id.bankjateng.cabutforce.pipelines.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id

/**
 * @author Samuel Mareno
 * @Date 27/12/22
 */

@Entity(name = "product_type")
data class ProductType(
    @Id
    val id: Int,
    val name: String
)
