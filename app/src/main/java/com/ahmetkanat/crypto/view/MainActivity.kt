package com.ahmetkanat.crypto.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahmetkanat.crypto.R
import com.ahmetkanat.crypto.adapter.CryptoAdapter
import com.ahmetkanat.crypto.model.CryptoModel
import com.ahmetkanat.crypto.service.CryptoAPI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), CryptoAdapter.Listener {

    private var BASE_URL = "https://api.nomics.com/v1/"
    private lateinit var compositeDisposable: CompositeDisposable
    private var cryptoModel : ArrayList<CryptoModel>? = null
    private var cryptoAdapter : CryptoAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        compositeDisposable = CompositeDisposable()

        //Recycler View
        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        loadData()
    }


    private fun loadData(){

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(CryptoAPI::class.java)

        compositeDisposable.add(retrofit.getData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::handleResponse))

    }

    private fun handleResponse(cryptoList : List<CryptoModel>){

        cryptoModel = ArrayList(cryptoList)

        cryptoModel?.let {
           cryptoAdapter = CryptoAdapter(it,this@MainActivity)
            recyclerView.adapter = cryptoAdapter
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    override fun onItemClick(cryptoModel: CryptoModel) {
        Toast.makeText(this,"Base : ${cryptoModel.base}",Toast.LENGTH_SHORT).show()
    }
}