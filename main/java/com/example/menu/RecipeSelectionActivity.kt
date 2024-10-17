package com.example.menu

import MenuItem
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import com.bumptech.glide.Glide

class RecipeSelectionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_selection)

        findViewById<ImageButton>(R.id.btnBac).setOnClickListener {
            onBackPressed()
        }

        setupCustomActionBar()

        val recipes = intent.getParcelableArrayListExtra<MenuItem>("RECIPES") ?: return

        val container = findViewById<LinearLayout>(R.id.recipeContainer)

        recipes.forEach { recipe ->
            val view = layoutInflater.inflate(R.layout.item_recipe, container, false)

            val ivRecipeImage = view.findViewById<ImageView>(R.id.ivRecipeImage)
            val tvRecipeName = view.findViewById<TextView>(R.id.tvRecipeName)
            val ivVegetarian = view.findViewById<ImageView>(R.id.ivVegetarian)
            val ivGlutenFree = view.findViewById<ImageView>(R.id.ivGlutenFree)

            Glide.with(this)
                .load(recipe.imageResId)
                .into(ivRecipeImage)

            tvRecipeName.text = recipe.name

            ivVegetarian.visibility = if (recipe.isVegetarian) View.VISIBLE else View.GONE
            ivGlutenFree.visibility = if (recipe.isGlutenFree) View.VISIBLE else View.GONE

            view.setOnClickListener {
                openMenuDetail(recipe)
            }

            container.addView(view)
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
                else -> false
            }
        }
        popupMenu.show()
    }

    private fun scaleDrawable(drawable: Drawable, width: Int, height: Int): Drawable {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return BitmapDrawable(resources, bitmap)
    }

    private fun combineDrawables(drawable1: Drawable?, drawable2: Drawable?): Drawable {
        val width = (drawable1?.intrinsicWidth ?: 0) + (drawable2?.intrinsicWidth ?: 0)
        val height = maxOf(drawable1?.intrinsicHeight ?: 0, drawable2?.intrinsicHeight ?: 0)

        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        drawable1?.let {
            it.setBounds(0, 0, it.intrinsicWidth, it.intrinsicHeight)
            it.draw(canvas)
        }

        drawable2?.let {
            val left = drawable1?.intrinsicWidth ?: 0
            it.setBounds(left, 0, left + it.intrinsicWidth, it.intrinsicHeight)
            it.draw(canvas)
        }

        return BitmapDrawable(resources, bitmap)
    }

    private fun openMenuDetail(menuItem: MenuItem) {
        Log.d("RecipeSelection", "Opening detail for: ${menuItem.name}")
        val intent = Intent(this, MenuDetailActivity::class.java).apply {
            putExtra("MENU_ITEM", menuItem)
        }
        startActivity(intent)
    }
}