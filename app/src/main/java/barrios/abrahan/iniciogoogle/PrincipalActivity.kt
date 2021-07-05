package barrios.abrahan.iniciogoogle

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.common.SignInButton

class PrincipalActivity : AppCompatActivity() {
    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)
        val bundle= intent.extras
        var tv_name: TextView= findViewById(R.id.tv_name)
        var tv_email: TextView= findViewById(R.id.tv_email)

        if(bundle!=null){
            val name= bundle.getString("name")
            val email= bundle.getString("email")

            tv_name.text=name
            tv_email.text=email
        }

        val btn_cerrar: Button = findViewById(R.id.btn_cerrar_sesion)
        btn_cerrar.setOnClickListener {
            finish()
        }




    }
}