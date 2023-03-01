package com.example.ltandroid.listcat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ltandroid.R;

import java.util.ArrayList;


interface OnUpdateListener {
    void invoke(Cat cat);
}

public class CatAdapter extends RecyclerView.Adapter<CatAdapter.CatViewHolder> {

    private ArrayList<Cat> mList;
    private ArrayList<Cat> mListAll;
    private Context mContext;
    private OnUpdateListener onUpdateListener;

    public CatAdapter(ArrayList mList, Context mContext, OnUpdateListener onUpdateListener) {
        this.mListAll = new ArrayList<>();
        this.mListAll.addAll(mList);
        this.mList = mList;
        this.mContext = mContext;
        this.onUpdateListener = onUpdateListener;
    }

    void insertList(Cat cat) {
        mList.add(cat);
        mListAll.add(cat);
        notifyDataSetChanged();
    }

    void updateList(Cat cat) {
        for (int i = 0; i < mList.size(); i++) {
            if (mList.get(i).getId() == cat.getId()) {
                mList.set(i, cat);
                mListAll.set(i,cat);
                notifyDataSetChanged();
                break;
            }
        }
    }

    void searchList(String query){
        ArrayList listSearch = new ArrayList();
        for(int i=0;i<mListAll.size();i++){
            if(mListAll.get(i).getName().contains(query)){
                listSearch.add(mListAll.get(i));
            }
        }
        mList.clear();
        mList.addAll(listSearch);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_cat, parent, false);
        CatViewHolder viewHolder = new CatViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CatViewHolder holder, int position) {
        holder.img.setImageResource(mList.get(position).getImg());
        holder.name.setText(mList.get(position).getName());
        holder.desc.setText(mList.get(position).getDescription());
        holder.price.setText(mList.get(position).getPrice());
        holder.itemView.setOnClickListener(view -> {
            onUpdateListener.invoke(mList.get(position));
        });
        holder.remove.setOnClickListener(view -> {
            mList.remove(holder.getAdapterPosition());
            notifyItemRemoved(holder.getAdapterPosition());
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class CatViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView price;
        private TextView desc;
        private ImageView img;
        private Button remove;

        public CatViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_catTitle);
            price = itemView.findViewById(R.id.tv_catPrice);
            desc = itemView.findViewById(R.id.tv_catDesc);
            img = itemView.findViewById(R.id.iv_cat);
            remove = itemView.findViewById(R.id.btn_removeCat);
        }

    }
}
