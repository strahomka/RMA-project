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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tablereservationproject.util.DatabaseHelper;
import com.example.tablereservationproject.util.Login;
import com.example.tablereservationproject.MainActivity;
import com.example.tablereservationproject.R;
import com.example.tablereservationproject.model.Reservation;
import com.example.tablereservationproject.repositories.ReservationRepository;
import com.example.tablereservationproject.model.Table;
import com.example.tablereservationproject.repositories.TableRepository;

import java.util.List;

public class ProfileFragment extends Fragment {

    private MainActivity mainActivity;
    public ProfileFragment() {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Context appContext = getContext();

        DatabaseHelper db = new DatabaseHelper(appContext, 10);
        ReservationRepository reservationRepository = new ReservationRepository(db, appContext);
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        Login login = new Login(appContext);

        Button profileButton = mainActivity.findViewById(R.id.profileButton);
        Button logoutButton = view.findViewById(R.id.logoutButton);
        Button editProfileButton = view.findViewById(R.id.editProfileButton);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login.logout();

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

        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.fragmentContainerView, UpdateFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();
            }
        });

        TableRepository tableRepository = new TableRepository(db);
        LayoutInflater inflater2 = LayoutInflater.from(appContext);
        LinearLayout mainLayout = view.findViewById(R.id.profileLayout);
        List<Reservation> list = reservationRepository.getAllReservationsFromUser();
        for (Reservation reservation : list) {
            LinearLayout subLayout = (LinearLayout) inflater2.inflate(R.layout.profile_sublayout, null);
            Table table = tableRepository.getTableById(reservation.getTable_id());
            TextView tableTv = subLayout.findViewById(R.id.tableTv2);
            TextView dateTv = subLayout.findViewById(R.id.dateTv2);
            TextView timeTv = subLayout.findViewById(R.id.timeTv2);
            TextView numOfGuestsTv = subLayout.findViewById(R.id.numOfGuestsTv2);

            tableTv.setText(table.getTable_name());
            dateTv.setText(String.valueOf(reservation.getDate()));
            timeTv.setText(String.valueOf(reservation.getTime()));
            numOfGuestsTv.setText(String.valueOf(reservation.getNum_guests()));

            Button deleteButton = subLayout.findViewById(R.id.deleteButton);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    reservationRepository.deleteReservation(reservation.getReservation_id());
                }
            });

            Button editButton = subLayout.findViewById(R.id.editButton);
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences sharedPreferences = appContext.getSharedPreferences("reservationIdPrefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("reservationId", String.valueOf(reservation.getReservation_id()));
                    editor.apply();

                    FragmentManager fm = getFragmentManager();
                    fm.beginTransaction()
                            .replace(R.id.fragmentContainerView, ReservationEditFragment.class, null)
                            .setReorderingAllowed(true)
                            .addToBackStack(null)
                            .commit();
                }
            });

            mainLayout.addView(subLayout);
        }

        return view;
    }
}