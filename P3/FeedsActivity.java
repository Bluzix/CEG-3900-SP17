/*
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.firebase.samples.apps.friendlypix;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import junit.framework.Assert;

import java.util.ArrayList;
import java.util.List;

public class FeedsActivity extends AppCompatActivity implements PostsFragment.OnPostSelectedListener {
    private static final String TAG = "FeedsActivity";
    private FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeds);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewPager viewPager = (ViewPager) findViewById(R.id.feeds_view_pager);
        FeedsPagerAdapter adapter = new FeedsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(PostsFragment.newInstance(PostsFragment.TYPE_HOME), "HOME");
        adapter.addFragment(PostsFragment.newInstance(PostsFragment.TYPE_FEED), "FEED");
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.feeds_tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user == null || user.isAnonymous()) {
                    Toast.makeText(FeedsActivity.this, "You must sign-in to post.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent newPostIntent = new Intent(FeedsActivity.this, NewPostActivity.class);
                startActivity(newPostIntent);
            }
        });
    }

    @Override
    public void onPostComment(String postKey) {
        Intent intent = new Intent(this, CommentsActivity.class);
        intent.putExtra(CommentsActivity.POST_KEY_EXTRA, postKey);
        startActivity(intent);
    }

    /**
     * Process Un/Liking a post.
     * <p>
     *     This is the implementation of the onPostLike method located in the OnPostSelectedListener
     *     interface inside of PostFragment.  This method communicates with Firebase to like or
     *     unlike a post that has been stored in Firebase.
     * </p>
     * <p>
     *     PreCondition: this method is called from the FeedActivity on a post being displayed
     *     PostCondition: Firebase's database will reflect the action of Liking or Unliking a
     *     the post.
     * </p>
     * @param postKey Non-null String that is the unique Key for a post inside of Firebase.
     */
    @Override
    public void onPostLike(final String postKey) {
        Assert.assertFalse( "postKey was null or empty", postKey == null || postKey.isEmpty() );

        final String userKey = FirebaseUtil.getCurrentUserId();
        Assert.assertFalse( "Current User Id was null or empty", userKey == null ||
                userKey.isEmpty() );

        final DatabaseReference postLikesRef = FirebaseUtil.getLikesRef();
        Assert.assertNotNull( "Reference to post Likes was null", postLikesRef );

        // We now attach a ValueEventListener to observe if the Current User Likes or unlikes the post
        postLikesRef.child(postKey).child(userKey).addListenerForSingleValueEvent(new ValueEventListener() {

            /**
             * Update posts based on values currently in Firebase.
             * <p>
             *     When the Current User uses the outer function to Like or Unlike a post, we need
             *     to update the post in Firebase to reflect the changes the User wants to perform
             *     on the app.
             * </p>
             * <p>
             *     We use onDataChange( DataSnapshot ) to read the contents of Firebase.
             * </p>
             * <p>
             *     PreCondition: this function is called from a ValueEventListener that is added to
             *     the Firebase database branch that is responsible for storing the Like that the
             *     Current User wants to add to or remove from the selected post.
             *     PostCondition: A Like has been recorded into Firebase, or it has been removed
             *     from Firebase.
             * </p>
             * @param dataSnapshot Non-null DataSnapshot of the Current User's Like or lack of a
             *                     Like for a certain post.
             */
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Assert.assertNotNull( "DataSnapshot was null", dataSnapshot);

                // if dataSnapshot.exists() is true, then the User wants to unlike the post
                if (dataSnapshot.exists()) {
                    // User already liked this post, so we toggle like off.
                    postLikesRef.child(postKey).child(userKey).removeValue();
                }

                // else the User has not Liked the post and wishes to Like the post now
                else {
                    /* ServerValue is a class in com.google.firebase.database and is assumed that it
                     * has an accessible TIMESTAMP value that does not return a null or empty
                     * String.
                     */
                    postLikesRef.child(postKey).child(userKey).setValue(ServerValue.TIMESTAMP);
                }
            }

            /**
             * Ignore Firebase Database Errors
             * <p>
             *     onCancelled is usually triggered as a result of a failure to communicate with the
             *     Firebase Database, or communication was reject due to the set database rules.
             *     However, this function has nothing in its definition; so all Database Errors are
             *     ignored for Un/Liking a post.
             * </p>
             * <p>
             *     PreCondition: this function is called from a ValueEventListener that is added to
             *     the Firebase database branch that is responsible for storing the Like that the
             *     Current User wants to add to or remove from the selected post.
             *     PostCondition: nothing will happen; this function does nothing with the
             *     DatabaseError that it has been given.
             * </p>
             * @param firebaseError A DatabaseError from Firebase, can be non-null since it is not
             *                      acted upon in this function.
             */
            @Override
            public void onCancelled(DatabaseError firebaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_feeds, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            // TODO: Add settings screen.
            return true;
        } else if (id == R.id.action_profile) {
            startActivity(new Intent(this, ProfileActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class FeedsPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public FeedsPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
