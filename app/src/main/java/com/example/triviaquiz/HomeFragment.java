package com.example.triviaquiz;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    String level,category="";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.home_fragment, container, false);

//https://opentdb.com/api.php?amount=10&category=18&difficulty=easy&type=multiple

        Button btnPlay = (Button)view.findViewById(R.id.btnPlay);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(HomeFragment.this.getContext());
                dialog.setContentView(R.layout.layout_dialog);
                dialog.show();

                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                Spinner spinnerCategory = (Spinner) dialog.findViewById(R.id.spinnerCategory);
                Spinner spinnerLevel = (Spinner) dialog.findViewById(R.id.spinnerLevel);


                spinnerLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        level = parent.getItemAtPosition(position).toString();
                        //Log.e("level", "onItemSelected: "+position );
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                            level="easy";
                    }
                });

                String categoryNumber[] = new String[]{"","9","10","16","17","18","19","21","22","23","27","28"};
                spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                       // Log.e("position" ,"dsfa"+position );
                        category = categoryNumber[position];

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                            category="";
                    }
                });


                Button btnStart = (Button)dialog.findViewById(R.id.btnStartQuiz);
                btnStart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent quizIntent = new Intent(HomeFragment.this.getContext(), QuizActivity.class);
                        quizIntent.putExtra("Level",level);
                        quizIntent.putExtra("Category", category);
                        startActivity(quizIntent);
                    }
                });
            }
        });
        return view;
    }

}