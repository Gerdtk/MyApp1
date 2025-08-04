package com.example.realm

import android.util.Log
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.types.RealmObject


class FCRUD(private val realm: Realm) {

        fun insertar(pago: Pago) {
            realm.writeBlocking {
                copyToRealm(pago)
            }
            Log.d("REALM", "Pago insertado")
        }

        fun obtenerTodos(): List<Pago> {
            return realm.query<Pago>().find()
        }

    //////////////////

        fun eliminar(id: String) {
            realm.writeBlocking {
                val pago= query<Pago>("id == $0", id).first().find()
                pago?.let {
                    delete(it)
                } ?: Log.e("REALM", "Pago con ID $id no encontrado")
            }
        }
    ///////////////////////////
    fun modificar(id: String, nuevafechaIn: String, nuevafechaOut: String, nuevopago: String, nuevoestatus: String) {
            realm.writeBlocking {
                val pago = query<Pago>("id == $0", id).first().find()
                pago?.apply {
                    this.fechaIn = nuevafechaIn
                    this.fechaOut = nuevafechaOut
                    this.pagos = nuevopago
                    this.estatus = nuevoestatus
                } ?: Log.e("REALM", "No se encontró viaje para modificar con ID: $id")
            }
    }
}

