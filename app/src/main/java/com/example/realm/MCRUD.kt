package com.example.realm

import android.util.Log
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query

class MCRUD(private val realm: Realm) {

        fun insertar(maleta: Maleta) {
            realm.writeBlocking {
                copyToRealm(maleta)
            }
            Log.d("REALM", "Maleta insertada")
        }

        fun obtenerTodos(): List<Maleta> {
            return realm.query<Maleta>().find()
        }

        fun eliminar(id: String) {
            realm.writeBlocking {
                val maleta= query<Maleta>("id == $0", id).first().find()
                maleta?.let {
                    delete(it)
                } ?: Log.e("REALM", "Maleta con ID $id no encontrada")
            }
        }

        fun modificar(id: String, nuevoPeso: String, nuevotypo: String, nuevaTransporte: String, nuevoNoViaje: String) {
            realm.writeBlocking {
                val maleta = query<Maleta>("id == $0", id).first().find()
                maleta?.apply {
                    this.peso = nuevoPeso
                    this.typo = nuevotypo
                    this.transporte = nuevaTransporte
                    this.noViaje = nuevoNoViaje
                } ?: Log.e("REALM", "No se encontró maleta para modificar con ID: $id")
            }
        }

    }
