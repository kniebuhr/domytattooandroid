package ink.domytattoo.activity

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import ink.domytattoo.Constants
import ink.domytattoo.R
import ink.domytattoo.rest.request.SignInBody
import ink.domytattoo.rest.response.AccountModel
import ink.domytattoo.rest.service.AccountService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {

    val accountService by lazy {
        AccountService.create()
    }
    var disposable: Disposable? = null

    var progress: ProgressDialog? = null

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var login = login_username
        var password = login_password

        login_signup.setOnClickListener {
            startActivity(Intent(applicationContext, SignUpActivity::class.java))
        }

        login_button.setOnClickListener{
            if(validate(login.text.toString(), password.text.toString())){

                var pattern = Pattern.compile("/^[^\\s@]+@[^\\s@]+\\.[^\\s@]+\$/")
                var matcher = pattern.matcher(login.text.toString())

                progress = ProgressDialog(this)
                progress!!.setTitle("Carregando")
                progress!!.setMessage("Aguarde...")
                progress!!.setCancelable(false) // disable dismiss by tapping outside of the dialog
                progress!!.show()

                if(matcher.matches()){
                    disposable =
                        accountService.signin(SignInBody(login.text.toString(), "", password.text.toString()))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                { result -> onResult(result) },
                                { error -> Toast.makeText(baseContext, error.localizedMessage, Toast.LENGTH_SHORT).show() }
                            )
                } else {
                    disposable =
                        accountService.signin(SignInBody("", login.text.toString(), password.text.toString()))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                { result -> onResult(result)
                                },
                                { error ->  onError(error.localizedMessage) }
                            )
                }

            } else {
                Toast.makeText(baseContext, "Preencha os campos de login", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun onResult(result : AccountModel.SignIn){
        progress!!.hide()
        var sharedPref = this.getPreferences(MODE_PRIVATE)
        var editor = sharedPref.edit()
        editor.putString(Constants().EXTRA_USERNAME, result.userName)
        editor.putBoolean(Constants().EXTRA_IS_CUSTOMER, result.isCustomer)
        editor.putString(Constants().EXTRA_USER_ID  , result._id)
        editor.commit()

        startActivity(Intent(applicationContext, FragmentActivity::class.java))
    }

    fun onError(message: String){
        progress!!.hide()
        Toast.makeText(baseContext, message, Toast.LENGTH_SHORT).show()
    }

    fun validate(stringLogin : String, stringPass : String): Boolean{
        if(!stringLogin.isNullOrBlank() && !stringPass.isNullOrBlank()){
            return true
        }
        return false
    }



}
