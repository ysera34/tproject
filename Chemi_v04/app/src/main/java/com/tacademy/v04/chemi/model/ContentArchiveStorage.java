package com.tacademy.v04.chemi.model;

import android.content.Context;

import com.tacademy.v04.chemi.R;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by yoon on 2016. 11. 23..
 */

public class ContentArchiveStorage {

    private static ContentArchiveStorage sContentArchiveStorage;
    private Context mAppContext;

    private ArrayList<Content> mContents;

    private ContentArchiveStorage(Context context) {
        mAppContext = context;
        mContents = new ArrayList<>();

        for (int i = 0; i < 9; i++) {
            Content content = new Content();
            switch (i%9) {
                case 0 :
                    content.setContentType(1);
                    content.setContentNumber(11);
                    content.setTitle("모기약 가정용 바닥재 등 일상용품,\n아이 두뇌 발달 저해");
                    content.setDescription("");
                    content.setContentImageId(R.drawable.content_image_1101);
                    content.setContentTypeImageId(R.drawable.ic_main_banner_type1);
                    break;
                case 1 :
                    content.setContentType(2);
                    content.setContentNumber(21);
                    content.setTitle("뽀송뽀송 우리 아이 피부를 지켜줄\n유아용 물티슈 10선");
                    content.setDescription("좋은 물티슈 고르는 TIP!");
                    content.setContentImageId(R.drawable.content_image_2101);
                    content.setContentTypeImageId(R.drawable.ic_main_banner_type2);
                    break;
                case 2 :
                    content.setContentType(3);
                    content.setContentNumber(31);
                    content.setTitle("아토피 피부를 위한 샤워법");
                    content.setDescription("베이킹 소다와 전분가루 입욕법");
                    content.setContentImageId(R.drawable.content_image_3101);
                    content.setContentTypeImageId(R.drawable.ic_main_banner_type3);
                    break;
                case 3 :
                    content.setContentType(1);
                    content.setContentNumber(12);
                    content.setTitle("환경부, 안전기준 위반 생활화학제품\n11개 퇴출 발표");
                    content.setDescription("");
                    content.setContentImageId(R.drawable.content_image_1201);
                    content.setContentTypeImageId(R.drawable.ic_main_banner_type1);
                    break;
                case 4 :
                    content.setContentType(2);
                    content.setContentNumber(22);
                    content.setTitle("30대 주부님들이 가장 많이 찾아본\n제품 TOP5");
                    content.setDescription("12월 넷째");
                    content.setContentImageId(R.drawable.content_image_2201);
                    content.setContentTypeImageId(R.drawable.ic_main_banner_type2);
                    break;
                case 5 :
                    content.setContentType(3);
                    content.setContentNumber(32);
                    content.setTitle("천연 세제 만들기");
                    content.setDescription("케미녀가 해봤습니다!");
                    content.setContentImageId(R.drawable.content_image_3201);
                    content.setContentTypeImageId(R.drawable.ic_main_banner_type3);
                    break;
                case 6 :
                    content.setContentType(1);
                    content.setContentNumber(13);
                    content.setTitle("물티슈보다 독성물질 더 나온\n방향제, 환경부는 \"퇴출 안돼\"");
                    content.setDescription("호흡기 직접 영향 주는데도... 스프레이 제품 방치하는 정");
                    content.setContentImageId(R.drawable.content_image_1301);
                    content.setContentTypeImageId(R.drawable.ic_main_banner_type1);
                    break;
                case 7 :
                    content.setContentType(2);
                    content.setContentNumber(23);
                    content.setTitle("우리 아이, 촉촉한 피부를 위한\n샴푸&바디워시 10선");
                    content.setDescription("좋은 샴푸와 바디워시를 고르는 TIP!");
                    content.setContentImageId(R.drawable.content_image_2301);
                    content.setContentTypeImageId(R.drawable.ic_main_banner_type2);
                    break;
                case 8 :
                    content.setContentType(3);
                    content.setContentNumber(33);
                    content.setTitle("물티슈의 안전한 사용법");
                    content.setDescription("물티슈 사용의 기본적 원칙");
                    content.setContentImageId(R.drawable.content_image_3301);
                    content.setContentTypeImageId(R.drawable.ic_main_banner_type3);
                    break;
            }
            mContents.add(content);
        }
    }

    public static ContentArchiveStorage get(Context context) {
        if (sContentArchiveStorage == null) {
            sContentArchiveStorage = new ContentArchiveStorage(context.getApplicationContext());
        }
        return sContentArchiveStorage;
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

    public ArrayList<Content> getTypeContents(int contentType) {

        ArrayList<Content> contents = new ArrayList<>();

        for (Content content : mContents) {
            if (content.getContentType()==contentType) {
                contents.add(content);
            }
        }
        return contents;
    }
}
