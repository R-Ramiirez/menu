package com.example.menu

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Ocultar la Action Bar
        supportActionBar?.hide()

        setContentView(R.layout.activity_start)

        Log.d("StartActivity", "onCreate called")

        val btnPrimer = findViewById<Button>(R.id.btnPrimer)
        val btnSegundo = findViewById<Button>(R.id.btnSegundo)
        val btnTercero = findViewById<Button>(R.id.btnTercero)
        val btnCuarto = findViewById<Button>(R.id.btnCuarto)

        btnPrimer.setOnClickListener { navigateToMain("MENOS DE 6 AÑOS") }
        btnSegundo.setOnClickListener { navigateToMain("6 A 10 AÑOS") }
        btnTercero.setOnClickListener { navigateToMain("11 A 14 AÑOS") }
        btnCuarto.setOnClickListener { navigateToMain("15 A 18 AÑOS") }
    }

    private fun navigateToMain(ageGroup: String) {
        Log.d("StartActivity", "navigateToMain called with ageGroup: $ageGroup")
        try {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("AGE_GROUP", ageGroup)
            Log.d("StartActivity", "Intent created successfully")
            startActivity(intent)
            Log.d("StartActivity", "startActivity called")
        } catch (e: Exception) {
            Log.e("StartActivity", "Error starting MainActivity", e)
            // Mostrar un mensaje al usuario
            Toast.makeText(this, "Error al abrir la siguiente pantalla", Toast.LENGTH_SHORT).show()
        }
    }
}