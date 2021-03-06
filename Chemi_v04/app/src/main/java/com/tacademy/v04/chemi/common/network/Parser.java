package com.tacademy.v04.chemi.common.network;

import android.util.Log;

import com.tacademy.v04.chemi.common.network.NetworkConfig.User;
import com.tacademy.v04.chemi.model.Chemical;
import com.tacademy.v04.chemi.model.Effect;
import com.tacademy.v04.chemi.model.Product;
import com.tacademy.v04.chemi.model.Review;
import com.tacademy.v04.chemi.model.Word;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tacademy.v04.chemi.common.network.NetworkConfig.Chemical.Key.ABBR;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.Chemical.Key.CHEMICALS;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.Chemical.Key.CHEMICAL_ID;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.Chemical.Key.CONSTITUTION_ID;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.Chemical.Key.CONSTITUTION_NAME;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.Chemical.Key.DESCRIPTION;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.Chemical.Key.EFFECTS;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.Chemical.Key.HAZARD;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.Chemical.Key.KEYWORD;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.Chemical.Key.MIX;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.Chemical.Key.NAMEEN;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.Chemical.Key.NAMEKO;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.Product.Key.BRAND;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.Product.Key.CATEGORY_ID;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.Product.Key.IMAGE_PATH;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.Product.Key.MAKER;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.Product.Key.NAME;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.Product.Key.PRODUCT_ID;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.Product.Key.PURPOSE;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.Product.Key.RATING;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.Product.Key.RATING_COUNT;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.Product.Key.RELEASED;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.Product.Key.TYPE;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.RESPONSE_DATA;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.RESPONSE_MESSAGE;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.RESPONSE_SUCCESS;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.Review.Key.REVIEW_CREATED;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.Review.Key.REVIEW_ID;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.Review.Key.REVIEW_IMAGE1;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.Review.Key.REVIEW_IMAGE2;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.Review.Key.REVIEW_IMAGE3;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.Review.Key.REVIEW_IMAGE_EXIST;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.Review.Key.REVIEW_NEGATIVE;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.Review.Key.REVIEW_POSITIVE;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.Review.Key.REVIEW_RATING;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.Review.Key.WRITER_BIRTHYEAR;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.Review.Key.WRITER_CHILD;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.Review.Key.WRITER_CONSTITUTION;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.Review.Key.WRITER_CONSTITUTIONS;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.Review.Key.WRITER_GENDER;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.Review.Key.WRITER_ID;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.Review.Key.WRITER_NAME;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.Search.Key.SEARCHED_BRAND;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.Search.Key.SEARCHED_CATEGORY_ID;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.Search.Key.SEARCHED_ID;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.Search.Key.SEARCHED_IMAGE;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.Search.Key.SEARCHED_MAKER;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.Search.Key.SEARCHED_TYPE;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.Search.Key.SEARCH_WORD;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.Search.Key.SEARHCED_PRODUCT_NAME;
import static com.tacademy.v04.chemi.common.network.NetworkConfig.User.PRODUCT_NAME;

/**
 * Created by yoon on 2016. 11. 25..
 */

public class Parser {

    private static final String TAG = Parser.class.getSimpleName();

    /**
     * products
     * @param responseObject
     * @return ArrayList<Product>
     */
    public static ArrayList<Product> parseProductList(JSONObject responseObject) {

        ArrayList<Product> products = new ArrayList<>();
        try {
            String responseMessage = responseObject.getString(RESPONSE_MESSAGE);
            if (responseMessage.equals(RESPONSE_SUCCESS)) {
                JSONArray dataArray = responseObject.getJSONArray(RESPONSE_DATA);
                if (dataArray != null) {
                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject productObject = (JSONObject) dataArray.get(i);
                        Product product = new Product();
                        product.setProductId(productObject.getInt(PRODUCT_ID));
                        product.setCategoryId(productObject.getInt(CATEGORY_ID));
                        product.setMaker(productObject.getString(MAKER));
                        product.setBrand(productObject.getString(BRAND));
                        product.setName(productObject.getString(NAME));
//                        product.setRatingAvg((float) productObject.get(RATING));
                        Object rating = productObject.get(RATING);
                        float ratingf = 0.0f;
//                        Log.i("parseProductList rating", String.valueOf(rating));
                        if (rating instanceof Integer) {
                            ratingf = ((Integer) rating).floatValue();
                        } else if (rating instanceof Double) {
                            ratingf = ((Double) rating).floatValue();
                        } else if (rating == null) {
                            ratingf = 0.0f;
                        }
//                        Log.i("float", String.valueOf(ratingf));
//
                        product.setRatingAvg(ratingf);
//                        Log.i("product", String.valueOf(product.getRatingAvg()));
                        product.setRatingCount(productObject.getInt(RATING_COUNT));
                        product.setImagePath(productObject.getString(IMAGE_PATH));
                        products.add(product);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.w("Parse Exception", e.getMessage());
        }
        return products;
    }

    /**
     * /products/searchfilters?categoryid= ?inchemicals= ?exchemicals=
     * products
     * @param responseObject
     * @return ArrayList<Product>
     */
    public static ArrayList<Product> parseCustomSearchProductList(JSONObject responseObject) {

        ArrayList<Product> products = new ArrayList<>();
        try {
            String responseMessage = responseObject.getString(RESPONSE_MESSAGE);
            if (responseMessage.equals(RESPONSE_SUCCESS)) {
                JSONArray dataArray = responseObject.getJSONArray(RESPONSE_DATA);
                if (dataArray != null) {
                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject productObject = (JSONObject) dataArray.get(i);
                        Product product = new Product();
                        product.setProductId(productObject.getInt(PRODUCT_ID));
                        product.setCategoryId(productObject.getInt(CATEGORY_ID));
                        product.setMaker(productObject.getString(MAKER));
                        product.setBrand(productObject.getString(BRAND));
                        product.setName(productObject.getString(NAME));
                        product.setType(productObject.getString(TYPE));
                        product.setPurpose(productObject.getString(PURPOSE));
//                        product.setRatingAvg((float) productObject.get(RATING));
//                        Object rating = productObject.get(RATING);
//                        float ratingf = 0.0f;
////                        Log.i("parseProductList rating", String.valueOf(rating));
//                        if (rating instanceof Integer) {
//                            ratingf = ((Integer) rating).floatValue();
//                        } else if (rating instanceof Double) {
//                            ratingf = ((Double) rating).floatValue();
//                        } else if (rating == null) {
//                            ratingf = 0.0f;
//                        }
////                        Log.i("float", String.valueOf(ratingf));
////
//                        product.setRatingAvg(ratingf);

                        product.setRatingAvg((float) productObject.getDouble(RATING));

//                        product.setRatingCount(productObject.getInt(RATING_COUNT));
                        product.setImagePath(productObject.getString(IMAGE_PATH));
                        products.add(product);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.w("Parse Exception", e.getMessage());
        }
        return products;
    }

    /**
     * products
     * @param responseObject
     * @param products
     * @return ArrayList<Product>
     */
    public static ArrayList<Product> parseProductList(JSONObject responseObject, ArrayList<Product> products) {

//        ArrayList<Product> products = new ArrayList<>();
        try {
            String responseMessage = responseObject.getString(RESPONSE_MESSAGE);
            if (responseMessage.equals(RESPONSE_SUCCESS)) {
                JSONArray dataArray = responseObject.getJSONArray(RESPONSE_DATA);
                if (dataArray != null) {
                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject productObject = (JSONObject) dataArray.get(i);
                        Product product = new Product();
                        product.setProductId(productObject.getInt(PRODUCT_ID));
                        product.setCategoryId(productObject.getInt(CATEGORY_ID));
                        product.setMaker(productObject.getString(MAKER));
                        product.setBrand(productObject.getString(BRAND));
                        product.setName(productObject.getString(NAME));
//                        product.setRatingAvg((float) productObject.get(RATING));
                        Object rating = productObject.get(RATING);
                        float ratingf = 0.0f;
//                        Log.i("parseProductList rating", String.valueOf(rating));
                        if (rating instanceof Integer) {
                            ratingf = ((Integer) rating).floatValue();
                        } else if (rating instanceof Double) {
                            ratingf = ((Double) rating).floatValue();
                        } else if (rating == null) {
                            ratingf = 0.0f;
                        }
//                        Log.i("float", String.valueOf(ratingf));
//
                        product.setRatingAvg(ratingf);
//                        Log.i("product", String.valueOf(product.getRatingAvg()));
                        product.setRatingCount(productObject.getInt(RATING_COUNT));
                        product.setImagePath(productObject.getString(IMAGE_PATH));
                        products.add(product);
                        Log.i("parseProductList", product.toStringId());
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.w("Parse Exception", e.getMessage());
        }
        Log.i(TAG, "parseProductList mProducts.size() : " + products.size());
        return products;
    }

    /**
     * products/:product_id
     * @param responseObject
     * @return Product
     */
    public static Product parseProduct(JSONObject responseObject) {

        Product product = new Product();
        ArrayList<Chemical> chemicals = new ArrayList<>();
        try {
            String responseMessage = responseObject.getString(RESPONSE_MESSAGE);
            if (responseMessage.equals(RESPONSE_SUCCESS)) {
                JSONObject dataObject = responseObject.getJSONObject(RESPONSE_DATA);
                if (dataObject != null) {

                    // need to equivalent reference injection including Storage
                    product.setProductId(dataObject.getInt(PRODUCT_ID));
                    product.setCategoryId(dataObject.getInt(CATEGORY_ID));
                    product.setMaker(dataObject.getString(MAKER));
                    product.setBrand(dataObject.getString(BRAND));
                    product.setName(dataObject.getString(NAME));
                    product.setType(dataObject.getString(TYPE));
                    product.setPurpose(dataObject.getString(PURPOSE));
                    product.setReleaseDate(dataObject.getString(RELEASED));
//                    product.setRatingAvg((float) dataObject.getDouble(RATING));
                    Object rating = dataObject.get(RATING);
                    float ratingf = 0.0f;
                    if (rating instanceof Integer) {
                        ratingf = ((Integer) rating).floatValue();
                    } else if (rating instanceof Double) {
                        ratingf = ((Double) rating).floatValue();
                    } else if (rating == null) {
                        ratingf = 0.0f;
                    }
                    product.setRatingAvg(ratingf);
                    product.setRatingCount(dataObject.getInt(RATING_COUNT));
                    product.setImagePath(dataObject.getString(IMAGE_PATH));

                    JSONArray chemicalArray = dataObject.getJSONArray(CHEMICALS);
                    if (chemicalArray != null) {
                        for (int i = 0; i < chemicalArray.length(); i++) {
                            JSONObject chemicalObject = (JSONObject) chemicalArray.get(i);
                            Chemical chemical = new Chemical();
                            chemical.setChemicalId(chemicalObject.getInt(CHEMICAL_ID));
                            chemical.setNameKo(chemicalObject.getString(NAMEKO));
                            chemical.setNameEn(chemicalObject.getString(NAMEEN));
                            chemical.setHazard(chemicalObject.getInt(HAZARD));
                            chemical.setKeyword(chemicalObject.getString(KEYWORD));
                            chemicals.add(chemical);
                        }
                    }
                    product.setChemicals(chemicals);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.w("Parse Exception", e.getMessage());
        }
        return product;
    }

    /**
     * products/:product_id
     * @param responseObject
     * @param product
     * @return Product
     */
    public static Product parseProduct(JSONObject responseObject, Product product) {

//        Product product = new Product();
        ArrayList<Chemical> chemicals = new ArrayList<>();
        try {
            String responseMessage = responseObject.getString(RESPONSE_MESSAGE);
            if (responseMessage.equals(RESPONSE_SUCCESS)) {
                JSONObject dataObject = responseObject.getJSONObject(RESPONSE_DATA);
                if (dataObject != null) {

                    // need to equivalent reference injection including Storage
                    product.setProductId(dataObject.getInt(PRODUCT_ID));
                    product.setCategoryId(dataObject.getInt(CATEGORY_ID));
                    product.setMaker(dataObject.getString(MAKER));
                    product.setBrand(dataObject.getString(BRAND));
                    product.setName(dataObject.getString(NAME));
                    product.setType(dataObject.getString(TYPE));
                    product.setPurpose(dataObject.getString(PURPOSE));
                    product.setReleaseDate(dataObject.getString(RELEASED));
//                    product.setRatingAvg((float) dataObject.getDouble(RATING));
                    Object rating = dataObject.get(RATING);
                    float ratingf = 0.0f;
                    if (rating instanceof Integer) {
                        ratingf = ((Integer) rating).floatValue();
                    } else if (rating instanceof Double) {
                        ratingf = ((Double) rating).floatValue();
                    } else if (rating == null) {
                        ratingf = 0.0f;
                    }
                    product.setRatingAvg(ratingf);
                    product.setRatingCount(dataObject.getInt(RATING_COUNT));
                    product.setImagePath(dataObject.getString(IMAGE_PATH));

                    JSONArray chemicalArray = dataObject.getJSONArray(CHEMICALS);
                    if (chemicalArray != null) {
                        for (int i = 0; i < chemicalArray.length(); i++) {
                            JSONObject chemicalObject = (JSONObject) chemicalArray.get(i);
                            Chemical chemical = new Chemical();
                            chemical.setChemicalId(chemicalObject.getInt(CHEMICAL_ID));
                            chemical.setNameKo(chemicalObject.getString(NAMEKO));
                            chemical.setNameEn(chemicalObject.getString(NAMEEN));
                            chemical.setHazard(chemicalObject.getInt(HAZARD));
                            chemical.setKeyword(chemicalObject.getString(KEYWORD));
                            chemical.setMarkedIndex(i);
                            chemicals.add(chemical);
                        }
                    }
                    product.setChemicals(chemicals);
                }
            }
            Log.i("parseProduct", product.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            Log.w("Parse Exception", e.getMessage());
        }
        return product;
    }

    /**
     * products/:product_id/chemicals
     * @param responseObject
     * @return ArrayList<Chemical>
     */
    public static ArrayList<Chemical> parseChemicalList(JSONObject responseObject) {

        ArrayList<Chemical> chemicals = new ArrayList<>();
        try {
            String responseMessage = responseObject.getString(RESPONSE_MESSAGE);
            if (responseMessage.equals(RESPONSE_SUCCESS)) {
                JSONArray dataArray = responseObject.getJSONArray(RESPONSE_DATA);
                if (dataArray != null) {
                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject chemicalObject = (JSONObject) dataArray.get(i);
                        Chemical chemical = new Chemical();
                        chemical.setChemicalId(chemicalObject.getInt(CHEMICAL_ID));
                        chemical.setNameKo(chemicalObject.getString(NAMEKO));
                        chemical.setNameEn(chemicalObject.getString(NAMEEN));
                        chemical.setHazard(chemicalObject.getInt(HAZARD));
                        chemical.setKeyword(chemicalObject.getString(KEYWORD));
                        chemicals.add(chemical);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.w("Parse Exception", e.getMessage());
        }
        return chemicals;
    }

    /**
     * chemicals/:chemical_id
     * @param responseObject
     * @return Chemical
     */
    public static Chemical parseChemical(JSONObject responseObject, Chemical chemical) {

//        Chemical chemical = new Chemical();
        ArrayList<Effect> effects = new ArrayList<>();
        try {
            String responseMessage = responseObject.getString(RESPONSE_MESSAGE);
            if (responseMessage.equals(RESPONSE_SUCCESS)) {
                JSONObject dataObject = responseObject.getJSONObject(RESPONSE_DATA);
                if (dataObject != null) {
                    chemical.setChemicalId(dataObject.getInt(CHEMICAL_ID));
                    chemical.setNameKo(dataObject.getString(NAMEKO));
                    chemical.setNameEn(dataObject.getString(NAMEEN));
                    chemical.setAbbr(dataObject.getString(ABBR));
                    chemical.setMix(dataObject.getString(MIX));
                    chemical.setHazard(dataObject.getInt(HAZARD));
                    chemical.setKeyword(dataObject.getString(KEYWORD));

                    JSONArray effectArray = dataObject.getJSONArray(EFFECTS);
                    if (effectArray != null) {
                        for (int i = 0; i < effectArray.length(); i++) {
                            JSONObject effectObject = (JSONObject) effectArray.get(i);
                            Effect effect = new Effect();
                            effect.setId(effectObject.getInt(CONSTITUTION_ID));
                            effect.setConstitutionName(effectObject.getString(CONSTITUTION_NAME));
                            effect.setDescription(effectObject.getString(DESCRIPTION));
                            effects.add(effect);
                        }
                    }
                    chemical.setEffects(effects);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.w("Parse Exception", e.getMessage());
        }
        return chemical;
    }

    /**
     * chemicals/:chemical_id
     * @param responseObject
     * @return Chemical
     */
    public static Chemical parseChemical(JSONObject responseObject, Chemical chemical, boolean isLove) {

//        Chemical chemical = new Chemical();
        ArrayList<String> constitutions = new ArrayList<>();
        try {
            String responseMessage = responseObject.getString(RESPONSE_MESSAGE);
            if (responseMessage.equals(RESPONSE_SUCCESS)) {
                JSONObject dataObject = responseObject.getJSONObject(RESPONSE_DATA);
                if (dataObject != null) {
                    chemical.setChemicalId(dataObject.getInt(CHEMICAL_ID));
                    chemical.setNameKo(dataObject.getString(NAMEKO));
                    chemical.setNameEn(dataObject.getString(NAMEEN));
                    chemical.setAbbr(dataObject.getString(ABBR));
                    chemical.setMix(dataObject.getString(MIX));
                    chemical.setHazard(dataObject.getInt(HAZARD));
                    chemical.setKeyword(dataObject.getString(KEYWORD));

                    JSONArray effectArray = dataObject.getJSONArray(EFFECTS);
                    if (effectArray != null) {
                        for (int i = 0; i < effectArray.length(); i++) {
                            JSONObject effectObject = (JSONObject) effectArray.get(i);
//                            Effect effect = new Effect();
//                            effect.setId(effectObject.getInt(CONSTITUTION_ID));
//                            effect.setConstitutionName(effectObject.getString(CONSTITUTION_NAME));
//                            effect.setDescription(effectObject.getString(DESCRIPTION));
//                            effects.add(effect);

                            int constitutionId = effectObject.getInt(CONSTITUTION_ID);
                            String description = effectObject.getString(DESCRIPTION);

                            constitutions.add(description);
                        }
                        chemical.setConstitutions(constitutions);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.w("Parse Exception", e.getMessage());
        }
        return chemical;
    }

    /**
     * products/:product_id/reviews
     * @param responseObject
     * @return ArrayList<Review>
     */
    public static ArrayList<Review> parseReviewList(JSONObject responseObject) {

        ArrayList<Review> reviews = new ArrayList<>();
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        try {
            String responseMessage = responseObject.getString(RESPONSE_MESSAGE);
            if (responseMessage.equals(RESPONSE_SUCCESS)) {
                JSONArray dataArray = responseObject.getJSONArray(RESPONSE_DATA);
                if (dataArray != null) {
                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject reviewObject = (JSONObject) dataArray.get(i);
                        Review review = new Review();
                        review.setReviewId(reviewObject.getInt(REVIEW_ID));
                        review.setUserId(reviewObject.getInt(WRITER_ID));
                        review.setName(reviewObject.getString(WRITER_NAME));
                        review.setGender(reviewObject.getString(WRITER_GENDER));
                        review.setBirthYear(reviewObject.getInt(WRITER_BIRTHYEAR));
                        review.setChild(reviewObject.getInt(WRITER_CHILD));

                        Object ratingObject = reviewObject.get(REVIEW_RATING);
                        float ratingf = 0.0f;
                        if (ratingObject instanceof Integer) {
                            ratingf = ((Integer) ratingObject).floatValue();
                        } else if (ratingObject instanceof Double) {
                            ratingf = ((Double) ratingObject).floatValue();
                        } else if (ratingObject == null) {
                            ratingf = 0.0f;
                        }
                        review.setRatingValue(ratingf);

                        if (reviewObject.getString(REVIEW_POSITIVE).equals("null")) {
                            review.setPositiveContent("");
                        } else {
                            review.setPositiveContent(reviewObject.getString(REVIEW_POSITIVE));
                        }

                        if (reviewObject.getString(REVIEW_NEGATIVE).equals("null")) {
                            review.setNegativeContent("");
                        } else {
                            review.setNegativeContent(reviewObject.getString(REVIEW_NEGATIVE));
                        }

                        if (!reviewObject.getString(REVIEW_IMAGE1).equals("null")) {
                            review.getImagePaths().add(reviewObject.getString(REVIEW_IMAGE1));
                        }
                        if (!reviewObject.getString(REVIEW_IMAGE2).equals("null")) {
                            review.getImagePaths().add(reviewObject.getString(REVIEW_IMAGE2));
                        }
                        if (!reviewObject.getString(REVIEW_IMAGE3).equals("null")) {
                            review.getImagePaths().add(reviewObject.getString(REVIEW_IMAGE3));
                        }

                        if (reviewObject.getInt(REVIEW_IMAGE_EXIST) == 1) {
                            review.setPhotoCheck(true);
                        }

                        review.setCreatedDate(reviewObject.getString(REVIEW_CREATED));
                        jsonArray = reviewObject.getJSONArray(WRITER_CONSTITUTIONS);
                        switch (jsonArray.length()) {
                            case 2 :
                                jsonObject = (JSONObject) jsonArray.get(1);
                                review.setConstitution2(jsonObject.getString(WRITER_CONSTITUTION));
                            case 1 :
                                jsonObject = (JSONObject) jsonArray.get(0);
                                review.setConstitution1(jsonObject.getString(WRITER_CONSTITUTION));
                                break;
                            case 0 :
                                break;
                        }
//                        Log.i("review parser", review.toString());
                        reviews.add(review);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.w("Parse Exception", e.getMessage());
        }
        return reviews;
    }

    /**
     * products/:product_id/reviews
     * @param responseObject
     * @param reviews
     * @return ArrayList<Review>
     */
    public static ArrayList<Review> parseReviewList(JSONObject responseObject, ArrayList<Review> reviews) {

//        ArrayList<Review> reviews = new ArrayList<>();
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        try {
            String responseMessage = responseObject.getString(RESPONSE_MESSAGE);
            if (responseMessage.equals(RESPONSE_SUCCESS)) {
                JSONArray dataArray = responseObject.getJSONArray(RESPONSE_DATA);
                if (dataArray != null) {
                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject reviewObject = (JSONObject) dataArray.get(i);
                        Review review = new Review();
                        review.setReviewId(reviewObject.getInt(REVIEW_ID));
                        review.setUserId(reviewObject.getInt(WRITER_ID));
                        review.setName(reviewObject.getString(WRITER_NAME));
                        review.setGender(reviewObject.getString(WRITER_GENDER));
                        review.setBirthYear(reviewObject.getInt(WRITER_BIRTHYEAR));
                        review.setChild(reviewObject.getInt(WRITER_CHILD));
                        review.setRating(reviewObject.getDouble(REVIEW_RATING));
                        review.setPositiveContent(reviewObject.getString(REVIEW_POSITIVE));
                        review.setNegativeContent(reviewObject.getString(REVIEW_NEGATIVE));
                        review.setCreatedDate(reviewObject.getString(REVIEW_CREATED));
                        jsonArray = reviewObject.getJSONArray(WRITER_CONSTITUTIONS);
                        jsonObject = (JSONObject) jsonArray.get(0);
                        review.setConstitution1(jsonObject.getString(WRITER_CONSTITUTION));
                        jsonObject = (JSONObject) jsonArray.get(1);
                        review.setConstitution2(jsonObject.getString(WRITER_CONSTITUTION));
                        reviews.add(review);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.w("Parse Exception", e.getMessage());
        }
        return reviews;
    }

    /**
     * /products/:product_id/reviews/:review_id
     * @param responseObject
     * @param review
     * @return
     */
    public static Review parseReview(JSONObject responseObject, Review review) {

        try {
            String responseMessage = responseObject.getString(RESPONSE_MESSAGE);
            if (responseMessage.equals(RESPONSE_SUCCESS)) {
                JSONArray dataArray = responseObject.getJSONArray(RESPONSE_DATA);
                if (dataArray != null) {
                    JSONObject reviewObject = dataArray.getJSONObject(0);
                    review.setReviewId(reviewObject.getInt(REVIEW_ID));
                    review.setUserId(reviewObject.getInt(WRITER_ID));
                    review.setName(reviewObject.getString(WRITER_NAME));
                    review.setGender(reviewObject.getString(WRITER_GENDER));
                    review.setBirthYear(reviewObject.getInt(WRITER_BIRTHYEAR));
                    review.setChild(reviewObject.getInt(WRITER_CHILD));;

                    Object rating = reviewObject.get(RATING);
                    float ratingf = 0.0f;
//                        Log.i("parseProductList rating", String.valueOf(rating));
                    if (rating instanceof Integer) {
                        ratingf = ((Integer) rating).floatValue();
                    } else if (rating instanceof Double) {
                        ratingf = ((Double) rating).floatValue();
                    } else if (rating == null) {
                        ratingf = 0.0f;
                    }
//                        Log.i("float", String.valueOf(ratingf));
//
                    review.setRating(ratingf);

//                    review.setPositiveContent(reviewObject.getString(REVIEW_POSITIVE));
//                    review.setNegativeContent(reviewObject.getString(REVIEW_NEGATIVE));
                    if (reviewObject.getString(REVIEW_POSITIVE).equals("null")) {
                        review.setPositiveContent("");
                    } else {
                        review.setPositiveContent(reviewObject.getString(REVIEW_POSITIVE));
                    }

                    if (reviewObject.getString(REVIEW_NEGATIVE).equals("null")) {
                        review.setNegativeContent("");
                    } else {
                        review.setNegativeContent(reviewObject.getString(REVIEW_NEGATIVE));
                    }

//                    String imagePath1 = reviewObject.getString(REVIEW_IMAGE1);
//                    String imagePath2 = reviewObject.getString(REVIEW_IMAGE2);
//                    String imagePath3 = reviewObject.getString(REVIEW_IMAGE3);
//                    review.getImagePaths().add(imagePath1);
//                    review.getImagePaths().add(imagePath2);
//                    review.getImagePaths().add(imagePath3);
//                    if (!imagePath1.equals("null") || !imagePath2.equals("null") || !imagePath3.equals("null")) {
//                        review.setPhotoCheck(true);
//                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.w("Parse Exception", e.getMessage());
        }

        return review;
    }

    /**
     * /products/search?wordpart=
     * @param responseObject
     * @return
     */
    public static ArrayList<Word> parseSearchWordPartList(JSONObject responseObject) {

        ArrayList<Word> searchedWords = new ArrayList<>();
        try {
            String responseMessage = responseObject.getString(RESPONSE_MESSAGE);
            if (responseMessage.equals(RESPONSE_SUCCESS)) {
                JSONObject dataObject = responseObject.getJSONObject(RESPONSE_DATA);
                if (dataObject != null) {
                    JSONArray wordsArray = dataObject.getJSONArray(SEARCH_WORD);
                    for (int i = 0; i < wordsArray.length(); i++) {
                        JSONObject wordObject = (JSONObject) wordsArray.get(i);
                        Word word = new Word();
                        word.setProductId(wordObject.getInt(SEARCHED_ID));
                        word.setBrand(wordObject.getString(SEARCHED_BRAND));
                        word.setName(wordObject.getString(SEARHCED_PRODUCT_NAME));
                        searchedWords.add(word);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.w("Parse Exception", e.getMessage());
        }
        return searchedWords;
    }

    /**
     * /products/search?wordpart=
     * @param responseObject
     * @return
     */
    public static ArrayList<String> parseStringSearchWordPartList(JSONObject responseObject) {

        ArrayList<String> searchedWords = new ArrayList<>();
        try {
            String responseMessage = responseObject.getString(RESPONSE_MESSAGE);
            if (responseMessage.equals(RESPONSE_SUCCESS)) {
                JSONObject dataObject = responseObject.getJSONObject(RESPONSE_DATA);
                if (dataObject != null) {
                    JSONArray wordsArray = dataObject.getJSONArray(SEARCH_WORD);
                    for (int i = 0; i < wordsArray.length(); i++) {
                        JSONObject wordObject = (JSONObject) wordsArray.get(i);
                        String str = wordObject.getString(SEARHCED_PRODUCT_NAME);
                        searchedWords.add(str);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.w("Parse Exception", e.getMessage());
        }
        return searchedWords;
    }

    /**
     * /products?keyword=
     * @param responseObject
     * @return
     */
    public static ArrayList<Product> parseSearchKeywordProductList(JSONObject responseObject) {

        ArrayList<Product> products = new ArrayList<>();
        try {
            String responseMessage = responseObject.getString(RESPONSE_MESSAGE);
            if (responseMessage.equals(RESPONSE_SUCCESS)) {
                JSONArray dataObjects = responseObject.getJSONArray(RESPONSE_DATA);
                if (dataObjects != null) {
                    for (int i = 0; i < dataObjects.length(); i++) {
                        JSONObject productObject = (JSONObject) dataObjects.get(i);
                        Product product = new Product();
                        product.setProductId(productObject.getInt(PRODUCT_ID));
                        product.setCategoryId(productObject.getInt(CATEGORY_ID));
                        product.setMaker(productObject.getString(MAKER));
                        product.setBrand(productObject.getString(BRAND));
                        product.setName(productObject.getString(NAME));
                        Object rating = productObject.get(RATING);
                        float ratingf = 0.0f;
//                        Log.i("parseProductList rating", String.valueOf(rating));
                        if (rating instanceof Integer) {
                            ratingf = ((Integer) rating).floatValue();
                        } else if (rating instanceof Double) {
                            ratingf = ((Double) rating).floatValue();
                        } else if (rating == null) {
                            ratingf = 0.0f;
                        }
//                        Log.i("float", String.valueOf(ratingf));
//
                        product.setRatingAvg(ratingf);
//                        Log.i("product", String.valueOf(product.getRatingAvg()));
                        product.setRatingCount(productObject.getInt(RATING_COUNT));
                        product.setImagePath(productObject.getString(IMAGE_PATH));
                        products.add(product);
                        Log.i("parseProductList", product.toStringId());
                    }
                } else {
                    return null;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.w("Parse Exception", e.getMessage());
        }
        return products;
    }

    /**
     *
     * @param responseObject
     * @return
     */
    public static ArrayList<Product> parseSearchFilterProductList(JSONObject responseObject) {

        ArrayList<Product> products = new ArrayList<>();
        try {
            String responseMessage = responseObject.getString(RESPONSE_MESSAGE);
            if (responseMessage.equals(RESPONSE_SUCCESS)) {
                JSONArray dataArray = responseObject.getJSONArray(RESPONSE_DATA);
                if (dataArray != null) {
                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject productObject = (JSONObject) dataArray.get(i);
                        Product product = new Product();
                        product.setProductId(productObject.getInt(SEARCHED_ID));
                        product.setCategoryId(productObject.getInt(SEARCHED_CATEGORY_ID));
                        product.setMaker(productObject.getString(SEARCHED_MAKER));
                        product.setBrand(productObject.getString(SEARCHED_BRAND));
                        product.setName(productObject.getString(SEARHCED_PRODUCT_NAME));
                        product.setType(productObject.getString(SEARCHED_TYPE));

                        Object rating = productObject.get(RATING);
                        float ratingf = 0.0f;
//                        Log.i("parseProductList rating", String.valueOf(rating));
                        if (rating instanceof Integer) {
                            ratingf = ((Integer) rating).floatValue();
                        } else if (rating instanceof Double) {
                            ratingf = ((Double) rating).floatValue();
                        } else if (rating == null) {
                            ratingf = 0.0f;
                        }
//                        Log.i("float", String.valueOf(ratingf));
//
                        product.setRatingAvg(ratingf);
                        product.setImagePath(productObject.getString(SEARCHED_IMAGE));
//                        product.setRatingCount(productObject.getInt(RATING_COUNT));
                        products.add(product);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.w("Parse Exception", e.getMessage());
        }
        return products;
    }

    /**
     * /users/:id/products
     * @param responseObject
     * @return
     */
    public static ArrayList<Product> parseArchiveProductList(JSONObject responseObject) {

        ArrayList<Product> products = new ArrayList<>();
        try {
            String responseMessage = responseObject.getString(RESPONSE_MESSAGE);
            if (responseMessage.equals(RESPONSE_SUCCESS)) {
                JSONArray dataArray = responseObject.getJSONArray(RESPONSE_DATA);
                if (dataArray != null) {
                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject archiveProductObject = dataArray.getJSONObject(i);
                        Product product = new Product();
                        product.setProductId(archiveProductObject.getInt(User.PRODUCT_ID));
                        product.setName(archiveProductObject.getString(PRODUCT_NAME));
                        product.setImagePath(archiveProductObject.getString(IMAGE_PATH));
                        product.setRatingAvg((float) archiveProductObject.getDouble(RATING));
                        products.add(product);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.w("Parse Exception", e.getMessage());
        }
        return products;
    }

    public static ArrayList<Word> parseChemicalKeyword(JSONObject responseObject) {

        ArrayList<Word> words = new ArrayList<>();
        try {
            String responseMessage = responseObject.getString(RESPONSE_MESSAGE);
            if (responseMessage.equals(RESPONSE_SUCCESS)) {
                JSONArray dataArray = responseObject.getJSONArray(RESPONSE_DATA);
                if (dataArray != null) {
                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject chemicalWordObject = dataArray.getJSONObject(i);
                        Word word = new Word();
                        word.setProductId(chemicalWordObject.getInt("id"));
                        word.setNameKO(chemicalWordObject.getString("nameko"));
                        word.setNameEN(chemicalWordObject.getString("nameen"));
                        words.add(word);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.w("Parse Exception", e.getMessage());
        }
        return words;
    }
}



/**
 * JSON Parser Template
 */

//    try {
//        String responseMessage = responseObject.getString(RESPONSE_MESSAGE);
//        if (responseMessage.equals(RESPONSE_SUCCESS)) {
//            JSONArray dataArray = responseObject.getJSONArray(RESPONSE_DATA);
//            if (dataArray != null) {
//
//            }
//        }
//    } catch (JSONException e) {
//        e.printStackTrace();
//        Log.w("Parse Exception", e.getMessage());
//    }