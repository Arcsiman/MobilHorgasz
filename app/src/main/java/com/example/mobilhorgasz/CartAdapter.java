package com.example.mobilhorgasz;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import com.bumptech.glide.Glide;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private ArrayList<Product> mProductsData;
    //private ArrayList<Product> mProductsDataAll;
    private Context mContext;
    //private int lastPosition = -1;
    CartAdapter(Context context, ArrayList<Product> itemsData){
        this.mProductsData = itemsData;
        //this.mProductsDataAll= itemsData;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.cart_item, parent, false));
    }

    @Override
    public void onBindViewHolder( CartAdapter.ViewHolder holder, int position) {
        Product currentItem= mProductsData.get(position);
        holder.bindTo(currentItem);
//        if (holder.getAdapterPosition() > lastPosition) {
//            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_row);
//            holder.itemView.startAnimation(animation);
//            lastPosition = holder.getAdapterPosition();
//        }
    }

    @Override
    public int getItemCount() {
        return mProductsData.size();
    }


//    @Override
//    public Filter getFilter() {
//        return shoppingFilter;
//    }
//
//    private Filter shoppingFilter = new Filter() {
//        @Override
//        protected FilterResults performFiltering(CharSequence constraint) {
//            ArrayList<ShoppingItem> filteredList = new ArrayList<>();
//            FilterResults results = new FilterResults();
//            if(constraint == null || constraint.length()==0){
//                results.count=mProductsDataAll.size();
//                results.values=mProductsDataAll;
//            }else{
//                String filterPattern = constraint.toString().toLowerCase().trim();
//
//                for(Product item: mProductsDataAll){
//                    if(item.getName().toLowerCase().contains(filterPattern)){
//                        filteredList.add(item);
//                    }
//                }
//                results.count = filteredList.size();
//                results.values=filteredList;
//            }
//
//            return results;
//        }
//
//        @Override
//        protected void publishResults(CharSequence constraint, FilterResults results) {
//            mProductsData = (ArrayList<Product>) results.values;
//            notifyDataSetChanged();
//        }
//    };
    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView mTitleText;
        private TextView mInfoText;
        private TextView mPriceText;
        private ImageView mItemImage;
        private RatingBar mRatingBar;
        public ViewHolder(View itemView){
            super(itemView);

            mTitleText=itemView.findViewById(R.id.itemTitle);
            mInfoText=itemView.findViewById(R.id.subTitle);
            mPriceText=itemView.findViewById(R.id.price);
            mItemImage=itemView.findViewById(R.id.itemImage);
            mRatingBar=itemView.findViewById(R.id.ratingBar);


//            itemView.findViewById(R.id.add_to_cart).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Log.d("Activity", "Add cart buttom clicked!");
//
//                }
//            });
        }

        public void bindTo(Product currentItem) {
            mTitleText.setText(currentItem.getName());
            mInfoText.setText(currentItem.getInfo());
            mPriceText.setText(currentItem.getPrice());
            mRatingBar.setRating(currentItem.getRatedInfo());

            Glide.with(mContext).load(currentItem.getImageResource()).into(mItemImage);
        }
    };
};

