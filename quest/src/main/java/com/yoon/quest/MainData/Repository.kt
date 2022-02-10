package com.yoon.quest.MainData

import android.app.Application
import androidx.lifecycle.LiveData

class Repository(application: Application) {
    private val dataModelDAO: DataModelDAO
    private var dataModelList: LiveData<List<DataModel>>

    init {
        var db = AppDatabase.getInstance(application)
        dataModelDAO = db!!.dataModelDao()
        dataModelList = db.dataModelDao().all
    }

    fun insert(data: DataModel) {
        dataModelDAO.insert(data)
    }

    fun delete(data: DataModel) {
        dataModelDAO.delete(data)
    }

    fun update(id: Int, data: DataModel){
        dataModelDAO.dataAllUpdate(id, data.title, data.content, data.color)
    }

    fun getDataList(): LiveData<List<DataModel>>{
        return dataModelList
    }

    fun getAll() {
        dataModelList = dataModelDAO.all
    }

    fun getIdData(id: Int): DataModel {
        return dataModelDAO.getIdData(id)
    }

    fun getDataByColor(color: String){
        dataModelList = dataModelDAO.getDataPickedColor(color)
    }
}