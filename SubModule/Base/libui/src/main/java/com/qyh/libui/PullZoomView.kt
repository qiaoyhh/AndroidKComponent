package com.qyh.libui

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.Scroller
import androidx.core.view.ViewCompat
import androidx.core.widget.NestedScrollView
import kotlin.math.abs

class PullZoomView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.scrollViewStyle
) : NestedScrollView(context, attrs, defStyleAttr) {

    private var sensitive = 2.5f //放大的敏感系数
    private var zoomTime = 500 //头部缩放时间，单位 毫秒
    private var isParallax = false //是否让头部具有视差动画
    private var isZoomEnable = true //是否允许头部放大
    private val scroller: Scroller//辅助缩放的对象

    private var isActionDown = false //第一次接收的事件是否是Down事件
    private var isZooming = false //是否正在被缩放
    private var headerParams: MarginLayoutParams? = null//头部的参数

    private var headerHeight = 0//头部的原始高度

    private var headerView: View? = null//头布局

    private var zoomView: View? = null //用于缩放的View
    private var contentView: View? = null//主体内容View
    private var lastEventX = 0f//Move事件最后一次发生时的X坐标

    private var lastEventY = 0f//Move事件最后一次发生时的Y坐标

    private var downX = 0f//Down事件的X坐标

    private var downY = 0f//Down事件的Y坐标

    private var maxY = 0//允许的最大滑出距离

    private val touchSlop: Int
    private var scrollListener: OnScrollListener? = null//滚动的监听
    private var pullZoomListener: OnPullZoomListener? = null//下拉放大的监听
    private val isTop: Boolean
        get() = scrollY <= 0

    companion object {
        private const val TAG_HEADER = "header" //头布局Tag
        private const val TAG_ZOOM = "zoom" //缩放布局Tag
        private const val TAG_CONTENT = "content" //内容布局Tag
    }

    init {
        val a = getContext().obtainStyledAttributes(attrs, R.styleable.PullZoomView)
        sensitive = a.getFloat(R.styleable.PullZoomView_pzv_sensitive, sensitive)
        isParallax = a.getBoolean(R.styleable.PullZoomView_pzv_isParallax, isParallax)
        isZoomEnable = a.getBoolean(R.styleable.PullZoomView_pzv_isZoomEnable, isZoomEnable)
        zoomTime = a.getInt(R.styleable.PullZoomView_pzv_zoomTime, zoomTime)
        a.recycle()
        scroller = Scroller(getContext())
        touchSlop = ViewConfiguration.get(context).scaledTouchSlop
        viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
                maxY = contentView?.top ?: 0
            }
        })

        overScrollMode = OVER_SCROLL_NEVER
    }

    fun setOnScrollListener(scrollListener: OnScrollListener?) {
        this.scrollListener = scrollListener
    }

    /** 滚动的监听，范围从 0 ~ maxY  */
    interface OnScrollListener {
        fun onScroll(l: Int, t: Int, oldl: Int, oldt: Int) {}
        fun onHeaderScroll(currentY: Int, maxY: Int) {}
        fun onContentScroll(l: Int, t: Int, oldl: Int, oldt: Int) {}
    }


    fun setOnPullZoomListener(pullZoomListener: OnPullZoomListener?) {
        this.pullZoomListener = pullZoomListener
    }

    interface OnPullZoomListener {
        fun onPullZoom(originHeight: Int, currentHeight: Int) {}
        fun onZoomFinish() {}
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        findTagViews(this)
        checkNotNull(headerView == null || zoomView == null || contentView == null) { "content, header, zoom 都不允许为空,请在Xml布局中设置Tag，或者使用属性设置" }
        headerParams = headerView?.layoutParams as MarginLayoutParams
        headerHeight = headerParams?.height ?: 0
        smoothScrollTo(0, 0) //如果是滚动到最顶部，默认最顶部是ListView的顶部
    }

    /** 递归遍历所有的View，查询Tag  */
    private fun findTagViews(v: View?) {
        if(v == null) return
        if (v is ViewGroup) {
            for (i in 0 until v.childCount) {
                val childView = v.getChildAt(i) ?: return
                val tag = childView.tag
                if (TAG_CONTENT == tag && contentView == null) contentView = childView
                if (TAG_HEADER == tag && headerView == null) headerView = childView
                if (TAG_ZOOM == tag && zoomView == null) zoomView = childView
                (childView as? ViewGroup)?.let { findTagViews(it) }
            }
        } else {
            val tag = v.tag ?: return
            if (TAG_CONTENT == tag && contentView == null) contentView = v
            if (TAG_HEADER == tag && headerView == null) headerView = v
            if (TAG_ZOOM == tag && zoomView == null) zoomView = v
        }
    }

    private var scrollFlag = false //该标记主要是为了防止快速滑动时，onScroll回调中可能拿不到最大和最小值
    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        var t = t
        super.onScrollChanged(l, t, oldl, oldt)
        scrollListener?.onScroll(l, t, oldl, oldt)
        if (t in 0..maxY) {
            scrollFlag = true
            scrollListener?.onHeaderScroll(t, maxY)
        } else if (scrollFlag) {
            scrollFlag = false
            if (t < 0) t = 0
            if (t > maxY) t = maxY
            scrollListener?.onHeaderScroll(t, maxY)
        }
        if (t >= maxY) {
            scrollListener?.onContentScroll(
                l, t - maxY, oldl, oldt - maxY
            )
        }

        if (isParallax) {
            if (t in 0..headerHeight) {
                headerView!!.scrollTo(0, -(0.65 * t).toInt())
            } else {
                headerView!!.scrollTo(0, 0)
            }
        }
    }

    /**
     * 主要用于解决 RecyclerView嵌套，直接拦截事件，可能会出现其他问题
     * 如果不需要使用  RecyclerView，可以将这里代码注释掉
     */
    override fun onInterceptTouchEvent(e: MotionEvent): Boolean {
        when (e.action) {
            MotionEvent.ACTION_DOWN -> {
                downX = e.x
                downY = e.y
            }

            MotionEvent.ACTION_MOVE -> {
                val moveY = e.y
                if (abs(moveY - downY) > touchSlop) {
                    return true
                }
            }
        }
        return super.onInterceptTouchEvent(e)
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        if (!isZoomEnable) return super.onTouchEvent(ev)
        val currentX = ev.x
        val currentY = ev.y
        when (ev.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                run {
                    lastEventX = currentX
                    downX = lastEventX
                }
                run {
                    lastEventY = currentY
                    downY = lastEventY
                }
                scroller.abortAnimation()
                isActionDown = true
            }

            MotionEvent.ACTION_MOVE -> {
                if (!isActionDown) {
                    lastEventX = currentX
                    downX = lastEventX
                    lastEventY = currentY
                    downY = lastEventY
                    scroller.abortAnimation()
                    isActionDown = true
                }
                val shiftX = abs(currentX - downX)
                val shiftY = abs(currentY - downY)
                val dy = currentY - lastEventY
                lastEventY = currentY
                if (isTop) {
                    if (shiftY > shiftX && shiftY > touchSlop) {
                        var height = (headerParams!!.height + dy / sensitive + 0.5).toInt()
                        if (height <= headerHeight) {
                            height = headerHeight
                            isZooming = false
                        } else {
                            isZooming = true
                        }
                        headerParams?.height = height
                        headerView?.layoutParams = headerParams
                        pullZoomListener?.onPullZoom(
                            headerHeight, headerParams?.height ?: 0
                        )
                    }
                }
            }

            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                isActionDown = false
                if (isZooming) {
                    headerParams?.height?.let {
                        scroller.startScroll(
                            0, it, 0, -(headerParams!!.height - headerHeight), zoomTime
                        )
                    }
                    isZooming = false
                    ViewCompat.postInvalidateOnAnimation(this)
                }
            }
        }
        return isZooming || super.onTouchEvent(ev)
    }

    private var isStartScroll = false //当前是否下拉过


    override fun computeScroll() {
        super.computeScroll()
        if (scroller.computeScrollOffset()) {
            isStartScroll = true
            headerParams?.height = scroller.currY
            headerView?.layoutParams = headerParams
            pullZoomListener?.onPullZoom(
                headerHeight, headerParams!!.height
            )
            ViewCompat.postInvalidateOnAnimation(this)
        } else {
            if (isStartScroll) {
                isStartScroll = false
                pullZoomListener?.onZoomFinish()
            }
        }
    }


    fun setSensitive(sensitive: Float) {
        this.sensitive = sensitive
    }

    fun setIsParallax(isParallax: Boolean) {
        this.isParallax = isParallax
    }

    fun setIsZoomEnable(isZoomEnable: Boolean) {
        this.isZoomEnable = isZoomEnable
    }

    fun setZoomTime(zoomTime: Int) {
        this.zoomTime = zoomTime
    }
}