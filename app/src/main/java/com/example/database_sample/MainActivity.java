package com.example.database_sample;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.adapter.DbRecyclerAdapter;
import com.example.database.DBHelperClass;
import com.example.model.DBModel;

import de.hdodenhof.circleimageview.CircleImageView;

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

	    CircleImageView imgCircle = view.findViewById(R.id.imgCircle);
        final EditText etName = view.findViewById(R.id.etName);
        final EditText etEmail = view.findViewById(R.id.etEmail);
	    LinearLayout llChangeImage = view.findViewById(R.id.llChangeImage);
	    AppCompatButton btnSave = view.findViewById(R.id.btnSave);
	    AppCompatButton btnCancel = view.findViewById(R.id.btnCancel);

	    final AlertDialog dialog = builder.create();
	    btnSave.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View view) {

			    if (TextUtils.isEmpty(etName.getText().toString().trim())) {
				    etName.setError("Please enter your name");
				    return;
			    }

			    if (TextUtils.isEmpty(etEmail.getText().toString().trim())) {
				    etEmail.setError("Please enter you email");
				    return;
			    }

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
				    dialog.cancel();
			    }
		    }
	    });

	    btnCancel.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View view) {
			    dialog.cancel();
		    }
	    });

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

        dialog.show();
    }
}
