package com.example.menu

import MenuItem
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
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
    private var currentWeek = 0
    private val recipesByDate = mutableMapOf<String, List<MenuItem>>()
    private lateinit var ageGroup: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btnReturn).setOnClickListener {
            // Aquí especificas la actividad a la que quieres navegar
            val intent = Intent(this, StartActivity::class.java)
            startActivity(intent)
            finish() // Esto cierra la actividad actual después de abrir la nueva
        }

        ageGroup = intent.getStringExtra("AGE_GROUP") ?: "MENOS DE 6 AÑOS"

        setupCustomActionBar()
        initializeRecipes()
        initializeViews()
        setupClickListeners()
        updateWeekMenu()
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

        val titleId = resources.getIdentifier("action_bar_title", "id", "android")
        val titleTextView = findViewById<TextView>(titleId)
        titleTextView?.setTextColor(Color.BLACK)

        supportActionBar?.customView?.findViewById<ImageButton>(R.id.btnOpenMenu)?.setOnClickListener { view ->
            showPopupMenu(view)
        }
    }

    private fun initializeViews() {
        btnMonday = findViewById(R.id.btnMonday)
        btnTuesday = findViewById(R.id.btnTuesday)
        btnWednesday = findViewById(R.id.btnWednesday)
        btnThursday = findViewById(R.id.btnThursday)
        btnFriday = findViewById(R.id.btnFriday)
        btnPreviousWeek = findViewById(R.id.btnPreviousWeek)
        btnNextWeek = findViewById(R.id.btnNextWeek)
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
    }

    private fun initializeRecipes() {
        when (ageGroup) {
            "MENOS DE 6 AÑOS" -> {
                addRecipe("2024-10-14", listOf(
                    MenuItem("TROCITOS DE MANZANA CON CREMA DE MANÍ", R.drawable.manzanacremademani,
                        "1.- Lavar y cortar la manzana en rodajas finas.\n \n" +
                                "2.- Untar una capa delgada de crema de maní sobre cada rodaja y ¡SERVIR!",
                        "• 1 manzana\n• 2 cdas de crema de maní",
                        false, true, 5, "1 Ración"),
                ))
                addRecipe("2024-10-15", listOf(
                    MenuItem("CRACKERS INTEGRALES CON QUESO Y RODAJAS DE PEPINO", R.drawable.crackersconpepino,
                        "1.- Lavar y cortar el pepino en rodajas finas.\n\n" +
                                "2.- Colocar una cucharadita de queso sobre cada cracker.\n\n" +
                                "3.- Añadir una rodaja de pepino encima de cada cracker y ¡SERVIR!",
                        "• 6 crackers integrales\n• 1/4 taza de queso cottage o philadelphia\n• 1/2 pepino",
                        false, false,5,"6 Crackers")
                ))
                addRecipe("2024-10-16", listOf(
                    MenuItem("CEREALES INTEGRALES CON LECHE Y FRUTA FRESCA", R.drawable.cerealeslechefruta,
                        "1.- Colocar los cereales en un tazón.\n\n" +
                                "2.- Añadir la leche y las frutas frescas cortadas encima.\n\n" +
                                "3.- Servir inmediatamente.",
                        "• 1/2 taza de cereales integrales bajos en azúcar\n• 1/2 taza de leche\n• 1/4 taza de frutas frescas (plátano, frutillas, kiwi)",
                        false, false,5,"1 Ración")
                ))
                addRecipe("2024-10-17", listOf(
                    MenuItem("YOGURT S/AZÚCAR CON FRUTA", R.drawable.yogurtconfruta,
                        "1.- Lavar y cortar la fruta en trozos pequeños.\n\n" +
                                "2.- Servir junto con el yogurt.",
                        "• 1 yogurt natural sin azúcar\n• 1 fruta de estación",
                        false, true,5,"1 Ración")
                ))
                addRecipe("2024-10-18", listOf(
                    MenuItem("COMPOTA DE FRUTA CON LECHE DESCREMADA", R.drawable.compotayleche,
                        "1.- Calentar el jugo de naranja en una cacerola hasta que esté tibio.\n\n" +
                                "2.- Añadir la gelatina sin sabor y remover hasta que se disuelva completamente.\n\n" +
                                "3.- Colocar las frutas en moldes individuales y verter el jugo con gelatina encima.\n\n" +
                                "4.- Refrigerar durante al menos 2 horas o hasta que la gelatina cuaje.",
                        "• 1 taza de jugo de naranja natural\n• 1/2 taza de plátanos cortados en trozos\n• 1/2 taza de kiwi\n• 2 cucharadas de gelatina sin sabor",
                        false, true,120,"1 Ración")
                ))
                addRecipe("2024-10-21", listOf(
                    MenuItem("ROLLITO DE JAMÓN  DE PAVO Y QUESO", R.drawable.rollitopavoqueso,
                        "1.- Colocar una rebanada de jamón de pavo sobre una superficie plana.\n\n" +
                                "2.- Colocar una rebanada de queso sobre el jamón.\n\n" +
                                "3.- Enrollar el jamón de pavo con el queso adentro, formando un rollito.\n\n" +
                                "4.- Cortar el rollito por la mitad para hacer dos piezas más pequeñas y fáciles de manejar.",
                        "• 2 rebanadas de jamón de pavo (preferiblemente bajo en sodio)\n• 2 rebanadas de queso light",
                        false, true,5,"1 Ración")
                ))
                addRecipe("2024-10-22", listOf(
                    MenuItem("BOCADOS DE PLÁTANO CON MANTEQUILLA DE MANÍ", R.drawable.bocadosplatanomani,
                        "1.- Pelar el plátano y córtalo en rodajas de aproximadamente 1 cm de grosor.\n\n" +
                                "2.- Colocar una pequeña cantidad de mantequilla de maní (aproximadamente 1/2 cucharadita) en la parte superior de cada rodaja de plátano.\n\n" +
                                "3.- Para hacer bocados más completos, puede colocar otra rodaja de plátano encima de la mantequilla de maní, formando un pequeño \"sándwich\" de plátano y mantequilla de maní.",
                        "• 1 plátano maduro pero firme\n• 2 cucharadas de mantequilla de maní",
                        true, true,5,"1 Ración")
                ))
                addRecipe("2024-10-23", listOf(
                    MenuItem("SÁNDWICH DE PAN INTEGRAL DE TOMATE CON PALTA CON JUGO S/ AZÚCAR", R.drawable.sandwichconjugo,
                    "1.- Tostar ligeramente las rebanadas de pan integral.\n\n" +
                            "2.- Moler la palta y esparcirla sobre una rebanada de pan.\n\n" +
                            "3.- Cortar el tomate en rodajas finas y colocarlas sobre la palta\n\n" +
                            "4.- Cubrir con la otra rebanada de pan y cortar en mini sándwiches\n\n" +
                            "5.- Servir junto con el jugo.",
                    "• 2 rebanadas de pan integral\n" +
                            "• 1/2 palta madura\n" +
                            "• 1 tomate pequeño\n" +
                            "• 1 caja individual de jugo s/azúcar.",
                    true, false,15,"1 Ración")
                ))
                addRecipe("2024-10-24", listOf(
                    MenuItem("GELATINA NATURAL DE FRUTAS.", R.drawable.gelatinafrutas,
                        "1.- Calentar el jugo de naranja en una cacerola hasta que esté tibio.\n\n" +
                                "2.- Añadir la gelatina sin sabor y remover hasta que se disuelva completamente.\n\n" +
                                "3.- Colocar las frutas en moldes individuales y verter el jugo con gelatina encima.\n\n" +
                                "4.- Refrigerar durante al menos 2 horas o hasta que la gelatina cuaje.",
                        "• 1 taza de jugo de naranja natural\n" +
                                "• 1/2 taza de plátanos cortados en trozos\n" +
                                "• 1/2 taza de kiwi\n" +
                                "• 2 cucharadas de gelatina sin sabor",
                        true, true,120,"")
                ))
                addRecipe("2024-10-25", listOf(
                    MenuItem("CRACKERS INTEGRALES CON PASTA DE HUEVO", R.drawable.crackerconhuevo,
                        "1.- Lavar y poner a cocer los huevos durante 11-12 minutos.\n\n" +
                                "2.- Descascar los huevos y molerlos.\n\n" +
                                "3.- Agregar el yogurt griego para mejorar la consistencia.\n\n" +
                                "4.- Añadir la pasta de huevo a las galletas crackers y servir.",
                        "• 6 crackers integrales\n" +
                                "• 2 huevos duros\n" +
                                "• 1 cda de yogurt griego",
                        false, false, 20, "1 Ración"),
                ))
                addRecipe("2024-10-28", listOf(
                    MenuItem("CEREALES INTEGRALES CON LECHE Y FRUTA FRESCA", R.drawable.cerealeslechefruta,
                        "1.- Colocar los cereales en un tazón.\n\n" +
                                "2.- Añadir la leche y las frutas frescas cortadas encima.\n\n" +
                                "3.- Servir inmediatamente.",
                        "• 1/2 taza de cereales integrales bajos en azúcar\n• 1/2 taza de leche\n• 1/4 taza de frutas frescas (plátano, frutillas, kiwi)",
                        false, false,5,"1 Ración")
                ))
                addRecipe("2024-10-29", listOf(
                    MenuItem("COMPOTA DE FRUTA CON LECHE DESCREMADA", R.drawable.compotayleche,
                        "1.- Calentar el jugo de naranja en una cacerola hasta que esté tibio.\n\n" +
                                "2.- Añadir la gelatina sin sabor y remover hasta que se disuelva completamente.\n\n" +
                                "3.- Colocar las frutas en moldes individuales y verter el jugo con gelatina encima.\n\n" +
                                "4.- Refrigerar durante al menos 2 horas o hasta que la gelatina cuaje.",
                        "• 1 taza de jugo de naranja natural\n• 1/2 taza de plátanos cortados en trozos\n• 1/2 taza de kiwi\n• 2 cucharadas de gelatina sin sabor",
                        false, true,120,"1 Ración")
                ))
                addRecipe("2024-10-30", listOf(
                    MenuItem("GELATINA NATURAL DE FRUTAS.", R.drawable.gelatinafrutas,
                        "1.- Calentar el jugo de naranja en una cacerola hasta que esté tibio.\n\n" +
                                "2.- Añadir la gelatina sin sabor y remover hasta que se disuelva completamente.\n\n" +
                                "3.- Colocar las frutas en moldes individuales y verter el jugo con gelatina encima.\n\n" +
                                "4.- Refrigerar durante al menos 2 horas o hasta que la gelatina cuaje.",
                        "• 1 taza de jugo de naranja natural\n" +
                                "• 1/2 taza de plátanos cortados en trozos\n" +
                                "• 1/2 taza de kiwi\n" +
                                "• 2 cucharadas de gelatina sin sabor",
                        true, true,120,"")
                ))
                addRecipe("2024-10-31", listOf(
                    MenuItem("BOCADOS DE PLÁTANO CON MANTEQUILLA DE MANÍ", R.drawable.bocadosplatanomani,
                        "1.- Pelar el plátano y córtalo en rodajas de aproximadamente 1 cm de grosor.\n\n" +
                                "2.- Colocar una pequeña cantidad de mantequilla de maní (aproximadamente 1/2 cucharadita) en la parte superior de cada rodaja de plátano.\n\n" +
                                "3.- Para hacer bocados más completos, puede colocar otra rodaja de plátano encima de la mantequilla de maní, formando un pequeño \"sándwich\" de plátano y mantequilla de maní.",
                        "• 1 plátano maduro pero firme\n• 2 cucharadas de mantequilla de maní",
                        true, true,5,"1 Ración")
                ))
                addRecipe("2024-11-01", listOf(
                    MenuItem("CRACKERS INTEGRALES CON QUESO Y RODAJAS DE PEPINO", R.drawable.crackersconpepino,
                        "1.- Lavar y cortar el pepino en rodajas finas.\n\n" +
                                "2.- Colocar una cucharadita de queso sobre cada cracker.\n\n" +
                                "3.- Añadir una rodaja de pepino encima de cada cracker y ¡SERVIR!",
                        "• 6 crackers integrales\n• 1/4 taza de queso cottage o philadelphia\n• 1/2 pepino",
                        false, false,5,"6 Crackers")
                ))
                addRecipe("2024-11-04", listOf(
                    MenuItem("YOGURT S/AZÚCAR CON FRUTA", R.drawable.yogurtconfruta,
                        "1.- Lavar y cortar la fruta en trozos pequeños.\n\n" +
                                "2.- Servir junto con el yogurt.",
                        "• 1 yogurt natural sin azúcar\n• 1 fruta de estación",
                        false, true,5,"1 Ración")
                ))
                addRecipe("2024-11-05", listOf(
                    MenuItem("TROCITOS DE MANZANA CON CREMA DE MANÍ", R.drawable.manzanacremademani,
                        "1.- Lavar y cortar la manzana en rodajas finas.\n \n" +
                                "2.- Untar una capa delgada de crema de maní sobre cada rodaja y ¡SERVIR!",
                        "• 1 manzana\n• 2 cdas de crema de maní",
                        false, true, 5, "1 Ración"),
                ))
                addRecipe("2024-11-06", listOf(
                    MenuItem("SÁNDWICH DE PAN INTEGRAL DE TOMATE CON PALTA CON JUGO S/ AZÚCAR", R.drawable.sandwichconjugo,
                        "1.- Tostar ligeramente las rebanadas de pan integral.\n\n" +
                                "2.- Moler la palta y esparcirla sobre una rebanada de pan.\n\n" +
                                "3.- Cortar el tomate en rodajas finas y colocarlas sobre la palta\n\n" +
                                "4.- Cubrir con la otra rebanada de pan y cortar en mini sándwiches\n\n" +
                                "5.- Servir junto con el jugo.",
                        "• 2 rebanadas de pan integral\n" +
                                "• 1/2 palta madura\n" +
                                "• 1 tomate pequeño\n" +
                                "• 1 caja individual de jugo s/azúcar.",
                        true, false,15,"1 Ración")
                ))
                addRecipe("2024-11-07", listOf(
                    MenuItem("CRACKERS INTEGRALES CON PASTA DE HUEVO", R.drawable.crackerconhuevo,
                        "1.- Lavar y poner a cocer los huevos durante 11-12 minutos.\n\n" +
                                "2.- Descascar los huevos y molerlos.\n\n" +
                                "3.- Agregar el yogurt griego para mejorar la consistencia.\n\n" +
                                "4.- Añadir la pasta de huevo a las galletas crackers y servir.",
                        "• 6 crackers integrales\n" +
                                "• 2 huevos duros\n" +
                                "• 1 cda de yogurt griego",
                        false, false, 20, "1 Ración"),
                ))
                addRecipe("2024-11-08", listOf(
                    MenuItem("ROLLITO DE JAMÓN  DE PAVO Y QUESO", R.drawable.rollitopavoqueso,
                        "1.- Colocar una rebanada de jamón de pavo sobre una superficie plana.\n\n" +
                                "2.- Colocar una rebanada de queso sobre el jamón.\n\n" +
                                "3.- Enrollar el jamón de pavo con el queso adentro, formando un rollito.\n\n" +
                                "4.- Cortar el rollito por la mitad para hacer dos piezas más pequeñas y fáciles de manejar.",
                        "• 2 rebanadas de jamón de pavo (preferiblemente bajo en sodio)\n• 2 rebanadas de queso light",
                        false, true,5,"1 Ración")
                ))
            }
            "6 A 10 AÑOS" -> {
                addRecipe("2024-10-14", listOf(
                    MenuItem("WRAP DE POLLO Y VEGETALES", R.drawable.wrapdepollovegetales,
                        "1.- Armar el wrap: Unta la palta en la tortilla, añade el pollo, la lechuga y el tomate.\n\n" +
                                "2.- Enrolla firmemente y corta por la mitad.",
                        "• 1 tortilla integral.\n" +
                                "• 2 rebanadas de pechuga de pollo.\n" +
                                "• 1 hoja de lechuga.\n" +
                                "• 2 rodajas de tomate.\n" +
                                "• 1/2 palta",
                        false, false,5,"1 Ración")
                ))
                addRecipe("2024-10-15", listOf(
                    MenuItem("PALOMITAS DE MAÍZ CASERAS", R.drawable.palomitasmaizcaseras,
                        "1.- Calentar el aceite en una olla grande a fuego medio-alto.\n\n" +
                                "2.- Agregar los granos de maíz y cubrir la olla con una tapa.\n\n" +
                                "3.- Agitar la olla de vez en cuando hasta que las palomitas dejen de estallar.\n\n" +
                                "4.- Retirar del fuego, añadir sal al gusto y mezclar bien.",
                        "• 1/4 taza de granos de maíz para palomitas\n" +
                                "• 1 cucharada de aceite (de preferencia aceite de oliva) \n" +
                                "• Sal al gusto",
                        true, true,15,"")
                ))
                addRecipe("2024-10-16", listOf(
                    MenuItem("MINI TORTILLA DE VERDURAS", R.drawable.minitortilladeverduras,
                        "1.- Batir los huevos en un tazón y añadir las espinacas, pimientos, queso, sal y pimienta.\n\n" +
                                "2.- Calentar el aceite en una sartén a fuego medio.\n\n" +
                                "3.- Verter la mezcla de huevos y cocinar hasta que esté firme por ambos lados.",
                        "• 2 huevos\n" +
                                "• 1/4 taza de espinacas picadas\n" +
                                "• 1/4 taza de pimientos en cubos\n" +
                                "• 1 cucharada de queso rallado (opcional)\n" +
                                "• Sal a gusto\n" +
                                "• 1 cucharadita de aceite de oliva",
                        false, true,10,"")
                ))
                addRecipe("2024-10-17", listOf(
                    MenuItem("PUDIN DE CHÍA Y FRUTAS", R.drawable.pudindechiayfrutas,
                        "1.- En un recipiente, mezclar la leche, semillas de chía y miel.\n\n" +
                                "2.- Deja reposar en el refrigerador durante al menos 2 horas o toda la noche.\n\n" +
                                "3.- Servir el pudín y decorar con frutas frescas.",
                        "• 1 taza de leche descremada \n" +
                                "• 3 cucharadas de semillas de chía\n" +
                                "• 1 cucharada de miel (opcional)\n" +
                                "• Frutas frescas (frutillas, arándano, plátano) para decorar.",
                        false, true,10,"")
                ))
                addRecipe("2024-10-18", listOf(
                    MenuItem("CRACKERS INTEGRALES CON PALTA", R.drawable.crackerpalta,
                        "1.- Moler la palta en un tazón.\n\n" +
                                "2.- Añadir el tomate, jugo de limón y sal, mezclando bien.\n\n" +
                                "3.- Untar la palta a las galletas crackers integrales.",
                        "• 6 Crackers integrales\n" +
                                "• 1 palta maduro\n" +
                                "• 1/2 tomate pequeño en cubos\n" +
                                "• Jugo de 1/2 limón\n" +
                                "• Sal al gusto",
                        true, false,10,"6 Crackers")
                ))
                addRecipe("2024-10-21", listOf(
                    MenuItem("MINI SÁNDWICHES DE QUESO Y JAMÓN", R.drawable.sandjamonqueso,
                        "1.- Colocar la rebanada de jamón y queso entre las rebanadas de pan.\n\n" +
                                "2.- Corta el sándwich en 4 partes pequeñas (cuadrados o triángulos).\n\n" +
                                "3.- Servir como mini bocadillos.",
                        "• 2 rebanadas de pan integral\n" +
                                "• 1 rebanada de jamón bajo en grasa\n" +
                                "• 1 rebanada de queso light",
                        false, false,5,"2 Rebanadas")
                ))
                addRecipe("2024-10-22", listOf(
                    MenuItem("YOGURT CON GRANOLA Y FRUTAS\n", R.drawable.yogurtgranolafrutas,
                        "1.- En un bowl colocar el yogurt\n\n" +
                                "2.- Luego colocar encima la granola y la fruta variada.",
                        "• 1 unidad de yogurt \n" +
                                "• 1 taza de granola\n" +
                                "• 2 tazas de fruta variada que sea de su preferencia (mora, arándanos, frutilla, frambuesa, manzana, plátano, etc)",
                        false, false,5,"1 Ración")
                ))
                addRecipe("2024-10-23", listOf(
                    MenuItem("PALITOS DE MANZANA CON MIEL Y CANELA", R.drawable.manzanamielcanela,
                        "1.- Lavar y cortar la manzana en rodajas o palitos.\n\n" +
                                "2.- Colocar los palitos de manzana en un plato.\n\n" +
                                "3.- Rociar la miel sobre las manzanas.\n\n" +
                                "4.- Espolvorear la canela por encima y mezclar bien.",
                        "• 1 manzana (preferiblemente roja o verde)\n• 1 cucharada de miel\n• 1/2 cucharadita de canela en polvo",
                        true, true,3,"1 Ración")
                ))
                addRecipe("2024-10-24", listOf(
                    MenuItem("MINI PANCAKES DE PLÁTANO", R.drawable.minipancakesplatano,
                        "1.- En un bowl, mezclar todos los ingredientes menos el plátano  (la preparación tiene que quedar espesa).\n\n" +
                                "2.- Después cortar los plátanos en rodajas del grosor que prefieras y cubre cada uno con la mezcla. \n\n" +
                                "3.- Cocínarlos en una sartén antiadherente o aceitada, vuelta y vuelta.\n\n" +
                                "4.- puedes acompañarlos con frutas, miel, pasta de maní o como mas guste.",
                        "• 2 plátanos \n• 1/2 taza de harina\n• 1 cdta de polvo de hornear\n• 1/3 taza de leche\n• 1 huevo",
                        false, false,10,"8 Mini Pancakes")
                ))
                addRecipe("2024-10-25", listOf(
                    MenuItem("PALITOS DE VERDURAS", R.drawable.palitosdeverduras,
                        "1.- Lavar bien todas las verduras. \n\n" +
                                "2.- Pelar la zanahoria y, si prefiere, el pepino (también puede dejar la cáscara del pepino si es fina).\n\n" +
                                "3.- Cortar todas las verduras en tiras del tamaño de un dedo o palitos.\n\n" +
                                "4.- Si desea darles un poco más de sabor, puede exprimir un poco de jugo de limón sobre los palitos de verduras, una pizca de sal, y hierbas secas si lo prefiere. \n\n" +
                                "5.- Puede acompañarlos con hummus, yogurt griego u otra pasta.",
                        "• 1 zanahoria\n• 1 pepino\n• 1 pimentón (rojo, amarillo o verde, según preferencia)\n• 1 tallo de apio",
                        true, true,15,"2 Raciónes")
                ))
                addRecipe("2024-10-28", listOf(
                    MenuItem("WRAP DE POLLO Y VEGETALES", R.drawable.wrapdepollovegetales,
                        "1.- Armar el wrap: Unta la palta en la tortilla, añade el pollo, la lechuga y el tomate.\n\n" +
                                "2.- Enrolla firmemente y corta por la mitad.",
                        "• 1 tortilla integral.\n" +
                                "• 2 rebanadas de pechuga de pollo.\n" +
                                "• 1 hoja de lechuga.\n" +
                                "• 2 rodajas de tomate.\n" +
                                "• 1/2 palta",
                        false, false,5,"1 Ración")
                ))
                addRecipe("2024-10-29", listOf(
                    MenuItem("PALOMITAS DE MAÍZ CASERAS", R.drawable.palomitasmaizcaseras,
                        "1.- Calentar el aceite en una olla grande a fuego medio-alto.\n\n" +
                                "2.- Agregar los granos de maíz y cubrir la olla con una tapa.\n\n" +
                                "3.- Agitar la olla de vez en cuando hasta que las palomitas dejen de estallar.\n\n" +
                                "4.- Retirar del fuego, añadir sal al gusto y mezclar bien.",
                        "• 1/4 taza de granos de maíz para palomitas\n" +
                                "• 1 cucharada de aceite (de preferencia aceite de oliva) \n" +
                                "• Sal al gusto",
                        true, true,15,"")
                ))
                addRecipe("2024-10-30", listOf(
                    MenuItem("MINI TORTILLA DE VERDURAS", R.drawable.minitortilladeverduras,
                        "1.- Batir los huevos en un tazón y añadir las espinacas, pimientos, queso, sal y pimienta.\n\n" +
                                "2.- Calentar el aceite en una sartén a fuego medio.\n\n" +
                                "3.- Verter la mezcla de huevos y cocinar hasta que esté firme por ambos lados.",
                        "• 2 huevos\n" +
                                "• 1/4 taza de espinacas picadas\n" +
                                "• 1/4 taza de pimientos en cubos\n" +
                                "• 1 cucharada de queso rallado (opcional)\n" +
                                "• Sal a gusto\n" +
                                "• 1 cucharadita de aceite de oliva",
                        false, true,10,"")
                ))
                addRecipe("2024-10-31", listOf(
                    MenuItem("PUDIN DE CHÍA Y FRUTAS", R.drawable.pudindechiayfrutas,
                        "1.- En un recipiente, mezclar la leche, semillas de chía y miel.\n\n" +
                                "2.- Deja reposar en el refrigerador durante al menos 2 horas o toda la noche.\n\n" +
                                "3.- Servir el pudín y decorar con frutas frescas.",
                        "• 1 taza de leche descremada \n" +
                                "• 3 cucharadas de semillas de chía\n" +
                                "• 1 cucharada de miel (opcional)\n" +
                                "• Frutas frescas (frutillas, arándano, plátano) para decorar.",
                        false, true,10,"")
                ))
                addRecipe("2024-11-01", listOf(
                    MenuItem("CRACKERS INTEGRALES CON PALTA", R.drawable.crackerpalta,
                        "1.- Moler la palta en un tazón.\n\n" +
                                "2.- Añadir el tomate, jugo de limón y sal, mezclando bien.\n\n" +
                                "3.- Untar la palta a las galletas crackers integrales.",
                        "• 6 Crackers integrales\n" +
                                "• 1 palta maduro\n" +
                                "• 1/2 tomate pequeño en cubos\n" +
                                "• Jugo de 1/2 limón\n" +
                                "• Sal al gusto",
                        true, false,10,"6 Crackers")
                ))
                addRecipe("2024-11-04", listOf(
                    MenuItem("MINI SÁNDWICHES DE QUESO Y JAMÓN", R.drawable.sandjamonqueso,
                        "1.- Colocar la rebanada de jamón y queso entre las rebanadas de pan.\n\n" +
                                "2.- Corta el sándwich en 4 partes pequeñas (cuadrados o triángulos).\n\n" +
                                "3.- Servir como mini bocadillos.",
                        "• 2 rebanadas de pan integral\n" +
                                "• 1 rebanada de jamón bajo en grasa\n" +
                                "• 1 rebanada de queso light",
                        false, false,5,"2 Rebanadas")
                ))
                addRecipe("2024-11-05", listOf(
                    MenuItem("YOGURT CON GRANOLA Y FRUTAS\n", R.drawable.yogurtgranolafrutas,
                        "1.- En un bowl colocar el yogurt\n\n" +
                                "2.- Luego colocar encima la granola y la fruta variada.",
                        "• 1 unidad de yogurt \n" +
                                "• 1 taza de granola\n" +
                                "• 2 tazas de fruta variada que sea de su preferencia (mora, arándanos, frutilla, frambuesa, manzana, plátano, etc)",
                        false, false,5,"1 Ración")
                ))
                addRecipe("2024-11-06", listOf(
                    MenuItem("PALITOS DE MANZANA CON MIEL Y CANELA", R.drawable.palitosdeverduras,
                        "1.- Lavar y cortar la manzana en rodajas o palitos.\n\n" +
                                "2.- Colocar los palitos de manzana en un plato.\n\n" +
                                "3.- Rociar la miel sobre las manzanas.\n\n" +
                                "4.- Espolvorear la canela por encima y mezclar bien.",
                        "• 1 manzana (preferiblemente roja o verde)\n• 1 cucharada de miel\n• 1/2 cucharadita de canela en polvo",
                        true, true,3,"1 Ración")
                ))
                addRecipe("2024-11-07", listOf(
                    MenuItem("MINI PANCAKES DE PLÁTANO", R.drawable.minipancakesplatano,
                        "1.- En un bowl, mezclar todos los ingredientes menos el plátano  (la preparación tiene que quedar espesa).\n\n" +
                                "2.- Después cortar los plátanos en rodajas del grosor que prefieras y cubre cada uno con la mezcla. \n\n" +
                                "3.- Cocínarlos en una sartén antiadherente o aceitada, vuelta y vuelta.\n\n" +
                                "4.- puedes acompañarlos con frutas, miel, pasta de maní o como mas guste.",
                        "• 2 plátanos \n• 1/2 taza de harina\n• 1 cdta de polvo de hornear\n• 1/3 taza de leche\n• 1 huevo",
                        false, false,10,"8 Mini Pancakes")
                ))
                addRecipe("2024-11-08", listOf(
                    MenuItem("PALITOS DE VERDURAS", R.drawable.palitosdeverduras,
                        "1.- Lavar bien todas las verduras. \n\n" +
                                "2.- Pelar la zanahoria y, si prefiere, el pepino (también puede dejar la cáscara del pepino si es fina).\n\n" +
                                "3.- Cortar todas las verduras en tiras del tamaño de un dedo o palitos.\n\n" +
                                "4.- Si desea darles un poco más de sabor, puede exprimir un poco de jugo de limón sobre los palitos de verduras, una pizca de sal, y hierbas secas si lo prefiere. \n\n" +
                                "5.- Puede acompañarlos con hummus, yogurt griego u otra pasta.",
                        "• 1 zanahoria\n• 1 pepino\n• 1 pimentón (rojo, amarillo o verde, según preferencia)\n• 1 tallo de apio",
                        true, true,15,"2 Raciónes")
                ))
            }
            "11 A 14 AÑOS" -> {
                addRecipe("2024-10-14", listOf(
                    MenuItem("BARRITAS DE CEREAL CASERA", R.drawable.barracereal,
                        "1.- Tostar los copos de avena y los frutos secos en una sartén.\n\n" +
                                "2. Cortar las pasas y los frutos secos.\n\n" +
                                "3. Mezclar todos los ingredientes en un bowl.\n\n" +
                                "4. En una olla, calentar la miel hasta que haga burbujas y adquiera punto caramelo.\n\n" +
                                "5. Agregar la mezcla de ingredientes secos a la miel y integrar bien.\n\n" +
                                "6. Verter en una bandeja aceitada y aplastar para compactar.\n\n" +
                                "7. Enfriar completamente, cortar en barritas y ¡listo!.",
                        "• 2 tazas de copos de avena sin gluten\n• 1/2 taza de frutos secos variados\n" +
                                "• 1/4 taza de pasas\n• 1/3 taza de miel\n• 1 cucharadita de canela",
                        true, true,3,"3 Barritas")
                ))
                addRecipe("2024-10-15", listOf(
                    MenuItem("GALLETA DE AVENA Y PLÁTANO", R.drawable.galletasavenaplatano,
                        "1.- Pelar los plátanos y moler en un puré suave con un tenedor.\n\n" +
                                "2. Agregar los copos de avena, canela, esencia de vainilla y una pizca de sal.\n\n" +
                                "3. Mezclar bien e incorporar los ingredientes opcionales si los deseas.\n\n" +
                                "4. Formar bolitas con la mezcla y colocar en una bandeja con papel de horno.\n\n" +
                                "5. Aplastar un poco cada bolita con los dedos para darles forma de galleta.\n\n" +
                                "6. Hornear unos 14-15 minutos hasta que los bordes se empiecen a dorar.\n\n" +
                                "7. Sacar del horno y ¡degustar!",
                        "• 2 plátanos grandes maduros\n• 2 tazas de copos de avena\n" +
                                "• 2 cdas de miel (u otras alternativas como endulzantes)\n• Frutos secos\n• 1/4 taza pasas\n" +
                                "• 1/2 cdta de canela\n• 1/2 cdta de extracto de vainilla\n• 1/2 taza de pasas, nueces picadas, coco, chips de chocolate, arándanos… (opcional)",
                        true, true,20,"16 Galletas")
                ))
                addRecipe("2024-10-16", listOf(
                    MenuItem("BOLITAS ENERGÉTICAS  DÁTILES Y AVENA", R.drawable.bolitasenergeticas,
                        "1.- Tostar las almendras a 180°C durante 5-10 minutos.\n\n" +
                                "2. Remojar los dátiles en agua hirviendo durante 10 minutos y escurrir.\n\n" +
                                "3. Procesar las almendras, dátiles y demás ingredientes hasta obtener una pasta.\n\n" +
                                "4. Formar bolitas y ajustar la consistencia con agua si es necesario.\n\n" +
                                "5. Revolver las bolitas en cacao en polvo y retirar el exceso.\n\n" +
                                "6. Guardar en el refrigerador por 5 días o en el congelador por 3 meses. ¡Listas para disfrutar!",
                        "• 120 g almendras\n• 320 g dátiles sin pepa\n• 1 taza de avena\n• 1 cdta sal.\n• 1 cdta cacao en polvo\n• Agua",
                        true, true,25,"20 - 25 Bolitas")
                ))
                addRecipe("2024-10-17", listOf(
                    MenuItem("PUDDING DE AVENA Y CHÍA", R.drawable.puddingavena,
                        "1.- Lavar bien el frasco de vidrio con tapa.\n\n" +
                                "2. Mezclar la avena, las semillas de chía, la leche y el edulcorante dentro del frasco.\n\n" +
                                "3. Añadir el extracto de vainilla y mezclar bien.\n\n" +
                                "4. Cubrir el frasco y dejar reposar en el refrigerador durante al menos 4 horas o toda la noche.\n\n" +
                                "5. Revolver la mezcla para asegurarse de que esté uniforme y cremosa.\n\n" +
                                "6. Lavar y pelar las frutas frescas, y luego decorar el pudding con ellas.",
                        "• 1 vaso de leche o bebida vegetal\n• 4 cdas de avena\n• 2 cdas de semillas de chía\n" +
                                "• 1 cda de miel, o edulcorante a elección\n• 1/2 cdta de extracto de vainilla\n• Frutas frescas a elección",
                        false, true,5,"1 Ración")
                ))
                addRecipe("2024-10-18", listOf(
                    MenuItem("BURRITOS DE PLÁTANO", R.drawable.burritosdeplatano,
                        "1.- Untar la cucharadita de mantequilla de maní en la tortilla, esparciéndola de manera uniforme.\n\n" +
                                "2. Colocar el plátano en el borde de la tortilla.\n\n" +
                                "3. Enrollar la tortilla para cerrarla.\n\n" +
                                "4. Cortar el burrito por la mitad y ¡DISFRUTAR!",
                        "• 1 fajita integral\n• 1 plátano\n• 1 cdta de mantequilla de maní",
                        true, false,5,"1 Ración")
                ))
                addRecipe("2024-10-21", listOf(
                    MenuItem("BROCHETAS DE FRUTA", R.drawable.brochetasfruta,
                        "1.- Lavar bien las frutas.\n\n" +
                                "2. Cortar las frutas en cuadrados.\n\n" +
                                "3. Ensartar las frutas en palitos para brochetas, alternando los colores para que sean atractivas",
                        "• 8 frutillas\n• 1 plátano\n• 1 manzana\n• 1 mandarina\n• 8 uvas\n• Brochetas",
                        true, true,15,"8 Brochetas")
                ))
                addRecipe("2024-10-22", listOf(
                    MenuItem("COCADAS SALUDABLES", R.drawable.cocadassaludables,
                        "1.- Moler el plátano con un tenedor.\n\n" +
                                "2. Agregar el coco y mezclar hasta obtener una masa consistente.\n\n" +
                                "3. Añadir las nueces trozadas y mezclar bien.\n\n" +
                                "4. Formar las cocadas con la mezcla.\n\n" +
                                "5. Fundir el chocolate semiamargo.\n\n" +
                                "6. Bañar las cocadas en el chocolate fundido y decorar.\n\n" +
                                "7. Dejar enfriar en el refrigerador por un par de horas antes de disfrutar.",
                        "• 1 plátano bien maduro\n• 2 cdas de coco rallado\n• 2 cdas de nueces o Cualquier fruto seco de preferencia\n• Chocolate semi amargo",
                        false, true,15,"15 Cocadas")
                ))
                addRecipe("2024-10-23", listOf(
                    MenuItem("GRANOLA CASERA", R.drawable.granolacasera,
                        "1.- Precalentar el horno a 160°C.\n\n" +
                                "2.- Mezclar la avena, las almendras, el mix de semillas, la canela, y la sal en un bowl.\n\n" +
                                "3.- Añadir la esencia de vainilla, el aceite, y la miel; revolver hasta integrar.\n\n" +
                                "4.- Extender la mezcla en una bandeja engrasada o forrada con papel mantequilla.\n\n" +
                                "5.- Hornear por 15 minutos, removiendo a los 7 minutos para asegurar un horneado uniforme.\n\n" +
                                "6.- Dejar enfriar la mezcla.\n\n" +
                                "7.- Agregar las pasas y guardar en un envase hermético.",
                        "• 2 tazas de avena tradicional\n• 1/2 taza de almendras laminadas (o cualquier otro fruto seco)\n" +
                                "• 1/2 taza de mix de semillas (chía, linaza, maravillas, etc.)\n• 1/2 cdta de Canela Molida\n• 1/4 cdta de Sal\n" +
                                "• 1/2 cdta de Esencia de Vainilla \n• 2 cdas de aceite vegetal\n" +
                                "• 4 cdas de miel de maple o miel de abejas\n• Pasas a gusto (opcional)",
                        true, true,30,"")
                ))
                addRecipe("2024-10-24", listOf(
                    MenuItem("MUFFIN DE ZANAHORIA", R.drawable.muffinzanahoria,
                        "1.- Precalentar el horno a 180°C y preparar 10 moldes para muffins con papelitos o enmantequillando el molde.\n\n" +
                                "2.- Batir el huevo con el azúcar, la miel, y la esencia de vainilla durante 3-4 minutos hasta obtener una mezcla cremosa.\n\n" +
                                "3.- Añadir las harinas, la zanahoria, la canela, y el polvo de hornear; batir solo hasta integrar.\n\n" +
                                "4.- Dividir la mezcla entre los moldes y espolvorear con avena.\n\n" +
                                "5.- Hornear por 25 minutos o hasta que un palito insertado en el centro salga seco.\n\n" +
                                "6.- Dejar enfriar antes de disfrutar.",
                        "• 1 huevo\n" +
                                "• 3 cucharadas de aceite de maravilla\n• 1/2 taza de azúcar\n• 1 cucharadita de esencia de vainilla\n" +
                                "• 1 taza de harina de avena o blanca\n• 1 cdta de canela molida\n• 1 cdta de polvo de hornear\n" +
                                "• Hojuelas de avena para espolvorear\n• 1 taza de zanahoria rallada",
                        false, false,45,"10 Muffins")
                ))
                addRecipe("2024-10-25", listOf(
                    MenuItem("GARBANZOS CROCANTES", R.drawable.garbanzoscrocantes,
                        "1.- Precalentar el horno a 180°C.\n\n" +
                                "2.- En un bowl, juntar todos los ingredientes y mezclar bien para sazonar los garbanzos uniformemente.\n\n" +
                                "3.- Colocar los garbanzos en una bandeja y hornear por 30 a 35 minutos, vigilando que no se peguen o quemen.\n\n" +
                                "4.- Retirar del horno cuando estén crocantes.\n\n" +
                                "5.- Consumir con moderación y disfrutar.",
                        "• 2 Tazas de garbanzos cocidos\n• 1/2 Cdta de sal\n• 1 Cdta de pimentón en polvo\n" +
                                "• 1 Cdta de ajo de ajo en polvo\n• 2 Cdtas de orégano\n• 3 Cdas de aceite de oliva.",
                        true, true,40,"")
                ))
                addRecipe("2024-10-28", listOf(
                    MenuItem("BARRITAS DE CEREAL CASERA", R.drawable.barracereal,
                        "1.- Tostar los copos de avena y los frutos secos en una sartén.\n\n" +
                                "2. Cortar las pasas y los frutos secos.\n\n" +
                                "3. Mezclar todos los ingredientes en un bowl.\n\n" +
                                "4. En una olla, calentar la miel hasta que haga burbujas y adquiera punto caramelo.\n\n" +
                                "5. Agregar la mezcla de ingredientes secos a la miel y integrar bien.\n\n" +
                                "6. Verter en una bandeja aceitada y aplastar para compactar.\n\n" +
                                "7. Enfriar completamente, cortar en barritas y ¡listo!.",
                        "• 2 tazas de copos de avena sin gluten\n• 1/2 taza de frutos secos variados\n" +
                                "• 1/4 taza de pasas\n• 1/3 taza de miel\n• 1 cucharadita de canela",
                        true, true,3,"3 Barritas")
                ))
                addRecipe("2024-10-29", listOf(
                    MenuItem("GALLETA DE AVENA Y PLÁTANO", R.drawable.galletasavenaplatano,
                        "1.- Pelar los plátanos y moler en un puré suave con un tenedor.\n\n" +
                                "2. Agregar los copos de avena, canela, esencia de vainilla y una pizca de sal.\n\n" +
                                "3. Mezclar bien e incorporar los ingredientes opcionales si los deseas.\n\n" +
                                "4. Formar bolitas con la mezcla y colocar en una bandeja con papel de horno.\n\n" +
                                "5. Aplastar un poco cada bolita con los dedos para darles forma de galleta.\n\n" +
                                "6. Hornear unos 14-15 minutos hasta que los bordes se empiecen a dorar.\n\n" +
                                "7. Sacar del horno y ¡degustar!",
                        "• 2 plátanos grandes maduros\n• 2 tazas de copos de avena\n" +
                                "• 2 cdas de miel (u otras alternativas como endulzantes)\n• Frutos secos\n• 1/4 taza pasas\n" +
                                "• 1/2 cdta de canela\n• 1/2 cdta de extracto de vainilla\n• 1/2 taza de pasas, nueces picadas, coco, chips de chocolate, arándanos… (opcional)",
                        true, true,20,"16 Galletas")
                ))
                addRecipe("2024-10-30", listOf(
                    MenuItem("BOLITAS ENERGÉTICAS  DÁTILES Y AVENA", R.drawable.bolitasenergeticas,
                        "1.- Tostar las almendras a 180°C durante 5-10 minutos.\n\n" +
                                "2. Remojar los dátiles en agua hirviendo durante 10 minutos y escurrir.\n\n" +
                                "3. Procesar las almendras, dátiles y demás ingredientes hasta obtener una pasta.\n\n" +
                                "4. Formar bolitas y ajustar la consistencia con agua si es necesario.\n\n" +
                                "5. Revolver las bolitas en cacao en polvo y retirar el exceso.\n\n" +
                                "6. Guardar en el refrigerador por 5 días o en el congelador por 3 meses. ¡Listas para disfrutar!",
                        "• 120 g almendras\n• 320 g dátiles sin pepa\n• 1 taza de avena\n• 1 cdta sal.\n• 1 cdta cacao en polvo\n• Agua",
                        true, true,25,"20 - 25 Bolitas")
                ))
                addRecipe("2024-10-31", listOf(
                    MenuItem("PUDDING DE AVENA Y CHÍA", R.drawable.puddingavena,
                        "1.- Lavar bien el frasco de vidrio con tapa.\n\n" +
                                "2. Mezclar la avena, las semillas de chía, la leche y el edulcorante dentro del frasco.\n\n" +
                                "3. Añadir el extracto de vainilla y mezclar bien.\n\n" +
                                "4. Cubrir el frasco y dejar reposar en el refrigerador durante al menos 4 horas o toda la noche.\n\n" +
                                "5. Revolver la mezcla para asegurarse de que esté uniforme y cremosa.\n\n" +
                                "6. Lavar y pelar las frutas frescas, y luego decorar el pudding con ellas.",
                        "• 1 vaso de leche o bebida vegetal\n• 4 cdas de avena\n• 2 cdas de semillas de chía\n" +
                                "• 1 cda de miel, o edulcorante a elección\n• 1/2 cdta de extracto de vainilla\n• Frutas frescas a elección",
                        false, true,5,"1 Ración")
                ))
                addRecipe("2024-11-01", listOf(
                    MenuItem("BURRITOS DE PLÁTANO", R.drawable.burritosdeplatano,
                        "1.- Untar la cucharadita de mantequilla de maní en la tortilla, esparciéndola de manera uniforme.\n\n" +
                                "2. Colocar el plátano en el borde de la tortilla.\n\n" +
                                "3. Enrollar la tortilla para cerrarla.\n\n" +
                                "4. Cortar el burrito por la mitad y ¡DISFRUTAR!",
                        "• 1 fajita integral\n• 1 plátano\n• 1 cdta de mantequilla de maní",
                        true, false,5,"1 Ración")
                ))
                addRecipe("2024-11-04", listOf(
                    MenuItem("BROCHETAS DE FRUTA", R.drawable.brochetasfruta,
                        "1.- Lavar bien las frutas.\n\n" +
                                "2. Cortar las frutas en cuadrados.\n\n" +
                                "3. Ensartar las frutas en palitos para brochetas, alternando los colores para que sean atractivas",
                        "• 8 frutillas\n• 1 plátano\n• 1 manzana\n• 1 mandarina\n• 8 uvas\n• Brochetas",
                        true, true,15,"8 Brochetas")
                ))
                addRecipe("2024-11-05", listOf(
                    MenuItem("COCADAS SALUDABLES", R.drawable.cocadassaludables,
                        "1.- Moler el plátano con un tenedor.\n\n" +
                                "2. Agregar el coco y mezclar hasta obtener una masa consistente.\n\n" +
                                "3. Añadir las nueces trozadas y mezclar bien.\n\n" +
                                "4. Formar las cocadas con la mezcla.\n\n" +
                                "5. Fundir el chocolate semiamargo.\n\n" +
                                "6. Bañar las cocadas en el chocolate fundido y decorar.\n\n" +
                                "7. Dejar enfriar en el refrigerador por un par de horas antes de disfrutar.",
                        "• 1 plátano bien maduro\n• 2 cdas de coco rallado\n• 2 cdas de nueces o Cualquier fruto seco de preferencia\n• Chocolate semi amargo",
                        false, true,15,"15 Cocadas")
                ))
                addRecipe("2024-11-06", listOf(
                    MenuItem("GRANOLA CASERA", R.drawable.granolacasera,
                        "1.- Precalentar el horno a 160°C.\n\n" +
                                "2.- Mezclar la avena, las almendras, el mix de semillas, la canela, y la sal en un bowl.\n\n" +
                                "3.- Añadir la esencia de vainilla, el aceite, y la miel; revolver hasta integrar.\n\n" +
                                "4.- Extender la mezcla en una bandeja engrasada o forrada con papel mantequilla.\n\n" +
                                "5.- Hornear por 15 minutos, removiendo a los 7 minutos para asegurar un horneado uniforme.\n\n" +
                                "6.- Dejar enfriar la mezcla.\n\n" +
                                "7.- Agregar las pasas y guardar en un envase hermético.",
                        "• 2 tazas de avena tradicional\n• 1/2 taza de almendras laminadas (o cualquier otro fruto seco)\n" +
                                "• 1/2 taza de mix de semillas (chía, linaza, maravillas, etc.)\n• 1/2 cdta de Canela Molida\n• 1/4 cdta de Sal\n" +
                                "• 1/2 cdta de Esencia de Vainilla \n• 2 cdas de aceite vegetal\n" +
                                "• 4 cdas de miel de maple o miel de abejas\n• Pasas a gusto (opcional)",
                        true, true,30,"")
                ))
                addRecipe("2024-11-07", listOf(
                    MenuItem("MUFFIN DE ZANAHORIA", R.drawable.muffinzanahoria,
                        "1.- Precalentar el horno a 180°C y preparar 10 moldes para muffins con papelitos o enmantequillando el molde.\n\n" +
                                "2.- Batir el huevo con el azúcar, la miel, y la esencia de vainilla durante 3-4 minutos hasta obtener una mezcla cremosa.\n\n" +
                                "3.- Añadir las harinas, la zanahoria, la canela, y el polvo de hornear; batir solo hasta integrar.\n\n" +
                                "4.- Dividir la mezcla entre los moldes y espolvorear con avena.\n\n" +
                                "5.- Hornear por 25 minutos o hasta que un palito insertado en el centro salga seco.\n\n" +
                                "6.- Dejar enfriar antes de disfrutar.",
                        "• 1 huevo\n" +
                                "• 3 cucharadas de aceite de maravilla\n• 1/2 taza de azúcar\n• 1 cucharadita de esencia de vainilla\n" +
                                "• 1 taza de harina de avena o blanca\n• 1 cdta de canela molida\n• 1 cdta de polvo de hornear\n" +
                                "• Hojuelas de avena para espolvorear\n• 1 taza de zanahoria rallada",
                        false, false,45,"10 Muffins")
                ))
                addRecipe("2024-11-08", listOf(
                    MenuItem("GARBANZOS CROCANTES", R.drawable.garbanzoscrocantes,
                        "1.- Precalentar el horno a 180°C.\n\n" +
                                "2.- En un bowl, juntar todos los ingredientes y mezclar bien para sazonar los garbanzos uniformemente.\n\n" +
                                "3.- Colocar los garbanzos en una bandeja y hornear por 30 a 35 minutos, vigilando que no se peguen o quemen.\n\n" +
                                "4.- Retirar del horno cuando estén crocantes.\n\n" +
                                "5.- Consumir con moderación y disfrutar.",
                        "• 2 Tazas de garbanzos cocidos\n• 1/2 Cdta de sal\n• 1 Cdta de pimentón en polvo\n" +
                                "• 1 Cdta de ajo de ajo en polvo\n• 2 Cdtas de orégano\n• 3 Cdas de aceite de oliva.",
                        true, true,40,"")
                ))
            }
            "15 A 18 AÑOS" -> {
                addRecipe("2024-10-14", listOf(
                    MenuItem("QUESADILLAS SALUDABLES", R.drawable.quesadillassaludables,
                        "1.- Pelar y cocer el huevo en agua hirviendo. \n\n" +
                                "2.- Cuando esté listo, cortar en rodajas.\n\n" +
                                "3.- Pelar la palta y moler en un puré. \n\n" +
                                "4.- Rebanar el tomate en rodajas.\n\n" +
                                "5.- Cortar el pan pita por la mitad. \n\n" +
                                "6.- Agregar el huevo, junto con el tomate, el puré de palta y la lechuga.\n\n" +
                                "7.- Cortar el pan en 4 trozos iguales y ¡SERVIR!",
                        "• 1 pan pita\n" +
                                "• 1 huevo\n" +
                                "• 1/2 tomate\n" +
                                "• 1/4 de taza de lechuga\n" +
                                "• 1/2 palta",
                        false, false,7,"1 Ración"),
                ))
                addRecipe("2024-10-15", listOf(
                    MenuItem("PAN INTEGRAL CON PALTA Y JAMÓN", R.drawable.panpaltajamon,
                        "1.- Cortar por la mitad una palta.\n\n" +
                                "2.- Sacarle la cáscara y moler.\n\n" +
                                "3.- aliñar con sal.\n\n" +
                                "4.- Luego a las 2 rebanadas de pan molde integral se le agrega la palta y la rebanada de jamón.",
                        "• 2 rebanadas de pan molde integral\n" +
                                "• 1/2 palta\n" +
                                "• 1 pizca de sal\n" +
                                "• 1 rebanada de jamón",
                        false, false,7,"1 Ración")
                ))
                addRecipe("2024-10-16", listOf(
                    MenuItem("HOT CAKES DE AVENA Y FRUTAS", R.drawable.hotcakesdeavenafrutas,
                        "1.- Colocar la avena en una licuadora y triturar por unos segundos hasta obtener harina de avena. \n\n" +
                                "2.- Añadir los dos huevos y el plátano a la licuadora con la avena y triturar hasta que la mezcla quede homogénea.\n\n" +
                                "3.- Encender la cocina y calentar un sartén a fuego medio con un poco de aceite. \n\n" +
                                "4.- Verter una porción de la mezcla en el sartén al tamaño deseado.\n\n" +
                                "5.- Esperar a que aparezcan burbujas en la superficie y luego girar, cocinando por 2-3 minutos más.\n\n" +
                                "6.- Servir los panqueques con fruta y miel.",
                        "• 3/4 taza de avena instantánea\n" +
                                "• 2 huevos\n" +
                                "• 1 plátano mediano\n" +
                                "• Aceite\n" +
                                "• Miel\n" +
                                "• Frutas a elección ( frutilla, arandanos, mora, frambuesa, etc)",
                        false, true,30,"")
                ))
                addRecipe("2024-10-17", listOf(
                    MenuItem("CHIPS DE MANZANA", R.drawable.chipsdemanzana,
                        "1.- Precalentar el horno a 120°C.\n\n" +
                                "2.- Lavar y cortar las manzanas en rodajas finas, quitando el corazón.\n\n" +
                                "3.- Colocar las rodajas en una bandeja para hornear y espolvorea canela.\n\n" +
                                "4.- Hornear durante 1-2 horas hasta que estén crujientes.",
                        "• 2 manzanas\n" +
                                "• Canela al gusto",
                        true, true,90,"1 Ración")
                ))
                addRecipe("2024-10-18", listOf(
                    MenuItem("FRUTOS SECOS CON COMPOTA DE FRUTAS", R.drawable.frutossecoscompota,
                        "Abrir los frutos secos y la compota, y ¡SERVIR!",
                        "• 1 paquete individual de frutos secos\n" +
                                "• 1 compota individual (sabor de su preferencia)",
                        true, true,1,"1 Ración")
                ))
                addRecipe("2024-10-21", listOf(
                    MenuItem("GALLETAS DE ARROZ CON MANTEQUILLA MANÍ Y FRUTA", R.drawable.galletasdearrozmaniyfruta,
                        "1.- Untar la mantequilla de maní sobre las galletas de arroz\n\n" +
                                "2.- Lavar y pelar la futa\n\n" +
                                "3.- Cortar en rodajas\n\n" +
                                "4.- Colocar sobre la mantequilla de maní y ¡A disfrutar!",
                        "• 2 galletas de arroz\n" +
                                "• 2 cdtas de mantequilla de maní\n" +
                                "• Fruta a elección",
                        true, true,5,"1 Ración")
                ))
                addRecipe("2024-10-22", listOf(
                    MenuItem("HUEVO DURO", R.drawable.huevoduro,
                        "1.- Hervir los huevos en agua durante 10-12 minutos.\n\n" +
                                "2.- Enfriar en agua fría, pelar y cortar en mitades.\n\n" +
                                "3.- Sazonar con sal y pimienta si lo desea.",
                        "• 2 huevos",
                        false, true,12,"1 Ración")
                ))
                addRecipe("2024-10-23", listOf(
                    MenuItem("PALITOS DE APIO CON QUESO CREMA", R.drawable.palitosdeapioquesocrema,
                        "Cortar el apio en palitos y servir con queso crema como salsa. Es un snack crujiente y delicioso.",
                        "• 2 tallos de apio\n" +
                                "• 2 cucharadas de queso crema",
                        false, true,12,"1 Ración")
                ))
                addRecipe("2024-10-24", listOf(
                    MenuItem("YOGURT CON FRUTOS SECOS", R.drawable.yogurtconfrutossecos,
                        "1.- En un bol, colocar el yogur.\n\n" +
                                "2.- Agrega los frutos secos por encima.\n\n" +
                                "3.- Mezclar y disfrutar.",
                        "• 1 unidad de yogurt\n" +
                                "• 1/4 taza de frutos secos (nueces, almendras, etc.) (aproximadamente 30 g)",
                        false, true,3,"1 Ración")
                ))
                addRecipe("2024-10-25", listOf(
                    MenuItem("GALLETAS DE AVENA Y MANZANA", R.drawable.galletaavenamanzana,
                        "1.- Precalentar el horno a 180°C. \n\n" +
                                "2.- Mezclar todos los ingredientes en un bowl. \n\n" +
                                "3.- Formar pequeñas galletas y colocarlas en una bandeja para hornear. \n\n" +
                                "4.- Hornear durante 12-15 minutos. \n\n" +
                                "5.- Dejar enfriar y ¡DISFRUTAR!",
                        "• 1 taza de copos de avena\n" +
                                "• 1 manzana rallada\n" +
                                "• 1/4 taza de miel\n" +
                                "• 1 cucharadita de canela en polvo",
                        true, true,15,"1 Ración")
                ))
                addRecipe("2024-10-28", listOf(
                    MenuItem("QUESADILLAS SALUDABLES", R.drawable.quesadillassaludables,
                        "1.- Pelar y cocer el huevo en agua hirviendo. \n\n" +
                                "2.- Cuando esté listo, cortar en rodajas.\n\n" +
                                "3.- Pelar la palta y moler en un puré. \n\n" +
                                "4.- Rebanar el tomate en rodajas.\n\n" +
                                "5.- Cortar el pan pita por la mitad. \n\n" +
                                "6.- Agregar el huevo, junto con el tomate, el puré de palta y la lechuga.\n\n" +
                                "7.- Cortar el pan en 4 trozos iguales y ¡SERVIR!",
                        "• 1 pan pita\n" +
                                "• 1 huevo\n" +
                                "• 1/2 tomate\n" +
                                "• 1/4 de taza de lechuga\n" +
                                "• 1/2 palta",
                        false, false,7,"1 Ración"),
                ))
                addRecipe("2024-10-29", listOf(
                    MenuItem("PAN INTEGRAL CON PALTA Y JAMÓN", R.drawable.panpaltajamon,
                        "1.- Cortar por la mitad una palta.\n\n" +
                                "2.- Sacarle la cáscara y moler.\n\n" +
                                "3.- aliñar con sal.\n\n" +
                                "4.- Luego a las 2 rebanadas de pan molde integral se le agrega la palta y la rebanada de jamón.",
                        "• 2 rebanadas de pan molde integral\n" +
                                "• 1/2 palta\n" +
                                "• 1 pizca de sal\n" +
                                "• 1 rebanada de jamón",
                        false, false,7,"1 Ración")
                ))
                addRecipe("2024-10-30", listOf(
                    MenuItem("HOT CAKES DE AVENA Y FRUTAS", R.drawable.hotcakesdeavenafrutas,
                        "1.- Colocar la avena en una licuadora y triturar por unos segundos hasta obtener harina de avena. \n\n" +
                                "2.- Añadir los dos huevos y el plátano a la licuadora con la avena y triturar hasta que la mezcla quede homogénea.\n\n" +
                                "3.- Encender la cocina y calentar un sartén a fuego medio con un poco de aceite. \n\n" +
                                "4.- Verter una porción de la mezcla en el sartén al tamaño deseado.\n\n" +
                                "5.- Esperar a que aparezcan burbujas en la superficie y luego girar, cocinando por 2-3 minutos más.\n\n" +
                                "6.- Servir los panqueques con fruta y miel.",
                        "• 3/4 taza de avena instantánea\n" +
                                "• 2 huevos\n" +
                                "• 1 plátano mediano\n" +
                                "• Aceite\n" +
                                "• Miel\n" +
                                "• Frutas a elección ( frutilla, arandanos, mora, frambuesa, etc)",
                        false, true,30,"")
                ))
                addRecipe("2024-10-31", listOf(
                    MenuItem("CHIPS DE MANZANA", R.drawable.chipsdemanzana,
                        "1.- Precalentar el horno a 120°C.\n\n" +
                                "2.- Lavar y cortar las manzanas en rodajas finas, quitando el corazón.\n\n" +
                                "3.- Colocar las rodajas en una bandeja para hornear y espolvorea canela.\n\n" +
                                "4.- Hornear durante 1-2 horas hasta que estén crujientes.",
                        "• 2 manzanas\n" +
                                "• Canela al gusto",
                        true, true,90,"1 Ración")
                ))
                addRecipe("2024-11-01", listOf(
                    MenuItem("FRUTOS SECOS CON COMPOTA DE FRUTAS", R.drawable.frutossecoscompota,
                        "Abrir los frutos secos y la compota, y ¡SERVIR!",
                        "• 1 paquete individual de frutos secos\n" +
                                "• 1 compota individual (sabor de su preferencia)",
                        true, true,1,"1 Ración")
                ))
                addRecipe("2024-11-04", listOf(
                    MenuItem("GALLETAS DE ARROZ CON MANTEQUILLA MANÍ Y FRUTA", R.drawable.galletasdearrozmaniyfruta,
                        "1.- Untar la mantequilla de maní sobre las galletas de arroz\n\n" +
                                "2.- Lavar y pelar la futa\n\n" +
                                "3.- Cortar en rodajas\n\n" +
                                "4.- Colocar sobre la mantequilla de maní y ¡A disfrutar!",
                        "• 2 galletas de arroz\n" +
                                "• 2 cdtas de mantequilla de maní\n" +
                                "• Fruta a elección",
                        true, true,5,"1 Ración")
                ))
                addRecipe("2024-11-05", listOf(
                    MenuItem("HUEVO DURO", R.drawable.huevoduro,
                        "1.- Hervir los huevos en agua durante 10-12 minutos.\n\n" +
                                "2.- Enfriar en agua fría, pelar y cortar en mitades.\n\n" +
                                "3.- Sazonar con sal y pimienta si lo desea.",
                        "• 2 huevos",
                        false, true,12,"1 Ración")
                ))
                addRecipe("2024-11-06", listOf(
                    MenuItem("PALITOS DE APIO CON QUESO CREMA", R.drawable.palitosdeapioquesocrema,
                        "Cortar el apio en palitos y servir con queso crema como salsa. Es un snack crujiente y delicioso.",
                        "• 2 tallos de apio\n" +
                                "• 2 cucharadas de queso crema",
                        false, true,12,"1 Ración")
                ))
                addRecipe("2024-11-07", listOf(
                    MenuItem("YOGURT CON FRUTOS SECOS", R.drawable.yogurtconfrutossecos,
                        "1.- En un bol, colocar el yogur.\n\n" +
                                "2.- Agrega los frutos secos por encima.\n\n" +
                                "3.- Mezclar y disfrutar.",
                        "• 1 unidad de yogurt\n" +
                                "• 1/4 taza de frutos secos (nueces, almendras, etc.) (aproximadamente 30 g)",
                        false, true,3,"1 Ración")
                ))
                addRecipe("2024-11-08", listOf(
                    MenuItem("GALLETAS DE AVENA Y MANZANA", R.drawable.galletaavenamanzana,
                        "1.- Precalentar el horno a 180°C. \n\n" +
                                "2.- Mezclar todos los ingredientes en un bowl. \n\n" +
                                "3.- Formar pequeñas galletas y colocarlas en una bandeja para hornear. \n\n" +
                                "4.- Hornear durante 12-15 minutos. \n\n" +
                                "5.- Dejar enfriar y ¡DISFRUTAR!",
                        "• 1 taza de copos de avena\n" +
                                "• 1 manzana rallada\n" +
                                "• 1/4 taza de miel\n" +
                                "• 1 cucharadita de canela en polvo",
                        true, true,15,"1 Ración")
                ))
            }
        }
    }

    private fun addRecipe(date: String, recipes: List<MenuItem>) {
        recipesByDate[date] = recipes
    }

    private fun updateWeekMenu() {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.WEEK_OF_YEAR, currentWeek)
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)

        val buttons = listOf(btnMonday, btnTuesday, btnWednesday, btnThursday, btnFriday)
        buttons.forEach { button ->
            val dateText = formatDate(calendar.time)
            val dateKey = formatDateKey(calendar.time)
            val recipes = recipesByDate[dateKey]

            button.text = dateText

            if (recipes != null && recipes.isNotEmpty()) {
                val originalDrawable = ContextCompat.getDrawable(this, recipes[0].imageResId)
                val bitmap = (originalDrawable as BitmapDrawable).bitmap
                val circularBitmapDrawable = RoundedBitmapDrawableFactory.create(resources, bitmap)
                circularBitmapDrawable.isCircular = true

                // Aumentar el tamaño de la imagen
                val imageSize = 230 // Puedes ajustar este valor según lo grande que quieras la imagen
                circularBitmapDrawable.setBounds(0, 0, imageSize, imageSize)

                button.setCompoundDrawables(circularBitmapDrawable, null, null, null)

                // Ajustar el padding del botón para acomodar la imagen más grande
                button.compoundDrawablePadding = 30 // Espacio entre la imagen y el texto

                button.setOnClickListener {
                    openRecipeSelectionActivity(dateKey)
                }
                button.isEnabled = true
            } else {
                button.setCompoundDrawables(null, null, null, null)
                button.isEnabled = false
                button.setOnClickListener(null)
            }

            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }
    }

    private fun formatDate(date: Date): String {
        val sdf = SimpleDateFormat("EEEE d MMMM", Locale("es", "ES"))
        return sdf.format(date).capitalize()
    }

    private fun formatDateKey(date: Date): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale("es", "ES"))
        return sdf.format(date)
    }

    private fun openRecipeSelectionActivity(dateKey: String) {
        val intent = Intent(this, RecipeSelectionActivity::class.java).apply {
            putExtra("DATE_KEY", dateKey)
            putParcelableArrayListExtra("RECIPES", ArrayList(recipesByDate[dateKey] ?: emptyList()))
            putExtra("AGE_GROUP", ageGroup)
        }
        startActivity(intent)
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