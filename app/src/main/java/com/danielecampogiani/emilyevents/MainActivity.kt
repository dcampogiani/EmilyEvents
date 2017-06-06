package com.danielecampogiani.emilyevents

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.danielecampogiani.network.facebook.FacebookAPI
import com.danielecampogiani.network.facebook.FacebookResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject lateinit var facebookAPI: FacebookAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EmilyEventsApp.appComponent.inject(this)
        setContentView(R.layout.activity_main)

        facebookAPI.getEvents("44.4992192", "11.2616451", "1000", "popularity")
                .enqueue(object : Callback<com.danielecampogiani.network.facebook.FacebookResult> {
                    override fun onResponse(call: Call<FacebookResult>?, response: Response<FacebookResult>?) {

                        response?.let {
                            if (response.isSuccessful) {
                                Toast.makeText(this@MainActivity, response.body().toString(), Toast.LENGTH_LONG).show()
                            } else {
                                Toast.makeText(this@MainActivity, response.errorBody()?.string(), Toast.LENGTH_LONG).show()
                            }
                        }
                    }

                    override fun onFailure(call: Call<FacebookResult>?, t: Throwable?) {
                        Toast.makeText(this@MainActivity, t.toString(), Toast.LENGTH_LONG).show()
                    }
                })
    }
}
