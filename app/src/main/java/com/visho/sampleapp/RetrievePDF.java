package com.visho.sampleapp;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class RetrievePDF extends AppCompatActivity {
    ListView listView;
    DatabaseReference reference;

    List<putPDF> uploadedPDF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_p_d_f);

        listView = findViewById(R.id.listView);
        uploadedPDF = new ArrayList<putPDF>();

        retrievePDFFiles();
    }

    private void retrievePDFFiles() {
        reference = FirebaseDatabase.getInstance().getReference("SampleApp");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds : snapshot.getChildren()) {
                    putPDF putPDF = ds.getValue(com.visho.sampleapp.putPDF.class);
                    uploadedPDF.add(putPDF);

                }
                String[] uploadsName = new String[uploadedPDF.size()];

                for (int i = 0; i < uploadsName.length; i++) {

                    uploadsName[i] = uploadedPDF.get(i).getName();

                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,uploadsName){

                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

                        View view =  super.getView(position, convertView, parent);
                        TextView textview = (TextView) view.findViewById(android.R.id.text1);
                        textview.setTextColor(Color.BLACK);
                        textview.setTextSize(20);
                        return view;
                    }
                };
                    listView.setAdapter(arrayAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}