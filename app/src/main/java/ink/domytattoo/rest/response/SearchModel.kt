package ink.domytattoo.rest.response

import java.io.Serializable

/**
 * Created by knieb on 11/11/2017.
 */

object SearchModel {
    data class Flashwork(
        var _id : String,
        var tattooArtist : Artist,
        var style : Style,
        var price : Float,
        var isAuction : Boolean,
        var expireDate : String,
        var __v: Int,
        var images : List<Image>
    ) : Serializable
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
    ) : Serializable
    data class Phone (
        var number: String,
        var whatsapp: Boolean
    ) : Serializable
    data class Style (
        var _id : String,
        var name : String,
        var __v : Int
    ) : Serializable
    data class Image (
        var url : String,
        var _id : String
    ) : Serializable
}