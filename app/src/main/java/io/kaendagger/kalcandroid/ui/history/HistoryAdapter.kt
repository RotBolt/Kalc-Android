package io.kaendagger.kalcandroid.ui.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.kaendagger.kalcandroid.R
import io.kaendagger.kalcandroid.data.entity.Calculation
import io.kaendagger.kalcandroid.data.entity.Date
import io.kaendagger.kalcandroid.data.entity.HistoryEntity
import kotlinx.android.synthetic.main.layout_calculations.view.*
import kotlinx.android.synthetic.main.layout_calculations.view.tvResult
import kotlinx.android.synthetic.main.layout_date.view.*

class HistoryAdapter(private var list: List<HistoryEntity>) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    private val TYPE_DATE = 0
    private val TYPE_CALC = 1

    lateinit var pasteAction:(String)->Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            TYPE_DATE -> ViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.layout_date, parent, false)
            )
            else -> ViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.layout_calculations, parent, false)
            )
        }
    }

    fun updateList(newList:List<HistoryEntity>){
        list = newList
        notifyDataSetChanged()
    }

    override fun getItemCount() = list.size

    override fun getItemViewType(position: Int): Int {
        return when (list[position]) {
            is Date -> TYPE_DATE
            is Calculation -> TYPE_CALC
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        when (item) {
            is Date -> holder.bindDate(item)
            is Calculation -> holder.bindCalculations(item)
        }
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindDate(date: Date) {
            itemView.tvDate.text = date.dateString
        }

        fun bindCalculations(calc: Calculation) {
            with(itemView) {
                tvExpression.text = calc.expression
                tvExpression.isSelected= true

                tvResult.text = calc.result
                tvResult.isSelected= true

                btnPaste.setOnClickListener {
                    pasteAction(calc.result)
                }
            }
        }

    }
}