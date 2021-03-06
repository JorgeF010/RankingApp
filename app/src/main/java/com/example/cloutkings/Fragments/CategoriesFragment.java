package com.example.cloutkings.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cloutkings.Categories.Category;
import com.example.cloutkings.Categories.CategoriesAdapter;
import com.example.cloutkings.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;

public class CategoriesFragment extends Fragment {

    // Current view of the fragment
    private View view;
    private Fragment categories;
    private FragmentManager catManager;
    private FragmentTransaction catTransaction;
    // RecyclerView - Categories
    private RecyclerView mRecyclerView;
    private CategoriesAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    // HashMap
    private ArrayList<Category> listOfCategories;
    // Ad
    private AdView adView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        assert this.catManager != null;
        this.catManager = getFragmentManager();
        this.catTransaction = catManager.beginTransaction();
        this.view = inflater.inflate(R.layout.fragment_categories, container, false);
        createCategories();
        buildRecyclerView();
        setAds();
        return view;
    }

    /**
     * List of Categories - ArrayList<Category>
     */
    public void createCategories() {
        this.listOfCategories = new ArrayList<>();
        Category YouTube = new Category(R.drawable.ic_youtube, "YouTube");
        Category Instagram = new Category(R.drawable.ic_instagram, "Instagram");
        Category TikTok = new Category(R.drawable.ic_tiktok, "TikTok");
        Category Twitch = new Category(R.drawable.ic_twitch, "Twitch");
        Category Entrepreneurs = new Category(R.drawable.ic_entrepreneur, "Entrepreneurs");
        Category rappers = new Category(R.drawable.ic_music, "Music");
        Category soccerPlayers = new Category(R.drawable.ic_soccer, "Soccer Players");
        Category footballPlayers = new Category(R.drawable.ic_american_football, "Football Players");
        Category basketballPlayers = new Category(R.drawable.ic_basketball, "Basketball Players");
        Category baseballPlayers = new Category(R.drawable.ic_baseball, "Baseball Players");
        Category musicProducers = new Category(R.drawable.ic_recording_studio, "Music Producers");
        this.listOfCategories.add(YouTube);
        this.listOfCategories.add(Instagram);
        this.listOfCategories.add(TikTok);
        this.listOfCategories.add(Twitch);
        this.listOfCategories.add(Entrepreneurs);
        this.listOfCategories.add(rappers);
        this.listOfCategories.add(musicProducers);
        this.listOfCategories.add(soccerPlayers);
        this.listOfCategories.add(footballPlayers);
        this.listOfCategories.add(basketballPlayers);
        this.listOfCategories.add(baseballPlayers);
    }

    /**
     * Builds the RecyclerView and the upVote and downVote ImageButtons
     */
    public void buildRecyclerView() {
        // Creating the RecyclerView object
        this.mRecyclerView = view.findViewById(R.id.recyclerViewCategories);
        // Better performance + doesn't need to change in size
        this.mRecyclerView.setHasFixedSize(true);
        this.mLayoutManager = new LinearLayoutManager(super.getContext());
        this.mAdapter = new CategoriesAdapter(this.listOfCategories);
        this.mRecyclerView.setLayoutManager(this.mLayoutManager);
        this.mRecyclerView.setAdapter(this.mAdapter);
        // Adds a space in between categories
        this.mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        this.mAdapter.setOnItemClickListener(new CategoriesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Fragment categoryFragment = null;
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = manager.beginTransaction();
                // may need to access the first instance not create a new one because their values will be different
                // might not have to do that too because If I create a table i can update that table and just check the table
                categoryFragment = newInstance(position);
                fragmentTransaction.replace(R.id.listOfCategories, categoryFragment).addToBackStack(null).commit();
            }
        });


    }

    public static ProfilesFragment newInstance(int id) {
        ProfilesFragment categoryFragment = new ProfilesFragment();
        Bundle args = new Bundle();
        args.putInt("ID", id);
        categoryFragment.setArguments(args);
        return categoryFragment;
    }

    /**
     * Setups up Google Ads on the CategoriesFragment page
     */
    public void setAds() {
        MobileAds.initialize(this.getContext(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });
        this.adView = view.findViewById(R.id.adViewCategories);
        AdRequest adRequest = new AdRequest.Builder().build();
        this.adView.loadAd(adRequest);
    }
}
