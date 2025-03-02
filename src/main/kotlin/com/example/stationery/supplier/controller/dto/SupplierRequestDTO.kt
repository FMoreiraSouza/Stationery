import com.example.stationery.product.entity.ProductEntity
import com.example.stationery.supplier.entity.SupplierEntity
import jakarta.validation.constraints.NotBlank

data class SupplierRequestDTO(
    @NotBlank(message = "O nome do fornecedor não pode estar em branco")
    val name: String,

    @NotBlank(message = "O fornecedor deve ter uma forma de contato")
    val contact: String
){
    fun toSupplier() = SupplierEntity(
        name = name,
        contact = contact
    )
}
