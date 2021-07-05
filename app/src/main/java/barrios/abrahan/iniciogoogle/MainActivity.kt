package barrios.abrahan.iniciogoogle

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task


class MainActivity : AppCompatActivity() {

    val RC_SIGN_IN= 123
    val COD_LOGOUT=323

    lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)


        val btn: SignInButton = findViewById(R.id.sign_in_button)
        btn.setOnClickListener {
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
            Toast.makeText(this,"StartActivityWorks",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
            Toast.makeText(this,"OnActivityResultWorks",Toast.LENGTH_SHORT).show()
        }

        if(requestCode==COD_LOGOUT){
            signOut()

        }
    }

    override fun onStart() {
        super.onStart()
        // Check for existing Google Sign In account, if the user is already signed in
// the GoogleSignInAccount will be non-null.
        // Check for existing Google Sign In account, if the user is already signed in
// the GoogleSignInAccount will be non-null.
        val account = GoogleSignIn.getLastSignedInAccount(this)
        updateUI(account)
    }

    private fun signOut() {
        mGoogleSignInClient.signOut()
            .addOnCompleteListener(this) {
                Toast.makeText(this,"Sesion Terminada",Toast.LENGTH_SHORT).show()
            }
    }


    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {

            val account: GoogleSignInAccount?= completedTask.getResult(ApiException::class.java)

            // Signed in successfully, show authenticated UI.

            updateUI(account)
            Toast.makeText(this,"HandleResultWorks",Toast.LENGTH_SHORT).show()

        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
           // Log.w(TAG, "signInResult:failed code=" + e.statusCode)
            updateUI(null)
            val intent= Intent(this,PrincipalActivity::class.java)
            intent.putExtra("name","Null")
            intent.putExtra("email","Null")
            startActivity(intent)
            Toast.makeText(this,"HandleResultDontWork",Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(account: GoogleSignInAccount?) {
        if(account!=null){
            Toast.makeText(this,"UpdateUItWorks",Toast.LENGTH_SHORT).show()
            val intent= Intent(this,PrincipalActivity::class.java)
            intent.putExtra("name",account.displayName)
            intent.putExtra("email",account.email)
            startActivityForResult(intent,COD_LOGOUT)
        }

    }
}