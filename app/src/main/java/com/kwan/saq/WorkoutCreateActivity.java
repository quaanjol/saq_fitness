package com.kwan.saq;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.kwan.saq.model.MusclePart;
import com.kwan.saq.model.MusclePartSpinner;
import com.kwan.saq.model.WorkoutExerciseItemCreate;

import java.util.ArrayList;
import java.util.List;

public class WorkoutCreateActivity extends AppCompatActivity implements View.OnClickListener {

    List<WorkoutExerciseItemCreate> exerciseItemCreateList;
    Gson gson;
    List<MusclePart> musclePartList;

    private EditText workoutName;
    private EditText totalSets;
    private Spinner spinnerMainMuscle;
    private Button nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workout_create);

        exerciseItemCreateList = new ArrayList<>();

        Intent intent = this.getIntent();


        musclePartList = _CONST.getMusclePartList(this);

        initView();
    }

    private void initView() {
        workoutName = findViewById(R.id.workoutName);
        totalSets = findViewById(R.id.totalSets);
        spinnerMainMuscle = findViewById(R.id.spinnerMainMuscle);
        nextBtn = findViewById(R.id.nextBtn);


        // set up
        spinnerMainMuscle = (Spinner) findViewById(R.id.spinnerMainMuscle);

        ArrayList<MusclePartSpinner> musclePartSpinnerArrayList = new ArrayList<>();

        for(MusclePart musclePart: musclePartList) {
            musclePartSpinnerArrayList.add(new MusclePartSpinner(musclePart.getId(), musclePart.getName()));
        }

        //fill data in spinner
        ArrayAdapter<MusclePartSpinner> adapter = new ArrayAdapter<MusclePartSpinner>(this, android.R.layout.simple_spinner_dropdown_item, musclePartSpinnerArrayList);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMainMuscle.setAdapter(adapter);

        spinnerMainMuscle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //
        nextBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == nextBtn) {
            if(workoutName.getText().toString().equals("") || totalSets.getText().toString().equals("")) {
                Toast toast = Toast.makeText(this, "Please fill in all the information to continue", Toast.LENGTH_SHORT);
                toast.show();
                return;
            } else {
                goToAddExerciseItemPage();
            }
        }
    }

    private void goToAddExerciseItemPage() {
        Intent addExerciseItemIntent = new Intent(this, WorkoutCreateAddExerciseActivity.class);
        addExerciseItemIntent.putExtra("workoutName", workoutName.getText().toString());
        addExerciseItemIntent.putExtra("totalSets", Integer.parseInt(totalSets.getText().toString()));
        addExerciseItemIntent.putExtra("mainMuscle", spinnerMainMuscle.getSelectedItem().toString());
        startActivity(addExerciseItemIntent);
    }
}
