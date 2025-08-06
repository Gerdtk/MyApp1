package com.example.realm

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.*
import android.view.View
import io.realm.kotlin.*




class UsuarioActivity : AppCompatActivity() {
    private lateinit var realm: Realm
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_usuario)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val config = RealmConfiguration.Builder(
            schema = setOf(Usuario::class)
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
        val crud = UCRUD(realm)
        val listaUs = crud.obtenerTodos()
        val nombres = listaUs.map { "(${it.id}) ${it.nombre}  ${it.edad} ${it.typo} ${it.viajeId} " }



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
                            Toast.makeText(this@UsuarioActivity, "No hay usuarios para modificar", Toast.LENGTH_SHORT).show()
                            return
                        }

                        val adapterEli = ArrayAdapter(
                            this@UsuarioActivity,
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
                            listaUs.forEach { usuario ->
                                appendLine("Id: ${usuario.id}")
                                appendLine("Nombre: ${usuario.nombre}")
                                appendLine("Edad: ${usuario.edad}")
                                appendLine("Tipo: ${usuario.typo}")
                                appendLine("Viaje: ${usuario.viajeId}")
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
                            Toast.makeText(this@UsuarioActivity, "No hay usuarios para modificar", Toast.LENGTH_SHORT).show()
                            return
                        }

                        val adapterMod = ArrayAdapter(
                            this@UsuarioActivity,
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
            val nombre = Nombre.text.toString()
            val edad = Edad.text.toString()
            val typo = Tipo.text.toString()
            val viajeId = Viaje.text.toString()

            // Validar que no esté vacío
            if (nombre.isBlank() || edad.isBlank()) {
                Toast.makeText(this, "Nombre y edad son requeridos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val usuario = Usuario().apply() {
                this.id = (1000..9999).random().toString()
                this.nombre = nombre
                this.edad = edad
                this.typo = typo
                this.viajeId = viajeId
            }
            val crud = UCRUD(realm)
            crud.insertar(usuario)
            Toast.makeText(this, "Usuario guadado", Toast.LENGTH_SHORT).show()
            Nombre.text.clear()
            Edad.text.clear()
            Tipo.text.clear()
            Viaje.text.clear()


        }

        /////////////////==== Modificar ====///////////////////
        btnGuM.setOnClickListener { //Modificar
            val nombre = Nombrem.text.toString()
            val edad = Edadm.text.toString()
            val typo = Tipom.text.toString()
            val viajeId = Viajem.text.toString()

            val spinnerMod = findViewById<Spinner>(R.id.spinModificar)
            val usuarioSelec = spinnerMod.selectedItem as Usuario
            Log.d("Gato", "tamaño: ${listaUs.size}, ")
            listaUs.forEach { Log.d("Gato", "Usuario. ${it.nombre}, ID: ${it.id}") }
            Log.d("Gato", "${usuarioSelec.nombre} ")

            if (usuarioSelec == null) {
                Toast.makeText(this, "No se seleccionó ningún usuario", Toast.LENGTH_SHORT).show()
                return@setOnClickListener

            }

            val idSelec = usuarioSelec.id

            // Validar que no esté vacío
            if (nombre.isBlank() || edad.isBlank()) {
                Toast.makeText(this, "Nombre y edad son requeridos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val crud = UCRUD(realm)
            crud.modificar(idSelec, nombre, edad, typo, viajeId)

            Toast.makeText(this, "Usuario Modificado", Toast.LENGTH_SHORT).show()

            Nombrem.text.clear()
            Edadm.text.clear()
            Tipom.text.clear()
            Viajem.text.clear()
        }

        /////////////////==== Eliminar ====///////////////////

        btnEli.setOnClickListener {



            Log.d("Gato", "tamaño: ${listaUs.size}, ")
            listaUs.forEach { Log.d("Gato", "Usuario. ${it.nombre}, ID: ${it.id}") }
            val usuarioSelec = spinnerEli.selectedItem as Usuario


            if (usuarioSelec == null) {
                Log.d("Gato", "na' por aqui")
                Toast.makeText(this, "no se encuetra al usuario", Toast.LENGTH_SHORT).show()
                return@setOnClickListener

            }
            val idSelec = usuarioSelec.id
            val nombreU = usuarioSelec.nombre

            if (idSelec.isBlank()) {
                Log.d("Gato", "Id en blanco")
            } else if (nombreU.isBlank()) {
                Log.d("Gato", " El Id: ${usuarioSelec.id}, 'Ta en blanco el nombre ")
            } else {
                Log.d("Gato", "este es el que vamo' a eliminar ID: ${usuarioSelec.id}, NOmbre: ${usuarioSelec.nombre}")
                crud.eliminar(idSelec)
                Toast.makeText(this, "Usuario eliminado", Toast.LENGTH_SHORT).show()

            }
        }
    }



}