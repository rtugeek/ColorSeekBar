package com.rtugeek.android.colorseekbardemo.fragment

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import com.rtugeek.android.colorseekbar.thumb.DefaultThumbDrawer
import com.rtugeek.android.colorseekbardemo.Utils
import com.rtugeek.android.colorseekbardemo.databinding.FragmentColorSeekBarBinding
import kotlinx.android.synthetic.main.fragment_color_seek_bar.*

/**
 * A placeholder fragment containing a simple view.
 */
class ColorSeekBarFragment : Fragment() {

    private var _binding: FragmentColorSeekBarBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentColorSeekBarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        colorSeekBar.thumbDrawer = DefaultThumbDrawer(15, Color.BLACK, 3, Color.WHITE)
        colorSeekBar.color = -16777216
        colorSeekBar.setOnColorChangeListener { progress, color ->
            textView.setTextColor(colorSeekBar.color)
            Log.i(TAG, "===progress:$progress-color:$color===")
        }
        chkVertical.setOnCheckedChangeListener { _, isChecked ->
            colorSeekBar.isVertical = isChecked
        }
        seek_height.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                colorSeekBar.barHeight = Utils.dp2px(requireContext(), progress.toFloat())
                tv_height.text = "Bar Height: ${progress}dp"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
        seek_border.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                colorSeekBar.borderSize = Utils.dp2px(requireContext(), progress.toFloat())
                tv_border.text = "Border Size: ${progress}dp"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        seek_thumb_size.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                val thumbDrawer = DefaultThumbDrawer(
                    Utils.dp2px(requireContext(), progress.toFloat()), Color.WHITE, Color.BLACK
                )
                thumbDrawer.ringBorderSize = 1
                thumbDrawer.ringSize = 5
                colorSeekBar.thumbDrawer = thumbDrawer
                tv_thumb_size.text = "Thumb Size: ${progress}dp"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })


        chk_enable.setOnCheckedChangeListener { buttonView, isChecked ->
            colorSeekBar.isEnabled = isChecked
        }
        chk_show_thumb.setOnCheckedChangeListener { buttonView, isChecked ->
            colorSeekBar.isShowThumb = isChecked
        }

        seek_radius.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                colorSeekBar.borderRadius = progress
                tv_radius.text =
                    String.format("Bar Radius: %dpx", progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
    }

    override fun onStop() {
        super.onStop()
    }

    companion object {
        private const val TAG = "ColorSeekBar"

        @JvmStatic
        fun newInstance(): ColorSeekBarFragment {
            return ColorSeekBarFragment()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}