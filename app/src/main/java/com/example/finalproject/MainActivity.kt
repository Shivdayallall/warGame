package com.example.finalproject

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.finalproject.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var dealerBTN: ImageView
    private lateinit var playerScore: TextView
    private lateinit var cpuScore: TextView
    private lateinit var playerCard: ImageView
    private lateinit var cpuCard: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        viewModel.warMP = MediaPlayer.create(this, R.raw.shotgun)
        viewModel.cardMP = MediaPlayer.create(this, R.raw.cardsound)
        viewModel.backgroundMP = MediaPlayer.create(this, R.raw.background)
        viewModel.backgroundMP.start()
        viewModel.backgroundMP.isLooping = true
        viewModel.random = kotlin.random.Random

        playerCard = binding.playerCard
        cpuCard = binding.computerCard
        playerScore = binding.playerScore
        cpuScore = binding.computerScore
        dealerBTN = binding.dealBTN
        var p1Score = 0
        var comScore = 0


        fun updateScore(card1: Int, card2: Int) {

            if (card1 > card2) {
                p1Score++
                playerScore.text = "$p1Score"
            } else if (card2 > card1) {
                comScore++
                cpuScore.text = "$comScore"
            } else if (card1 == card2) {
                Toast.makeText(this, "PREPARE FOR WAR", Toast.LENGTH_SHORT).show()
                viewModel.warMP.start()

                Handler().postDelayed({
                    val intent = Intent(this, WarActivity::class.java)
                    intent.putExtra("playerOneScore", p1Score)
                    intent.putExtra("computerScore", comScore)
                    startActivityForResult(intent, 1)
                }, 2000)


            }

        }

        dealerBTN.setOnClickListener {
            viewModel.cardMP.start()
            val card1 = viewModel.random.nextInt(viewModel.cardsArray.size)
            val card2 = viewModel.random.nextInt(viewModel.cardsArray.size)

            // call the display card function pass in the a random number and a image view
            viewModel.displayCardImage(card1, playerCard)
            viewModel.displayCardImage(card2, cpuCard)
            updateScore(card1, card2)

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 1) {
            if(resultCode == RESULT_OK) {
                var playerUpdatedScore = data?.getIntExtra("playerResult",0)
                var cpuUpdatedScore = data?.getIntExtra("cpuResult",0)
//                playerScore.setText("" + playerUpdatedScore)
//                cpuScore.setText("" + cpuUpdatedScore )

                playerScore.text = "$playerUpdatedScore"
                cpuScore.text = "$cpuUpdatedScore"

            }
        }
    }

    override fun onDestroy() {
        var viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        super.onDestroy()
        viewModel.backgroundMP.release()
        viewModel.warMP.release()
        viewModel.cardMP.release()
    }
}
