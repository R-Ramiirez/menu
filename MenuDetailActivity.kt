package com.example.menu

import MenuItem
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity

class MenuDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_detail)

        findViewById<ImageButton>(R.id.btnBac).setOnClickListener {
            onBackPressed()
        }

        setupCustomActionBar()

        val menuItem = intent.getParcelableExtra<MenuItem>("MENU_ITEM")

        if (menuItem != null) {
            findViewById<TextView>(R.id.tvRecipeName).text = menuItem.name
            findViewById<TextView>(R.id.tvRecipeDescription).text = menuItem.description
            findViewById<TextView>(R.id.tvRecipeIngredientes).text = menuItem.ingredients
            findViewById<ImageView>(R.id.ivRecipeImage).setImageResource(menuItem.imageResId)

            // Formato para tiempo y raciones
            findViewById<TextView>(R.id.tiempo).text = "${menuItem.tiempoMinutos} minutos"
            findViewById<TextView>(R.id.racion).text = menuItem.raciones  // Usar directamente el texto de raciones

            findViewById<ImageView>(R.id.ivVegetarian).visibility =
                if (menuItem.isVegetarian) View.VISIBLE else View.GONE
            findViewById<ImageView>(R.id.ivGlutenFree).visibility =
                if (menuItem.isGlutenFree) View.VISIBLE else View.GONE
        } else {
            // Manejar el caso en que no hay receta
            findViewById<TextView>(R.id.tvRecipeName).text = "No hay receta disponible"
            findViewById<TextView>(R.id.tvRecipeDescription).text = "Por favor, seleccione otra fecha"
            findViewById<TextView>(R.id.tvRecipeIngredientes).text = ""
            findViewById<TextView>(R.id.tiempo).text = ""
            findViewById<TextView>(R.id.racion).text = ""
        }
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

        // Forzar el color del texto de la Action Bar
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayShowTitleEnabled(true)

        val titleId = resources.getIdentifier("action_bar_title", "id", "android")
        val titleTextView = findViewById<TextView>(titleId)
        titleTextView?.setTextColor(Color.BLACK)

        val customView = supportActionBar?.customView
        customView?.let {
            val btnOpenMenu: ImageButton = it.findViewById(R.id.btnOpenMenu)
            btnOpenMenu.setOnClickListener { view ->
                showPopupMenu(view)
            }
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