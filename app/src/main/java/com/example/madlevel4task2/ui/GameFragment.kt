package com.example.madlevel4task2.ui

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.madlevel4task2.R
import com.example.madlevel4task2.databinding.PlayFragmentBinding
import com.example.madlevel4task2.model.ItemChoice
import com.example.madlevel4task2.model.RockPaperScissors
import com.example.madlevel4task2.model.Win
import com.example.madlevel4task2.repository.RockPaperScissorsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Math.floorMod
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class GameFragment : Fragment() {

    private val sdf = SimpleDateFormat("dd.MM.yyyy 'at' HH:mm:ss z", Locale.getDefault())
    private var currentDateAndTime: String = sdf.format(Date())
    private val aRPS = ItemChoice.values()
    private lateinit var binding: PlayFragmentBinding
    private val mainScope = CoroutineScope(Dispatchers.Main)
    private lateinit var rockPaperScissorsRepository: RockPaperScissorsRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = PlayFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rockPaperScissorsRepository = RockPaperScissorsRepository(requireContext())

        binding.ivRock.setOnClickListener { play(ItemChoice.ROCK) }
        binding.ivPaper.setOnClickListener { play(ItemChoice.PAPER) }
        binding.ivScissors.setOnClickListener { play(ItemChoice.SCISSORS) }

        updateStats()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun play(playerRPS: ItemChoice) {
        decideWinner(playerRPS, aRPS[Random.nextInt(aRPS.size)])
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun decideWinner(player: ItemChoice, computer: ItemChoice) {
        val winner = when {
            player == computer -> {
                Win.DRAW
            }
            floorMod((player.ordinal - 1), aRPS.size) == computer.ordinal -> {
                Win.PLAYER
            }
            else -> {
                Win.COMPUTER
            }
        }

        binding.ivPlayer.setImageResource(getImageResource(player.ordinal))
        binding.ivCpu.setImageResource(getImageResource(computer.ordinal))
        binding.tvResult.setText(getResultString(winner.ordinal))


        val game = RockPaperScissors(
            player = player.ordinal,
            computer = computer.ordinal,
            winner = winner.ordinal,
            date = currentDateAndTime
        )

        mainScope.launch {
            withContext(Dispatchers.IO) {
                rockPaperScissorsRepository.insertGame(game)
            }
        }

        updateStats()
    }

    private fun updateStats() {

        mainScope.launch {
            val win = withContext(Dispatchers.IO) {
                rockPaperScissorsRepository.getAllPlayerWins()
            }
            val draw = withContext(Dispatchers.IO) {
                rockPaperScissorsRepository.getAllDraws()
            }
            val lose = withContext(Dispatchers.IO) {
                rockPaperScissorsRepository.getAllCpuWins()
            }
            binding.tvStatistics.text = getString(
                R.string.statistics,
                win,
                draw,
                lose
            )

        }
    }
}

fun getImageResource(rps: Int): Int {
    return when (ItemChoice.values()[rps]) {
        ItemChoice.ROCK -> R.drawable.rock
        ItemChoice.PAPER -> R.drawable.paper
        ItemChoice.SCISSORS -> R.drawable.scissors
    }
}

private fun getResultString(winner: Int): Int {
    return when (Win.values()[winner]) {
        Win.PLAYER -> R.string.win
        Win.DRAW -> R.string.draw
        Win.COMPUTER -> R.string.lose
    }
}