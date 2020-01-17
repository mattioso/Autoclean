package uk.co.appoly.autoclean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class servicesConfirmRecyclerViewAdapter extends RecyclerView.Adapter<servicesConfirmRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ServiceModel> mServiceTypes;
    private LayoutInflater mInflater;

    public servicesConfirmRecyclerViewAdapter(Context context, ArrayList<ServiceModel> serviceModels) {

        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mServiceTypes = serviceModels;

    }

    @NonNull
    @Override
    public servicesConfirmRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.services_recyclerview_item, parent, false);
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull servicesConfirmRecyclerViewAdapter.ViewHolder holder, int position) {

        holder.myTitle.setText(mServiceTypes.get(position).title);
        holder.myTime.setText(mServiceTypes.get(position).time);
        holder.myPrice.setText("£" + String.valueOf(mServiceTypes.get(position).price));
        holder.myImageView.setImageResource(mServiceTypes.get(position).imageResource);



    }

    @Override
    public int getItemCount() {
        return mServiceTypes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView myTitle, myTime, myPrice;
        ImageView myImageView;

        public ViewHolder(View view, Context context) {

            super(view);

            myImageView = itemView.findViewById(R.id.imageView);
            myTitle = itemView.findViewById(R.id.title);
            myTime = itemView.findViewById(R.id.time);
            myPrice = itemView.findViewById(R.id.price);

        }

        @Override
        public void onClick(View v) {

        }
    }

}
