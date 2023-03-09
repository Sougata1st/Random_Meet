package models

import android.webkit.JavascriptInterface
import com.example.testing.recyclerview.olivia.activities.CallActivity

open class InterfaceKt(val callactivity: CallActivity) {

    @JavascriptInterface
    public fun onPeerConnected() {
        callactivity.onPeerConnected()
    }
}