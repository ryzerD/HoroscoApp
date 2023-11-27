package com.symbiot.horoscapp.ui.horoscope.adapter

import android.view.View
import android.view.animation.LinearInterpolator
import androidx.recyclerview.widget.RecyclerView
import com.symbiot.horoscapp.databinding.ItemHoroscopeBinding
import com.symbiot.horoscapp.domain.model.HoroscopeInfo

class HoroscopoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemHoroscopeBinding.bind(view)
    fun render(horoscopeInfo: HoroscopeInfo, onItemSelected: (HoroscopeInfo) -> Unit) {
        binding.ivHoroscope.setImageResource(horoscopeInfo.img)
        binding.tvHoroscope.text = binding.tvHoroscope.context.getText(horoscopeInfo.name)

        binding.clParent.setOnClickListener {
            startRotationAnimation(binding.ivHoroscope, newLamda = {
                onItemSelected(horoscopeInfo)
            })

        }
    }

    private fun startRotationAnimation(view: View, newLamda:()->Unit) {
        view.animate().apply {
            duration = 300
            interpolator = LinearInterpolator()
            rotationBy(360f)
            withEndAction {
                newLamda()
            }
            start()
        }
    }

}