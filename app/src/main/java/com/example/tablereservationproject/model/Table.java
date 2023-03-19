package com.example.tablereservationproject.model;

public class Table {
    public static final String TABLE_NAME = "my_tables";
    public static final String FIELD_TABLE_ID = "table_id";
    public static final String FIELD_TABLE_NAME = "table_name";

    private int table_id;
    private String table_name;

    public Table(int table_id, String table_name) {
        this.table_id = table_id;
        this.table_name = table_name;
    }

    public int getTable_id() {
        return table_id;
    }

    public void setTable_id(int table_id) {
        this.table_id = table_id;
    }

    public String getTable_name() {
        return table_name;
    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }

    @Override
    public String toString() {
        return "Table{" +
                "table_id=" + table_id +
                ", table_name='" + table_name + '\'' +
                '}';
    }
}
