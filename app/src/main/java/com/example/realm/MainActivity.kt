package com.example.realm

import android.widget.EditText
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.annotation.OptIn
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.util.Log
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import androidx.core.view.*
import androidx.media3.common.util.UnstableApi
import android.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var realm: Realm

    @OptIn(UnstableApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout)
        Log.d("REALM", "mensaje de prueba")

        val config = RealmConfiguration.Builder(
            schema = setOf(Usuario::class)
        )

            .schemaVersion(1)
            .build()

        realm = Realm.open(config)
/////////////////////////////////////////////////////////////////////////////////////////////

        val intNombre = findViewById<EditText>(R.id.intNombre)
        val intEdad = findViewById<EditText>(R.id.intEdad)
        val btnIns = findViewById<Button>(R.id.btnIns)


        /////////////////////////////////////////////////////////////////////////////////////////////
        //Funciones
        fun insertar(realm: Realm, id: Int, nombre: String, edad: Int) {
            realm.writeBlocking {
                copyToRealm(Usuario().apply {
                    this.id = id;
                    this.edad = edad;
                    this.nombre = nombre;
                })
            }
        }

/////////////////////////////////////////////////////////////////////////////////////////////////
        //funciones2
        if (btnIns == null) {
            Log.e("ERROR", "Btn2 no fue encontrado, revisa el ID en activity_main.xml")
        } else {
            btnIns.setOnClickListener {
                val nombre = intNombre.text.toString()
                val edad = intEdad.text.toString()

                if (nombre.isNotEmpty() && edad.isNotEmpty()) {
                    val id = (1000..9999).random()
                    insertar(realm, id, nombre, edad.toInt())

                    Toast.makeText(this, "Usuario guardado", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Usuario no registrado", Toast.LENGTH_SHORT).show()
                }

            }
            val usuarios = realm.query(Usuario::class).find()
            for (u in usuarios) {
                Log.d("REALM", "ID: ${u.id}, Nombre: ${u.nombre}, Edad: ${u.edad}")


            }
        }
    }
}