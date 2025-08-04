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

class MaletaActivity : AppCompatActivity() {
    private lateinit var realm: Realm
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_maleta)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
                val config = RealmConfiguration.Builder(
                    schema = setOf(Maleta::class)
                )
                    .schemaVersion(2)
                    .build()
                realm = Realm.open(config)

                val spinnerAccion = findViewById<Spinner>(R.id.spinnerAccion)
                val layoutInsertar = findViewById<LinearLayout>(R.id.layoutInsertar)
                val layoutEliminar = findViewById<LinearLayout>(R.id.layoutEliminar)
                val layoutModificar = findViewById<LinearLayout>(R.id.layoutModi)
                val textMostar = findViewById<TextView>(R.id.tvMos)




                val Nombre = findViewById<EditText>(R.id.inNombre)
                val Edad = findViewById<EditText>(R.id.inEdad)
                val Tipo = findViewById<EditText>(R.id.inTipo)
                val Viaje = findViewById<EditText>(R.id.inViajeId)
                val spinnerMod = findViewById<Spinner>(R.id.spinModificar)
                val spinnerEli = findViewById<Spinner>(R.id.spinEliminar)

                val Nombrem = findViewById<EditText>(R.id.inNombreM)
                val Edadm = findViewById<EditText>(R.id.inEdadM)
                val Tipom = findViewById<EditText>(R.id.inTipoM)
                val Viajem = findViewById<EditText>(R.id.inViajeIdM)


                val btnGu = findViewById<Button>(R.id.btnG)
                val btnGuM = findViewById<Button>(R.id.btnGM)
                val btnEli = findViewById<Button>(R.id.btnEli)
                val crud = MCRUD(realm)
                val listaUs = crud.obtenerTodos()
                val nombres = listaUs.map { "(${it.Idm}) ${it.peso}  ${it.transporte} ${it.typo} ${it.noViaje} " }



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
                                    Toast.makeText(this@MaletaActivity, "No hay maletas para modificar", Toast.LENGTH_SHORT).show()
                                    return
                                }

                                val adapterEli = ArrayAdapter(
                                    this@MaletaActivity,
                                    android.R.layout.simple_spinner_item,
                                    listaActualizada.map { "(${it.Idm}) ${it.typo}" }  // Mostrar algo legible
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
                                    listaUs.forEach { usuario ->
                                        appendLine("Id: ${usuario.Idm}")
                                        appendLine("Peso: ${usuario.peso}")
                                        appendLine("Transporte: ${usuario.transporte}")
                                        appendLine("Tipo: ${usuario.typo}")
                                        appendLine("Viaje: ${usuario.noViaje}")
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
                                    Toast.makeText(this@MaletaActivity, "No hay maleta para modificar", Toast.LENGTH_SHORT).show()
                                    return
                                }

                                val adapterMod = ArrayAdapter(
                                    this@MaletaActivity,
                                    android.R.layout.simple_spinner_item,
                                    listaActualizada.map { "(${it.Idm}) ${it.typo}" }  // Mostrar algo legible
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
                    val nombre = Nombre.text.toString()
                    val edad = Edad.text.toString()
                    val typo = Tipo.text.toString()
                    val noViaje = Viaje.text.toString()

                    // Validar que no esté vacío
                    if (nombre.isBlank() || noViaje.isBlank()) {
                        Toast.makeText(this, "Peso y viaje son requeridos", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                    val maleta = Maleta().apply() {
                        this.Idm = (1000..9999).random().toString()
                        this.peso = nombre
                        this.transporte = edad
                        this.typo = typo
                        this.noViaje = noViaje
                    }
                    val crud = MCRUD(realm)
                    crud.insertar(maleta)
                    Toast.makeText(this, "Usuario guadado", Toast.LENGTH_SHORT).show()
                }

                /////////////////==== Modificar ====///////////////////
                btnGuM.setOnClickListener { //Modificar
                    val nombre = Nombrem.text.toString()
                    val transporte = Edadm.text.toString()
                    val typo = Tipom.text.toString()
                    val viajeId = Viajem.text.toString()
                    val spinnerMod = findViewById<Spinner>(R.id.spinModificar)
                    val adapterMod = ArrayAdapter(this, android.R.layout.simple_spinner_item, listaUs)
                    adapterMod.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinnerMod.adapter = adapterMod
                    val maletaSelec = spinnerMod.selectedItem as Maleta
                    val idSelec = maletaSelec.Idm


                    // Validar que no esté vacío
                    if (nombre.isBlank() || transporte.isBlank()) {
                        Toast.makeText(this, "Peso y transporte son requeridos", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                    // Usa el id o el nombre, depende de cómo llenaste el spinner
                    val crud = UCRUD(realm)

                    crud.modificar(idSelec, nombre, transporte, typo, viajeId)
                    Toast.makeText(this, "Maleta Modificado", Toast.LENGTH_SHORT).show()

                }
/////////////////==== Eliminar ====///////////////////
                btnEli.setOnClickListener {
                    val nombre = Nombrem.text.toString()
                    val spinnerEli = findViewById<Spinner>(R.id.spinEliminar)
                    val adapterEli = ArrayAdapter(this, android.R.layout.simple_spinner_item, listaUs)
                    adapterEli.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinnerEli.adapter = adapterEli
                    val usuarioSelec = spinnerMod.selectedItem as Usuario
                    val idSelec = usuarioSelec.id
                    if (nombre.isBlank()) {
                        Toast.makeText(this, "Nombre y edad son requeridos", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }

                    crud.eliminar(idSelec)
                    Toast.makeText(this, "Usuario Eliminado", Toast.LENGTH_SHORT).show()

                }
            }



        }

