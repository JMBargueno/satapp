package com.dmtroncoso.satapp.retrofit.generator;

import android.text.TextUtils;

import com.dmtroncoso.satapp.common.SharedPreferencesManager;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Credentials;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    public static final String BASE_URL = "https://satapp-api.herokuapp.com";

    private static HttpLoggingInterceptor logging = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS);

    private static OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();

    private static OkHttpClient httpClient = new OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(httpClient)
                    .addConverterFactory(GsonConverterFactory.create());

    public static Retrofit retrofit = null;

    public static <S> S createService(Class<S> serviceClass) {
        return createService(serviceClass, null, null);
    }

    public static <S> S createService(Class<S> serviceClass, String username, String password){
        if(!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)){
            String authToken = Credentials.basic(username, password);
            return createService(serviceClass, authToken);
        }

        return createService(serviceClass, null);
    }

    public static <S> S createService(Class<S> serviceClass, final String authToken){
        if(!TextUtils.isEmpty(authToken)){
            AuthenticationInterceptor interceptor = new AuthenticationInterceptor(authToken);

            if(!httpClient.interceptors().contains(interceptor)){
                httpClientBuilder.addInterceptor(new Interceptor() {
                    @NotNull
                    @Override
                    public Response intercept(@NotNull Chain chain) throws IOException {
                        Request original = chain.request();
                        HttpUrl originalHttpUrl = original.url();

                        HttpUrl url = originalHttpUrl.newBuilder()
                                .build();

                        Request.Builder requestBuilder = original.newBuilder()
                                .url(url);

                        Request request = requestBuilder.build();
                        return chain.proceed(request);
                    }
                });

                httpClientBuilder.addInterceptor(interceptor);
                httpClientBuilder.addInterceptor(logging);

                builder.client(httpClientBuilder.build());
                retrofit = builder.build();
            }
        }

        return retrofit.create(serviceClass);
    }

    public static <S> S createServiceRegisterTest(Class<S> serviceClass){

                httpClientBuilder.addInterceptor(new Interceptor() {
                    @NotNull
                    @Override
                    public Response intercept(@NotNull Chain chain) throws IOException {
                        Request original = chain.request();
                        HttpUrl originalHttpUrl = original.url();

                        HttpUrl url = originalHttpUrl.newBuilder()
                                .addQueryParameter("access_token", "elpabloesunchaquetitasyeltroncosounfatiguitas")
                                .build();

                        Request.Builder requestBuilder = original.newBuilder()
                                .url(url);

                        Request request = requestBuilder.build();
                        return chain.proceed(request);
                    }
                });
                httpClientBuilder.addInterceptor(logging);

                builder.client(httpClientBuilder.build());
                retrofit = builder.build();

        return retrofit.create(serviceClass);
    }

    public static <S> S createServiceRegister(Class<S> serviceClass){
                httpClientBuilder.addInterceptor(new Interceptor() {
                    @NotNull
                    @Override
                    public Response intercept(@NotNull Chain chain) throws IOException {
                        Request original = chain.request();
                        HttpUrl originalHttpUrl = original.url();

                        HttpUrl url = originalHttpUrl.newBuilder()
                                .addQueryParameter("access_token", "elpabloesunchaquetitasyeltroncosounfatiguitas")
                                .build();

                        Request.Builder requestBuilder = original.newBuilder()
                                .url(url);

                        Request request = requestBuilder.build();
                        return chain.proceed(request);
                    }
                });

                httpClientBuilder.addInterceptor(logging);

                builder.client(httpClientBuilder.build());
                retrofit = builder.build();

        return retrofit.create(serviceClass);
    }
}
