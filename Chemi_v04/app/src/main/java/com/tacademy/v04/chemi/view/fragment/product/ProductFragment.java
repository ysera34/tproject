package com.tacademy.v04.chemi.view.fragment.product;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.tacademy.v04.chemi.R;
import com.tacademy.v04.chemi.model.Product;
import com.tacademy.v04.chemi.model.ProductStorage;
import com.tacademy.v04.chemi.view.activity.MainActivity;

import java.util.ArrayList;
import java.util.UUID;

import static com.tacademy.v04.chemi.common.Common.REQUEST_NAVIGATION_FAQ;

/**
 * Created by yoon on 2016. 11. 14..
 */

public class ProductFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = ProductFragment.class.getSimpleName();

    private static final String ARG_PRODUCT_ID = "product_id";

    private ProductStorage mProductStorage;
    private ArrayList<Product> mProducts;
    private Product mProduct;

    private TextView mProductTitleTextView;

    private TabLayout mProductDetailTabLayout;
    private ViewPager mProductDetailViewPager;
    private ArrayList<Fragment> mProductDetailListFragments;
    private ArrayList<String> mProductDetailFragmentsTitles;

    private BottomSheetDialog mProductShareBottomSheetDialog;
    private ImageButton mProductShareImageButton1;
    private ImageButton mProductShareImageButton2;
    private ImageButton mProductShareImageButton3;

    public static ProductFragment newInstance(UUID productId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_PRODUCT_ID, productId);

        ProductFragment fragment = new ProductFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ProductFragment() {
        mProductStorage = ProductStorage.get(getActivity());
        mProducts = mProductStorage.getProducts();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mProduct = new Product();
//        UUID productId = (UUID) getActivity().getIntent()
//                .getSerializableExtra(ProductActivity.EXTRA_PRODUCT_ID);
        UUID productId = (UUID) getArguments().getSerializable(ARG_PRODUCT_ID);
        mProduct = ProductStorage.get(getActivity()).getProduct(productId);

        setHasOptionsMenu(true);

        mProductDetailListFragments = new ArrayList<>();
        mProductDetailFragmentsTitles = new ArrayList<>();

//        Log.i(TAG, "onCreate " + mProduct.toString());

        addProductDetailFragment(ChemicalListFragment.newInstance(mProduct.getId()),
                getString(R.string.product_detail_chemical_list_name));
        addProductDetailFragment(ReviewListFragment.newInstance(mProduct.getId()), getString(R.string.product_detail_review_list_name));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_product, container, false);
//        mProductTitleTextView = (TextView) view.findViewById(R.id.product_name);
//        mProductTitleTextView.setText(mProduct.getName());

        mProductDetailTabLayout = (TabLayout) view.findViewById(R.id.product_detail_tabLayout);
        mProductDetailViewPager = (ViewPager) view.findViewById(R.id.product_detail_view_pager);

        FragmentManager fm = getChildFragmentManager();
        mProductDetailViewPager.setAdapter(new FragmentPagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                return mProductDetailListFragments.get(position);
            }

            @Override
            public int getCount() {
                return mProductDetailListFragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
//                return super.getPageTitle(position);
                return mProductDetailFragmentsTitles.get(position);
            }
        });

        mProductDetailTabLayout.setupWithViewPager(mProductDetailViewPager);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void addProductDetailFragment(Fragment fragment, String title) {
        mProductDetailListFragments.add(fragment);
        mProductDetailFragmentsTitles.add(title);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bottom_sheet_product_share_button1 :
                Toast.makeText(getActivity(), "카카오톡으로 공유합니다. 업데이트 예정입니다.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bottom_sheet_product_share_button2 :
                Toast.makeText(getActivity(), "카카오 스토리로 공유합니다. 업데이트 예정입니다.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bottom_sheet_product_share_button3 :
                Toast.makeText(getActivity(), "네이버 블로그로 공유합니다. 업데이트 예정입니다.", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.menu_product_detail_toolbar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_faq) {
            Toast.makeText(getActivity(), "자주 묻는 질문을 삼가하세요.", Toast.LENGTH_SHORT).show();
            Intent intent = MainActivity.newIntent(getActivity(), REQUEST_NAVIGATION_FAQ);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_archive) {
            Toast.makeText(getActivity(), "보관함에 추가되었습니다.", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.action_share) {
            Toast.makeText(getActivity(), "공유하겠습니다.", Toast.LENGTH_SHORT).show();

            mProductShareBottomSheetDialog = new BottomSheetDialog(getActivity());
            View mShareBottomSheetView = getLayoutInflater(getArguments())
                    .inflate(R.layout.bottom_sheet_product_share, null);
            mProductShareBottomSheetDialog.setContentView(mShareBottomSheetView);

            mProductShareImageButton1 = (ImageButton) mShareBottomSheetView
                    .findViewById(R.id.bottom_sheet_product_share_button1);
            mProductShareImageButton2 = (ImageButton) mShareBottomSheetView
                    .findViewById(R.id.bottom_sheet_product_share_button2);
            mProductShareImageButton3 = (ImageButton) mShareBottomSheetView
                    .findViewById(R.id.bottom_sheet_product_share_button3);

            mProductShareImageButton1.setOnClickListener(this);
            mProductShareImageButton2.setOnClickListener(this);
            mProductShareImageButton3.setOnClickListener(this);
            mProductShareBottomSheetDialog.show();
            return true;
        }

//        if (id == R.id.action_search) {
//            startActivity(SearchActivity.newIntent(getApplicationContext()));
//            return true;
//        } else if (id == R.id.action_home) {
//            startActivity(MainActivity.newIntent(getApplicationContext()));
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

}
