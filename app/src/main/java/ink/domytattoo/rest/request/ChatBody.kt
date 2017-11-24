package ink.domytattoo.rest.request

/**
 * Created by knieb on 15/11/2017.
 */
data class ChatBody(
    var orderId : String,
    var negotiationId : String,
    var senderId : String,
    var message : String
)