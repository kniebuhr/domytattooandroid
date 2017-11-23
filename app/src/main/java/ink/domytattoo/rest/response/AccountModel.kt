package ink.domytattoo.rest.response

/**
 * Created by knieb on 11/11/2017.
 */

object AccountModel {
    data class SignIn(
        var _id : String,
        var isCustomer : Boolean,
        var name : String,
        var userName : String,
        var email : String,
        var phone : Phone
    )

    data class Phone(
        var number: String,
        var whatsapp : Boolean
    )

    data class Images(
        var images : List<String>
    )
}