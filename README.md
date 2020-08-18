# GigyaSessionExample
Gigya Android SDK invalid session issue example.

1. Insert Gigya api key to **AndroidManifest.xml**
```xml
<meta-data
  android:name="apiKey"
  android:value="{InsertApiKey}" />
  ```
2. Start application on Android emulator and login via email / username + password
3. Put breakpoint on sessionLifecycleReciever **onReceive** method.
```kotlin
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
 ```
 4. Change Cellular settings on Android emulator to **GSM** and signal strength **Poor**
 5. Wait few minutes until **invalid session** is triggered on broadcast reciever.
