package com.yoon.quest

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yoon.quest.MainData.DataModel
import com.yoon.quest.databinding.ItemBinding
import timber.log.Timber

class ListAdapter(private val mContext: Context, private val listener: Listener) :
    RecyclerView.Adapter<com.yoon.quest.ListAdapter.ItemViewHolder>(),
    ItemTouchHelperCallback.ItemTouchHelperListener {

    private var mListener: Listener = listener
    private val items = ArrayList<DataModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    public fun setList(data: ArrayList<DataModel>) {
        items.clear()
        for(item in data) {
            Log.e("checkCheck", "ㅇㅇㅇㅇ 데이터 내용 : ${item.title}")
        }
        items.addAll(data)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val mParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        // 마지막 아이템에 마진추가.
        if (position == items.size - 1) {
            mParams.topMargin = 30
            mParams.leftMargin = 30
            mParams.rightMargin = 30
            mParams.bottomMargin = 40
        } else {
            mParams.topMargin = 30
            mParams.leftMargin = 30
            mParams.rightMargin = 30
        }
        holder.itemView.layoutParams = mParams
        holder.bind(items[position])
    }

    override fun onItemMove(from_position: Int, to_position: Int): Boolean {
//        Collections.swap((List<?>) mDataModel, from_position, to_position);
//        notifyItemMoved(from_position, to_position);
        return false
    }

    override fun onItemSwipe(position: Int) {
        mListener.eventRemoveItem(items[position])
        notifyDataSetChanged()
    }

    inner class ItemViewHolder(private val binding: ItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(dataModel: DataModel) {
            binding.title.text = dataModel.title
            binding.content.text = dataModel.content + ""
            setContainerColor(binding.linearItem, dataModel.color)
        }

        init {
            // 클릭이벤트
            itemView.setOnClickListener { v: View? ->
                val position = adapterPosition
                mListener.eventItemClick(items[position])
            }
        }
    }

    // 선택한 컬러로 카드뷰 띄우기
    @SuppressLint("ResourceType")
    private fun setContainerColor(v: View, color: String) {
        when (color) {
            "#" + Integer.toHexString(
                ContextCompat.getColor(
                    mContext,
                    R.color.schedule1
                ) and 0x00ffffff
            ) -> {
                v.setBackgroundResource(R.drawable.card_item_box1)
            }
            "#" + Integer.toHexString(
                ContextCompat.getColor(
                    mContext,
                    R.color.schedule2
                ) and 0x00ffffff
            ) -> {
                v.setBackgroundResource(R.drawable.card_item_box2)
            }
            "#" + Integer.toHexString(
                ContextCompat.getColor(
                    mContext,
                    R.color.schedule3
                ) and 0x00ffffff
            ) -> {
                v.setBackgroundResource(R.drawable.card_item_box3)
            }
            "#" + Integer.toHexString(
                ContextCompat.getColor(
                    mContext,
                    R.color.schedule4
                ) and 0x00ffffff
            ) -> {
                v.setBackgroundResource(R.drawable.card_item_box4)
            }
            "#" + Integer.toHexString(
                ContextCompat.getColor(
                    mContext,
                    R.color.schedule5
                ) and 0x00ffffff
            ) -> {
                v.setBackgroundResource(R.drawable.card_item_box5)
            }
            "#" + Integer.toHexString(
                ContextCompat.getColor(
                    mContext,
                    R.color.schedule6
                ) and 0x00ffffff
            ) -> {
                v.setBackgroundResource(R.drawable.card_item_box6)
            }
            "#" + Integer.toHexString(
                ContextCompat.getColor(
                    mContext,
                    R.color.schedule7
                ) and 0x00ffffff
            ) -> {
                v.setBackgroundResource(R.drawable.card_item_box7)
            }
            "#" + Integer.toHexString(
                ContextCompat.getColor(
                    mContext,
                    R.color.schedule8
                ) and 0x00ffffff
            ) -> {
                v.setBackgroundResource(R.drawable.card_item_box8)
            }
        }
    }

    interface Listener {
        fun eventRemoveItem(dataModel: DataModel)
        fun eventItemClick(dataModel: DataModel)
    }
}