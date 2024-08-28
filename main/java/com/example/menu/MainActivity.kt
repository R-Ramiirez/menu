package com.example.menu

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var btnMonday: Button
    private lateinit var btnTuesday: Button
    private lateinit var btnWednesday: Button
    private lateinit var btnThursday: Button
    private lateinit var btnFriday: Button
    private lateinit var btnPreviousWeek: ImageButton
    private lateinit var btnNextWeek: ImageButton
    private lateinit var tvWeekInfo: TextView

    private var currentWeek = 0
    private val menuItems = listOf(
        MenuItem("Barra de cereal", R.drawable.images, "Barra de cereal nutritiva con avena y frutas secas."),
        MenuItem("Yogurt con frutas", R.drawable.images, "Yogurt natural con una mezcla de frutas frescas de temporada."),
        MenuItem("Sándwich integral", R.drawable.images, "Sándwich en pan integral con pavo y verduras."),
        MenuItem("Fruta fresca", R.drawable.images, "Selección de frutas frescas de temporada."),
        MenuItem("Galletas integrales", R.drawable.images, "Galletas integrales con semillas y miel.")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeViews()
        setupClickListeners()
        updateWeekMenu()
    }

    private fun initializeViews() {
        btnMonday = findViewById(R.id.btnMonday)
        btnTuesday = findViewById(R.id.btnTuesday)
        btnWednesday = findViewById(R.id.btnWednesday)
        btnThursday = findViewById(R.id.btnThursday)
        btnFriday = findViewById(R.id.btnFriday)
        btnPreviousWeek = findViewById(R.id.btnPreviousWeek)
        btnNextWeek = findViewById(R.id.btnNextWeek)
        tvWeekInfo = findViewById(R.id.tvWeekInfo)
    }

    private fun setupClickListeners() {
        btnPreviousWeek.setOnClickListener {
            currentWeek--
            updateWeekMenu()
        }

        btnNextWeek.setOnClickListener {
            currentWeek++
            updateWeekMenu()
        }

        listOf(btnMonday, btnTuesday, btnWednesday, btnThursday, btnFriday).forEachIndexed { index, button ->
            button.setOnClickListener {
                val menuItem = menuItems[index % menuItems.size]
                openMenuDetail(menuItem)
            }
        }
    }

    private fun updateWeekMenu() {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.WEEK_OF_YEAR, currentWeek)
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)

        val buttons = listOf(btnMonday, btnTuesday, btnWednesday, btnThursday, btnFriday)
        buttons.forEachIndexed { index, button ->
            val menuItem = menuItems[index % menuItems.size]
            val dateText = formatDate(calendar.time)
            button.text = "$dateText\n${menuItem.name}"

            // Establecer la imagen a la izquierda del botón
            val drawable = ContextCompat.getDrawable(this, menuItem.imageResId)
            drawable?.setBounds(0, 0, 100, 100) // Ajusta el tamaño según sea necesario
            button.setCompoundDrawables(drawable, null, null, null)

            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }

        tvWeekInfo.text = "Semana del ${formatDate(calendar.time)}"
    }

    private fun formatDate(date: Date): String {
        val sdf = SimpleDateFormat("EEEE d MMMM", Locale("es", "ES"))
        return sdf.format(date).capitalize()
    }

    private fun openMenuDetail(menuItem: MenuItem) {
        val intent = Intent(this, MenuDetailActivity::class.java).apply {
            putExtra("MENU_NAME", menuItem.name)
            putExtra("MENU_DESCRIPTION", menuItem.description)
            putExtra("MENU_IMAGE_RES_ID", menuItem.imageResId)
        }
        startActivity(intent)
    }
}

data class MenuItem(val name: String, val imageResId: Int, val description: String)