package ink.domytattoo

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Log
import android.widget.ImageView

/**
 * Created by knieb on 12/11/2017.
 */
class ImageHelper(imageView : ImageView) : AsyncTask<String, Void, Bitmap>() {

    var bmImage = imageView

    override fun doInBackground(vararg p0: String?): Bitmap {
        var url = p0[0]
        var mIcon : Bitmap? = null
        try {
            var inputStream = java.net.URL(url).openStream()
            mIcon = BitmapFactory.decodeStream(inputStream)
        } catch (e : Exception) {
            Log.e("Error", e.message)
            e.printStackTrace()
        }
        return mIcon as Bitmap
    }

    override fun onPostExecute(result: Bitmap?) {
        bmImage.setImageBitmap(result)
    }
}