package com.dmtroncoso.satapp.anotaciones;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dmtroncoso.satapp.R;
import com.dmtroncoso.satapp.anotaciones.anotacionesFragment.OnListFragmentInteractionListener;
import com.dmtroncoso.satapp.retrofit.model.anotaciones.Notas;

import java.util.List;

public class MyanotacionesRecyclerViewAdapter extends RecyclerView.Adapter<MyanotacionesRecyclerViewAdapter.ViewHolder> {

    private final List<Notas> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyanotacionesRecyclerViewAdapter(List<Notas> items, OnListFragmentInteractionListener listener) {
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
        holder.mItem = mValues.get(position);

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

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;

        public Notas mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;

        }

    }
}
