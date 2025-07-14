package com.example.realm

import io.realm.kotlin.types.annotations.PrimaryKey
import io.realm.kotlin.types.RealmObject

class Habitacion: RealmObject {
    @PrimaryKey
    var idh: Int = 0

    var typo: String = ""
    var estatus: String = ""
    var checkIn: String = ""
    var checkOut: String = ""
}