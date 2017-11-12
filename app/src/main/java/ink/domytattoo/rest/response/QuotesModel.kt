package ink.domytattoo.rest.response

/**
 * Created by knieb on 11/11/2017.
 */

object QuotesModel {
    data class Flashwork(
        var _id : String,
        var tattooArtist : Artist,
        var style : Style,
        var price : Float,
        var isAuction : Boolean,
        var expireDate : String,
        var __v: Int,
        var images : List<Image>
    )
    data class Artist(
        var _id : String,
        var isCustomer : Boolean,
        var name : String,
        var userName : String,
        var password : String,
        var email : String,
        var instagram :  String,
        var phone : Phone,
        var isCostumer : Boolean,
        var __v : Int,
        var city : String,
        var state : String,
        var country : String,
        var bio : String
    )
    data class Phone (
        var number: String,
        var whatsapp: Boolean
    )
    data class Style (
        var _id : String,
        var name : String,
        var __v : Int
    )
    data class Image (
        var url : String,
        var _id : String
    )
}