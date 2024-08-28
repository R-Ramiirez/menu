package com.example.menu

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MenuDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_detail)

        val menuName = intent.getStringExtra("MENU_NAME") ?: "Menú Desconocido"
        val menuDescription = intent.getStringExtra("MENU_DESCRIPTION") ?: "Sin descripción disponible"
        val menuImageResId = intent.getIntExtra("MENU_IMAGE_RES_ID", R.drawable.images)

        findViewById<TextView>(R.id.tvMenuName).text = menuName
        findViewById<TextView>(R.id.tvMenuDescription).text = menuDescription
        findViewById<ImageView>(R.id.ivMenuImage).setImageResource(menuImageResId)
    }
}