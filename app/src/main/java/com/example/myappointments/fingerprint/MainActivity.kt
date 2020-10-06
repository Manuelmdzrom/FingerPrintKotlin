package com.example.myappointments.fingerprint

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricPrompt.PromptInfo
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val msgText = findViewById<TextView>(R.id.txt_msg)
        val login_button =
            findViewById<Button>(R.id.login_button)
        val biometricManager =
            BiometricManager.from(this)
        when (biometricManager.canAuthenticate()) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                msgText.text = "El usuario puede autenticarse correctamente."
                msgText.setTextColor(Color.parseColor("#Fafafa"))
            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> msgText.text =
                "El dispositivo no tiene detector de huella"
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                msgText.text = "El detector de huella no esta disponoible"
                login_button.visibility = View.GONE
            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                msgText.text =
                    "Tu dispositivo no tiene ninguna hueela guardad, porfavor checa en tus ajusts de seguridad"
                login_button.visibility = View.GONE
            }
        }
        //biometric dialog box
        val executor = ContextCompat.getMainExecutor(this)
        val biometricPrompt =
            BiometricPrompt(
                this@MainActivity,
                executor,
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationError(
                        errorCode: Int,
                        errString: CharSequence
                    ) {
                        super.onAuthenticationError(errorCode, errString)
                    }

                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)
                        Toast.makeText(
                            applicationContext,
                            "Logueado Correctamente",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                    }
                })
        val promptInfo = PromptInfo.Builder()
            .setTitle("Login")
            .setDescription("Usaste tu detector de huella para loguearte en tu App")
            .setNegativeButtonText("Cancel")
            .build()
        login_button.setOnClickListener { biometricPrompt.authenticate(promptInfo) }
    }
}
