package com.unknowns.devsoc;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;


public class HomeFragment extends Fragment {


    private static final String TAG = "HomeFragment";
    private DatabaseReference rootRef,temperature,breath;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: called");

        LineGraphSeries<DataPoint> series;

        View v = inflater.inflate(R.layout.frag_home,null);

        TextView textView1=(TextView) v.findViewById(R.id.breath) ;
        TextView textView=(TextView) v.findViewById(R.id.temp) ;
        double y, x;
        x = -0.5;
        y = -0.5;
        GraphView graph = (GraphView) v.findViewById(R.id.graph);
        graph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.NONE);
        graph.getGridLabelRenderer().setHorizontalAxisTitle("X");
        graph.getGridLabelRenderer().setHorizontalLabelsVisible(false);
        graph.getGridLabelRenderer().setVerticalLabelsVisible(false);
        graph.getGridLabelRenderer().setVerticalAxisTitle("Y");
        series = new LineGraphSeries<DataPoint>();
        for (int i = 0; i < 500; i++) {
            x = x + 0.1;
            y = y + 0.1;
            series.appendData(new DataPoint(x, y), true, 500);
        }
        graph.addSeries(series);
        rootRef = FirebaseDatabase.getInstance().getReference();
        temperature = rootRef.child("Data");

        temperature.child("Temp").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                textView.setVisibility(View.VISIBLE);
                textView.setText(value);
                Log.d("LOG", ""+value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });
        breath=rootRef.child("Data");
        breath.child("Breath").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                textView1.setVisibility(View.VISIBLE);
                textView1.setText(value);
                Log.d("LOG", ""+value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return v;

    }
}

