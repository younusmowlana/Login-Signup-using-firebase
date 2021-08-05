package com.example.easylearn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.anychart.AnyChartView.*;

public class HomeActivity extends AppCompatActivity {
    AnyChartView anyChartView;
    private int count;
    Button btnLogout;
    FirebaseAuth mFirebaseAuth;
    //private TextView avl;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btnLogout = findViewById(R.id.logout);
       // avl = findViewById(R.id.avl);
        count = 0;

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Remaining");



        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                String value = dataSnapshot.getValue(String.class);
               // avl.setText(value);
               // Log.d("", "Value is: " + value);


                double remain = Double.parseDouble(value);



                Log.d("firebumi", "onDataChange: "+remain+" hhh");





                setup(remain);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("", "Failed to read value.", error.toException());
            }
        });



        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intToMain = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intToMain);
            }
        });

    }

    public void setup(double remain){



        List<DataEntry> data = new ArrayList<>();
        double x = remain;
        double tot = 12.5-x;
        data.add(new ValueDataEntry("Remaining", x));
        data.add(new ValueDataEntry("Total", tot));



        Pie pie = AnyChart.pie();




        pie.data(data);


        pie.title("Gas Consumption Manegement System ");

        pie.labels().position("outside");

        pie.legend().title().enabled(true);
        pie.legend().title()
                .text("Gas Consumption")
                .padding(0d, 0d, 10d, 0d);

        pie.legend()
                .position("center-bottom")
                .itemsLayout(LegendLayout.HORIZONTAL)
                .align(Align.CENTER);

        anyChartView = findViewById(R.id.anyChartView);


        anyChartView.setChart(pie);






    }
}