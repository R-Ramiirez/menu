package com.example.menu

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity

class PlatoSaludableActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plato_saludable)

        findViewById<ImageButton>(R.id.btnBack).setOnClickListener {
            // Aquí especificas la actividad a la que quieres navegar
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Esto cierra la actividad actual después de abrir la nueva
        }

        setupCustomActionBar()
    }

    private fun setupCustomActionBar() {
        supportActionBar?.apply {
            setDisplayShowHomeEnabled(false)
            setDisplayShowTitleEnabled(false)
            setDisplayShowCustomEnabled(true)

            val customView = layoutInflater.inflate(R.layout.custom_action_bar, null)
            customView.layoutParams = ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT
            )
            setCustomView(customView)

            // Configurar el botón de retroceso
            customView.findViewById<ImageButton>(R.id.btnBack).setOnClickListener {
                onBackPressed()
            }

            // Configurar el botón de menú (si lo necesitas)
            customView.findViewById<ImageButton>(R.id.btnOpenMenu).setOnClickListener {
                // Tu lógica para abrir el menú
            }
        }

        supportActionBar?.title = "EducaCOLACIONES"

        val titleId = resources.getIdentifier("action_bar_title", "id", "android")
        val titleTextView = findViewById<TextView>(titleId)
        titleTextView?.setTextColor(Color.BLACK)

        supportActionBar?.customView?.findViewById<ImageButton>(R.id.btnOpenMenu)
            ?.setOnClickListener { view ->
                showPopupMenu(view)
            }
    }

    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_item1 -> {
                    startActivity(Intent(this, DietasEspecialesActivity::class.java))
                    true
                }

                R.id.menu_item2 -> {
                    startActivity(Intent(this, ConsejosPracticosActivity::class.java))
                    true
                }
                R.id.menu_item3 -> {
                    startActivity(Intent(this, PlatoSaludableActivity::class.java))
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }
}