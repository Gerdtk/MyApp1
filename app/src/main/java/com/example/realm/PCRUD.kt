package com.example.realm

import android.util.Log
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query

class PCRUD(private val realm: Realm) {

        fun insertar(puesto: Puesto) {
            realm.writeBlocking {
                copyToRealm(puesto)
            }
            Log.d("REALM", "Puesto insertado")
        }

        fun obtenerTodos(): List<Puesto> {
            return realm.query<Puesto>().find()
        }

        fun eliminar(id: String) {
            realm.writeBlocking {
                val puesto = query<Puesto>("idp == $0", id).first().find()
                puesto?.let {
                    delete(it)
                } ?: Log.e("REALM", "Puesto con ID $id no encontrado")
            }
        }

        fun modificar(id: String, nuevoNombre: String, nuevotypo: String, nuevaEdad: String, nuevoViajeId: String) {
            realm.writeBlocking {
                val puesto = query<Puesto>("idp == $0", id).first().find()
                puesto?.apply {
                    this.asiento = nuevoNombre
                    this.transpote = nuevaEdad
                    this.uso= nuevotypo
                    this.estatus = nuevoViajeId
                } ?: Log.e("REALM", "No se encontró puesto para modificar con ID: $id")
            }
        }

    }

