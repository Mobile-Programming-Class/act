package com.allam.act

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.allam.act.api.json.JsonActivity
import com.allam.act.imgrecycler.ImgActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initItem()
    }

    fun initItem() {
        val btnApiJson = findViewById<Button>(R.id.btnJsonApi)
        val btnImgRecycler = findViewById<Button>(R.id.btnImgRecycler)
        val btnKeluar = findViewById<Button>(R.id.btnKeluar)

        btnApiJson.setOnClickListener() {
            val intent = Intent(this, JsonActivity::class.java)
            startActivity(intent)
        }

        btnImgRecycler.setOnClickListener() {
            val intent = Intent(this, ImgActivity::class.java)
            startActivity(intent)
        }

        btnKeluar.setOnClickListener() {
            moveTaskToBack(true)
        }
    }
}