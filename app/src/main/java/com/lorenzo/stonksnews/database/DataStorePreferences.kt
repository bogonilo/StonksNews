package com.lorenzo.stonksnews.database

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

val Context.userPreferencesDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "user"
)

val FILTER_NEWS_BY_FAVORITE_SYMBOLS = booleanPreferencesKey("filter_news_by_favorite_symbol")

val USER_REGION_SELECTED = stringPreferencesKey("user_region_selected")
