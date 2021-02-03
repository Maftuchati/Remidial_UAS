package com.example.remidial_uas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.remidial_uas.database.DataBukuDB
import com.example.remidial_uas.database.Constant
import com.example.remidial_uas.database.Buku
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    val db by lazy { DataBukuDB.getDatabase(this) }
    val database = Firebase.database
    lateinit var bukuAdapter: BukuAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addBuku()
        getListBuku()
        sync_buku()
    }

    fun addBuku() {
        btn_add_buku.setOnClickListener {
            intentEdit(0, Constant.TYPE_CREATE)
        }
    }

    fun intentEdit(bukuId: Int, intentType: Int) {
        startActivity(
            Intent(applicationContext, AddBuku::class.java)
                .putExtra("intent_id", bukuId)
                .putExtra("intent_type", intentType)
        )
    }

    fun getListBuku() {
        btn_list_buku.setOnClickListener {
            startActivity(Intent(this, ListBuku::class.java))
        }
    }

    fun sync_buku() {
        btnSync1.setOnClickListener {
            getDataFromFirebase()
            addDataToFirebase()
        }
    }

    private fun getDataFromFirebase() {

        val refBuku = database.getReference("buku")

        refBuku.addListenerForSingleValueEvent(object : ValueEventListener {
            var uid = ""
            var judul_buku = ""
            var penerbit = ""
            var pengarang = ""
            var tahun = ""
            var jenis_buku = ""
            var lokasi_rak = ""

            override fun onDataChange(snapshot: DataSnapshot) {
                for (ds in snapshot.children) {
                    uid = ds.child("id").getValue(String::class.java).toString()
                    judul_buku = ds.child("judul_buku").getValue(String::class.java).toString()
                    penerbit = ds.child("penerbit").getValue(String::class.java).toString()
                    pengarang = ds.child("pengarang").getValue(String::class.java).toString()
                    tahun = ds.child("tahun").getValue(String::class.java).toString()
                    jenis_buku = ds.child("jenis_buku").getValue(String::class.java).toString()
                    lokasi_rak = ds.child("lokasi_rak").getValue(String::class.java).toString()


                    CoroutineScope(Dispatchers.IO).launch {
                        if (snapshot != null) {

                            db.bukuDao().insert(
                                Buku(
                                    uid,
                                    judul_buku,
                                    penerbit,
                                    pengarang,
                                    tahun,
                                    jenis_buku,
                                    lokasi_rak
                                )
                            )
                        }
                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
                Log.d("errornya", error.toString())
            }
        })
    }

    private fun addDataToFirebase() {
        CoroutineScope(Dispatchers.IO).launch {
            val buku = db.bukuDao().getBuku()
            Log.d("ListBukuMain", "respons : $buku")
            withContext(Dispatchers.Main) {
                val refBuku = database.getReference("buku")
                refBuku.setValue(buku)

            }

        }

    }
}
