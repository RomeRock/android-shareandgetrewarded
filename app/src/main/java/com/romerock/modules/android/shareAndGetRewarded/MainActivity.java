package com.romerock.modules.android.shareAndGetRewarded;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.img_logo_romerock)
    ImageView img_logo_romerock;
    @BindView(R.id.follow_twitter)
    ImageView followTwitter;
    @BindView(R.id.follow_gitHub)
    ImageView followGitHub;
    @BindView(R.id.follow_facebook)
    ImageView followFacebook;
    @BindView(R.id.tittle)
    TextView rateUsDetect;
    @BindView(R.id.btn_detect)
    Button btnDetect;
    @BindView(R.id.content_main)
    RelativeLayout contentMain;
    @BindView(R.id.relContent)
    RelativeLayout relContent;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.txtActive)
    TextView txtActive;
    @BindView(R.id.imgSharedFacebook)
    ImageView imgSharedFacebook;
    @BindView(R.id.imgSharedTwitter)
    ImageView imgSharedTwitter;
    @BindView(R.id.txtRestore)
    TextView txtRestore;
    private AlertDialog alertDialog;
    private SharedPreferences sharedPref;
    private String rateUs;
    private Typeface font;
    private SharedPreferences.Editor ed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, 0, 0);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        sharedPref = getSharedPreferences(getString(R.string.preferences_name), MODE_PRIVATE);
        ed = sharedPref.edit();
        WebView view = new WebView(this);
        view.setVerticalScrollBarEnabled(false);
        view.setBackgroundColor(getResources().getColor(R.color.drawable));
        ((RelativeLayout) findViewById(R.id.relContent)).addView(view);
        view.loadData(getString(R.string.thank_you), "text/html; charset=utf-8", "utf-8");
        font = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Thin.ttf");
        txtActive.setTypeface(font);

    }

    public void popUp() {
        AlertDialog.Builder builder;
        LayoutInflater inflater;
        builder = new AlertDialog.Builder(this);
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        builder.setCancelable(true);
        View view = inflater.inflate(R.layout.pop_up_share, null);
        view.findViewById(R.id.popUpNoShare).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();

            }
        });

        view.findViewById(R.id.popUpShareFacebook).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onShared(0);
            }

        });
        view.findViewById(R.id.popUpShareTwitter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onShared(1);
            }
        });


        TextView txtTittleShare = (TextView) view.findViewById(R.id.txtTittleShare);
        TextView txtContentShare = (TextView) view.findViewById(R.id.txtContentShare);
        font = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");
        txtTittleShare.setTypeface(font);
        txtContentShare.setTypeface(font);
        builder.setView(view);
        builder.create();
        alertDialog = builder.show();
    }

    private void onShared(int type) {
        alertDialog.dismiss();
        switch (type) {
            case 0:
            default:
                ed.putBoolean(getString(R.string.preferences_shared_facebook), true);
                break;
            case 1:
                ed.putBoolean(getString(R.string.preferences_shared_twitter), true);
                break;
        }
        ed.putBoolean(getString(R.string.preferences_full), true);
        ed.commit();
        recreate();

    }


    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }


    @OnClick({R.id.img_logo_romerock, R.id.follow_twitter, R.id.follow_gitHub, R.id.follow_facebook, R.id.btn_detect, R.id.txtRestore})
    public void onClick(View view) {
        String url = "";
        switch (view.getId()) {
            case R.id.img_logo_romerock:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.romerock_page))));
                break;
            case R.id.follow_facebook:
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.follow_us_facebook_profile)));
                    startActivity(intent);
                } catch (Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.follow_us_facebook))));
                }
                break;
            case R.id.follow_gitHub:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.follow_us_git_hub))));
                break;
            case R.id.follow_twitter:
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.follow_us_twitter_profile)));
                    startActivity(intent);
                } catch (Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.follow_us_twitter))));
                }
                break;
            case R.id.btn_detect:
                popUp();
                break;
            case R.id.txtRestore:
                if (sharedPref.getBoolean(getString(R.string.preferences_full), false)) {
                    SharedPreferences.Editor ed = sharedPref.edit();
                    ed.putBoolean(getString(R.string.preferences_full), false);
                    ed.putBoolean(getString(R.string.preferences_shared_facebook), false);
                    ed.putBoolean(getString(R.string.preferences_shared_twitter), false);
                    ed.commit();
                    recreate();
                }
                break;

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sharedPref.getBoolean(getString(R.string.preferences_full), false)) {
            txtActive.setText(getString(R.string.active_full_on));
            txtRestore.setTextColor(getResources().getColor(R.color.colorPrimary));
        }
        else{
            txtActive.setText(getString(R.string.active_full_off));
            txtRestore.setTextColor(getResources().getColor(R.color.restore_off));
        }

        if (sharedPref.getBoolean(getString(R.string.preferences_shared_facebook), false))
            imgSharedFacebook.setVisibility(View.VISIBLE);
        else
            imgSharedFacebook.setVisibility(View.GONE);

        if (sharedPref.getBoolean(getString(R.string.preferences_shared_twitter), false))
            imgSharedTwitter.setVisibility(View.VISIBLE);
        else
            imgSharedTwitter.setVisibility(View.GONE);
    }
}
