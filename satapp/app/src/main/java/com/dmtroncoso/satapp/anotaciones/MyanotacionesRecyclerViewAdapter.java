package com.dmtroncoso.satapp.anotaciones;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dmtroncoso.satapp.R;
import com.dmtroncoso.satapp.anotaciones.anotacionesFragment.OnListFragmentInteractionListener;
import com.dmtroncoso.satapp.common.MyApp;
import com.dmtroncoso.satapp.data.anotaciones.AnotacionViewModel;
import com.dmtroncoso.satapp.retrofit.generator.ServiceGenerator;
import com.dmtroncoso.satapp.retrofit.model.anotaciones.Notas;
import com.dmtroncoso.satapp.retrofit.service.SataService;
import com.dmtroncoso.satapp.tickets.Anotaciones;

import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyanotacionesRecyclerViewAdapter extends RecyclerView.Adapter<MyanotacionesRecyclerViewAdapter.ViewHolder> {

    private List<Anotaciones> mValues;
    private final OnListFragmentInteractionListener mListener;
    Context ctx;
    SataService service;

    public MyanotacionesRecyclerViewAdapter(List<Anotaciones> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_anotaciones, parent, false);

        ctx = parent.getContext();
        service = ServiceGenerator.createServiceTicket(SataService.class);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        if(mValues != null){
            holder.mItem = mValues.get(position);

            holder.textBody.setText(holder.mItem.getCuerpo());
            holder.textCreatedAt.setText(holder.mItem.getCreatedAt());

            if(holder.mItem.getIdUsuario().getId() != null) {
                Call<ResponseBody> call = service.getImageOfUser(holder.mItem.getIdUsuario().getId());
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                Bitmap bmp = BitmapFactory.decodeStream(response.body().byteStream());

                                Glide
                                        .with(ctx)
                                        .load(bmp)
                                        .apply(RequestOptions.bitmapTransform(new CropCircleTransformation()))
                                        .into(holder.imageViewAnot);
                            } else {

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(ctx, "Error de conexión", Toast.LENGTH_SHORT).show();
                    }
                });
            }else{

            }
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {

                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    public void setData(List<Anotaciones> resultList){
        this.mValues = resultList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {

        if (mValues != null) {
            return mValues.size();
        }else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView textBody;
        public final TextView textCreatedAt;
        public final ImageView imageViewAnot;

        public Anotaciones mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;

            textBody = view.findViewById(R.id.textViewBodyAn);
            textCreatedAt = view.findViewById(R.id.textViewCreatedAtAn);
            imageViewAnot = view.findViewById(R.id.imageViewAnotaciones);
        }

    }
}
