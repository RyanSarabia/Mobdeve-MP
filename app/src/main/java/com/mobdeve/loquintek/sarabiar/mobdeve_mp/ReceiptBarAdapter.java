package com.mobdeve.loquintek.sarabiar.mobdeve_mp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ReceiptBarAdapter extends RecyclerView.Adapter<ReceiptBarHolder>{

    private ArrayList<ReceiptBarModel> receiptList;
//    private ArrayList<ReceiptBarModel> receiptListFull;
    private Context context;
//    private Database db;

    public ReceiptBarAdapter(Context context) {
        receiptList = new ArrayList<ReceiptBarModel>();
//        receiptListFull = new ArrayList<>(receiptList);
//        db = new Database(context);
        this.context = context;

    }

    @Override
    public ReceiptBarHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.receipt_bar_layout, parent, false);

        ReceiptBarHolder holder = new ReceiptBarHolder(view, context);
        return holder;
    }

    @Override
    public void onBindViewHolder(ReceiptBarHolder holder, final int position) {
        holder.setReceiptBarDateTv(receiptList.get(position).getReceiptBarDate());
        holder.setReceiptBarTotalTv(receiptList.get(position).getReceiptBarTotal());
        holder.setReceiptBarItemsTv(receiptList.get(position).getReceiptBarItems());
        holder.setReceiptBarStoreTv(receiptList.get(position).getReceiptBarStore());
        holder.setReceiptBarSerialNo(receiptList.get(position).getReceiptBarSerial());
        holder.setReceiptBarTagChp(receiptList.get(position).getReceiptBarTag());
    }

    @Override
    public int getItemCount() {
        return receiptList.size();
    }

    public void addItem(String storeName, String date, String items, double total, String serial, String tag){
        receiptList.add(new ReceiptBarModel(storeName, date, items, total, serial, tag));
        notifyItemInserted(receiptList.size()-1);
    }

    public void removeItem(int position) {
        receiptList.remove(position);
    }

    public void setTag(int position, String tag) {
        receiptList.get(position).setReceiptBarTag(tag);
        notifyItemChanged(position);
    }

    public void clearList() {
        receiptList.clear();
        notifyDataSetChanged();
    }


//    @Override
//    public Filter getFilter() {
//        return receiptFilter;
//    }
//
//    private Filter receiptFilter = new Filter() {
//        @Override
//        protected FilterResults performFiltering(CharSequence constraint) {
//            List<ReceiptBarModel> filteredList = new ArrayList<>();
//
//            String searchInput = constraint.toString().toLowerCase().trim();
//
//            filteredList = db.searchFilteredReceipts(searchInput, isMerchant, date, year, isAscending);
//
//            FilterResults results = new FilterResults();
//            results.values = filteredList;
//
//            return results;
//        }
//
//        @Override
//        protected void publishResults(CharSequence constraint, FilterResults results) {
//            receiptList.clear();
//            receiptList.addAll((List)results.values);
//            notifyDataSetChanged();
//        }
//    };


}
