package com.example.finalproject

import android.app.Activity
import android.os.Handler
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceActivity.Header
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.finalproject.databinding.ActivityWarBinding

class WarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWarBinding
    private lateinit var playerWarCard: ImageView
    private lateinit var cpuWarCard: ImageView
    private lateinit var warBTN: ImageView
    private  lateinit var cardMP: MediaPlayer



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle("LET THE WAR BEGIN")
        binding = ActivityWarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        cardMP = MediaPlayer.create(this, R.raw.cardsound)
        viewModel.random = kotlin.random.Random

        playerWarCard = binding.PLAYERWARCARD
        cpuWarCard = binding.CPUWARCARD
        warBTN = binding.WARBTN

        var playerCurrentScore = intent.getIntExtra("playerOneScore", 0)
        var cpuCurrentScore = intent.getIntExtra("computerScore", 0)

        fun getWarWinner(card1: Int, card2: Int ) {
            if (card1 > card2) {
                playerCurrentScore += 3
                var playerResultIntent = Intent()
                playerResultIntent.putExtra("playerResult", playerCurrentScore)
                setResult(RESULT_OK,playerResultIntent)

                println("p1 score:$playerCurrentScore")
                println("cpu score: $cpuCurrentScore")

            } else if(card2 > card1) {
                cpuCurrentScore += 3
                var cpuResultIntent = Intent()
                cpuResultIntent.putExtra("cpuResult", cpuCurrentScore)
                setResult(RESULT_OK,cpuResultIntent)

                println("cpu score: $cpuCurrentScore")
                println("p1 score:$playerCurrentScore")

            }
        }


        warBTN.setOnClickListener {
            cardMP.start()
            val card1 = viewModel.random.nextInt(viewModel.cardsArray.size)
            val card2 = viewModel.random.nextInt(viewModel.cardsArray.size)

            getWarWinner(card1, card2)

            viewModel.displayCardImage(card1, playerWarCard)
            viewModel.displayCardImage(card2, cpuWarCard)

            Handler().postDelayed({
                finish()
            }, 2000)



        } // close warBTN()


    } // close onCreate
    override fun onDestroy() {
        super.onDestroy()
        cardMP.release()
    }


}