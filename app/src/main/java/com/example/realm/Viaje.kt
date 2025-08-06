package com.example.realm

import io.realm.kotlin.types.annotations.PrimaryKey
import io.realm.kotlin.types.RealmObject

class Viaje: RealmObject{

    @PrimaryKey
    var idv: String = ""
    var typo: String = ""
    var fechaIn: String = ""
    var fechaOut: String = ""
    var destino: String = ""

    override fun toString(): String{
        return "ID: (${idv}), Destino: ${destino}"
    }

}