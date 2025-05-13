package com.example.chat.di


import android.content.Context
import androidx.room.Room
import com.example.chat.data.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext app: Context): ContactDatabase =
        Room.databaseBuilder(app, ContactDatabase::class.java, "contact_db").build()

    @Provides
    fun provideDao(db: ContactDatabase): ContactDao = db.contactDao()

    @Provides
    fun provideRepository(dao: ContactDao): ContactRepository = ContactRepositoryImpl(dao)
}

