package com.yoon.quest.MainData

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Database 객체 -> abstract class , RoomDatabase 상속
 * Database annotation -> Entity 클래스와 버전을 명시
 **/
@Database(entities = [DataModel::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dataModelDao(): DataModelDAO

    /**
     * 이 객체가 제공하는 Data Access Object
     * AppDatabase 가 생성되고 TodoDAO 를 통해서 조작한다.
     **/
    companion object {
        private var instance: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase? {
            if (instance == null) {
                synchronized(AppDatabase::class)
                {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "tempDB"
                    ).allowMainThreadQueries().build()
                }
            }
            return instance
        }
    }
}