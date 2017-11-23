package ink.domytattoo.activity

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import ink.domytattoo.Constants
import ink.domytattoo.R
import ink.domytattoo.SharedPreferencesHelper
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

        val login = login_username
        val password = login_password

        login_signup.setOnClickListener {
            startActivity(Intent(applicationContext, SignUpActivity::class.java))
        }

        login_button.setOnClickListener{
            if(validate(login.text.toString(), password.text.toString())){

                val pattern = Pattern.compile("/^[^\\s@]+@[^\\s@]+\\.[^\\s@]+\$/")
                val matcher = pattern.matcher(login.text.toString())

                progress = ProgressDialog(this)
                progress!!.setTitle("Carregando")
                progress!!.setMessage("Aguarde...")
                progress!!.setCancelable(false)
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
        progress!!.dismiss()

        SharedPreferencesHelper.initializeInstance(this)

        if(result.isCustomer){
            SharedPreferencesHelper.getInstance().putString(Constants().EXTRA_USER_ID, result._id)
            SharedPreferencesHelper.getInstance().putString(Constants().EXTRA_USERNAME, result.userName)
            SharedPreferencesHelper.getInstance().putBoolean(Constants().EXTRA_IS_CUSTOMER, result.isCustomer)
            startActivity(Intent(applicationContext, FragmentActivity::class.java))
            finish()
        } else {
            Toast.makeText(this, "Lamento, mas só funciona para clientes", Toast.LENGTH_SHORT)
        }
    }

    fun onError(message: String){
        progress!!.dismiss()
        Toast.makeText(baseContext, message, Toast.LENGTH_SHORT).show()
    }

    fun validate(stringLogin : String, stringPass : String): Boolean{
        if(!stringLogin.isNullOrBlank() && !stringPass.isNullOrBlank()){
            return true
        }
        return false
    }
}
