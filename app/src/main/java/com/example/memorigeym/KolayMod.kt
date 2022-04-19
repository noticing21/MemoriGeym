package com.example.memorigeym

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.graphics.drawable.toDrawable
import androidx.core.view.isVisible
import com.example.memorigeym.R.drawable.*
import kotlinx.android.synthetic.main.activity_kolay_mod.*
import kotlinx.android.synthetic.main.activity_kolay_mod.view.*

class KolayMod : AppCompatActivity() {

    var db = { DataBaseHandler(this)  }

    var imgs: MutableList<Int> = mutableListOf(
        boraaslan,
        fusunaslan,
        pinarsavasturk,
        edipguner,
        hakanustunel,
        burakbeynek,
        fatihbal,
        buberkaya,
        boraaslan,
        fusunaslan,
        pinarsavasturk,
        edipguner,
        hakanustunel,
        burakbeynek,
        fatihbal,
        buberkaya
    )
    var btns: Array<ImageButton> = arrayOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kolay_mod)

        val btnAnaMenu = findViewById<Button>(R.id.btnAnaMenu)
        btnAnaMenu.setOnClickListener {
            val Intent = Intent(this, MainActivity::class.java)
            startActivity(Intent)
        }

        btns = arrayOf(
            img1, img2, img3, img4, img5, img6, img7, img8, img9, img10, img11,
            img12, img13, img14, img15, img16
        )
        imgs.shuffle()
        var seviye = "Kolay"
        var dondur = false
        var tiksayisi = 0
        var ilktik = -1
        var ikincitik = -1
        var hamle = 0
        val hamlesayisi = findViewById<TextView>(R.id.HamleSayisi)
        var eslesme = 0
        val eslesmesayisi = findViewById<TextView>(R.id.EslesmeSayisi)
        var puan = 0
        val puansayac = findViewById<TextView>(R.id.PuanSayac)

        var timer = findViewById<TextView>(R.id.timer)
        object : CountDownTimer(60000,1000){
            override fun onTick(kalan: Long){
                timer.setText("Kalan Saniye: " + kalan / 1000)
                if( eslesme < 8){
                for (i in 0..15) {
                    btns[i].setOnClickListener {
                        hamle++
                        hamlesayisi.text = "Hamle Sayısı : $hamle"
                        if (!dondur) {
                            btns[i].setImageResource(imgs[i])
                            btns[i].setTag(imgs[i])
                            if (tiksayisi == 0) {
                                ilktik = i
                            }

                            if (tiksayisi == 1){
                                ikincitik = i
                            }
                            tiksayisi++
                        }else {
                            btns[ikincitik].setImageResource(klu)
                            btns[ilktik].setImageResource(klu)
                            dondur = false
                            tiksayisi = 0
                        }
                        if (tiksayisi == 2) {
                            dondur = true
                            if (btns[ilktik].tag == btns[ikincitik].tag) {
                                btns[ikincitik].isClickable = false
                                btns[ilktik].isClickable = false
                                btns[ikincitik].alpha = 0F
                                btns[ilktik].alpha = 0F
                                dondur = false
                                tiksayisi = 0
                                eslesme++
                                eslesmesayisi.text = "Eşleşme Sayısı : $eslesme/8"
                                puan += 10
                                puansayac.text = "Puan : $puan"
                            }else{
                                hamle--
                                puan -= 2
                                puansayac.text = "Puan : $puan"
                            }

                        }
                    }
                }
            }else if (eslesme == 8){
                    cancel()
                    db.invoke().insertData(Skor(id = 0, puan = puan, time = (60-(kalan.toInt()/1000)), seviye = seviye))
                    btnAnaMenu.isVisible = true
                    Toast.makeText(getApplicationContext(),"Oyun Bitti, Ana Menüye Dönebilirsiniz",Toast.LENGTH_LONG).show()
                }
            }
            override fun onFinish() {
                btnAnaMenu.isVisible = true
                timer.setText("Süre Bitti!")
                Toast.makeText(getApplicationContext(),"Oyun Bitti, Ana Menüye Dönebilirsiniz",Toast.LENGTH_LONG).show()
                for (i in 0..15) {
                    btns[i].setOnClickListener {
                        btns[i].isClickable = false
                    }
                }
                db.invoke().insertData(Skor(id = 0, puan = puan, time = 60, seviye = seviye))
            }
        }.start()
    }

}







