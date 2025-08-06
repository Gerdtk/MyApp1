package com.example.realm

import io.realm.kotlin.types.annotations.PrimaryKey
import io.realm.kotlin.types.RealmObject

class Puesto: RealmObject {
    @PrimaryKey
    var idp: String = ""
    var asiento: String = ""
    var transpote: String = ""
    var uso: String = ""
    var estatus: String = ""

    override fun toString(): String {
        return "(${idp}), ${asiento}"
    }

}