package io.kaendagger.kalcandroid.ui.history


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.kaendagger.kalcandroid.R
import io.kaendagger.kalcandroid.data.Repository
import io.kaendagger.kalcandroid.data.entity.Calculation
import io.kaendagger.kalcandroid.data.entity.Date
import io.kaendagger.kalcandroid.data.entity.HistoryEntity
import kotlinx.coroutines.*
import java.util.*
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_history.*
import kotlin.coroutines.CoroutineContext

class HistoryFragment : Fragment() {


    lateinit var pasteAction:(String)->Unit

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val historyAdapter =  HistoryAdapter(emptyList()).also {
            it.pasteAction = pasteAction
        }
        rvCalculations.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = historyAdapter
            isNestedScrollingEnabled = true
        }
        Repository(requireActivity().application).getAllCalculations()
            .observe(this@HistoryFragment, Observer {
                val historyEntities = getHistoryEntityList(it)
                historyAdapter.updateList(historyEntities)
            })

    }

    private fun getHistoryEntityList(list: List<Calculation>): List<HistoryEntity> {

        fun parseDate(timeInMillis: Long): String {
            val calendar = Calendar.getInstance().also { it.timeInMillis = timeInMillis }
            return with(calendar) {
                val day = get(Calendar.DAY_OF_MONTH)
                val month = get(Calendar.MONTH) + 1
                val year = get(Calendar.YEAR)

                "$day/$month/$year"
            }
        }

        val historyMap = hashMapOf<String, ArrayList<Calculation>>()
        for (item in list) {
            val dateString = parseDate(item.timeInMillis)
            var shortList = historyMap[dateString]
            if (shortList == null) {
                shortList = arrayListOf()
            }
            shortList.add(item)
            historyMap[dateString] = shortList
        }
        Log.i("PUI", " map size ${historyMap.size}")
        val historyEntities = mutableListOf<HistoryEntity>()
        for (item in historyMap) {
            historyEntities.add(Date(item.key))
            val shortList = historyMap[item.key]
            shortList?.forEach {
                historyEntities.add(it)
            }
        }
        return historyEntities
    }

}
