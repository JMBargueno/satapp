package com.dmtroncoso.satapp.tickets;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.dmtroncoso.satapp.QRScannerActivity;
import com.dmtroncoso.satapp.R;
import com.dmtroncoso.satapp.anotaciones.AnotacionesActivity;
import com.dmtroncoso.satapp.common.MyApp;
import com.dmtroncoso.satapp.common.SharedPreferencesManager;
import com.dmtroncoso.satapp.data.TicketViewModel;
import com.dmtroncoso.satapp.retrofit.generator.ServiceGenerator;
import com.dmtroncoso.satapp.retrofit.service.SataService;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class TicketFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final int SCANNER_CODE = 5;
    private static final int DETALLE_CODE = 1;
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    TicketViewModel ticketViewModel;
    List<Ticket> listTickets;
    List<Ticket> listTicketsUser;
    MyTicketRecyclerViewAdapter adapter;
    Context context;
    RecyclerView recyclerView;
    SataService service;
    int ticketPosition;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TicketFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static TicketFragment newInstance(int columnCount) {
        TicketFragment fragment = new TicketFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

        ticketViewModel = new ViewModelProvider(getActivity()).get(TicketViewModel.class);

        setHasOptionsMenu(true);
        service = ServiceGenerator.createServiceTicket(SataService.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ticket_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            String rolUser = SharedPreferencesManager.getSomeStringValue("loggedRole");

            if(rolUser.equalsIgnoreCase("admin")) {
                adapter = new MyTicketRecyclerViewAdapter(listTickets, mListener);
                recyclerView.setAdapter(adapter);

                loadTicketData();
            }else{
                adapter = new MyTicketRecyclerViewAdapter(listTicketsUser, mListener);
                recyclerView.setAdapter(adapter);


                loadTicketDataByUser();
            }
            new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    ticketPosition = viewHolder.getAdapterPosition();
                    showAlertDialog();
                }
            }).attachToRecyclerView(recyclerView);
        }

        return view;
    }

    private void showAlertDialog(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setTitle("Eliminar");
        builder.setMessage("¿Deseas eliminar este ticket?");

        builder.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Call<ResponseBody> call = service.deleteTicket(adapter.getTicket(ticketPosition).getId());
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(MyApp.getContext(), "Ticket eliminado", Toast.LENGTH_SHORT).show();
                        }else{

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(MyApp.getContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                adapter.getTicket(ticketPosition).getId();
                dialog.dismiss();
            }
        });

        builder.create().show();

    }

    /*private void showAlertDialogQR(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setTitle("QR");
        builder.setMessage("¿Qué deseas hacer?");

        builder.setPositiveButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("CREAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivityForResult(new Intent(MyApp.getContext(), QRScannerActivity.class), SCANNER_CODE);
                dialog.dismiss();
            }
        });

        builder.create().show();

    }*/

    public void loadTicketData(){
        ticketViewModel.getTickets().observe(getActivity(), new Observer<List<Ticket>>() {
            @Override
            public void onChanged(List<Ticket> tickets) {
                listTickets = tickets;
                adapter.setData(tickets);
            }
        });
    }

    public void loadTicketDataByUser(){
        ticketViewModel.getTicketsUser().observe(getActivity(), new Observer<List<Ticket>>() {
            @Override
            public void onChanged(List<Ticket> tickets) {
                listTicketsUser = tickets;
                adapter.setData(listTicketsUser);
            }
        });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Ticket item);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.barCode:
                startActivityForResult(new Intent(MyApp.getContext(), QRScannerActivity.class), SCANNER_CODE);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String parts = data.getStringExtra("result");
        if (requestCode == SCANNER_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Call<ResponseBody> call = service.getInventariableById(parts);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            Intent intent = new Intent(context, NewTicketActivity.class);
                            intent.putExtra("idInventario", parts);
                            startActivity(intent);
                        }else{
                            Intent intent = new Intent(context, AnotacionesActivity.class);
                            intent.putExtra("intentIdTicket", parts);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(MyApp.getContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }
    }
}
