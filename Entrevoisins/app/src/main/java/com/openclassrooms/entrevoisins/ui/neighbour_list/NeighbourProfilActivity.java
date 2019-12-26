package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

public class NeighbourProfilActivity extends AppCompatActivity {

    private TextView mUserName,mUserNameBar;
    private ImageView mUserAvatar;
    private ImageButton mBackButton;
    private FloatingActionButton mFavButton;
    private NeighbourApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neighbour_profil);

        mApiService = DI.getNeighbourApiService();

        mFavButton = findViewById(R.id.add_fav_button);
        mBackButton = findViewById(R.id.back_button);

        Intent intent = getIntent();
        if (intent != null) {

            Neighbour neighbour = intent.getParcelableExtra("neighbour");

            mUserName = findViewById(R.id.neighbour_name_profil);
            mUserNameBar = findViewById(R.id.neighbour_name_bar);

            mUserName.setText(neighbour.getName());
            mUserNameBar.setText(neighbour.getName());

            mUserAvatar = findViewById(R.id.app_bar_image);
            Glide.with(mUserAvatar.getContext())
                    .load(neighbour.getAvatarUrl())
                    .into(mUserAvatar);

            if (mApiService.getFavoriteNeighbours().contains(neighbour)) {
                mFavButton.setImageResource(R.drawable.ic_star_white_24dp);
            } else {
                mFavButton.setImageResource(R.drawable.ic_star_border_white_24dp);
            }


            mFavButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   if (mApiService.getFavoriteNeighbours().contains(neighbour)){
                       mFavButton.setImageResource(R.drawable.ic_star_border_white_24dp);
                       mApiService.deleteFavoriteNeighbours(neighbour);
                   }else{
                       mFavButton.setImageResource(R.drawable.ic_star_white_24dp);
                       mApiService.addFavoriteUser(neighbour);
                   }
                }
            });
        }

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
