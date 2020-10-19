package com.example.madlevel4task2.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madlevel4task2.model.RockPaperScissors
import com.example.madlevel4task2.R
import com.example.madlevel4task2.repository.RockPaperScissorsRepository
import com.example.madlevel4task2.databinding.HistoryFragmentBinding
import kotlinx.android.synthetic.main.history_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class HistoryFragment : Fragment() {

    private val games = arrayListOf<RockPaperScissors>()
    private val historyAdapter = HistoryAdapter(games)
    private lateinit var binding: HistoryFragmentBinding
    private lateinit var rockPaperScissorsRepository: RockPaperScissorsRepository
    private val mainScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = HistoryFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rockPaperScissorsRepository = RockPaperScissorsRepository(requireContext())
        getGamesFromDatabase()
        initRv()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete -> {
                deleteGamesFromDatabase()
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initRv() {
        rvHistory.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        rvHistory.adapter = historyAdapter
        rvHistory.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }

    private fun getGamesFromDatabase() {
        mainScope.launch {
            val games = withContext(Dispatchers.IO) {
                rockPaperScissorsRepository.getAllGames()
            }
            this@HistoryFragment.games.clear()
            this@HistoryFragment.games.addAll(games)
            this@HistoryFragment.historyAdapter.notifyDataSetChanged()
        }
    }

    private fun deleteGamesFromDatabase(): Boolean {
        mainScope.launch {
            withContext(Dispatchers.IO) {
                rockPaperScissorsRepository.deleteHistory()
            }
            getGamesFromDatabase()
        }
        return true
    }
}