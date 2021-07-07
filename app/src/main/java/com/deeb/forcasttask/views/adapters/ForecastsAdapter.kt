package com.deeb.forcasttask.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.deeb.domain.models.ForecastModel
import com.deeb.forcasttask.databinding.ItemForecastBinding

class ForecastsAdapter : RecyclerView.Adapter<ForecastsAdapter.ViewHolder>() {

    private var itemsList:MutableList<ForecastModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemForecastBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                var item=itemsList[position]
                holder.bind(item)
        }

    fun addValues(list: List<ForecastModel>){
        if(itemsList.isNotEmpty())
            itemsList.clear()
        itemsList.addAll(list)
        notifyDataSetChanged()
    }

    fun clear() {
        if(itemsList.isNotEmpty())
            itemsList.clear()
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemForecastBinding) : RecyclerView.ViewHolder(binding.root) {
      fun bind(item:ForecastModel){
          binding.tvDate.text=item.date
          binding.tvCity.text=item.city
          binding.tvMax.text=item.tempMax.toString()
          binding.tvMin.text=item.tempMin.toString()
          binding.tvWeather.text=item.weather

      }

    }
}