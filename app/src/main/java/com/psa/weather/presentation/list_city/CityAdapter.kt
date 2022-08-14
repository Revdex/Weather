package com.psa.weather.presentation.list_city

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.psa.weather.databinding.ItemCityBinding
import com.psa.weather.domain.model.CityEntity
import com.psa.weather.utils.getDrawableIdByName

class CityAdapter(private val listener: OnItemClickListener) :
    ListAdapter<CityEntity, CityAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class ViewHolder(private val binding: ItemCityBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                root.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position))
                    }
                }

            }
        }

        fun bind(city: CityEntity) {
            binding.apply {
                name.text = city.name
                date.text = city.date
                temperature.text = city.temperature
                binding.icon.setImageResource(binding.root.context.getDrawableIdByName(city.icon))
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(city: CityEntity)
    }

    class DiffCallback : DiffUtil.ItemCallback<CityEntity>() {
        override fun areItemsTheSame(oldItem: CityEntity, newItem: CityEntity) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: CityEntity, newItem: CityEntity) =
            oldItem.name == newItem.name
    }
}