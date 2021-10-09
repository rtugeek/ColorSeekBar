package com.rtugeek.android.colorseekbardemo.fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.rtugeek.android.colorseekbar.thumb.DefaultThumbDrawer
import com.rtugeek.android.colorseekbar.thumb.DrawableThumbDrawer
import com.rtugeek.android.colorseekbardemo.R
import com.rtugeek.android.colorseekbardemo.Utils
import kotlinx.android.synthetic.main.fragment_alpha_seek_bar.*
import kotlinx.android.synthetic.main.fragment_alpha_seek_bar.chkVertical
import kotlinx.android.synthetic.main.fragment_alpha_seek_bar.chk_enable
import kotlinx.android.synthetic.main.fragment_alpha_seek_bar.chk_show_thumb
import kotlinx.android.synthetic.main.fragment_alpha_seek_bar.seek_border
import kotlinx.android.synthetic.main.fragment_alpha_seek_bar.seek_height
import kotlinx.android.synthetic.main.fragment_alpha_seek_bar.seek_radius
import kotlinx.android.synthetic.main.fragment_alpha_seek_bar.seek_thumb_size
import kotlinx.android.synthetic.main.fragment_alpha_seek_bar.textView
import kotlinx.android.synthetic.main.fragment_alpha_seek_bar.tv_border
import kotlinx.android.synthetic.main.fragment_alpha_seek_bar.tv_height
import kotlinx.android.synthetic.main.fragment_alpha_seek_bar.tv_radius
import kotlinx.android.synthetic.main.fragment_alpha_seek_bar.tv_thumb_size
import kotlinx.android.synthetic.main.fragment_color_seek_bar.*

class AlphaSeekBarFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_alpha_seek_bar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        alphaSeekBar.progress = 255
        alphaSeekBar.thumbDrawer = DrawableThumbDrawer(
            ContextCompat.getDrawable(
                requireContext(),
                R.mipmap.ic_launcher
            ),
            50, 50
        )
        alphaSeekBar.maxProgress = 1000
        alphaSeekBar.borderColor = Color.BLACK
        alphaSeekBar.setOnAlphaChangeListener { progress, alpha ->
            Log.i("AlphaSeekBarFragment", "===progress:$progress-alpha:$alpha===")
            textView.alpha = alpha.toFloat() / 255
        }
        chkVertical.setOnCheckedChangeListener { _, isChecked ->
            alphaSeekBar.isVertical = isChecked
        }

        chk_enable.setOnCheckedChangeListener { buttonView, isChecked ->
            alphaSeekBar.isEnabled = isChecked
        }
        chk_show_thumb.setOnCheckedChangeListener { buttonView, isChecked ->
            alphaSeekBar.isShowThumb = isChecked
        }


        seek_height.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                alphaSeekBar.barHeight = Utils.dp2px(requireContext(), progress.toFloat())
                tv_height.text = "Bar Height: ${progress}dp"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
        seek_border.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                alphaSeekBar.borderSize = Utils.dp2px(requireContext(), progress.toFloat())
                tv_border.text = "Border Size: ${progress}dp"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        seek_thumb_size.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                val size = Utils.dp2px(requireContext(), progress.toFloat())
                alphaSeekBar.thumbDrawer = DrawableThumbDrawer(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.mipmap.ic_launcher
                    ),
                    size, size
                )
                tv_thumb_size.text = "Thumb Size: ${progress}dp"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })


        chk_enable.setOnCheckedChangeListener { buttonView, isChecked ->
            alphaSeekBar.isEnabled = isChecked
        }
        chk_show_thumb.setOnCheckedChangeListener { buttonView, isChecked ->
            alphaSeekBar.isShowThumb = isChecked
        }
        seek_radius.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                alphaSeekBar.borderRadius = progress
                tv_radius.text =
                    String.format("Bar Radius: %dpx", progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
        alphaSeekBar.alphaValue = 200
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AlphaSeekBarFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            AlphaSeekBarFragment()
    }

}