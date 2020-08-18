package builders.we.gigyaSessionExample

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.gigya.android.sdk.GigyaDefinitions
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainActivityViewModel>()
    private val sessionLifecycleReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action ?: return) {
                GigyaDefinitions.Broadcasts.INTENT_ACTION_SESSION_EXPIRED -> {
                    Toast.makeText(this@MainActivity, "Session_Expired", Toast.LENGTH_LONG).show()
                    viewModel.gigyaAccount.value = null
                }
                GigyaDefinitions.Broadcasts.INTENT_ACTION_SESSION_INVALID -> {
                    Toast.makeText(this@MainActivity, "Session_Invalid", Toast.LENGTH_LONG).show()
                    viewModel.gigyaAccount.value = null
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // setup login button
        bLogin.isEnabled = false
        viewModel.isLoginEnabled.observe(this) {
            bLogin.isEnabled = it == true
        }

        bLogin.setOnClickListener {
            viewModel.login()
        }

        // setup login and password edittext
        etLogin.doAfterTextChanged {
            viewModel.login.value = it?.toString()
        }

        etPassword.doAfterTextChanged {
            viewModel.password.value = it?.toString()
        }

        viewModel.gigyaAccount.observe(this) {
            if (it == null) {
                gLoginGroup.visibility = View.VISIBLE
                tvAccountInfo.visibility = View.GONE
            } else {
                gLoginGroup.visibility = View.GONE
                tvAccountInfo.visibility = View.VISIBLE
                tvAccountInfo.text = it.profile?.name
            }
        }

    }

    override fun onResume() {
        super.onResume()
        val intentFilter = IntentFilter()
        intentFilter.addAction(GigyaDefinitions.Broadcasts.INTENT_ACTION_SESSION_EXPIRED)
        intentFilter.addAction(GigyaDefinitions.Broadcasts.INTENT_ACTION_SESSION_INVALID)
        LocalBroadcastManager.getInstance(this).registerReceiver(sessionLifecycleReceiver, intentFilter)
    }

    override fun onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(sessionLifecycleReceiver)
        super.onPause()
    }


}