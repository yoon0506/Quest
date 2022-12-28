package com.yoon.quest.MainData

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


class Repository(application: Application) {
    private val dataModelDAO: DataModelDAO
    private var dataModelList: LiveData<List<DataModel>>
//    private var mutableDataModelList: MutableLiveData<List<DataModel>> = MutableLiveData()

    init {
        val db = AppDatabase.getInstance(application)!!
        dataModelDAO = db.dataModelDao()
        dataModelList = db.dataModelDao().all
//        mutableDataModelList.postValue(dataModelDAO.all.value)
//        mutableDataModelList.value = dataModelList.value
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

//    fun getDataList(): MutableLiveData<List<DataModel>>{
//        return mutableDataModelList
//    }

    fun getDataList(): LiveData<List<DataModel>>{
        Log.e("checkCheck","data 현황 3: ${dataModelList.value?.size}")
        return dataModelList
    }

    fun getAll() {
        dataModelList = dataModelDAO.all
//        mutableDataModelList.postValue(dataModelDAO.all.value)
        Log.e("checkCheck","data 현황 1: ${dataModelDAO.all.value?.size}")
//        mutableDataModelList.postValue(dataModelList.value)
//        mutableDataModelList.value = dataModelList.value
    }

    fun getIdData(id: Int): DataModel {
        return dataModelDAO.getIdData(id)
    }

    fun getDataByColor(color: String) {
//        mutableDataModelList.value = dataModelDAO.getDataPickedColor(color).value
        dataModelList = dataModelDAO.getDataPickedColor(color)
//        mutableDataModelList.value =dataModelList.value
        Log.e("checkCheck","data 현황 2 : ${dataModelList.value?.size}")
    }
}