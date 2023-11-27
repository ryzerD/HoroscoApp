package com.symbiot.horoscapp.ui.horoscope.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.symbiot.horoscapp.R
import com.symbiot.horoscapp.domain.model.HoroscopeInfo

class HoroscopoAdapter(
    private var horoscopeInfo: List<HoroscopeInfo> = emptyList(),
    private val onItemSelected: (HoroscopeInfo) -> Unit
) :
    RecyclerView.Adapter<HoroscopoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HoroscopoViewHolder {
        return HoroscopoViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_horoscope, parent, false)
        )
    }

    fun updateList(horoscopeInfo: List<HoroscopeInfo>) {
        this.horoscopeInfo = horoscopeInfo
        notifyDataSetChanged()
    }

    override fun getItemCount() = horoscopeInfo.size

    override fun onBindViewHolder(holder: HoroscopoViewHolder, position: Int) {
        holder.render(horoscopeInfo[position], onItemSelected)
    }
}