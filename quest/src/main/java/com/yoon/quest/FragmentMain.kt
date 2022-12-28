package com.yoon.quest

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.CompoundButton
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.yoon.quest.MainData.DataModel
import com.yoon.quest.MainData.DataModelViewModel
import com.yoon.quest.databinding.FragmentMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.everything.android.ui.overscroll.IOverScrollDecor
import me.everything.android.ui.overscroll.VerticalOverScrollBounceEffectDecorator
import me.everything.android.ui.overscroll.adapters.RecyclerViewOverScrollDecorAdapter
import timber.log.Timber
import java.lang.Exception

class FragmentMain : BaseFragment<FragmentMainBinding>() {
    private val mModel by viewModels<DataModelViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return DataModelViewModel(requireActivity().application) as T
            }
        }
    }
    private lateinit var mAdapter: ListAdapter
    private lateinit var mSelectedColor: String
    private val mSelectedColorList: ArrayList<String> = ArrayList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()

        mModel.getData().observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                Log.e("checkCheck","ㅇㅇㅇㅇ 데이터 사이즈 : ${it.size}")
                mAdapter.setList(ArrayList(it))
            } else
                Log.e("checkCheck","ㅇㅇㅇㅇ list is null")
        }
        mModel.showAllData()
        // 아래로 끌어당겨 데이터 추가
        var decor = VerticalOverScrollBounceEffectDecorator(
            RecyclerViewOverScrollDecorAdapter(
                mBinding.recycler,
                ItemTouchHelperCallback(mAdapter)
            )
        )
        decor.setOverScrollUpdateListener { decor1: IOverScrollDecor, _: Int, offset: Float ->
            val view: View? = decor1.view
            when {
                offset > 150 -> {
                    Timber.tag("checkCheck").d("offset : %s", offset)
                    // 'view' is currently being over-scrolled from the top.
                    val delayHandler = Handler()
                    delayHandler.postDelayed({
                        findNavController().navigate(R.id.fragAdd)
                    }, 500)
                }
                offset < 0 -> {
                    // 'view' is currently being over-scrolled from the bottom.
                }
                else -> {
                    // No over-scroll is in-effect.
                    // This is synonymous with having (state == STATE_IDLE).
                }
            }
        }

        // fold button
        mBinding.foldButton.setOnClickListener { clickFoldBtn() }
        // unfold button
        mBinding.unFoldButton.setOnClickListener { clickUnFoldBtn() }

        // 토글 컬러 color
        mBinding.colorBtn1.setOnCheckedChangeListener(mColorClickListener)
        mBinding.colorBtn2.setOnCheckedChangeListener(mColorClickListener)
        mBinding.colorBtn3.setOnCheckedChangeListener(mColorClickListener)
        mBinding.colorBtn4.setOnCheckedChangeListener(mColorClickListener)
        mBinding.colorBtn5.setOnCheckedChangeListener(mColorClickListener)
        mBinding.colorBtn6.setOnCheckedChangeListener(mColorClickListener)
        mBinding.colorBtn7.setOnCheckedChangeListener(mColorClickListener)
        mBinding.colorBtn8.setOnCheckedChangeListener(mColorClickListener)
    }

    private fun initRecyclerView() {
        mBinding.recycler.layoutManager = LinearLayoutManager(requireContext())
        mAdapter = ListAdapter(requireContext(), object : ListAdapter.Listener {
            override fun eventRemoveItem(dataModel: DataModel) {
                CoroutineScope(Dispatchers.Main).launch {
                    mModel.delete(dataModel)
                }
            }

            override fun eventItemClick(dataModel: DataModel) {
                val action = FragmentMainDirections.actionFragMainToFragEdit(dataModel.id)
                findNavController().navigate(action)
            }
        })
        mBinding.recycler.adapter = mAdapter
    }

    private fun clickFoldBtn() {
        mBinding.foldButton.visibility = View.GONE
        mBinding.foldView.visibility = View.VISIBLE
        val mmSlideUp: Animation = AnimationUtils.loadAnimation(context, R.anim.slide_up)
        mBinding.foldView.startAnimation(mmSlideUp)
    }

    private fun clickUnFoldBtn() {
        val mmSlideDown: Animation = AnimationUtils.loadAnimation(context, R.anim.slide_down)
        mBinding.foldView.startAnimation(mmSlideDown)
        mBinding.foldView.visibility = View.GONE
        val delayHandler = Handler()
        delayHandler.postDelayed({ mBinding.foldButton.visibility = View.VISIBLE }, 500)
    }

    private val mColorClickListener = CompoundButton.OnCheckedChangeListener { buttonView, _ ->
            if (buttonView != null) {
                mSelectedColor = setBlockColor(buttonView)
                if (!mSelectedColorList.contains(mSelectedColor)) {
                    mSelectedColorList.add(mSelectedColor)
                    buttonView.isChecked = true
                    //                    selectColor(mSelectedColor);
                } else {
                    mSelectedColorList.remove(mSelectedColor)
                    buttonView.isChecked = false
                    //                    cancelColor(mSelectedColor);
                }
            }
            Timber.tag("checkCheck").d("선택한 색갈 : %s", mSelectedColor)
            for (color in mSelectedColorList) {
                Timber.tag("checkCheck").d("선택한 색갈 리스트 : %s", color)
                try {
                    CoroutineScope(Dispatchers.Main).launch {
                        mModel.showDataByColor(color)
                    }
//                    mAdapter.setList(ArrayList(data.value))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

    private fun setBlockColor(colorBtnName: CompoundButton): String {
        var mmSetColor: String = ""
        when {
            mBinding.colorBtn1 == colorBtnName -> {
                mmSetColor = "#f5b1c8"
            }
            mBinding.colorBtn2 == colorBtnName -> {
                mmSetColor = "#f5d3ae"
            }
            mBinding.colorBtn3 == colorBtnName -> {
                mmSetColor = "#fff5b7"
            }
            mBinding.colorBtn4 == colorBtnName -> {
                mmSetColor = "#c8f6ae"
            }
            mBinding.colorBtn5 == colorBtnName -> {
                mmSetColor = "#a6e3db"
            }
            mBinding.colorBtn6 == colorBtnName -> {
                mmSetColor = "#afe4f4"
            }
            mBinding.colorBtn7 == colorBtnName -> {
                mmSetColor = "#c4c5f3"
            }
            mBinding.colorBtn8 == colorBtnName -> {
                mmSetColor = "#e1c3f4"
            }
        }
        return mmSetColor
    }


    override fun getViewBinding(): FragmentMainBinding {
        return FragmentMainBinding.inflate(layoutInflater)
    }
}