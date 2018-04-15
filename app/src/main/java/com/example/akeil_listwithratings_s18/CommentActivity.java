package com.example.akeil_listwithratings_s18;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;

import static org.apache.commons.lang3.StringUtils.substringAfter;
import static org.apache.commons.lang3.StringUtils.substringBefore;


public class CommentActivity extends AppCompatActivity {
    RatingBar rb;
    EditText commentView;
    int position;
    String course = "";
    float rating;
    String comment = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        position = getIntent().getIntExtra("position", 0);
        parseItemInfo(getIntent().getStringExtra("item"));
        rb = findViewById(R.id.ratingBar);
        rb.setRating(rating);
        commentView = findViewById(R.id.editText);
        commentView.setText(comment);
        setTitle("RATE " + course);

    }


    private void parseItemInfo(String itemText) {
        String info = substringAfter(itemText, "  :    ");
        course = substringBefore(itemText, "  :");
        if (info != null && !info.equals("")) {
            String ratingText = substringBefore(info, "    ");
            rating = ratingText.endsWith("\u00BD") ? (ratingText.length() - 1) + 0.5f : ratingText.length();
            comment = substringAfter(info, "    ");
        }
    }


    public void onCancel(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }


    public void onOK(View view) {
        Intent i = new Intent();
        i.putExtra("position", position);
        i.putExtra("rating", rb.getRating());
        i.putExtra("comment", commentView.getText().toString());
        setResult(RESULT_OK, i);
        finish();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onOK(findViewById(R.id.okButton));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

