package com.mst.prototype.ui.component.login

import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.mst.prototype.MainActivity
import com.mst.prototype.R
import com.mst.prototype.ui.profile.ProfileActivity
import com.mst.prototype.ui.register.RegistrationActivity
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


/**
 * Created by  Nandhini V
 */
class LoginActivity : AppCompatActivity() {

    private val TAG = "Login Screen"
    private val RC_SIGN_IN = 101
    private var mGoogleSignInClient: GoogleSignInClient? = null
    lateinit var callbackManager: CallbackManager
    private val EMAIL = "email"

    private lateinit var loginButton: AppCompatButton
    private lateinit var gmailButton: SignInButton
    private lateinit var facebookButton: LoginButton
    private lateinit var signInTextView: AppCompatTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        initialization()
        printHashKey(this)
        initializeViews()
        setOnClickListeners()
        defaultMethod()
    }

    private fun defaultMethod() {

        facebookButton.setOnClickListener {
            facebookButton.setReadPermissions(listOf(EMAIL))
            callbackManager = CallbackManager.Factory.create()
            // If you are using in a fragment, call loginButton.setFragment(this);
            // Callback registration
            facebookButton.registerCallback(
                callbackManager,
                object : FacebookCallback<LoginResult?> {
                    override fun onSuccess(loginResult: LoginResult?) {
                        Log.d("MainActivity", "Facebook token: " + loginResult!!.accessToken.token)
                        startActivity(
                            Intent(
                                applicationContext,
                                MainActivity::class.java
                            )
                        )
                    }

                    override fun onCancel() {
                    }

                    override fun onError(exception: FacebookException) { // App code
                    }
                })
            callbackManager = CallbackManager.Factory.create()
            LoginManager.getInstance().registerCallback(callbackManager,
                object : FacebookCallback<LoginResult?> {
                    override fun onSuccess(result: LoginResult?) { // App code
                    }

                    override fun onCancel() { // App code
                    }

                    override fun onError(exception: FacebookException) { // App code
                    }
                    /* val accessToken = AccessToken.getCurrentAccessToken()
                     accessToken != null && !accessToken.isExpired*/
                })

        }
    }

    private fun setOnClickListeners() {
        loginButton.setOnClickListener { doLogin() }
        gmailButton.setOnClickListener { googleLogin() }
        signInTextView.setOnClickListener { signInNavigation() }
    }

    private fun signInNavigation() {
        val nextScreenIntent = Intent(this, RegistrationActivity::class.java)
        startActivity(nextScreenIntent)
    }

    private fun initializeViews() {

        loginButton = findViewById(R.id.loginButton)
        gmailButton = findViewById(R.id.gmailButton)
        facebookButton = findViewById(R.id.facebookButton)
        signInTextView = findViewById(R.id.signInTextView)
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    private fun initialization() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
    }


    private fun googleLogin() {

        val signInIntent = mGoogleSignInClient!!.signInIntent
//        startActivityForResult(signInIntent, RC_SIGN_IN)
//        resultLauncher.launch(signInIntent)

    }


    private fun doLogin() {
        val nextScreenIntent = Intent(this, ProfileActivity::class.java)
        startActivity(nextScreenIntent)
        finish()
    }


    private fun navigateToMainScreen() {
        val nextScreenIntent = Intent(this, MainActivity::class.java)
        startActivity(nextScreenIntent)
        finish()
    }


    private fun updateUI(account: GoogleSignInAccount?) {
        if (account != null) {
            val nextScreenIntent = Intent(this, MainActivity::class.java)
            startActivity(nextScreenIntent)
            finish()
        }
    }

    fun printHashKey(pContext: Context) {
        try {
            val info: PackageInfo = pContext.getPackageManager()
                .getPackageInfo(pContext.getPackageName(), PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                val hashKey: String = String(Base64.encode(md.digest(), 0))
                Log.i(TAG, "printHashKey() Hash Key: $hashKey")
            }
        } catch (e: NoSuchAlgorithmException) {
            Log.e(TAG, "printHashKey()", e)
        } catch (e: Exception) {
            Log.e(TAG, "printHashKey()", e)
        }
    }
}

private fun LoginManager.registerCallback(
    callbackManager: CallbackManager,
    callback: FacebookCallback<LoginResult?>
) {

}
