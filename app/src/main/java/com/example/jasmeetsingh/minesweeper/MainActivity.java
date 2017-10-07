package com.example.jasmeetsingh.minesweeper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    MyButtons buttons[][];
    int n=10;
    LinearLayout rows[];
    LinearLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainLayout = (LinearLayout) findViewById(R.id.activity_main);
        //mainLayout.setPadding(20,50,20,20);
        setUpBoard();
    }

    private void setUpBoard() {
        buttons = new MyButtons[n][n];
        rows = new LinearLayout[n];

        mainLayout.removeAllViews();

        for (int i = 0; i < n; i++) {
            rows[i] = new LinearLayout(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0,1);
            params.setMargins(2, 2, 2, 2);

            rows[i].setLayoutParams(params);
            //rows[i].setOrientation(LinearLayout.HORIZONTAL);
            mainLayout.addView(rows[i]);
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                buttons[i][j] = new MyButtons(this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
                params.setMargins(2, 2, 2, 2);
                buttons[i][j].setLayoutParams(params);
               // buttons[i][j].setText("");

                buttons[i][j].setOnClickListener(this);
                rows[i].addView(buttons[i][j]);
            }
        }
        addMines();
        setCount();
    }
    private void addMines(){
        int num=2*n;
       final Random  r = new Random();
        for (int i=0;i<num;i++){
            int j=r.nextInt(n);
            int k=r.nextInt(n);
            if(buttons[j][k].getText().toString()!="*"){
                buttons[j][k].setText("*");
            }
            else{
                num++;
            }

        }

    }
    int[] arr_col={-1,0,1,1,1,0,-1,-1};
    int[] arr_row={-1,-1,-1,0,1,1,1,0};
    private void setCount(){



        for (int i=0;i<n;i++) {
            for (int j = 0; j < n; j++) {
                if(buttons[i][j].getText().toString()!="*"){
                    buttons[i][j].setText(0+"");
                }
            }
        }
        incrementCount();
//        for (int i=0;i<n;i++) {
//            for (int j = 0; j < n; j++) {
//
//                    buttons[i][j].setTextColor(getResources().getColor(android.R.color.transparent));
//
//
//            }
//        }

    }

    private void incrementCount() {
        for (int i=0;i<n;i++){
            for (int j=0;j<n;j++) {
                if (buttons[i][j].getText().toString() == "*") {

                    for (int k = 0; k < 8; k++) {
                        if ((i + arr_row[k]) >=0 && ((i + arr_row[k]) < n) && ((j + arr_col[k])) >= 0 && ((j + arr_col[k]) < n)&&buttons[i + arr_row[k]][j + arr_col[k]].getText().toString()!="*") {
                            String string = buttons[i + arr_row[k]][j + arr_col[k]].getText().toString();
                            int count =  Integer.parseInt(string);
                            count++;
                            buttons[i + arr_row[k]][j + arr_col[k]].setText(count + "");
                        }


                    }
                }
            }
        }
    }

    boolean gameover=false;
    int score=0;
    @Override
    public void onClick(View v) {
        MyButtons b = (MyButtons) v;
        if(gameover){
            return;
        }
        if(b.getText().toString().equals("*")){
            Toast.makeText(this, "GameOver!!!", Toast.LENGTH_SHORT).show();
            for (int i=0;i<n;i++) {
                for (int j = 0; j < n; j++) {
                    if (buttons[i][j].getText().toString() == "*") {
                        buttons[i][j].setTextColor(getResources().getColor(android.R.color.black));
                    }
                }
            }

                        gameover=true;
            return;
        }
        if(b.getText().toString()!="*"){
            if(b.getText().toString().equals("0")) {
                zeroCount(b);
            }
            b.setTextColor(getResources().getColor(android.R.color.black));
            score++;
        }
    }


    private void zeroCount(MyButtons b) {
        for (int i=0;i<n;i++) {
            for (int j = 0; j < n; j++) {
                if (buttons[i][j].getId()==(b.getId())) {
//                    int index_row = i;
//                    int index_col = j;

                    for (int k = 0; k < 8; k++) {
                        if ((i + arr_row[k]) >= 0 && ((i + arr_row[k]) < n) && ((j + arr_col[k])) >= 0 && ((j + arr_col[k]) < n) ) {
//                            if (buttons[i + arr_row[k]][j + arr_col[k]].getText().toString().equals("0")) {
//                                zeroCount(buttons[i + arr_row[k]][j + arr_col[k]]);
//                            }
                            buttons[i + arr_row[k]][j + arr_col[k]].setTextColor(getResources().getColor(android.R.color.black));
                            score++;
                        }


                    }
                }
            }
        }
    }
}
