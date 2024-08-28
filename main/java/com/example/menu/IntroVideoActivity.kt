package com.example.menu

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class IntroVideoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configurar pantalla completa
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        // Ocultar la barra de navegación y la barra de estado
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)

        setContentView(R.layout.activity_intro_video)

        val imageView = findViewById<ImageView>(R.id.imageView)
        imageView.setImageResource(R.drawable.inicio)

        // Ajustar el tamaño de la imagen para llenar la pantalla
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP

        // Lanzar la actividad siguiente después de un tiempo
        val handler = android.os.Handler()
        handler.postDelayed({
            startStartActivity()
        }, 1000) // 1000 milisegundos = 1 segundos
    }

    private fun startStartActivity() {
        val intent = Intent(this, StartActivity::class.java)
        startActivity(intent)
        finish()
    }
}