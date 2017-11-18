package com.codelab.sunshine.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codelab.sunshine.R;
import com.codelab.sunshine.model.ForecastModel;

import java.util.ArrayList;

/**
 * Created by Ashiq on 9/26/2016.
 */
public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<ForecastModel> arrayList;
    private ItemClickListener itemClickListener;

    public ForecastAdapter(Context context, ArrayList<ForecastModel> arrayList) {
        this.mContext = context;
        this.arrayList = arrayList;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvTitle, tvDescription, tvMaxTemp, tvMinTemp;
        private ImageView ivIndicatorIcon;


        public ViewHolder(View itemView) {
            super(itemView);

            ivIndicatorIcon = (ImageView) itemView.findViewById(R.id.iv_indicator_icon);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvDescription = (TextView) itemView.findViewById(R.id.tv_description);
            tvMaxTemp = (TextView) itemView.findViewById(R.id.tv_max_temp);
            tvMinTemp = (TextView) itemView.findViewById(R.id.tv_min_temp);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if(itemClickListener != null) {
                itemClickListener.onItemClick(view, getAdapterPosition());
            }
        }
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_weather, parent, false);
            return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.tvTitle.setText(arrayList.get(position).getDay());
        holder.tvDescription.setText(arrayList.get(position).getCondition() +", "+ arrayList.get(position).getDescription());
        holder.tvMaxTemp.setText(arrayList.get(position).getTempMax()+"°C");
        holder.tvMinTemp.setText(arrayList.get(position).getTempMin()+"°C");

        // TODO: icon management: https://openweathermap.org/weather-conditions
        /*String imageUrl = dataList.get(position).getImage();
        if (imageUrl != null) {
            Glide.with(mActivity)
                    .load(imageUrl)
                    .error(R.drawable.ic_user)
                    .crossFade()
                    .transform(new CircleTransform(mActivity.getApplicationContext()))
                    .into(holder.userImage);
        } else {
            holder.userImage.setImageResource(R.drawable.ic_user);
        }*/
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public interface ItemClickListener {
        public void onItemClick(View v, int position);
    }

}
