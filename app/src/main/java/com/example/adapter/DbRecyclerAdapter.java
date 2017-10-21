package com.example.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.database.DBHelperClass;
import com.example.database_sample.R;
import com.example.model.DBModel;

import java.util.ArrayList;

/**
 * Created by jbmm0007 on 13/10/17.
 */

public class DbRecyclerAdapter extends RecyclerView.Adapter<DbRecyclerAdapter.DBHolder> {

    ArrayList<DBModel> list;
    Context context;
    LayoutInflater layoutInflater;
    DBHelperClass dbHelperClass;

    public DbRecyclerAdapter(ArrayList<DBModel> list, Context context) {
        this.list = list;
        this.context = context;

        if (context != null) {
            layoutInflater = LayoutInflater.from(context);
            dbHelperClass = new DBHelperClass(context);
        }
    }

    @Override
    public DBHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.recycler_data,parent,false);
        DBHolder dbHolder = new DBHolder(view);
        return dbHolder;
    }

    @Override
    public void onBindViewHolder(DBHolder holder, int position) {
        holder.tvEmail.setText(list.get(position).getEmail());
        holder.tvName.setText(list.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public void add(DBModel model) {
        if (model != null) {
            list.add(model);
            notifyItemInserted(list.size());
        }
    }

    public class DBHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvEmail;
        ImageButton btnDelete;

        public DBHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            btnDelete = itemView.findViewById(R.id.btnDelete);

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (dbHelperClass.deleteFromDatabse(list.get(getAdapterPosition()).getId())>0) {
                        list.remove(getAdapterPosition());
                        notifyItemRemoved(getAdapterPosition());
                    } else {
                        Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
