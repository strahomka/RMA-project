package com.example.tablereservationproject.fragments;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.tablereservationproject.util.DatabaseHelper;
import com.example.tablereservationproject.util.Login;
import com.example.tablereservationproject.MainActivity;
import com.example.tablereservationproject.R;
import com.example.tablereservationproject.model.User;

public class LoginFragment extends Fragment {
    private MainActivity mainActivity;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Context appContext = getContext();
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        DatabaseHelper database = new DatabaseHelper(appContext, 10);
        SQLiteDatabase db = database.getReadableDatabase();

        EditText emailEt = view.findViewById(R.id.emailInput);
        EditText passwordEt = view.findViewById(R.id.passwordInput);

        Button profileButton = mainActivity.findViewById(R.id.profileButton);
        Button loginButton = view.findViewById(R.id.loginButton);
        Login login = new Login(appContext);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEt.getText().toString();
                String password = passwordEt.getText().toString();
                login.checkLogin(email,password);
                User user = login.checkLogin(email,password);

                if(user != null) {
                    String query = String.format("SELECT %s FROM %s WHERE %s = ? AND %s = ?;", User.FIELD_USER_ID,User.TABLE_NAME, User.FIELD_USER_EMAIL, User.FIELD_USER_PASSWORD);

                    Cursor result = db.rawQuery(query, new String[] {email, password});

                    if(result.moveToFirst()){
                        String userId = result.getString(result.getColumnIndex(User.FIELD_USER_ID));
                        login.storeUserId(userId);
                    }

                    profileButton.setVisibility(View.VISIBLE);
                    profileButton.setEnabled(true);

                    FragmentManager fm = getFragmentManager();
                    fm.beginTransaction()
                            .replace(R.id.fragmentContainerView, ReservationFragment.class, null)
                            .setReorderingAllowed(true)
                            .addToBackStack(null)
                            .commit();
                }
            }
        });




        Button resButton = view.findViewById(R.id.registrationButton);
        resButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.fragmentContainerView, RegistrationFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }
}