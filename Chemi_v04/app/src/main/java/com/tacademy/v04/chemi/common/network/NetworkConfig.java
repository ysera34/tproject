package com.tacademy.v04.chemi.common.network;

import java.io.File;

/**
 * Created by yoon on 2016. 11. 25..
 */

public class NetworkConfig {

//    public static final String URL_HOST = "http://52.78.226.188:3000";
    public static final String URL_HOST = "http://develop.inframincer.org:3000";
    public static final String IMAGE_URL_HOST = "https://s3.ap-northeast-2.amazonaws.com/chemistaticfiles";
    public static final String RESPONSE_MESSAGE = "message";
    public static final String RESPONSE_SUCCESS = "success";
    public static final String RESPONSE_ERROR = "error";
    public static final String RESPONSE_DATA = "data";
    public static final String COUNT = "count";
    public static final String PAGE = "page";
    public static final String PAGE_PREV = "prev";
    public static final String PAGE_NEXT = "next";

    public static final int SOCKET_TIMEOUT_GET_REQ = 5000;
    public static final int SOCKET_TIMEOUT_POST_REQ = 10000;

    public static final class Product {
        public static final String PATH = File.separator + "products";

        public static final class Key {
            public static final String PRODUCTS = "products";
            public static final String PRODUCT_ID = "id";
            public static final String CATEGORY_ID = "categoryid";
            public static final String MAKER = "maker";
            public static final String BRAND = "brand";
            public static final String NAME = "name";
            public static final String TYPE = "type";
            public static final String PURPOSE = "purpose";
            public static final String RELEASED = "released";
            public static final String RATING = "rating";
            public static final String RATING_COUNT = "ratingcount";
            public static final String IMAGE_PATH = "image";
        }
    }

    public static final class Chemical {
        public static final String PATH = File.separator + "chemicals";

        public static final class Key {
            public static final String CHEMICALS = "chemicals";
            public static final String CHEMICAL_ID = "id";
            public static final String NAMEKO = "nameko";
            public static final String NAMEEN = "nameen";
            public static final String ABBR = "abbr";
            public static final String MIX = "purpose";
            public static final String HAZARD = "hazard";
            public static final String KEYWORD = "keyword";
            public static final String EFFECTS = "effects";
            public static final String CONSTITUTION_ID = "constitutionid";
            public static final String CONSTITUTION_NAME = "name";
            public static final String DESCRIPTION = "description";
        }
    }

    public static final class Review {
        public static final String PATH = File.separator + "reviews";

        public static final class Key {
            public static final String REVIEW_ID = "id";
            public static final String WRITER_ID = "userid";
            public static final String WRITER_NAME = "name";
            public static final String WRITER_GENDER = "sex";
            public static final String WRITER_BIRTHYEAR = "birthyear";
            public static final String WRITER_CHILD = "child";
            public static final String REVIEW_RATING = "rating";
            public static final String REVIEW_POSITIVE = "reviewp";
            public static final String REVIEW_NEGATIVE = "reviewn";
            public static final String REVIEW_IMAGE1 = "images1";
            public static final String REVIEW_IMAGE2 = "images2";
            public static final String REVIEW_IMAGE3 = "images3";
            public static final String REVIEW_IMAGE_EXIST = "imageExists";
            public static final String REVIEW_CREATED = "created";
            public static final String WRITER_CONSTITUTIONS = "constitutions";
            public static final String WRITER_CONSTITUTION = "name";
        }
    }

    public static final class Search {
        public static final String SEARCH_TARGET_PRODUCT = File.separator + "products";
        public static final String SEARCH_FILTER_PATH = File.separator + "searchfilters";
        public static final String SEARCH_PATH = File.separator + "search";
        public static final String KEYWORD_PATH = File.separator + "keyword";
        public static final String SEARCH_WORDPART_QUERY = "?wordpart=";
        public static final String SEARCH_KEYWORD_QUERY = "?keyword=";
        public static final String SEARCH_LETTER_QUERY = "?letter=";
        public static final String SEARCH_CATEGORY_QUERY = "?categoryid=";
        public static final String SEARCH_INCHEMICAL_QUERY = "&inchemicals=";
        public static final String SEARCH_EXCHEMICAL_QUERY = "&exchemicals=";

        public static final class Key {
            public static final String SEARCH_WORD = "words";
            public static final String SEARCHED_ID = "id";
            public static final String SEARCHED_BRAND = "brand";
            public static final String SEARCHED_MAKER = "maker";
            public static final String SEARHCED_PRODUCT_NAME = "name";
            public static final String SEARCHED_CATEGORY_ID = "categoryid";
            public static final String SEARCHED_TYPE = "type";
            public static final String SEARCHED_PURPOSE = "purpose";
            public static final String SEARCHED_IMAGE = "image";

        }
    }

    public static final class User {
        public static final String PATH = File.separator + "users";
        public static final String PRODUCT_ID = "productid";
        public static final String PRODUCT_NAME = "productname";
    }
}
