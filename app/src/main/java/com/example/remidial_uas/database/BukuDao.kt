package com.example.remidial_uas.database

import androidx.room.*

@Dao
interface BukuDao {
    @Query("SELECT * FROM buku ORDER BY id ASC")
    fun getBuku(): List<Buku>

    @Query("SELECT * FROM buku WHERE id=:buku_id")
    suspend fun getBukuId(buku_id: String): List<Buku>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(buku: Buku)

    @Update
    fun updateBuku(buku : Buku)

    @Delete
    fun deleteBuku(buku: Buku)
}