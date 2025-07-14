package com.example.realm

import io.realm.kotlin.types.annotations.PrimaryKey
import io.realm.kotlin.types.RealmObject

class Viaje: RealmObject{

    @PrimaryKey
    var idv: Int = 0

    var typo: String = ""
    var fechaIn: String = ""
    var fechaOut: String = ""
    var destino: String = ""

}