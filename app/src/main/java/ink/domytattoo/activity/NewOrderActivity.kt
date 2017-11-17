package ink.domytattoo.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import ink.domytattoo.R
import ink.domytattoo.rest.service.OrderService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_new_quote.*
import kotlinx.android.synthetic.main.toolbar.*
import android.content.Intent
import android.app.Activity
import android.net.Uri
import java.io.FileNotFoundException
import android.provider.MediaStore.MediaColumns
import ink.domytattoo.Constants
import ink.domytattoo.rest.response.OrderModel
import ink.domytattoo.rest.response.SearchModel
import okhttp3.MediaType
import java.io.File
import okhttp3.RequestBody
import okhttp3.MultipartBody
import android.os.Build
import android.content.pm.PackageManager


class NewOrderActivity : AppCompatActivity() {

    val orderService by lazy {
        OrderService.create()
    }
    var disposable: Disposable? = null

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }

    var imageOnePath : String? = null
    var imageTwoPath : String? = null
    var imageThreePath : String? = null
    var artist : SearchModel.Artist? = null

    private val REQUEST_WRITE_PERMISSION = 786

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_quote)
        setSupportActionBar(toolbar)
        title = "Novo orçamento"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        getExtras()
        requestPermission()

        photo_one.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, 1)
        }

        photo_two.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, 2)
        }

        photo_three.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, 3)
        }

        order_button.setOnClickListener {
            var list = ArrayList<MultipartBody.Part>()
            var image : MultipartBody.Part? = null
            val name = RequestBody.create(MediaType.parse("text/plain"), "photos")

            if(imageOnePath != null){
                val file = File(imageOnePath)
                val reqFile = RequestBody.create(MediaType.parse("image/*"), file)
//                image = MultipartBody.Part.createFormData("photos", file.getName(), reqFile)
                list.add(MultipartBody.Part.createFormData("photos", file.getName(), reqFile))
            }
            if(imageTwoPath != null){
                val file = File(imageTwoPath)
                val reqFile = RequestBody.create(MediaType.parse("image/*"), file)
//                image = MultipartBody.Part.createFormData("photos", file.getName(), reqFile)
                list.add(MultipartBody.Part.createFormData("photos", file.getName(), reqFile))
            }
            if(imageThreePath != null){
                val file = File(imageThreePath)
                val reqFile = RequestBody.create(MediaType.parse("image/*"), file)
//                image = MultipartBody.Part.createFormData("photos", file.getName(), reqFile)
                list.add(MultipartBody.Part.createFormData("photos", file.getName(), reqFile))
            }



            val customerId = RequestBody.create(MediaType.parse("multipart/form-data"), "59d16e0f7b79910004d4666d")
            val place = RequestBody.create(MediaType.parse("multipart/form-data"),place.text.toString())
            val description = RequestBody.create(MediaType.parse("multipart/form-data"), description.text.toString())
            val artist = RequestBody.create(MediaType.parse("multipart/form-data"), artist!!._id)

            disposable =
                orderService.createNewOrder(if (list.isNotEmpty()) list else null,
                        customerId,
                        height.text.toString().toFloat(),
                        width.text.toString().toFloat(),
                        description,
                        place,
                        artist)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { result -> onResult()
                                },
                                { error -> Toast.makeText(baseContext, "Erro ao enviar orçamento", Toast.LENGTH_SHORT).show() }
                        )

        }
    }

    fun onResult(){
        finish()
    }

    fun getExtras(){
        artist = intent.extras.getSerializable(Constants().EXTRA_ARTIST) as SearchModel.Artist
    }

//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
//        if (requestCode == REQUEST_WRITE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            openFilePicker()//do your job
//        }
//    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when(requestCode){
            1,2,3 -> {
                if (resultCode === Activity.RESULT_OK) {
                    val selectedImage = data!!.getData()

                    val filePath = getPath(selectedImage)
                    val file_extn = filePath.substring(filePath.lastIndexOf(".") + 1)

                    try {
                        if (file_extn == "img" || file_extn == "jpg" || file_extn == "jpeg" || file_extn == "gif" || file_extn == "png") {
                            if(requestCode == 1){
                                imageOnePath = filePath
                                photo_one.setImageURI(selectedImage)
                            } else if(requestCode == 2){
                                imageTwoPath = filePath
                                photo_two.setImageURI(selectedImage)
                            } else {
                                imageThreePath = filePath
                                photo_three.setImageURI(selectedImage)
                            }

                        } else {
                            Toast.makeText(this, "Formato não suportado", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()
                    }

                }
            } else -> {

            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun getPath(uri: Uri): String {
        val projection = arrayOf(MediaColumns.DATA)
        val cursor = managedQuery(uri, projection, null, null, null)
        cursor.moveToFirst()
        return cursor.getString(cursor.getColumnIndexOrThrow(MediaColumns.DATA))
    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            requestPermissions(arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_WRITE_PERMISSION)
    }
}
