package com.example.memorigeym

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var db = { DataBaseHandler(this)  }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnKolay = findViewById<Button>(R.id.btnKolay)
        btnKolay.setOnClickListener {
            val Intent = Intent(this, KolayMod::class.java)
            startActivity(Intent)
        }
        val btnZor = findViewById<Button>(R.id.btnZor)
        btnZor.setOnClickListener {
            val Intent = Intent(this, ZorMod::class.java)
            startActivity(Intent)
        }

        fun skorGoster(list:MutableList<Skor>){
            skorlar.text = ""
            list.forEach { skorlar.text = skorlar.text.toString() + "\n" + "Puan: " + it.puan + " Süre: " + it.time + " Seviye: " + it.seviye}
        }
        skorGoster(db.invoke().readData())
  /*      val btnDel = findViewById<Button>(R.id.btnDel)
        btnDel.setOnClickListener {
            db.invoke().deleteAllData()
        }
*/

    }
}