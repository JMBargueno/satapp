package com.dmtroncoso.satapp.anotaciones;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dmtroncoso.satapp.R;
import com.dmtroncoso.satapp.anotaciones.anotacionesFragment.OnListFragmentInteractionListener;
import com.dmtroncoso.satapp.retrofit.model.anotaciones.Notas;
import com.dmtroncoso.satapp.tickets.Anotaciones;

import java.util.List;

public class MyanotacionesRecyclerViewAdapter extends RecyclerView.Adapter<MyanotacionesRecyclerViewAdapter.ViewHolder> {

    private List<Anotaciones> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyanotacionesRecyclerViewAdapter(List<Anotaciones> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_anotaciones, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        if(mValues != null){
            holder.mItem = mValues.get(position);

            holder.textBody.setText(holder.mItem.getCuerpo());
            holder.textCreatedAt.setText(holder.mItem.getCreatedAt());
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
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

        public Anotaciones mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;

            textBody = view.findViewById(R.id.textViewBodyAn);
            textCreatedAt = view.findViewById(R.id.textViewCreatedAtAn);
        }

    }
}
