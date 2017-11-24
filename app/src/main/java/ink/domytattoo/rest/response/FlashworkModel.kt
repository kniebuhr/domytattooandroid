package ink.domytattoo.rest.response

import java.io.Serializable

/**
 * Created by knieb on 11/11/2017.
 */

object FlashworkModel{

    data class Flashwork(
        var _id : String,
        var tattooArtist : Artist,
        var style : Style,
        var price : Float,
        var isAuction : Boolean,
        var expireDate : String?,
        var allBids : List<Bid>,
        var currentBid : Bid,
        var lastBid : Bid,
        var images : List<Image>
    ) : Serializable
    data class Artist (
        var _id : String,
        var email : String,
        var name : String,
        var instagram : String,
        var phone : Phone
    ) : Serializable
    data class Style (
        var _id: String,
        var name : String
    ) : Serializable
    data class Phone(
        var number : String,
        var whatsapp : Boolean
    ) : Serializable

    data class Bid (
        var user : String,
        var price : Float
    ) : Serializable
    data class Image(
        var _id: String,
        var url : String
    ) : Serializable
}