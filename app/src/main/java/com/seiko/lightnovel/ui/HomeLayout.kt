package com.seiko.lightnovel.ui

import android.content.Context
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.seiko.lightnovel.component.koin.viewModel
import com.seiko.lightnovel.component.view.autoMeasure
import com.seiko.lightnovel.viewmodel.HomeViewModel

class HomeLayout(context: Context) : BaseLayout(context) {

    private val title = TextView(context)
    private val btnDetail = Button(context)

    private val viewModel: HomeViewModel by viewModel()

    private val navController by lazy(LazyThreadSafetyMode.NONE) {
        findNavController()
    }

    init {
        title.text = "Home"
        btnDetail.text = "go To detail"

        addView(title)
        addView(btnDetail)

        btnDetail.setOnClickListener {
            navController.navigate("detail/test")
        }

        lifecycleScope.launchWhenResumed {
            viewModel.init()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        title.autoMeasure()
        btnDetail.autoMeasure()
    }

    override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {
        layoutCenter(title, btnDetail)
    }
}
