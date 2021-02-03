package com.example.remidial_uas.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Buku")
data class Buku (
    @PrimaryKey val id: String,
    @ColumnInfo(name = "judul_buku") val judul_buku: String,
    @ColumnInfo(name = "penerbit") val penerbit: String,
    @ColumnInfo(name = "perngarang") val perngarang: String,
    @ColumnInfo(name = "tahun") val tahun: String,
    @ColumnInfo(name = "jenis_buku") val jenis_buku: String,
    @ColumnInfo(name = "lokasi_rak") val lokasi_rak: String,
)