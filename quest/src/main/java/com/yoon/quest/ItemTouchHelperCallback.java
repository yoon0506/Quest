package com.yoon.quest;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class ItemTouchHelperCallback extends ItemTouchHelper.Callback {
    private ItemTouchHelperListener listener;
    private Paint p = new Paint();
    private boolean mOrderChanged;


    // 개구리 애니메이션
    private ImageView mFrogImage;
    private AnimationDrawable mJumpFrogAnim = null;

    // 백그라운드 이미지 애니메이션
    private ImageView mBGImage;
    private AnimationDrawable mBGAnim = null;

    public ItemTouchHelperCallback(ItemTouchHelperListener listener) {
        this.listener = listener;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
//        int drag_flags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int drag_flags = ItemTouchHelper.ACTION_STATE_IDLE;
        int swipe_flags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.ACTION_STATE_IDLE;
//        int swipe_flags = ItemTouchHelper.LEFT;
//        return makeMovementFlags(drag_flags, swipe_flags);
        return makeMovementFlags(drag_flags, swipe_flags);
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return listener.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
//        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        mOrderChanged = true;
        if (direction == ItemTouchHelper.LEFT) {
            listener.onItemSwipe(viewHolder.getAdapterPosition());
        }
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (viewHolder != null) {
            final View foregroundView = getView(viewHolder);
            getDefaultUIUtil().onSelected(foregroundView);
        }else{

            if (actionState == ItemTouchHelper.ACTION_STATE_IDLE && mOrderChanged) {
                frogAnimOff();
                mOrderChanged = false;
            }

        }
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView,
                            RecyclerView.ViewHolder viewHolder, float dX, float dY,
                            int actionState, boolean isCurrentlyActive) {
        final View foregroundView = getView(viewHolder);
        getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY,
                actionState, isCurrentlyActive);

        if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){
            mFrogImage = ((ListAdapter.ItemViewHolder) viewHolder).itemView.findViewById(R.id.frog);
            mBGImage = ((ListAdapter.ItemViewHolder) viewHolder).itemView.findViewById(R.id.background_image);
            mJumpFrogAnim = (AnimationDrawable) mFrogImage.getBackground();
            mBGAnim = (AnimationDrawable) mBGImage.getBackground();
            frogAnimOn();
        }
    }

    @Override
    public void onChildDrawOver(Canvas c, RecyclerView recyclerView,
                                RecyclerView.ViewHolder viewHolder, float dX, float dY,
                                int actionState, boolean isCurrentlyActive) {
        final View foregroundView = getView(viewHolder);
        getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY,
                actionState, isCurrentlyActive);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        final View foregroundView = getView(viewHolder);
        getDefaultUIUtil().clearView(foregroundView);
    }

    private View getView(RecyclerView.ViewHolder viewHolder) {
        if (((ListAdapter.ItemViewHolder) viewHolder) == null
                || ((ListAdapter.ItemViewHolder) viewHolder).itemView == null) {
            return null;
        }
        View mmGridView = ((ListAdapter.ItemViewHolder) viewHolder).itemView.findViewById(R.id.linearItem);
        if (mmGridView != null) {
            return mmGridView;
        } else {
            return null;
        }
    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }

    public interface ItemTouchHelperListener {
        boolean onItemMove(int from_position, int to_position);

        void onItemSwipe(int position);
    }

    private void frogAnimOn(){
        mFrogImage.post(new Runnable() {
            @Override
            public void run() {
                mJumpFrogAnim.start();
            }
        });

        mBGImage.post(new Runnable() {
            @Override
            public void run() {
                mBGAnim.start();
            }
        });
    }

    private void frogAnimOff(){
        mFrogImage.post(new Runnable() {
            @Override
            public void run() {
                mJumpFrogAnim.stop();
            }
        });
        mBGImage.post(new Runnable() {
            @Override
            public void run() {
                mBGAnim.stop();
            }
        });
    }
}