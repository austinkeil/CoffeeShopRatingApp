package com.example.akeil_listwithratings_s18;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.apache.commons.lang3.StringUtils;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView coursesView;
    private ArrayAdapter<String> coursesAdapter;
    String[] courses;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        coursesView = findViewById(R.id.coursesView);
        courses = getResources().getStringArray(R.array.cs_courses);
        coursesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, courses);
        coursesView.setAdapter(coursesAdapter);
        coursesView.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Intent intent = new Intent(this, CommentActivity.class);
        intent.putExtra("position", position);
        String item = coursesAdapter.getItem(position);
        intent.putExtra("item", item);
        startActivityForResult(intent, 0);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            float rating = data.getExtras().getFloat("rating");
            String comment = data.getExtras().getString("comment");
            int position = data.getExtras().getInt("position");
            courses[position] = getCourseRating(StringUtils.substringBefore(courses[position], "  :    "), rating, comment);
            coursesAdapter.notifyDataSetChanged();
        }
    }


    public String getCourseRating(String course, double rating, String comment) {
        StringBuilder sb = new StringBuilder(course + "  :    ");
        if (rating >= 0) {
            int ratingInt = (int) rating;
            for (int i = 0; i < ratingInt; i++)
                sb.append("\u2605");
            if (rating > (float) ratingInt)
                sb.append("\u00BD");
            sb.append("    ").append(comment);
        }
        return sb.toString();
    }

}
