package com.example.trabalhomark01;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.text.Html;
import android.widget.LinearLayout;
import android.widget.TextView;

public class treinos extends AppCompatActivity {

    private ViewPager mSlideViewPager;
    private SlideAdapter sliderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treinos);
        getSupportActionBar().hide();

        //Busca o ID e adiciona no objeto mSlideViewPager
        mSlideViewPager = findViewById(R.id.slideViewPager);
        //Executa os códigos na tela SlideAdapter
        sliderAdapter = new SlideAdapter(this);
        mSlideViewPager.setAdapter(sliderAdapter);


    }




}