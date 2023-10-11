package com.example.finalproject
import android.content.Intent
import android.media.MediaPlayer
import android.os.Handler
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel


class MainActivityViewModel:ViewModel() {
    lateinit var cardMP: MediaPlayer
    lateinit var warMP: MediaPlayer
    lateinit var backgroundMP: MediaPlayer
    lateinit var random: kotlin.random.Random

    val cardsArray = intArrayOf(
        R.drawable.hearts2,
        R.drawable.hearts3,
        R.drawable.hearts4,
        R.drawable.hearts5,
        R.drawable.hearts6,
        R.drawable.hearts7,
        R.drawable.hearts8,
        R.drawable.hearts9,
        R.drawable.hearts10,
        R.drawable.hearts11,
        R.drawable.hearts12,
        R.drawable.hearts13,
        R.drawable.hearts14
    )

    // get a number and a image
    fun displayCardImage(num: Int, img: ImageView) {
        img.setImageResource(cardsArray[num])
    }
}