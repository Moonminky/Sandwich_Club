package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONException;

import java.util.List;

import static com.udacity.sandwichclub.utils.JsonUtils.parseSandwichJson;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        TextView descriptionTV = (TextView) findViewById(R.id.description_tv);
        TextView originTV = (TextView) findViewById(R.id.origin_tv);
        TextView ingredientsTV = (TextView) findViewById(R.id.ingredients_tv);
        TextView alsoKnownAsTV = (TextView) findViewById(R.id.also_known_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = null;
        try {
            sandwich = parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich, alsoKnownAsTV, originTV, descriptionTV, ingredientsTV);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich, TextView alsoKnownAsTV, TextView placeOfOriginTV, TextView descriptionTV, TextView ingredientsTV) {
        List<String> alsoKnownAs = sandwich.getAlsoKnownAs();
        if (alsoKnownAs.isEmpty()) {
            alsoKnownAsTV.setText(R.string.no_synonym);
        } else {
            String alsoKnownAsString = "";
            for (int i = 0; i < alsoKnownAs.size(); i++) {
                alsoKnownAsString += alsoKnownAs.get(i);
                if (i < alsoKnownAs.size() - 1) alsoKnownAsString += ", ";
            }
            alsoKnownAsTV.setText(alsoKnownAsString);
        }
        if (sandwich.getPlaceOfOrigin().equals("")) {
            placeOfOriginTV.setText(R.string.empty_value);
        } else {
            placeOfOriginTV.setText(sandwich.getPlaceOfOrigin());
        }
        if (sandwich.getDescription().equals("")) {
            descriptionTV.setText(R.string.empty_value);
        } else {
            descriptionTV.setText(sandwich.getDescription());
        }

        List<String> ingredients = sandwich.getIngredients();
        if (ingredients == null) {
            ingredientsTV.setText(R.string.empty_value);
        } else {
            String ingredientsString = "";
            for (int i = 0; i < ingredients.size(); i++) {
                ingredientsString += ingredients.get(i);
                if (i < ingredients.size() - 1) ingredientsString += ", ";
            }
            ingredientsTV.setText(ingredientsString);
        }
    }
}
