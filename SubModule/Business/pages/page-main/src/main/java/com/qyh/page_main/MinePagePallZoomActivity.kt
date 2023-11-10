package com.qyh.page_main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import com.qyh.libui.PullZoomView
import com.scwang.smart.refresh.layout.api.RefreshHeader
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.simple.SimpleMultiListener

/**
 * describe:
 * @date     2023/7/5
 * @author   qiaoyh
 */
class MinePagePallZoomActivity : AppCompatActivity() {
    private var mScrollY = 0
    private lateinit var toolbar: Toolbar
    private lateinit var buttonBar: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mine_pull_zoom)

        toolbar = findViewById(R.id.toolbar)
        val scrollView = findViewById<PullZoomView>(R.id.scrollView)
        buttonBar = findViewById(R.id.buttonBarLayout)
        scrollViewListener(scrollView)

        scrollView.setOnPullZoomListener(object : PullZoomView.OnPullZoomListener {
            override fun onPullZoom(originHeight: Int, currentHeight: Int) {
                super.onPullZoom(originHeight, currentHeight)
                Log.e("qyh", "onPullZoom: ======" )
            }

            override fun onZoomFinish() {
                super.onZoomFinish()
                Log.e("qyh", "onZoomFinish: ======" )
            }
        })
    }


    private fun scrollViewListener(scrollView: NestedScrollView) {
        scrollView.setOnScrollChangeListener(object : NestedScrollView.OnScrollChangeListener {
            private var lastScrollY = 0
            private val h: Int = 400
            private val color =
                ContextCompat.getColor(applicationContext, R.color.colorPrimary) and 0x00ffffff

            override fun onScrollChange(
                v: NestedScrollView,
                scrollX: Int,
                scrollY: Int,
                oldScrollX: Int,
                oldScrollY: Int) {
                if (lastScrollY < h) {
                    mScrollY = h.coerceAtMost(scrollY)
                    buttonBar.alpha = 1f * mScrollY / h
                    toolbar.setBackgroundColor(255 * mScrollY / h shl 24 or color)
                }
                lastScrollY = scrollY
            }
        })
        buttonBar.alpha = 0f
        toolbar.setBackgroundColor(0)
    }
}