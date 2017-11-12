package ink.domytattoo.rest.response

/**
 * Created by knieb on 11/11/2017.
 */

object Model {
    data class SearchArtistResponse (

            var _id : String,
            var isCustomer : Boolean,
            var name : String,
            var userName : String,
            var password : String,
            var email : String,
            var instagram :  String,
            var phone : Phone
    )
}

data class Phone (
    var number: String,
    var whatsapp: Boolean
)