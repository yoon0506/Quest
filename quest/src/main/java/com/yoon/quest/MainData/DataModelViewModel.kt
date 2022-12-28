package com.yoon.quest.MainData

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DataModelViewModel(application: Application) : ViewModel() {
    private val repository = Repository(application)
//    private val items: MutableLiveData<List<DataModel>>
//        get() = repository.getDataList()
    private val items: LiveData<List<DataModel>>
        get() = repository.getDataList()

//    fun getData(): MutableLiveData<List<DataModel>> {
//        return items
//    }
//    fun getData(): LiveData<List<DataModel>> {
//        return repository.getDataList()
//    }
    fun getData(): LiveData<List<DataModel>> = items

    fun insert(data: DataModel) {
        repository.insert(data)
    }

    fun delete(data: DataModel) {
        repository.delete(data)
    }

    fun update(id: Int, data: DataModel) {
        repository.update(id, data)
    }

    fun getIdData(id: Int): DataModel {
        return repository.getIdData(id)
    }

    fun showData(){
//        items.postValue(repository.getDataList().value)
    }

    fun showAllData() {
        repository.getAll()
    }

    fun showDataByColor(color: String){
        repository.getDataByColor(color)
    }
}