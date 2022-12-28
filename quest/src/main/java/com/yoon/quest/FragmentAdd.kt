package com.yoon.quest

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.CompoundButton
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.yoon.quest.MainData.DataModel
import com.yoon.quest.MainData.DataModelViewModel
import com.yoon.quest.databinding.FragmentAddEditBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber

class FragmentAdd : BaseFragment<FragmentAddEditBinding>() {
    private val mModel by viewModels<DataModelViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return DataModelViewModel(requireActivity().application) as T
            }
        }
    }
    private var mSelectedColor: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//
//        /**
//         *  뒤로가기
//         */
//        val mmBackCallback : OnBackPressedCallback = object : OnBackPressedCallback(true){
//            override fun handleOnBackPressed() {
//                findNavController().popBackStack(R.id.fragMain, false)
//            }
//        }
//        requireActivity().onBackPressedDispatcher.addCallback(mmBackCallback)

        mBinding.backButton.setOnClickListener {
            findNavController().popBackStack(R.id.fragMain, false)
        }

        /**
         * Database 를 관찰하고 변경이 감지될 때 UI 갱신
         * 데이터베이스 mDB -> 데이터베이스 mDataModelDAO()
         * -> getAll() 가져오는 List<DataModel> 객체는 관찰 가능한 객체 이므로
         * -> observe 메소드로 관찰하고 변경이 되면 dataList 에 추가한다.
         * 변경된 내용이 담긴 dataList 를 출력한다.
         **/
        /*
         * 람다식 사용
         * file -> project structure -> modules -> source compatibility, target compatibility -> 1.8
         **/

        /**
         * Insert
         * 데이터배이스 객체 . 데이터베이스 DAO . insert -> new DataModel (텍스트 추가)
         **/
        mBinding.doneButton.setOnClickListener { v ->
            /**
             * AsyncTask 생성자에 execute 로 DataModelDAO 객체를 던저준다.
             * 비동기 처리
             */
            if (mBinding.title.text.toString() == "") {
                Toast.makeText(context, "제목을 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
//            if (mBinding.addContentEdit.getText().toString().equals("")) {
//                Toast.makeText(getContext(), "내용을 입력해주세요.", Toast.LENGTH_SHORT).show();
//                return;
//            }
            if (mSelectedColor == null) {
                Toast.makeText(context, "색깔을 선택해주세요.", Toast.LENGTH_SHORT).show()
                hideKeyboard()
                return@setOnClickListener
            }
            val dataModel = DataModel( mBinding.title.text.toString(), mBinding.contentEdit.text.toString(), mSelectedColor)
            CoroutineScope(Dispatchers.Main).launch{
                mModel.insert(dataModel)
            }
            findNavController().popBackStack(R.id.fragMain,false)
        }

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

    private val mColorClickListener: CompoundButton.OnCheckedChangeListener =
        object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
                // don't allow the un-checking
                if (!isChecked) {
                    buttonView.isChecked = true
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

    private fun showKeyboard() {
        val imm = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    override fun getViewBinding(): FragmentAddEditBinding {
        return FragmentAddEditBinding.inflate(layoutInflater)
    }
}