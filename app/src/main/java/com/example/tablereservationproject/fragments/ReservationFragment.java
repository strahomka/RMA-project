package com.example.tablereservationproject.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
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
import com.example.tablereservationproject.repositories.ReservationRepository;
import com.example.tablereservationproject.model.Table;
import com.example.tablereservationproject.repositories.TableRepository;
import com.example.tablereservationproject.util.Login;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class ReservationFragment extends Fragment {
    private Button reserveButton;
    private Spinner tableSpinner;
    private String selectedItem;



    public ReservationFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if(Login.loggedIn){
                    FragmentManager fm = getFragmentManager();
                    fm.beginTransaction()
                            .replace(R.id.fragmentContainerView, HomeFragment.class, null)
                            .setReorderingAllowed(true)
                            .addToBackStack(null)
                            .commit();
                }
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        Context appContext = getContext();
        View view = inflater.inflate(R.layout.fragment_reservation, container, false);
        DatabaseHelper db = new DatabaseHelper(appContext, 10);
        TableRepository tableRepository = new TableRepository(db);
        ReservationRepository reservationRepository = new ReservationRepository(db, appContext);
//      Spinner
        tableSpinner = view.findViewById(R.id.table_id_spinner);
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

        EditText dateEt = view.findViewById(R.id.dateInput);
        EditText timeEt = view.findViewById(R.id.timeInput);
        EditText numberOfGuestsEt = view.findViewById(R.id.numberOfGuestsInput);


        reserveButton = view.findViewById(R.id.reserveButton);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            reserveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try{
                        String tableId = "1";
                        if(selectedItem.equals("Table 1")){
                            tableId = "1";
                        } else if (selectedItem.equals("Table 2")) {
                            tableId = "2";
                        }else if (selectedItem.equals("Table 3")) {
                            tableId = "3";
                        }else if (selectedItem.equals("Table 4")) {
                            tableId = "4";
                        }else if (selectedItem.equals("Table 5")) {
                            tableId = "5";
                        }else if (selectedItem.equals("Table 6")) {
                            tableId = "6";
                        }else if (selectedItem.equals("Table 7")) {
                            tableId = "7";
                        }else if (selectedItem.equals("Table 8")) {
                            tableId = "8";
                        }
                        SharedPreferences sharedPreferences = appContext.getSharedPreferences("userIdPrefs", Context.MODE_PRIVATE);
                        String user_id = sharedPreferences.getString("userId", "");
                        System.out.println(user_id);
                        String date = dateEt.getText().toString();
                        String time = timeEt.getText().toString();
                        String numberOfGuests = numberOfGuestsEt.getText().toString();
//                        System.out.println(time);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            reservationRepository.addReservation(Integer.parseInt(tableId),Integer.parseInt(user_id), LocalDate.parse(date), LocalTime.parse(time), Integer.parseInt(numberOfGuests));
                            System.out.println();
                        }
                    }catch (DateTimeParseException e){
                        Toast.makeText(appContext.getApplicationContext(), "Invalid date format (must be yyyy-mm-dd) or invalid time format (must be hh:mm)", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            });
        }

        // Inflate the layout for this fragment
        return view;
    }
}