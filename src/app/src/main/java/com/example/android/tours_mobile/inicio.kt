package com.example.android.tours_mobile

import android.content.Intent
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity

public class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)

        Registrarse.setOnClickListener {

            val intent: Intent = Intent(this, registro::class.java)
            startActivity(intent)
        }
    }
}


