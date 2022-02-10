package com.yoon.quest

import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.yoon.quest.MainData.DataModel
import com.yoon.quest.MainData.DataModelViewModel
import com.yoon.quest.databinding.FragmentAddEditBinding
import timber.log.Timber

class FragmentEdit : BaseFragment<FragmentAddEditBinding>() {
    private val mModel by viewModels<DataModelViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return DataModelViewModel(requireActivity().application) as T
            }
        }
    }
    private var mSelectedColor: String? = null
    private lateinit var mSelectedData : DataModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mmReceivedData: FragmentEditArgs by navArgs()
        val mmReceivedKey = mmReceivedData.idKey
        mSelectedData = mModel.getIdData(mmReceivedKey)

        mBinding.backButton.setOnClickListener {
            findNavController().popBackStack(R.id.fragMain, false)
        }

        mBinding.doneButton.setOnClickListener {
            if (mBinding.title.text.toString() == "") {
                Toast.makeText(context, "제목을 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            
            val mmUpdateData = DataModel(
                mBinding.title.text.toString(),
                mBinding.contentEdit.text.toString(),
                mSelectedColor
            )
            mModel.update(mSelectedData.id, mmUpdateData)
            findNavController().popBackStack(R.id.fragMain, false)
        }

        mBinding.title.setText(mSelectedData.title)
        mBinding.contentEdit.setText(mSelectedData.content)
        getScheduleColor(mSelectedData.color)

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

    private val mColorClickListener: CompoundButton.OnCheckedChangeListener = object : CompoundButton.OnCheckedChangeListener {
            var avoidRecursions = false
            override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
                if (avoidRecursions) return
                avoidRecursions = true

                // don't allow the un-checking
                if (!isChecked) {
                    buttonView.isChecked = true
                    avoidRecursions = false
                    return
                }

                // un-check the previous checked button
                if (buttonView !== mBinding.colorBtn1 && mBinding.colorBtn1.isChecked) mBinding.colorBtn1.isChecked =
                    false else if (buttonView !== mBinding.colorBtn2 && mBinding.colorBtn2.isChecked) mBinding.colorBtn2.isChecked =
                    false else if (buttonView !== mBinding.colorBtn3 && mBinding.colorBtn3.isChecked) mBinding.colorBtn3.isChecked =
                    false else if (buttonView !== mBinding.colorBtn4 && mBinding.colorBtn4.isChecked) mBinding.colorBtn4.isChecked =
                    false else if (buttonView !== mBinding.colorBtn5 && mBinding.colorBtn5.isChecked) mBinding.colorBtn5.isChecked =
                    false else if (buttonView !== mBinding.colorBtn6 && mBinding.colorBtn6.isChecked) mBinding.colorBtn6.isChecked =
                    false else if (buttonView !== mBinding.colorBtn7 && mBinding.colorBtn7.isChecked) mBinding.colorBtn7.isChecked =
                    false else if (buttonView !== mBinding.colorBtn8 && mBinding.colorBtn8.isChecked) mBinding.colorBtn8.isChecked =
                    false
                avoidRecursions = false
                mSelectedColor = setBlockColor(buttonView)
                Timber.tag("checkCheck").d("선택한 색갈 : %s", mSelectedColor)
            }
        }

    private fun setBlockColor(colorBtnName: CompoundButton): String? {
        var mmSetColor: String? = null
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

    private fun getScheduleColor(color: String) {
        mSelectedColor = color
        when (color) {
            "#f5b1c8" -> mBinding.colorBtn1.isChecked = true
            "#f5d3ae" -> mBinding.colorBtn2.isChecked = true
            "#fff5b7" -> mBinding.colorBtn3.isChecked = true
            "#c8f6ae" -> mBinding.colorBtn4.isChecked = true
            "#a6e3db" -> mBinding.colorBtn5.isChecked = true
            "#afe4f4" -> mBinding.colorBtn6.isChecked = true
            "#c4c5f3" -> mBinding.colorBtn7.isChecked = true
            "#e1c3f4" -> mBinding.colorBtn8.isChecked = true
        }
    }

    override fun getViewBinding(): FragmentAddEditBinding {
        return FragmentAddEditBinding.inflate(layoutInflater)
    }
}