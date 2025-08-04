package com.example.realm

import android.util.Log
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query

class HCRUD(private val realm: Realm) {

        fun insertar(habitacion: Habitacion) {
            realm.writeBlocking {
                copyToRealm(habitacion)
            }
            Log.d("REALM", "Habitacion insertada")
        }

        fun obtenerTodos(): List<Habitacion> {
            return realm.query<Habitacion>().find()
        }

        fun eliminar(id: String) {
            realm.writeBlocking {
                val habitacion = query<Habitacion>("id == $0", id).first().find()
                habitacion?.let {
                    delete(it)
                } ?: Log.e("REALM", "Habitacion con ID $id no encontrado")
            }
        }

        fun modificar(id: String, nuevotypo: String, nuevoestatus: String,  nuevoCheckIn: String, nuevoCheckOut: String) {
            realm.writeBlocking {
                val habitacion = query<Habitacion>("id == $0", id).first().find()
                habitacion?.apply {
                    this.typo = nuevotypo
                    this.estatus = nuevoestatus
                    this.checkIn = nuevoCheckIn
                    this.checkOut = nuevoCheckOut
                } ?: Log.e("REALM", "No se encontró habitacion para modificar con ID: $id")
            }
        }

    }

