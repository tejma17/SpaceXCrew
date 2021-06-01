package com.tejma.spacexcrew.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.snackbar.Snackbar;
import com.tejma.spacexcrew.R;
import com.tejma.spacexcrew.pojo.Crew;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrewRVAdapter extends RecyclerView.Adapter<CrewRVAdapter.ViewHolder>{

    private List<Crew> crewList;
    private Context context;
    private final OnClickListener onClickListener;
    private RecyclerView recyclerView;


    public CrewRVAdapter(@NonNull Context context, List<Crew> crewList, OnClickListener onClickListener) {
        this.context = context;
        this.crewList = crewList;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.crew_card_rv_item, parent, false);
        return new ViewHolder(view, onClickListener);
    }

    public void updateAdapter(List<Crew> mDataList) {
        this.crewList = mDataList;
        notifyDataSetChanged();
    }

    public void removeAt(int position) {
        crewList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, crewList.size());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.name.setText(crewList.get(position).getName());
        holder.agency.setText(crewList.get(position).getAgency());
        holder.status.setText(crewList.get(position).getStatus());
        if(crewList.get(position).getStatus().equals("active"))
            holder.status.setTextColor(context.getResources().getColor(R.color.green));
        else
            holder.status.setTextColor(context.getResources().getColor(R.color.yellow));

        if(crewList.get(position).getImage()!=null)
            Glide.with(context).load(crewList.get(position).getImage())
                    .placeholder(R.drawable.ic_user_profile)
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .into(holder.profileImg);
        else
            holder.profileImg.setBackgroundResource(R.drawable.ic_user_profile);

    }



    @Override
    public int getItemCount() {
        return crewList == null ? 0 : crewList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView name, agency, status;
        ImageView profileImg;
        OnClickListener onClickListener;

        public ViewHolder(View itemView, OnClickListener onClickListener){
            super(itemView);
            this.name = itemView.findViewById(R.id.name);
            this.agency = itemView.findViewById(R.id.agency);
            this.status = itemView.findViewById(R.id.status);
            this.profileImg = itemView.findViewById(R.id.profile_img);
            this.onClickListener = onClickListener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onClickListener.onClick(getLayoutPosition(), v);
        }

        @Override
        public boolean onLongClick(View v) {
            onClickListener.onLongClick(getLayoutPosition(), v);
            return true;
        }
    }

    public interface OnClickListener{
        void onClick(int position, View v);
        void onLongClick(int position, View v);
    }
}
