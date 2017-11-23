package ink.domytattoo.rest.response

import java.io.Serializable

/**
 * Created by knieb on 11/11/2017.
 */

object OrderModel {
    data class Quote(
        var _id : String,
        var customer : Customer,
        var place : String,
        var description : String,
        var images : List<Image>,
        var height : Float,
        var width : Float,
        var negotiations : List<Negotiation>
    ) : Serializable

    data class Customer (
        var _id: String,
        var name : String
    ) : Serializable

    data class Image (
        var url: String,
        var _id: String
    ) : Serializable

    data class Negotiation (
        var tattooArtist : Artist,
        var _id: String,
        var price : Float?,
        var chat : List<ChatMessage>,
        var status : Status
    ) : Serializable

    data class Artist(
        var _id: String,
        var name: String
    ) : Serializable

    data class ChatMessage (
        var _id: String,
        var message : String,
        var sender : Sender?,
        var read : Boolean
    ) : Serializable

    data class Sender(
        var _id: String,
        var name: String
    ) : Serializable

    data class Status(
        var closed : Boolean,
        var canceledBySystem : Boolean,
        var canceledByCustomer : Boolean,
        var canceledByTattooArtist : Boolean,
        var viewed : Boolean
    ) : Serializable

    data class NegotiationRequest (
        var tattooArtist: String
    ) : Serializable
}