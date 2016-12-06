package com.tacademy.v04.chemi.model;

import android.content.Context;

import com.tacademy.v04.chemi.R;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by yoon on 2016. 11. 23..
 */

public class ContentMainStorage {

    private static ContentMainStorage sContentMainStorage;
    private Context mAppContext;

    private ArrayList<Content> mContents;

    private ContentMainStorage(Context context) {
        mAppContext = context;
        mContents = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            Content content = new Content();
            switch (i%6) {
                case 0 :
                    content.setContentType(1);
                    content.setTitle("모기약 가정용 바닥재 등 일상용품,\n아이 두뇌 발달 저해");
                    content.setDescription("");
                    content.setContentImageId(R.drawable.main_content_sample01);
                    content.setContentTypeImageId(R.drawable.ic_main_banner_type1);
                    break;
                case 1 :
                    content.setContentType(2);
                    content.setTitle("뽀송뽀송 우리 아이 피부를 지켜줄\n유아용 물티슈 10선");
                    content.setDescription("좋은 물티슈 고르는 TIP!");
                    content.setContentImageId(R.drawable.main_content_sample02);
                    content.setContentTypeImageId(R.drawable.ic_main_banner_type2);
                    break;
                case 2 :
                    content.setContentType(3);
                    content.setTitle("아토피 피부를 위한 샤워법");
                    content.setDescription("베이킹 소다와 전분가루 입욕법");
                    content.setContentImageId(R.drawable.main_content_sample03);
                    content.setContentTypeImageId(R.drawable.ic_main_banner_type3);
                    break;
                case 3 :
                    content.setContentType(1);
                    content.setTitle("환경부, 안전기준 위반 생활화학제품\n11개 퇴출 발표");
                    content.setDescription("");
                    content.setContentImageId(R.drawable.main_content_sample04);
                    content.setContentTypeImageId(R.drawable.ic_main_banner_type1);
                    break;
                case 4 :
                    content.setContentType(2);
                    content.setTitle("30대 주부님들이 가장 많이 찾아본\n제품 TOP5");
                    content.setDescription("12월 넷째");
                    content.setContentImageId(R.drawable.main_content_sample05);
                    content.setContentTypeImageId(R.drawable.ic_main_banner_type2);
                    break;
                case 5 :
                    content.setContentType(3);
                    content.setTitle("천연 세제 만들기");
                    content.setDescription("케미녀가 해봤습니다!");
                    content.setContentImageId(R.drawable.main_content_sample06);
                    content.setContentTypeImageId(R.drawable.ic_main_banner_type3);
                    break;
            }
            mContents.add(content);
        }
    }

    public static ContentMainStorage get(Context context) {
        if (sContentMainStorage == null) {
            sContentMainStorage = new ContentMainStorage(context.getApplicationContext());
        }
        return sContentMainStorage;
    }

    public ArrayList<Content> getContents() {
        return mContents;
    }

    public Content getContent(UUID id) {
        for (Content content : mContents) {
            if (content.getId().equals(id)) {
                return content;
            }
        }
        return null;
    }
}
