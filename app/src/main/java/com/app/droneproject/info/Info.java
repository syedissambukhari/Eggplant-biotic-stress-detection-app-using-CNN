package com.app.droneproject.info;

public interface Info {
    String TAG = "mytag";
    String TOKEN = "Authorization";

    int RV_TYPE_PLANT = 0;
    int RV_TYPE_DISEASE = 1;
    int RV_TYPE_CAPTURES = 3;

    String SEARCH_BASE_URL = "https://www.google.com/search?q=";

}
