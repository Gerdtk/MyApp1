package com.example.realm

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import android.widget.*
import android.view.View
import io.realm.kotlin.*


class ViajeActivity : AppCompatActivity() {
    private lateinit var realm:Realm
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_viaje)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

                val config = RealmConfiguration.Builder(
                    schema = setOf(Viaje::class)
                )
                    .schemaVersion(2)
                    .build()
                realm = Realm.open(config)

                val spinnerAccion = findViewById<Spinner>(R.id.spinnerAccion)
                val layoutInsertar = findViewById<LinearLayout>(R.id.layoutInsertar)
                val layoutEliminar = findViewById<LinearLayout>(R.id.layoutEliminar)
                val layoutModificar = findViewById<LinearLayout>(R.id.layoutModi)
                val textMostar = findViewById<TextView>(R.id.tvMos)




                val FechaIN = findViewById<EditText>(R.id.inFechaIn)
                val FechaOut = findViewById<EditText>(R.id.inFechaOut)
                val Tipo = findViewById<EditText>(R.id.inTipo)
                val Destino = findViewById<EditText>(R.id.inDestino)
                val spinnerMod = findViewById<Spinner>(R.id.spinModificar)
                val spinnerEli = findViewById<Spinner>(R.id.spinEliminar)

                val Nombrem = findViewById<EditText>(R.id.inNombreM)
                val Edadm = findViewById<EditText>(R.id.inEdadM)
                val Tipom = findViewById<EditText>(R.id.inTipoM)
                val Viajem = findViewById<EditText>(R.id.inViajeIdM)


                val btnGu = findViewById<Button>(R.id.btnG)
                val btnGuM = findViewById<Button>(R.id.btnGM)
                val btnEli = findViewById<Button>(R.id.btnEli)
                val crud = VCRUD(realm)
                val listaUs = crud.obtenerTodos()
                val nombres = listaUs.map { "(${it.idv}) ${it.fechaIn}  ${it.fechaOut} ${it.typo} ${it.destino} " }



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
                                    Toast.makeText(this@ViajeActivity, "No hay viajes para modificar", Toast.LENGTH_SHORT).show()
                                    return
                                }

                                val adapterEli = ArrayAdapter(
                                    this@ViajeActivity,
                                    android.R.layout.simple_spinner_item,
                                    listaActualizada  // Mostrar algo legible
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
                                    listaUs.forEach { viaje ->
                                        appendLine("Id: ${viaje.idv}")
                                        appendLine("MesIn: ${viaje.fechaIn}")
                                        appendLine("MesOut: ${viaje.fechaOut}")
                                        appendLine("pagos: ${viaje.typo}")
                                        appendLine("estatus: ${viaje.destino}")
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
                                    Toast.makeText(this@ViajeActivity, "No hay Viajes para modificar", Toast.LENGTH_SHORT).show()
                                    return
                                }

                                val adapterMod = ArrayAdapter(
                                    this@ViajeActivity,
                                    android.R.layout.simple_spinner_item,
                                    listaActualizada.map { "(${it.idv}) ${it.destino}" }  // Mostrar algo legible
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
                    val fechaIn = FechaIN.text.toString()
                    val fechaOut = FechaOut.text.toString()
                    val typo = Tipo.text.toString()
                    val destino = Destino.text.toString()

                    // Validar que no esté vacío
                    if (typo.isBlank() || destino.isBlank()) {
                        Toast.makeText(this, "Tipo y Destino son requeridos", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                    val viaje = Viaje().apply() {
                        this.idv = (1000..9999).random().toString()
                        this.typo = typo
                        this.fechaIn = fechaIn
                        this.fechaOut= fechaOut
                        this.destino = destino
                    }
                    val crud = VCRUD(realm)
                    crud.insertar(viaje)
                    Toast.makeText(this, "Usuario guadado", Toast.LENGTH_SHORT).show()
                }

                /////////////////==== Modificar ====///////////////////
                btnGuM.setOnClickListener { //Modificar
                    val fechaIn = Nombrem.text.toString()
                    val fechaOut = Edadm.text.toString()
                    val typo = Tipom.text.toString()
                    val destino = Viajem.text.toString()
                    val spinnerMod = findViewById<Spinner>(R.id.spinModificar)
                    val adapterMod = ArrayAdapter(this, android.R.layout.simple_spinner_item, listaUs)
                    adapterMod.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinnerMod.adapter = adapterMod
                    val viajeSelec = spinnerMod.selectedItem as Viaje
                    val idSelec = viajeSelec.idv


                    // Validar que no esté vacío
                    if (typo.isBlank() || destino.isBlank()) {
                        Toast.makeText(this, "Tipo y destino son requeridos", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                    // Usa el id o el nombre, depende de cómo llenaste el spinner
                    val crud = VCRUD(realm)

                    crud.modificar(idSelec, fechaIn, fechaOut, typo, destino)
                    Toast.makeText(this, "Viaje Modificado", Toast.LENGTH_SHORT).show()

                }

                //////////////////==== Eliminar ===////////////////////
                btnEli.setOnClickListener {
                    val destino = Viajem.text.toString()


                    val viajeSelec = spinnerEli.selectedItem as Viaje


                    if (viajeSelec == null) {
                        Log.d("Gato", "Na' por aqui")
                        Toast.makeText(this, "Destino es requerido", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                    val idSelec = viajeSelec.idv

                    if(idSelec.isBlank()) {
                        Log.d("Gato", "Id en blanco")

                    }else {
                        Log.d("Gato", "eliminado")
                        crud.eliminar(idSelec)
                        Toast.makeText(this, "Viaje Eliminado", Toast.LENGTH_SHORT).show()
                    }

                }
            }



        }