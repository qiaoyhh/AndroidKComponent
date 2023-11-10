package com.qyh.page_main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import com.scwang.smart.refresh.layout.api.RefreshHeader
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.simple.SimpleMultiListener

/**
 * describe:
 * @date     2023/7/5
 * @author   qiaoyh
 */
class MinePageRefreshActivity : AppCompatActivity() {
    private var mScrollY = 0
    private lateinit var toolbar: Toolbar
    private lateinit var parallax: View
    private lateinit var buttonBar: View
    private lateinit var refreshLayout: RefreshLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practice_weibo)

        toolbar = findViewById(R.id.toolbar)
        val scrollView = findViewById<NestedScrollView>(R.id.scrollView)
        parallax = findViewById(R.id.parallax)
        buttonBar = findViewById(R.id.buttonBarLayout)
        refreshLayout = findViewById(R.id.refreshLayout)
        scrollViewListener(scrollView)
        refreshLayoutListener()
    }

    private fun refreshLayoutListener() {
        refreshLayout.setOnMultiListener(object : SimpleMultiListener() {
            override fun onRefresh(refreshLayout: RefreshLayout) {
                refreshLayout.finishRefresh(true)
            }

            override fun onHeaderMoving(
                header: RefreshHeader, isDragging: Boolean, percent: Float,
                offset: Int, headerHeight: Int, maxDragHeight: Int) {
                val newOffset = 1f + (percent / 3)
                parallax.scaleY = newOffset
                parallax.scaleX = newOffset
                parallax.translationY = newOffset
                toolbar.alpha = 1 - percent.coerceAtMost(1f)
            }
        })
    }

    private fun scrollViewListener(scrollView: NestedScrollView) {
        scrollView.setOnScrollChangeListener(object : NestedScrollView.OnScrollChangeListener {
            private var lastScrollY = 0
            private val h: Int = 400
            private val color =
                ContextCompat.getColor(applicationContext, R.color.colorPrimary) and 0x00ffffff

            override fun onScrollChange(v: NestedScrollView, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int) {
                val mOffset = scrollY - oldScrollY
                if (lastScrollY < h) {
                    mScrollY = h.coerceAtMost(scrollY)
                    buttonBar.alpha = 1f * mScrollY / h
                    toolbar.setBackgroundColor(255 * mScrollY / h shl 24 or color)
                    parallax.translationY = (mOffset - mScrollY).toFloat()
                }
                lastScrollY = scrollY
            }
        })
        buttonBar.alpha = 0f
        toolbar.setBackgroundColor(0)
    }
}