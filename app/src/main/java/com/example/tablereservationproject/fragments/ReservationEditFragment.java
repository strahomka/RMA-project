package com.example.tablereservationproject.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tablereservationproject.util.DatabaseHelper;
import com.example.tablereservationproject.R;
import com.example.tablereservationproject.model.Reservation;
import com.example.tablereservationproject.repositories.ReservationRepository;
import com.example.tablereservationproject.model.Table;
import com.example.tablereservationproject.repositories.TableRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class ReservationEditFragment extends Fragment {

    private Spinner tableSpinner;

    private String selectedItem;
    public ReservationEditFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reservation_edit, container, false);
        Context appContext = getContext();
        DatabaseHelper db = new DatabaseHelper(appContext, 10);
        ReservationRepository reservationRepository = new ReservationRepository(db, appContext);
        TableRepository tableRepository = new TableRepository(db);
        SharedPreferences sharedPreferences = appContext.getSharedPreferences("reservationIdPrefs", Context.MODE_PRIVATE);
        String reservation_id = sharedPreferences.getString("reservationId", "");
        Reservation reservation = reservationRepository.getReservationById(Integer.parseInt(reservation_id));

        tableSpinner = view.findViewById(R.id.editResTableSpinner);
        ArrayList<String> tables = new ArrayList<>();
        for(Table table: tableRepository.getAllTables()){
            tables.add(table.getTable_name());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(appContext, android.R.layout.simple_spinner_item, tables);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        tableSpinner.setAdapter(adapter);
//      TEST


//      Forma
        tableSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItem = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }

        });

        Button submitButton = view.findViewById(R.id.submitButton);
        EditText dateEditEt = view.findViewById(R.id.dateEditText);
        EditText timeEditEt = view.findViewById(R.id.timeEditText);
        EditText numberOfGuestsEditEt = view.findViewById(R.id.numberOfGuestsEditText);

        dateEditEt.setText(String.valueOf(reservation.getDate()));
        timeEditEt.setText(String.valueOf(reservation.getTime()));
        numberOfGuestsEditEt.setText(String.valueOf(reservation.getNum_guests()));

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        String tableId = "";
                        if (selectedItem.equals("Table 1")) {
                            tableId = "1";
                        } else if (selectedItem.equals("Table 2")) {
                            tableId = "2";
                        } else if (selectedItem.equals("Table 3")) {
                            tableId = "3";
                        } else if (selectedItem.equals("Table 4")) {
                            tableId = "4";
                        } else if (selectedItem.equals("Table 5")) {
                            tableId = "5";
                        } else if (selectedItem.equals("Table 6")) {
                            tableId = "6";
                        } else if (selectedItem.equals("Table 7")) {
                            tableId = "7";
                        } else if (selectedItem.equals("Table 8")) {
                            tableId = "8";
                        }
                        SharedPreferences sharedPreferences2 = appContext.getSharedPreferences("userIdPrefs", Context.MODE_PRIVATE);
                        String user_id = sharedPreferences2.getString("userId", "");
                        String dateEdit = dateEditEt.getText().toString();
                        String timeEdit = timeEditEt.getText().toString();
                        String numberOfGuestsEdit = numberOfGuestsEditEt.getText().toString();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            reservationRepository.updateReservation(Integer.parseInt(reservation_id), Integer.parseInt(tableId), Integer.parseInt(user_id), LocalDate.parse(dateEdit),
                                    LocalTime.parse(timeEdit), Integer.parseInt(numberOfGuestsEdit));
                        }
                        FragmentManager fm = getFragmentManager();
                        fm.beginTransaction()
                                .replace(R.id.fragmentContainerView, ProfileFragment.class, null)
                                .setReorderingAllowed(true)
                                .addToBackStack(null)
                                .commit();
                    }catch (DateTimeParseException e){
                        Toast.makeText(appContext.getApplicationContext(), "Invalid date format (must be yyyy-mm-dd) or invalid time format (must be hh:mm)", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            });
        }

        return view;
    }
}