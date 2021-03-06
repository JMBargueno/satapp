package com.dmtroncoso.satapp.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.dmtroncoso.satapp.data.UserRepository;
import com.dmtroncoso.satapp.inventariables.ModalBottomInventariableFragment;
import com.dmtroncoso.satapp.retrofit.model.User;
import com.dmtroncoso.satapp.retrofit.model.UserResponse;

import java.util.List;

import okhttp3.ResponseBody;

public class UserViewModel extends AndroidViewModel {

    private UserRepository userRepository;
    private MutableLiveData<List<User>> listUserNoVal;
    private MutableLiveData<List<User>> listAllusers;
    private MutableLiveData<String> idUserSeleccionado;
    private MutableLiveData<ResponseBody> img;

    public UserViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository();
        listUserNoVal = new MutableLiveData<>();
        listAllusers = new MutableLiveData<>();
        this.idUserSeleccionado = new MutableLiveData<>();
        this.idUserSeleccionado.setValue(null);
    }

    public MutableLiveData<List<User>> getListUserNoVal(){
        listUserNoVal = userRepository.getUsersNoVal();
        return listUserNoVal;
    }

    public  MutableLiveData<ResponseBody> getImgUsr(String idUser){
        img = userRepository.getImagenes(idUser);
        return img;

    }

    public  MutableLiveData<List<User>> getListAllusers(){
        listAllusers = userRepository.getAllUsers();
        return listAllusers;
    }

    public void updateAvatar(){

    }

    public MutableLiveData<User> getLoggedUser(){
        return userRepository.getUserLogged();
    }

    public MutableLiveData<User> updateUser(String id , String name){
        return userRepository.changeUser(id,name);
    }


    public void setIdUserSeleccionado(String idUserSeleccionado) {
        this.idUserSeleccionado.setValue(idUserSeleccionado);
    }

    public MutableLiveData<String> getIdUserSeleccionado() {
        return idUserSeleccionado;
    }

    public void openDialogUserMenu(Context ctx, User user){

        ModalBottomUserFragment dialogUser = ModalBottomUserFragment.newInstance(user);
        dialogUser.show(((AppCompatActivity)ctx).getSupportFragmentManager(),"ModalBottomUserFragment");
    }
}
