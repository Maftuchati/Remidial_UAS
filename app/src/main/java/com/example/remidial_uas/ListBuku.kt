package com.example.remidial_uas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.remidial_uas.database.DataBukuDB
import com.example.remidial_uas.database.Constant
import com.example.remidial_uas.database.Buku
import kotlinx.android.synthetic.main.activity_list_buku.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListBuku : AppCompatActivity() {
    val db by lazy { DataBukuDB.getDatabase(this)}
    lateinit var bukuAdapter: BukuAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_buku)
        setupRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }
    fun loadData() {
        CoroutineScope(Dispatchers.IO).launch {
            val buku = db.bukuDao().getBuku()
            Log.d("ListBukuActivity", "data buku : $buku")
            withContext(Dispatchers.Main) {
                bukuAdapter.setData(buku)
            }
        }
    }
    fun intentEdit(bukuId: String, intentType: Int){
        startActivity(
            Intent(applicationContext, AddBuku::class.java)
                .putExtra("intent_id", bukuId)
                .putExtra("intent_type", intentType)
        )
    }
    private fun setupRecyclerView(){
        bukuAdapter = BukuAdapter(arrayListOf(), object  : BukuAdapter.onAdapterListener{
            override fun onClick(buku: Buku) {
                Toast.makeText(applicationContext, buku.judul_buku + ", " + buku.penerbit+ ", " + buku.perngarang, Toast.LENGTH_LONG).show()
            }

            override fun onDelete(buku: Buku) {
                CoroutineScope(Dispatchers.IO).launch {
                    db.bukuDao().deleteBuku(buku)
                    loadData()
                }
            }
            override fun onUpdate(buku: Buku) {
                intentEdit(buku.id, Constant.TYPE_UPDATE)
            }
        })
        list_buku.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = bukuAdapter
        }
    }
}