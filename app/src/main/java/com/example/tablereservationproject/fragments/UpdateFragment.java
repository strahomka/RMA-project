package com.example.tablereservationproject.fragments;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.example.tablereservationproject.MainActivity;
import com.example.tablereservationproject.R;
import com.example.tablereservationproject.model.User;
import com.example.tablereservationproject.repositories.UserRepository;

public class UpdateFragment extends Fragment {

    private MainActivity mainActivity;

    public UpdateFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Context appContext = getContext();
        View view = inflater.inflate(R.layout.fragment_update, container, false);
        DatabaseHelper db = new DatabaseHelper(appContext, 10);
        UserRepository userRepository = new UserRepository(db, appContext);
        Button profileButton = mainActivity.findViewById(R.id.profileButton);

        EditText emailUpdateEt = view.findViewById(R.id.emailEditText);
        EditText nameUpdateEt = view.findViewById(R.id.nameEditText);
        EditText passwordUpdateEt = view.findViewById(R.id.passwordEditText);
        SharedPreferences sharedPreferences = appContext.getSharedPreferences("userIdPrefs", Context.MODE_PRIVATE);
        String user_id_arg = sharedPreferences.getString("userId", "");
        User user = userRepository.getUserById(Integer.parseInt(user_id_arg));
        emailUpdateEt.setText(user.getEmail());
        nameUpdateEt.setText(user.getName());
        passwordUpdateEt.setText(user.getPassword());


        Button updateButton = view.findViewById(R.id.updateButton);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = emailUpdateEt.getText().toString();
                String name = nameUpdateEt.getText().toString();
                String password = passwordUpdateEt.getText().toString();
                if(name.isEmpty() || password.isEmpty()){
                    Toast.makeText(appContext, "Please enter your name and password", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    if(userRepository.validate(email)){
                        if(userRepository.isEmailExists(email)){
                            Toast.makeText(appContext, "Email already in use, please use another", Toast.LENGTH_SHORT).show();
                            return;
                        }else{
                            userRepository.updateUser(Integer.parseInt(user_id_arg),email,name,password);
                        }
                    }else {
                        Toast.makeText(appContext, "Wrong email format (please use 'johndoe@gmail.com')", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                userRepository.updateUser(Integer.parseInt(user_id_arg),email,name,password);
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.fragmentContainerView, ProfileFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();
            }
        });
        Button deleteAccountButton = view.findViewById(R.id.deleteAccoutButton);
        deleteAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences2 = appContext.getSharedPreferences("userIdPrefs", Context.MODE_PRIVATE);
                String user_id = sharedPreferences2.getString("userId", "");
                userRepository.deleteReservation(Integer.parseInt(user_id));

                profileButton.setVisibility(View.GONE);
                profileButton.setEnabled(false);

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