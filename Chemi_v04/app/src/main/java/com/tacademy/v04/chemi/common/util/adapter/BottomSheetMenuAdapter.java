package com.tacademy.v04.chemi.common.util.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tacademy.v04.chemi.R;
import com.tacademy.v04.chemi.model.BottomSheetMenu;

import java.util.ArrayList;

/**
 * Created by yoon on 2016. 12. 1..
 */

public class BottomSheetMenuAdapter extends RecyclerView.Adapter<BottomSheetMenuAdapter.MenuItemHolder> {

    private ArrayList<BottomSheetMenu> mMenus;
    private OnItemClickListener mItemClickListener;

    public BottomSheetMenuAdapter(ArrayList<BottomSheetMenu> menus) {
        mMenus = menus;
    }

    @Override
    public BottomSheetMenuAdapter.MenuItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.bottom_sheet_menu_list_item, parent, false);
        return new MenuItemHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(BottomSheetMenuAdapter.MenuItemHolder holder, int position) {
        holder.bindMenu(mMenus.get(position));
    }

    @Override
    public int getItemCount() {
        return mMenus.size();
    }

    public OnItemClickListener getOnItemClickListener() {
        return mItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(MenuItemHolder item, int position);
    }

    public class MenuItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private BottomSheetMenuAdapter mAdapter;
        ImageView mImageView;
        TextView mTextView;

        public MenuItemHolder(View itemView, BottomSheetMenuAdapter adapter) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.mAdapter = adapter;

            mImageView = (ImageView) itemView.findViewById(R.id.bottom_sheet_menu_item_image_view);
            mTextView = (TextView) itemView.findViewById(R.id.bottom_sheet_menu_item_title_text_view);
        }

        public void bindMenu(BottomSheetMenu bottomSheetMenu) {
            mImageView.setImageResource(bottomSheetMenu.getImageId());
            mTextView.setText(bottomSheetMenu.getTitleId());
        }

        @Override
        public void onClick(View view) {
            final OnItemClickListener listener = mAdapter.getOnItemClickListener();
            if (listener != null) {
                listener.onItemClick(this, getAdapterPosition());
            }
            Log.i("MenuItemHolder", "getAdapterPosition() : " + getAdapterPosition());
        }
    }
}
