package com.mobdeve.loquintek.sarabiar.mobdeve_mp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReceiptBarAdapter extends RecyclerView.Adapter<ReceiptBarHolder>{

    private ArrayList<ReceiptBarModel> receiptList;

    public ReceiptBarAdapter() {
        receiptList = new ArrayList<ReceiptBarModel>();

    }

    @Override
    public ReceiptBarHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.receipt_bar_layout, parent, false);

        ReceiptBarHolder holder = new ReceiptBarHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ReceiptBarHolder holder, final int position) {
        holder.setReceiptBarDateTv(receiptList.get(position).getReceiptBarDate());
        holder.setReceiptBarTotalTv(receiptList.get(position).getReceiptBarTotal());
        holder.setReceiptBarItemsTv(receiptList.get(position).getReceiptBarItems());
        holder.setReceiptBarStoreTv(receiptList.get(position).getReceiptBarStore());
    }

    @Override
    public int getItemCount() {
        return receiptList.size();
    }

    public void addItem(String storeName, String date, String items, double total){
        receiptList.add(new ReceiptBarModel(storeName, date, items, total));
        notifyItemInserted(receiptList.size()-1);
    }


}
