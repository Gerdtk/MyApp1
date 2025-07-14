package com.example.realm

import io.realm.kotlin.types.annotations.PrimaryKey
import io.realm.kotlin.types.RealmObject

class Usuario: RealmObject{
    @PrimaryKey
    var id: String = ""
    var nombre: String = ""
    var edad: String = ""
    var typo: String = ""
    var viajeId: String = ""

    override fun toString(): String{
        return "(${id} ${nombre}"
    }
}
