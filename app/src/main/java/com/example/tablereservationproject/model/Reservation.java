package com.example.tablereservationproject.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {

    public static final String TABLE_NAME = "reservations";
    public static final String FIELD_RESERVATION_ID = "reservation_id";
    public static final String FIELD_RESERVATION_TABLE_ID = "reservation_table_id";
    public static final String FIELD_RESERVATION_USER_ID = "reservation_user_id";
    public static final String FIELD_RESERVATION_DATE = "reservation_date";
    public static final String FIELD_RESERVATION_TIME = "reservation_time";
    public static final String FIELD_RESERVATION_NUM_GUESTS = "number_of_guests";

    private int reservation_id;
    private int table_id;

    private int user_id;
    private LocalDate date;
    private LocalTime time;
    private int num_guests;


    public Reservation(int reservation_id, int table_id, int user_id, LocalDate date, LocalTime time, int num_guests) {
        this.reservation_id = reservation_id;
        this.table_id = table_id;
        this.user_id = user_id;
        this.date = date;
        this.time = time;
        this.num_guests = num_guests;
    }

    public int getReservation_id() {
        return reservation_id;
    }

    public void setReservation_id(int reservation_id) {
        this.reservation_id = reservation_id;
    }

    public int getTable_id() {
        return table_id;
    }

    public void setTable_id(int table_id) {
        this.table_id = table_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public int getNum_guests() {
        return num_guests;
    }

    public void setNum_guests(int num_guests) {
        this.num_guests = num_guests;
    }


    @Override
    public String toString() {
        return "Reservation{" +
                "table_id=" + table_id +
                ", user_id=" + user_id +
                ", date=" + date +
                ", time=" + time +
                ", num_guests=" + num_guests +
                '}';
    }
}
