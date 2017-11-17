package ink.domytattoo.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import ink.domytattoo.R
import ink.domytattoo.rest.request.Phone
import ink.domytattoo.rest.request.SignUpBody
import ink.domytattoo.rest.service.AccountService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.toolbar.*

class SignUpActivity : AppCompatActivity() {

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
        setContentView(R.layout.activity_signup)
        setSupportActionBar(toolbar)
        title = "Cadastro"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        signup_button.setOnClickListener{
            if(validate()){
                var body = SignUpBody(
                        !artist.isSelected,
                        name.text.toString(),
                        username.text.toString(),
                        password.text.toString(),
                        email.text.toString(),
                        Phone(phone.text.toString(), whatsapp.isSelected),
                        bio.text.toString(),
                        country.text.toString(),
                        state.text.toString(),
                        city.text.toString()
                )
                disposable =
                        accountService.signup(body)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(
                                        { result -> onResult()
                                        },
                                        { error -> Toast.makeText(baseContext, "Não foi possível realizar o cadastro", Toast.LENGTH_SHORT).show() }
                                )
            }
        }
    }

    fun validate() : Boolean{
        if(!username.text.toString().isNullOrBlank() &&
                !name.text.toString().isNullOrBlank() &&
                !instagram.text.toString().isNullOrBlank() &&
                !phone.text.toString().isNullOrBlank() &&
                !email.text.toString().isNullOrBlank() &&
                !password.text.toString().isNullOrBlank() &&
                !confirm_password.text.toString().isNullOrBlank() &&
                !country.text.toString().isNullOrBlank() &&
                !city.text.toString().isNullOrBlank() &&
                !state.text.toString().isNullOrBlank() &&
                !bio.text.toString().isNullOrBlank())
            return true
        Toast.makeText(baseContext, "A gente sabe que é chato, mas preenche tudo. Pode ser de qualquer jeito.", Toast.LENGTH_SHORT).show()
        return false
    }

    fun onResult(){
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}
