package uk.co.appoly.autoclean;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ServicesRecyclerViewAdapter extends RecyclerView.Adapter<ServicesRecyclerViewAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    public List<ServiceModel> mServiceTypes;
    public List<Integer> selectedServiceTypes;
    private Context mContext;

    ItemClickListener clickListener;

    public ServicesRecyclerViewAdapter(Context context, List<ServiceModel> serviceTypes, List<Integer> selectedServiceTypes) {
        this.mInflater = LayoutInflater.from(context);
        this.mServiceTypes = serviceTypes;
        this.mContext = context;
        this.selectedServiceTypes = selectedServiceTypes;
    }

    @NonNull
    @Override
    public ServicesRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.services_recyclerview_item, parent, false);
        return new ViewHolder(view, mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull ServicesRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.myTitle.setText(mServiceTypes.get(position).title);
        holder.myTime.setText(mServiceTypes.get(position).time);
        holder.myPrice.setText("£" + String.valueOf(mServiceTypes.get(position).price));
        holder.myImageView.setImageResource(mServiceTypes.get(position).imageResource);

        int thisID = mServiceTypes.get(position).id;

        for (Integer selected: selectedServiceTypes) {
            if (selected == thisID) {
                holder.isSelected = true;
                holder.itemView.setBackgroundColor(Color.GREEN);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mServiceTypes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        boolean isSelected = false;

        TextView myTitle, myTime, myPrice;
        ImageView myImageView;

        public ViewHolder(View itemView, Context mContext) {
            super(itemView);
            itemView.setOnClickListener(this);

            myImageView = itemView.findViewById(R.id.imageView);
            myTitle = itemView.findViewById(R.id.title);
            myTime = itemView.findViewById(R.id.time);
            myPrice = itemView.findViewById(R.id.price);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) clickListener.onItemClick(v, getAdapterPosition());

            if (isSelected) {
                v.setBackgroundColor(Color.WHITE);
            } else {
                v.setBackgroundColor(Color.GREEN);
            }
            isSelected = !isSelected;
        }
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setClickListener(ItemClickListener listener) {
        this.clickListener = listener;
    }
}
