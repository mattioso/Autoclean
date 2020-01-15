package uk.co.appoly.autoclean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ServicesDisplayAdapter extends RecyclerView.Adapter<ServicesDisplayAdapter.ViewHolder> {

    private ArrayList<String> toDisplayTitle;
    private ArrayList<Integer> toDisplayPrice;

    private Context mContext;
    private LayoutInflater mInflater;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView titleTextView;
        public TextView priceTextView;

        public ViewHolder(View itemView, Context mContext) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.serviceTitleTextView);
            priceTextView = itemView.findViewById(R.id.servicePriceTextView);
        }
    }

    public ServicesDisplayAdapter(Context context, ArrayList<String> servicesTitles, ArrayList<Integer> price) {
        this.toDisplayTitle = servicesTitles;
        this.toDisplayPrice = price;

        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public ServicesDisplayAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.display_services_recyclerview_item, parent, false);
        return new ViewHolder(view, mContext);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.titleTextView.setText(toDisplayTitle.get(position));
        holder.priceTextView.setText("£" + toDisplayPrice.get(position));

    }

    @Override
    public int getItemCount() {
        return toDisplayTitle.size();
    }

}
