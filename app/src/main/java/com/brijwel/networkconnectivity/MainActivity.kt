package com.brijwel.networkconnectivity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.brijwel.networkconnectivity.connectivity.ConnectivityObserver
import com.brijwel.networkconnectivity.connectivity.ConnectivityObserver.Status.*
import com.brijwel.networkconnectivity.connectivity.ConnectivityObserverImpl
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val connectivityObserver: ConnectivityObserver by lazy {
        ConnectivityObserverImpl(
            application
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    connectivityObserver.observeConnectivity()
                        .collectLatest {
                            val status = findViewById<TextView>(R.id.status)
                            val root = findViewById<View>(R.id.root)
                            status.text = it.name
                            when (it) {
                                Available -> root.setBackgroundColor(Color.GREEN)
                                Losing -> root.setBackgroundColor(Color.YELLOW)
                                Unavailable -> root.setBackgroundColor(Color.GRAY)
                                Lost -> root.setBackgroundColor(Color.RED)
                            }

                        }
                }
            }
        }
    }
}