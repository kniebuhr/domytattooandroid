package ink.domytattoo.activity

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

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var login = login_username
        var password = login_password

        login_button.setOnClickListener{
            if(validate(login.text.toString(), password.text.toString())){

                var pattern = Pattern.compile("/^[^\\s@]+@[^\\s@]+\\.[^\\s@]+\$/")
                var matcher = pattern.matcher(login.text.toString())

                if(matcher.matches()){
                    disposable =
                        accountService.signin(SignInBody(login.text.toString(), "", password.text.toString()))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                { result -> onResult(result)
                                },
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
                                { error -> Toast.makeText(baseContext, error.localizedMessage, Toast.LENGTH_SHORT).show() }
                            )
                }
            } else {
                Toast.makeText(baseContext, "Preencha os campos de login", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun onResult(result : AccountModel.SignIn){
        var sharedPref = this.getPreferences(MODE_PRIVATE)
        var editor = sharedPref.edit()
        editor.putString(Constants().EXTRA_USERNAME, result.userName)
        editor.putBoolean(Constants().EXTRA_IS_CUSTOMER, result.isCustomer)
        editor.putString(Constants().EXTRA_USER_ID  , result._id)
        editor.commit()

        startActivity(Intent(applicationContext, FragmentActivity::class.java))
    }

    fun validate(stringLogin : String, stringPass : String): Boolean{
        if(!stringLogin.isNullOrBlank() && !stringPass.isNullOrBlank()){
            return true
        }
        return false
    }



}
