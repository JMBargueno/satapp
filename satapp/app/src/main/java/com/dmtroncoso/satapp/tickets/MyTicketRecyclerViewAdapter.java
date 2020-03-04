package com.dmtroncoso.satapp.tickets;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dmtroncoso.satapp.R;
import com.dmtroncoso.satapp.anotaciones.AnotacionesActivity;
import com.dmtroncoso.satapp.common.MyApp;
import com.dmtroncoso.satapp.common.SharedPreferencesManager;
import com.dmtroncoso.satapp.retrofit.generator.ServiceGenerator;
import com.dmtroncoso.satapp.retrofit.service.SataService;
import com.dmtroncoso.satapp.tickets.TicketFragment.OnListFragmentInteractionListener;

import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyTicketRecyclerViewAdapter extends RecyclerView.Adapter<MyTicketRecyclerViewAdapter.ViewHolder> {

    private List<Ticket> mValues;
    private final OnListFragmentInteractionListener mListener;
    Context ctx;
    SataService service;

    public MyTicketRecyclerViewAdapter(List<Ticket> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_ticket, parent, false);

        ctx = parent.getContext();
        service = ServiceGenerator.createServiceTicket(SataService.class);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if(mValues != null){
            holder.mItem = mValues.get(position);

            holder.txtTitle.setText(holder.mItem.getTitulo());
            holder.txtDescription.setText(holder.mItem.getDescripcion());

            /*if(holder.mItem.getFotos().size() > 0 || holder.mItem.getFotos() != null) {
                Call<ResponseBody> call = service.getImageOfTicket(holder.mItem.getFotos().get(0), 0);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            //Bitmap bitmap = MediaStore.Images.Media.getBitmap(MyApp.getContext().getContentResolver(), response.body());
                            Log.v("Imagen", response.body() + " body");
                            Glide
                                    .with(ctx)
                                    .load(response.body())
                                    .apply(RequestOptions.bitmapTransform(new CropCircleTransformation()))
                                    .into(holder.imageViewTicket);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }else{

            }*/
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    SharedPreferencesManager.setSomeStringValue("idTicket", holder.mItem.getId());
                    ctx.startActivity(new Intent(MyApp.getContext(), AnotacionesActivity.class));
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    public void setData(List<Ticket> resultList){
        this.mValues = resultList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mValues != null){
            return mValues.size();
        }else{
            return 0;
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView txtTitle;
        public final TextView txtDescription;
        public final ImageView imageViewTicket;

        public Ticket mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;

            txtTitle = view.findViewById(R.id.textViewTitleTicket);
            txtDescription = view.findViewById(R.id.textViewDescriptionTicket);
            imageViewTicket = view.findViewById(R.id.imageViewImgTicket);

        }

    }
}
