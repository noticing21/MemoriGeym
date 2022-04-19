package com.example.memorigeym

import android.content.Intent
import android.media.Image
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
import kotlinx.android.synthetic.main.activity_zor_mod.*

class ZorMod : AppCompatActivity() {

    var db = { DataBaseHandler(this)  }

    var imgsZor: MutableList<Int> = mutableListOf(
        accordion,
        bassoon,
        cello,
        clarinet,
        cymbals,
        french_horn,
        guitar,
        harmonica,
        harp,
        pan_flute,
        piano,
        saxophone,
        snare_drum,
        theremin,
        trombone,
        trumpet,
        tuba,
        xylophone,
        accordion,
        bassoon,
        cello,
        clarinet,
        cymbals,
        french_horn,
        guitar,
        harmonica,
        harp,
        pan_flute,
        piano,
        saxophone,
        snare_drum,
        theremin,
        trombone,
        trumpet,
        tuba,
        xylophone,
    )
    var btnsZor:Array<ImageButton> = arrayOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zor_mod)

        val btnAnaMenu = findViewById<Button>(R.id.btnAnaMenuZor)
        btnAnaMenu.setOnClickListener {
            val Intent = Intent(this, MainActivity::class.java)
            startActivity(Intent)
        }
        btnsZor = arrayOf(
            img101, img102, img103, img104, img105, img106, img107, img108, img109, img110, img111,
            img112, img113, img114, img115, img116, img117, img118, img119, img120, img121, img122,
            img123, img124, img125, img126, img127, img128, img129, img130, img131, img132, img133,
            img134, img135, img136
        )
        imgsZor.shuffle()
        var seviye = "Zor"
        var dondur = false
        var tiksayisi = 0
        var ilktik = -1
        var ikincitik = -1
        var hamle = 0
        val hamlesayisi = findViewById<TextView>(R.id.HamleSayisiZor)
        var eslesme = 0
        val eslesmesayisi = findViewById<TextView>(R.id.EslesmeSayisiZor)
        var puan = 0
        val puansayac = findViewById<TextView>(R.id.PuanSayacZor)

        var timer = findViewById<TextView>(R.id.timerZor)
        object : CountDownTimer(120000,1000){
            override fun onTick(kalan: Long){
                timer.setText("Kalan Saniye: " + kalan / 1000)
                if( eslesme < 18){
                    for (i in 0..35) {
                        btnsZor[i].setOnClickListener {
                            hamle++
                            hamlesayisi.text = "Hamle Sayısı : $hamle"
                            if (!dondur) {
                                btnsZor[i].setImageResource(imgsZor[i])
                                btnsZor[i].setTag(imgsZor[i])
                                if (tiksayisi == 0) {
                                    ilktik = i
                                }
                                if (tiksayisi == 1){
                                    ikincitik = i
                                }
                                tiksayisi++
                            }else {
                                btnsZor[ikincitik].setImageResource(music)
                                btnsZor[ilktik].setImageResource(music)
                                dondur = false
                                tiksayisi = 0
                            }

                            if (tiksayisi == 2) {
                                dondur = true
                                if (btnsZor[ilktik].tag == btnsZor[ikincitik].tag) {
                                    btnsZor[ikincitik].isClickable = false
                                    btnsZor[ilktik].isClickable = false
                                    btnsZor[ikincitik].alpha = 0F
                                    btnsZor[ilktik].alpha = 0F
                                    dondur = false
                                    tiksayisi = 0
                                    eslesme++
                                    eslesmesayisi.text = "Eşleşme Sayısı : $eslesme/18"
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
                }else if (eslesme == 18){
                    cancel()
                    db.invoke().insertData(Skor(id = 0, puan = puan, time = (120-(kalan.toInt()/1000)), seviye = seviye))
                    btnAnaMenu.isVisible = true
                    Toast.makeText(getApplicationContext(),"Oyun Bitti, Ana Menüye Dönebilirsiniz",Toast.LENGTH_LONG).show()
                }
            }
            override fun onFinish() {
                btnAnaMenu.isVisible = true
                timer.setText("Süre Bitti!")
                Toast.makeText(getApplicationContext(),"Oyun Bitti, Ana Menüye Dönebilirsiniz",Toast.LENGTH_LONG).show()
                for (i in 0..35) {
                    btnsZor[i].setOnClickListener {
                        btnsZor[i].isClickable = false
                    }
                }
                db.invoke().insertData(Skor(id = 0, puan = puan, time = 120, seviye = seviye))
            }
        }.start()
    }
}