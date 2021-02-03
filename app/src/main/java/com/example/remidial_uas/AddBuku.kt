package com.example.remidial_uas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.remidial_uas.database.DataBukuDB
import com.example.remidial_uas.database.Constant
import com.example.remidial_uas.database.Buku
import kotlinx.android.synthetic.main.activity_add_databuku.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class AddBuku : AppCompatActivity() {
    val db by lazy { DataBukuDB.getDatabase(this)}
    private var bukuId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_databuku)
        addBuku()
        setupView()
    }
    fun setupView(){
        val intentType = intent.getIntExtra("intent_type", 0)
        when(intentType){
            Constant.TYPE_CREATE -> {
                btn_update_buku.visibility = View.GONE
            }
            Constant.TYPE_UPDATE -> {
                btn_create_buku.visibility = View.GONE
                getBuku()
            }
        }
    }

    fun addBuku(){
        btn_create_buku.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                val uuid : String = UUID.randomUUID().toString()
                db.bukuDao().insert(
                    Buku(uuid, edtJudulBuku.text.toString(), edtPenerbit.text.toString(), edtPengarang.text.toString(), edtTahun.text.toString(), edtJenisBuku.text.toString(), edtLokasiRak.text.toString())
                )
                finish()
            }
        }
        btn_update_buku.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                db.bukuDao().updateBuku(
                    Buku(bukuId, edtJudulBuku.text.toString(), edtPenerbit.text.toString(), edtPengarang.text.toString(), edtTahun.text.toString(), edtJenisBuku.text.toString(), edtLokasiRak.text.toString())

                )
                finish()
            }
        }
    }
    fun getBuku(){
        bukuId = intent.getStringExtra("intent_id",).toString()
        CoroutineScope(Dispatchers.IO).launch {
            val data = db.bukuDao().getBukuId(bukuId)[0]
            edtJudulBuku.setText(data.judul_buku)
            edtPenerbit.setText(data.penerbit)
            edtPengarang.setText(data.perngarang)
            edtTahun.setText(data.tahun)
            edtJenisBuku.setText(data.jenis_buku)
            edtLokasiRak.setText(data.lokasi_rak)
        }
    }
}
