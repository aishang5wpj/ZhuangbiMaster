package com.aishang5wpj.zhuangbimaster.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.aishang5wpj.zhuangbimaster.R;
import com.aishang5wpj.zhuangbimaster.glide.ImageUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wpj on 16/6/13上午10:10.
 */
public class ImageDetailActivity extends BaseActivity {

    public static final String URL = "url";
    @BindView(R.id.imageview)
    protected ImageView mImageView;
    @BindView(R.id.toolbar)
    protected Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//       用 mToolbar.setTitle("")设置无效
        getSupportActionBar().setTitle("");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        String url = getIntent().getStringExtra(URL);
        ImageUtils.getInstance().display(mImageView, url);
    }
}