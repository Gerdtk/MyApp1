package com.example.realm

import android.widget.EditText
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import android.widget.*
import androidx.activity.compose.setContent
import androidx.annotation.OptIn
import androidx.appcompat.app.*
import androidx.media3.common.util.Log
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import androidx.core.view.*
import androidx.media3.common.util.UnstableApi
import android.util.*
import android.content.*


class MainActivity : AppCompatActivity() {
    private lateinit var realm: Realm

    @OptIn(UnstableApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout)
        Log.d("REALM", "mensaje de prueba")

        val config = RealmConfiguration.Builder(
            schema = setOf(Usuario::class, Habitacion::class)
        )

            .schemaVersion(2)
            /*.migration{migration, olVersion, newVersion ->
                if(olversion == 1L){
                    val schema = migration.schema
                    schema["Usuario"]?.addField("ejemplo", String::class.java)
                }
            }*/
            .build()

        realm = Realm.open(config)
/////////////////////////////////////////////////////////////////////////////////////////////
        //////////====== Usuario =======///////////
        val btnUs = findViewById<Button>(R.id.btnUs)
        btnUs.setOnClickListener{
            val intent = Intent(this, UsuarioActivity::class.java)
            startActivity(intent)
        }
        //////////====== Viaje =======///////////
        val btnV = findViewById<Button>(R.id.btnVi)
        btnV.setOnClickListener{
            val intent = Intent(this, ViajeActivity::class.java)
            startActivity(intent)
        }
        //////////====== puesto =======///////////
        val btnP = findViewById<Button>(R.id.btnPu)
        btnP.setOnClickListener{
            val intent = Intent(this, PuestoActivity::class.java)
            startActivity(intent)
        }
        //////////====== Habitacion =======///////////
        val btnH = findViewById<Button>(R.id.btnHa)
        btnH.setOnClickListener{
            val intent = Intent(this, HabitacionActivity::class.java)
            startActivity(intent)
        }
        //////////====== Maleta=======///////////
        val btnM = findViewById<Button>(R.id.btnMa)
        btnM.setOnClickListener{
            val intent = Intent(this, MaletaActivity::class.java)
            startActivity(intent)
        }
        //////////====== Pago =======///////////
        val btnF = findViewById<Button>(R.id.btnPa)
        btnF.setOnClickListener{
            val intent = Intent(this, PagoActivity::class.java)
            startActivity(intent)
        }


    }
}