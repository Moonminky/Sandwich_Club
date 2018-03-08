package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    /* Sandwich information */
    private static final String JSON_SANDWICH_NAME = "name";
    private static final String JSON_MAINNAME = "mainName";
    private static final String JSON_ALSOKNOWNAS = "alsoKnownAs";
    private static final String JSON_PLACEOFORIGIN = "placeOfOrigin";
    private static final String JSON_DESCRIPTION = "description";
    private static final String JSON_IMAGE = "image";
    private static final String JSON_INGREDIENTS = "ingredients";


    public static Sandwich parseSandwichJson(String json) throws JSONException {
        Sandwich sandwich = new Sandwich();
        String mainName;
        List<String> alsoKnownAs = new ArrayList<>();
        String placeOfOrigin;
        String description;
        String image;
        List<String> ingredients = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject jsonSandwichName = jsonObject.getJSONObject(JSON_SANDWICH_NAME);
            mainName = jsonSandwichName.optString(JSON_MAINNAME);
            JSONArray alsoKnownArray = jsonSandwichName.getJSONArray(JSON_ALSOKNOWNAS);
            for (int i = 0; i < alsoKnownArray.length(); i++) {
                String str = (String) alsoKnownArray.get(i);
                alsoKnownAs.add(str);
            }
            placeOfOrigin = jsonObject.getString(JSON_PLACEOFORIGIN);
            description = jsonObject.getString(JSON_DESCRIPTION);
            image = jsonObject.getString(JSON_IMAGE);
            JSONArray ingredientsArray = jsonObject.getJSONArray(JSON_INGREDIENTS);
            for (int i = 0; i < ingredientsArray.length(); i++) {
                String str = (String) ingredientsArray.get(i);
                ingredients.add(str);
            }
            sandwich.setMainName(mainName);
            sandwich.setAlsoKnownAs(alsoKnownAs);
            sandwich.setPlaceOfOrigin(placeOfOrigin);
            sandwich.setDescription(description);
            sandwich.setImage(image);
            sandwich.setIngredients(ingredients);
            return sandwich;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
