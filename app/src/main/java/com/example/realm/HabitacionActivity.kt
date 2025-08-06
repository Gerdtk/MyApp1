package com.example.realm

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

class HabitacionActivity : AppCompatActivity() {
    private lateinit var realm: Realm
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_habitacion)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
                val config = RealmConfiguration.Builder(
                    schema = setOf(Habitacion::class)
                )
                    .schemaVersion(2)
                    .build()
                realm = Realm.open(config)

                val spinnerAccion = findViewById<Spinner>(R.id.spinnerAccion)
                val layoutInsertar = findViewById<LinearLayout>(R.id.layoutInsertar)
                val layoutEliminar = findViewById<LinearLayout>(R.id.layoutEliminar)
                val layoutModificar = findViewById<LinearLayout>(R.id.layoutModi)
                val textMostar = findViewById<TextView>(R.id.tvMos)




                val Nombre = findViewById<EditText>(R.id.inCIN)
                val Edad = findViewById<EditText>(R.id.inCOUT)
                val Tipo = findViewById<EditText>(R.id.inTipo)
                val Estatus = findViewById<EditText>(R.id.inEstatus)
                val spinnerMod = findViewById<Spinner>(R.id.spinModificar)
                val spinnerEli = findViewById<Spinner>(R.id.spinEliminar)

                val Nombrem = findViewById<EditText>(R.id.inNombreM)
                val Edadm = findViewById<EditText>(R.id.inEdadM)
                val Tipom = findViewById<EditText>(R.id.inTipoM)
                val Viajem = findViewById<EditText>(R.id.inViajeIdM)


                val btnGu = findViewById<Button>(R.id.btnG)
                val btnGuM = findViewById<Button>(R.id.btnGM)
                val btnEli = findViewById<Button>(R.id.btnEli)
                val crud = HCRUD(realm)
                val listaUs = crud.obtenerTodos()
                val nombres = listaUs.map { "(${it.idh}) ${it.typo}  ${it.estatus} ${it.checkIn} ${it.checkOut} " }



                val acciones = listOf("Insertar", "Mostrar", "Modificar", "Eliminar")
                val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, acciones)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerAccion.adapter = adapter

                spinnerAccion.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        when (acciones[position]) {
                            "Insertar" -> {
                                layoutInsertar.visibility = View.VISIBLE
                                layoutEliminar.visibility = View.GONE
                                textMostar.visibility = View.GONE
                                layoutModificar.visibility = View.GONE
                                btnGu.visibility = View.VISIBLE
                                btnEli.visibility = View.GONE
                                btnGuM.visibility = View.GONE
                            }

                            "Eliminar" -> {
                                layoutInsertar.visibility = View.GONE
                                layoutEliminar.visibility = View.VISIBLE
                                layoutModificar.visibility = View.GONE
                                textMostar.visibility = View.GONE
                                btnGu.visibility = View.GONE
                                btnGuM.visibility = View.GONE
                                btnEli.visibility = View.VISIBLE


                                val listaActualizada = crud.obtenerTodos()

                                // Evita mostrar spinner vacío si no hay datos
                                if (listaActualizada.isEmpty()) {
                                    Toast.makeText(this@HabitacionActivity, "No hay usuarios para modificar", Toast.LENGTH_SHORT).show()
                                    return
                                }

                                val adapterEli = ArrayAdapter(
                                    this@HabitacionActivity,
                                    android.R.layout.simple_spinner_item,
                                    listaActualizada
                                )

                                adapterEli.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                                spinnerEli.adapter = adapterEli

                            }

                            "Mostrar" -> {
                                layoutInsertar.visibility = View.GONE
                                layoutEliminar.visibility = View.GONE
                                layoutModificar.visibility = View.GONE
                                textMostar.visibility = View.VISIBLE
                                btnGuM.visibility = View.GONE
                                btnEli.visibility = View.GONE

                                val textoCompleto = buildString {
                                    listaUs.forEach { habitacion ->
                                        appendLine("Id: ${habitacion.idh}")
                                        appendLine("Tipo: ${habitacion.typo}")
                                        appendLine("estatus: ${habitacion.estatus}")
                                        appendLine("Check In: ${habitacion.checkIn}")
                                        appendLine("Check Out: ${habitacion.checkOut}")
                                        appendLine("-------------")
                                    }

                                }

                                textMostar.text = textoCompleto
                            }

                            "Modificar" -> {
                                layoutInsertar.visibility = View.GONE
                                layoutEliminar.visibility = View.GONE
                                layoutModificar.visibility = View.VISIBLE
                                textMostar.visibility = View.GONE
                                btnGuM.visibility = View.VISIBLE
                                btnEli.visibility = View.GONE

                                val listaActualizada = crud.obtenerTodos()

                                // Evita mostrar spinner vacío si no hay datos
                                if (listaActualizada.isEmpty()) {
                                    Toast.makeText(this@HabitacionActivity, "No hay habitacion para modificar", Toast.LENGTH_SHORT).show()
                                    return
                                }

                                val adapterMod = ArrayAdapter(
                                    this@HabitacionActivity,
                                    android.R.layout.simple_spinner_item,
                                    listaActualizada
                                )

                                adapterMod.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                                spinnerMod.adapter = adapterMod


                            }

                            else -> {
                                layoutInsertar.visibility = View.GONE
                                layoutEliminar.visibility = View.GONE
                                textMostar.visibility = View.GONE

                            }

                        }
                    }



                    override fun onNothingSelected(parent: AdapterView<*>) {}

                }

                val btnR = findViewById<Button>(R.id.btnRew)
                btnR.setOnClickListener {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
                ///////////////////////==== Insertar ===/////////////////////////////
                btnGu.setOnClickListener {
                    val checkIn = Nombre.text.toString()
                    val checkOut = Edad.text.toString()
                    val typo = Tipo.text.toString()
                    val estatus = Estatus.text.toString()

                    // Validar que no esté vacío
                    if (typo.isBlank() || estatus.isBlank()) {
                        Toast.makeText(this, "Tipo y estatus son requeridos", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                    val habitacion = Habitacion().apply() {
                        this.idh = (1000..9999).random().toString()
                        this.typo = typo
                        this.estatus = estatus
                        this.checkIn = checkIn
                        this.checkOut = checkOut
                    }
                    val crud = HCRUD(realm)
                    crud.insertar(habitacion)
                    Toast.makeText(this, "Usuario guadado", Toast.LENGTH_SHORT).show()
                    Nombre.text.clear()
                    Edad.text.clear()
                    Tipo.text.clear()
                    Estatus.text.clear()
                }

                /////////////////==== Modificar ====///////////////////
                btnGuM.setOnClickListener { //Modificar
                    val nombre = Nombrem.text.toString()
                    val edad = Edadm.text.toString()
                    val typo = Tipom.text.toString()
                    val estatus = Viajem.text.toString()
                    val spinnerMod = findViewById<Spinner>(R.id.spinModificar)
                    val usuarioSelec = spinnerMod.selectedItem as Habitacion

                    if(usuarioSelec == null){
                        return@setOnClickListener
                    }
                    val idSelec = usuarioSelec.idh


                    // Validar que no esté vacío
                    if (typo.isBlank() || estatus.isBlank()) {
                        Toast.makeText(this, "Tipo y estatus son requeridos", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                    // Usa el id o el nombre, depende de cómo llenaste el spinner
                    val crud = HCRUD(realm)

                    crud.modificar(idSelec, nombre, edad, typo, estatus)
                    Toast.makeText(this, "Habitacion Modificada", Toast.LENGTH_SHORT).show()
                    Nombrem.text.clear()
                    Edadm.text.clear()
                    Tipom.text.clear()
                    Viajem.text.clear()
                }
                //////////////////////////==== Eliminar ==== //////////////////////////
                btnEli.setOnClickListener {

                    val spinnerEli = findViewById<Spinner>(R.id.spinEliminar)
                    val usuarioSelec = spinnerEli.selectedItem as Habitacion

                    if(usuarioSelec == null){
                        return@setOnClickListener
                    }
                    val idSelec = usuarioSelec.idh
                    val typo = usuarioSelec.typo

                    if (typo.isBlank()) {
                        Toast.makeText(this, "Tipo y estatus son requeridos", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }else{crud.eliminar(idSelec)
                    Toast.makeText(this, "Habitacion Eliminada", Toast.LENGTH_SHORT).show()}
                }
            }
}
