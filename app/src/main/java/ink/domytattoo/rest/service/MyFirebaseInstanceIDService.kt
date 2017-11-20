package ink.domytattoo.rest.service

import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService

/**
 * Created by victorcatao on 20/11/2017.
 */
class MyFirebaseInstanceIDService: FirebaseInstanceIdService() {

    companion object {
        const val TAG = "FIREBASEDMT"
    }

    override fun onTokenRefresh() {
        var refreshedToken = FirebaseInstanceId.getInstance().token
        Log.e(TAG, "Refreshed token:"+refreshedToken)

    }
}