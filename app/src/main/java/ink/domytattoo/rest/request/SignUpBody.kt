package ink.domytattoo.rest.request

/**
 * Created by knieb on 17/11/2017.
 */
data class SignUpBody (
    var isCustomer : Boolean,
    var name : String,
    var userName : String,
    var password : String,
    var email : String,
    var phone : Phone,
    var bio : String?,
    var country : String?,
    var state : String?,
    var city : String?
)

data class Phone (
    var number : String?,
    var whatsapp : Boolean?
)