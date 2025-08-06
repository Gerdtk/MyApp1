package com.example.realm
import android.util.Log
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.types.RealmObject

class UCRUD(private val realm: Realm) {

    fun insertar(usuario: Usuario) {
        realm.writeBlocking {
            copyToRealm(usuario)
        }
        Log.d("REALM", "usuario insertado")
    }

    fun obtenerTodos(): List<Usuario> {
        return realm.query<Usuario>().find()
    }

    fun eliminar(id: String) {
        realm.writeBlocking {

            val usuario = query<Usuario>("id == $0", id).first().find()
            usuario?.let {
                delete(it)
            } ?: Log.e("Gato", "Usuario con ID $id no encontrado")
        }
    }

    fun modificar(id: String, nuevoNombre: String, nuevotypo: String, nuevaEdad: String, nuevoViajeId: String) {
        realm.writeBlocking {
            val usuario = query<Usuario>("id == $0", id).first().find()
            usuario?.apply {
                this.nombre = nuevoNombre
                this.edad = nuevaEdad
                this.typo = nuevotypo
                this.viajeId = nuevoViajeId
            } ?: Log.e("REALM", "No se encontró usuario para modificar con ID: $id")
        }
    }

}
