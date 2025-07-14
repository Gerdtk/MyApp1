package com.example.realm
import io.realm.kotlin.types.annotations.PrimaryKey
import io.realm.kotlin.types.RealmObject
class Pago: RealmObject {
    @PrimaryKey
    var idf: Int = 0

    var fechaIn: String = ""
    var fechaOut: String = ""
    var pagos: String = ""
    var estatus: String = ""

}