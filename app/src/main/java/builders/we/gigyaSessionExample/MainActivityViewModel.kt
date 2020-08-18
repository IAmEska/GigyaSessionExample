package builders.we.gigyaSessionExample

import androidx.lifecycle.*
import com.gigya.android.sdk.Gigya
import com.gigya.android.sdk.GigyaLoginCallback
import com.gigya.android.sdk.account.models.GigyaAccount
import com.gigya.android.sdk.network.GigyaError
import kotlinx.coroutines.flow.combine

class MainActivityViewModel : ViewModel() {

    private val gigya = Gigya.getInstance(GigyaAccount::class.java)

    val login = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val isLoginEnabled = login.asFlow().combine(password.asFlow()) { l, p ->
        !l.isNullOrBlank() && !p.isNullOrBlank()
    }.asLiveData()

    val gigyaAccount = MutableLiveData<GigyaAccount>()


    /**
     * Login user by login and password to Gigya account
     * */
    fun login() {

        if(isLoginEnabled.value != true)
            return

        val login = requireNotNull(login.value)
        val password = requireNotNull(password.value)

        val callback = object: GigyaLoginCallback<GigyaAccount>() {
            override fun onSuccess(p0: GigyaAccount?) {
                gigyaAccount.value = p0
            }

            override fun onError(p0: GigyaError?) {
                println(p0?.toString())
            }
        }

        gigya.login(login, password, callback)
    }

}