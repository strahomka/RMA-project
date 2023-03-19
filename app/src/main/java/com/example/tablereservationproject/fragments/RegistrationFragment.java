package com.example.tablereservationproject.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tablereservationproject.util.DatabaseHelper;
import com.example.tablereservationproject.R;
import com.example.tablereservationproject.repositories.UserRepository;

public class RegistrationFragment extends Fragment {

    public RegistrationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registration, container, false);
        Context appContext = getContext();
        DatabaseHelper db = new DatabaseHelper(appContext, 10);
        UserRepository userRepo = new UserRepository(db, appContext);

        EditText emailEt = view.findViewById(R.id.emailInput2);
        EditText nameEt = view.findViewById(R.id.nameInput);
        EditText passwordEt = view.findViewById(R.id.passwordInput2);
        Button registerButton = view.findViewById(R.id.confirmButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEt.getText().toString();
                String name = nameEt.getText().toString();
                String password = passwordEt.getText().toString();
                if(name.isEmpty() || password.isEmpty()){
                    Toast.makeText(appContext, "Please enter your name and password", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    if(userRepo.validate(email)){
                        if(userRepo.isEmailExists(email)){
                            Toast.makeText(appContext, "Email already in use, please use another", Toast.LENGTH_SHORT).show();
                            return;
                        }else{
                            userRepo.addUser(email,name,password);
                        }
                    }else {
                        Toast.makeText(appContext, "Wrong email format (please use 'johndoe@gmail.com')", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }



                FragmentManager fm = getFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.fragmentContainerView, LoginFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;

    }
}