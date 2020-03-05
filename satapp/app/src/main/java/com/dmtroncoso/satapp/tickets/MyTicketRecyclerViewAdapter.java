package com.dmtroncoso.satapp.tickets;

import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
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
import com.karumi.dexter.Dexter;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
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

            if(holder.mItem.getFotos().size() > 0 && holder.mItem.getFotos() != null) {
                Call<ResponseBody> call = service.getImageOfUser(holder.mItem.getCreadoPor().getId());
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
                                        .into(holder.imageViewTicket);
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

            //Share option in an card
            holder.imageShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(holder.mItem.getFotos().size() > 0 && holder.mItem.getFotos() != null) {
                        new DownloadTask(holder.mItem.getTitulo(), holder.mItem.getDescripcion(), holder.mItem.getId()).execute("https://satapp-api.herokuapp.com"+ holder.mItem.getFotos().get(0));
                    }else{
                        Toast.makeText(ctx, "No contiene fotos este ticket", Toast.LENGTH_SHORT).show();
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, "Title : " + holder.mItem.getTitulo() + " / " +"Descripción : "+ holder.mItem.getDescripcion());
                        sendIntent.setType("text/plain");

                        Intent shareIntent = Intent.createChooser(sendIntent, null);
                        ctx.startActivity(shareIntent);
                    }

                }
            });
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    SharedPreferencesManager.setSomeStringValue("idTicket", holder.mItem.getId());
                    Intent intentAnotaciones = new Intent(MyApp.getContext(), AnotacionesActivity.class);
                    intentAnotaciones.putExtra("intentIdTicket", holder.mItem.getId());
                    ctx.startActivity(intentAnotaciones);
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

    public Ticket getTicket(int position){
        return mValues.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView txtTitle;
        public final TextView txtDescription;
        public final ImageView imageViewTicket;
        public final ImageView imageShare;

        public Ticket mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;

            txtTitle = view.findViewById(R.id.textViewTitleTicket);
            txtDescription = view.findViewById(R.id.textViewDescriptionTicket);
            imageViewTicket = view.findViewById(R.id.imageViewImgTicket);
            imageShare = view.findViewById(R.id.imageViewShare);
        }

    }

    private class DownloadTask extends AsyncTask<String, Void, File>{

        String title;
        String description;
        String idTicket;

        public DownloadTask(String title, String description, String idTicket) {
            this.title = title;
            this.description = description;
            this.idTicket = idTicket;
        }

        @Override
        protected File doInBackground(String... strings) {
            String url = strings[0];

            File result = null;
            try {
                result = downloadFile(url, ctx);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                return result;
            }
        }

        @Override
        protected void onPostExecute(File file) {

            // Obtenemos la URI a exponer a partir del fichero
            Uri myPhotoFileUri = FileProvider.getUriForFile(ctx, ctx.getApplicationContext().getPackageName() + ".provider", file);

            // Compartimos
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            shareIntent.putExtra(Intent.EXTRA_TEXT,"Título : "+ title + " / "+ "Descripción : "+ description);
            shareIntent.putExtra(Intent.EXTRA_STREAM, myPhotoFileUri);
            // Esta parte sirve para obtener, a partir de la extensión del fichero, el tipo mime
            String type = null;
            String extension = MimeTypeMap.getFileExtensionFromUrl(file.getAbsolutePath());
            if (extension != null) {
                type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
            }
            shareIntent.setType(type);
            ctx.startActivity(Intent.createChooser(shareIntent, "Enviar"));
        }
    }

    /**
     * Método que es capaz de descargar el fichero ubicado en la URL, y lo guarda en un fichero temporal en la caché
     * @param url
     * @param ctx
     * @return
     * @throws IOException
     */
    public File downloadFile(String url, Context ctx) throws IOException {
        final String tokenUser = SharedPreferencesManager.getSomeStringValue("token");
        final OkHttpClient client = new OkHttpClient();

        // Montamos la petición
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", "Bearer " + tokenUser)
                .build();

        // Ejecutamos la petición
        try (okhttp3.Response response = client.newCall(request).execute()) {
            // Si hay error, excepción
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            // En otro caso...

            // Obtenemos, a partir de las cabeceras, el tipo mime, y de él, la extensión
            Headers headers = response.headers();
            String contentType = headers.get("Content-Type");
            String suffix = "." + MimeTypeMap.getSingleton().getExtensionFromMimeType(contentType);

            // "Armamos" lo necesario para leer el fichero vía flujos
            InputStream is = response.body().byteStream();
            // Se guarda en un fichero temporal, dentro de la caché externa
            File file = File.createTempFile("img", suffix, ctx.getExternalCacheDir());
            BufferedInputStream input = new BufferedInputStream(is);
            BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(file));

            // Bucle clásico de lectura de un fichero en grupos de 4KB
            byte[] data = new byte[4*1024];

            long total = 0;
            int count;
            while ((count = input.read(data)) != -1) {
                total += count;
                output.write(data, 0, count);
            }

            // Cierre de flujos
            output.flush();
            output.close();
            input.close();

            return file;

        }
    }
}
