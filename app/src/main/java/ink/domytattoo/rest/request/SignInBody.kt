package ink.domytattoo.rest.request

/**
 * Created by knieb on 15/11/2017.
 */
data class SignInBody (
    var email : String?,
    var username : String?,
    var password : String
)