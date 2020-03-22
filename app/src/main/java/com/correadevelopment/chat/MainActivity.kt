package com.correadevelopment.chat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import kotlinx.android.synthetic.main.activity_loging.*

class MainActivity : AppCompatActivity() {

    private var Usuario: EditText? = null
    private var chat: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loging)

        Usuario = findViewById(R.id.etUsuario)
        chat = findViewById(R.id.etChat)

        btnLoging?.setOnClickListener {
            if (Usuario?.text.toString() != ""){
                val intent:Intent = Intent(this,Chat::class.java)
                var user:String = Usuario?.text.toString()
                var chat:String = chat?.text.toString()
                intent.putExtra("U",user)
                intent.putExtra("C",chat)
                Toast.makeText(this,"Bienvenido $user", Toast.LENGTH_SHORT).show()
                startActivity(intent)
            }else{
                Toast.makeText(this,"Debe escribir un usuario.", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
