/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mkulesh.onpc.config;

import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.mkulesh.onpc.R;

/**
 * A {@link PreferenceActivity} which implements and proxies the necessary calls
 * to be used with AppCompat.
 * <p>
 * This technique can be used with an {@link android.app.Activity} class, not just
 * {@link PreferenceActivity}.
 */
public abstract class AppCompatPreferenceActivity extends PreferenceActivity
{
    private AppCompatDelegate mDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        final com.mkulesh.onpc.config.Configuration configuration = new com.mkulesh.onpc.config.Configuration(this);
        setTheme(configuration.getTheme(com.mkulesh.onpc.config.Configuration.ThemeType.SETTINGS_THEME));
        getDelegate().installViewFactory();
        getDelegate().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        setupActionBar();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        getDelegate().onPostCreate(savedInstanceState);
    }

    public ActionBar getSupportActionBar()
    {
        return getDelegate().getSupportActionBar();
    }

    public void setSupportActionBar(@Nullable Toolbar toolbar)
    {
        getDelegate().setSupportActionBar(toolbar);
    }

    @NonNull
    @Override
    public MenuInflater getMenuInflater()
    {
        return getDelegate().getMenuInflater();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID)
    {
        getDelegate().setContentView(layoutResID);
    }

    @Override
    public void setContentView(View view)
    {
        getDelegate().setContentView(view);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params)
    {
        getDelegate().setContentView(view, params);
    }

    @Override
    public void addContentView(View view, ViewGroup.LayoutParams params)
    {
        getDelegate().addContentView(view, params);
    }

    @Override
    protected void onPostResume()
    {
        super.onPostResume();
        getDelegate().onPostResume();
    }

    @Override
    protected void onTitleChanged(CharSequence title, int color)
    {
        super.onTitleChanged(title, color);
        getDelegate().setTitle(title);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        getDelegate().onConfigurationChanged(newConfig);
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        getDelegate().onStop();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        getDelegate().onDestroy();
    }

    public void invalidateOptionsMenu()
    {
        getDelegate().invalidateOptionsMenu();
    }

    private AppCompatDelegate getDelegate()
    {
        if (mDelegate == null)
        {
            mDelegate = AppCompatDelegate.create(this, null);
        }
        return mDelegate;
    }

    protected void setupActionBar()
    {
        ViewGroup rootView = findViewById(R.id.action_bar_root); //id from appcompat
        if (rootView != null)
        {
            View view = getLayoutInflater().inflate(R.layout.settings_toolbar, rootView, false);
            rootView.addView(view, 0);

            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
        }

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
        case android.R.id.home:
            onBackPressed();
            return true;
        }
        return (super.onOptionsItemSelected(item));
    }
}
