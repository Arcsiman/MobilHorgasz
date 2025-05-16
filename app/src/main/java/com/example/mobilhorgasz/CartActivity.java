package com.example.mobilhorgasz;
import android.app.AlarmManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import androidx.appcompat.widget.Toolbar;
import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    private static final String LOG_TAG = CartActivity.class.getName();
    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private RecyclerView mRecyclerView;
    private RecyclerView mOffersRecyclerView;
    private ArrayList<Product> mItemList;
    private ArrayList<Product> mOffersList;
    private CartAdapter mAdapter;
    private CartAdapter mOffersAdapter;

    private FirebaseFirestore mFirestore;
    private CollectionReference mItems;

    private NotificationHandler mNotificationHandler;
    private AlarmManager mAlarmManager;
    private int gridNumber = 1;
    private boolean viewRow = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        if (user != null) {
            Log.d(LOG_TAG, "Authenticated user!");
        } else {
            Log.d(LOG_TAG, "Unauthenticated user! Redirecting...");
            finish();
        }
        db = FirebaseFirestore.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        mItems = mFirestore.collection("Items");


        TextView welcomeTextView = findViewById(R.id.cartTitle);
        Animation fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in_text);
        welcomeTextView.startAnimation(fadeInAnimation);


        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, gridNumber));
        mItemList = new ArrayList<>();
        mAdapter = new CartAdapter(this, mItemList);
        mRecyclerView.setAdapter(mAdapter);




        mOffersRecyclerView = findViewById(R.id.offersRecyclerView);
        mOffersRecyclerView.setLayoutManager(new GridLayoutManager(this, gridNumber));
        mOffersList = new ArrayList<>();
        mOffersAdapter = new CartAdapter(this, mOffersList);
        mOffersRecyclerView.setAdapter(mOffersAdapter);



        queryData2();
        queryOffers();

    }



    private void queryOffers() {
        mOffersList.clear();

        mItems.whereEqualTo("ratedInfo", 5)
                .orderBy("ratedInfo", Query.Direction.DESCENDING)
                .limit(10)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        Product item = document.toObject(Product.class);
                        item.setId(document.getId());
                        mOffersList.add(item);
                    }
                    mOffersAdapter.notifyDataSetChanged();
                });
    }
    private void queryData2() {
        mItemList.clear();

        mItems.whereGreaterThanOrEqualTo("cartedCount", 1)
                .orderBy("cartedCount", Query.Direction.DESCENDING)
                .limit(10)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        Product item = document.toObject(Product.class);
                        item.setId(document.getId());
                        mItemList.add(item);
                    }
                    mAdapter.notifyDataSetChanged();
                });
    }

}




