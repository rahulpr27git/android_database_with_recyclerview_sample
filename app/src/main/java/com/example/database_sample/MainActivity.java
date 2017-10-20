package com.example.database_sample;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.adapter.DbRecyclerAdapter;
import com.example.database.DBHelperClass;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    AppCompatButton btnAdd;

    DBHelperClass dbHelperClass;
    DbRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelperClass = new DBHelperClass(this);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        adapter = new DbRecyclerAdapter(dbHelperClass.getDataFromDatabase(),this);
        recyclerView.setAdapter(adapter);

        btnAdd = (AppCompatButton) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
    }

    public void showDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_add_data,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);

        final EditText etName = view.findViewById(R.id.etName);
        final EditText etEmail = view.findViewById(R.id.etEmail);

        /*builder.setTitle("ADD to database");
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                long status = dbHelperClass.insertIntoDatabase(
                        etName.getText().toString().trim(),
                        etEmail.getText().toString().trim()
                );
                if (status > 0) {
                    DBModel model = new DBModel();
                    model.setId(dbHelperClass.getLastInsertedId());
                    model.setName(etName.getText().toString().trim());
                    model.setEmail(etEmail.getText().toString().trim());
                    adapter.add(model);
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });*/

        builder.show();
    }
}
