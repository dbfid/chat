package com.example.chat

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chat.Adapter.*
import com.example.chat.Extensions.afterTextChanged
import com.example.chat.Extensions.toHHmma
import com.example.chat.api.NetworkService
import com.example.chat.data.Movie
import com.example.chat.data.chating
import com.example.chat.data.profilefemale
import com.example.chat.data.profilemale
import com.example.chat.model.ChatModel
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private var isMeRandom = false

    private var chatMessages: ArrayList<Any> = ArrayList()
    private var chatAdapter: ChatAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        chatAdapter = ChatAdapter(this@MainActivity, chatMessages)
        recyclerView.adapter = chatAdapter

        tvMessage.afterTextChanged {
            btnSend.visibility = if (tvMessage.text.trim().isNotEmpty()) View.VISIBLE else View.INVISIBLE
        }

        btnSend.setOnClickListener{
            isMeRandom = !isMeRandom
            sendMessage(ChatModel(tvMessage.text.trim().toString(), Calendar.getInstance().toHHmma(), isMeRandom, true))
            tvMessage.text = null
        }
        loadDummyData()
        CloseKeyboard()

    }

   private fun sendMessage(chatMessage: Any){
       chatMessages.add(chatMessage)
       chatAdapter!!.notifyItemInserted(chatMessages.size - 1)
       Handler().postDelayed({recyclerView.scrollToPosition(chatMessages.size - 1)}, 100)
   }

    private fun loadDummyData(){
        val yesterday: Calendar = Calendar.getInstance()
        /*yesterday.add(Calendar.DATE, -1)*/
        /*sendMessage(yesterday)
        sendMessage(ChatModel("Hello", "07:34 pm", false, false))
        sendMessage(ChatModel("Hi", "07:35 pm", true, false))
        sendMessage(ChatModel("Are you coming for dinner?", "07:35pm", false,))
        sendMessage(ChatModel("Yep, I am on my way","07:40pm", true, false))*/
        sendMessage(Calendar.getInstance())
    }

    fun CloseKeyboard()
    {
        var view = this.currentFocus

        if(view != null)
        {
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }





/*    private val TAG = javaClass.simpleName

    private lateinit var searchBtn: Button
    private lateinit var searchMovie: EditText
    *//*private lateinit var progressBar: ProgressBar*//*
    private lateinit var recyclerView: RecyclerView

    private lateinit var adapter: MovieAdapter
    private lateinit var call: Call<Movie>

    private lateinit var adapter2: profilemaleAdapter
    private lateinit var call2: Call<profilemale>

    private lateinit var adapter3: profilefemaleAdapter
    private lateinit var call3: Call<profilefemale>

    private lateinit var adapter4: ChatingAdapter
    private lateinit var call4: chating

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initAdapter()
    }

    private fun initView() {
        searchBtn = findViewById(R.id.button3)
        searchMovie = findViewById(R.id.tvMessage)
        recyclerView = findViewById(R.id.recyclerView)

        onClickListener()
    }

    private fun onClickListener() {
        searchBtn.setOnClickListener {searchMovie()}
    }

    private fun initAdapter() {
        adapter = MovieAdapter()
        recyclerView.adapter = adapter

        adapter.setItemClickListener { movie ->
            Intent(Intent.ACTION_VIEW, Uri.parse(movie.link)).takeIf {
                it.resolveActivity(packageManager) != null
            }?.run(this::startActivity)
        }
    }

    private fun searchMovie() {
        val movie = searchMovie.text.toString().trim()

        if (movie.isEmpty()) {
            onMessage("영화제목을 입력해 주세요")
        } else {
            onMessage("잠시만 기달려 주세요")
            requestMovie(movie)

        }
    }

    fun onMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun requestMovie(query: String) {
        adapter.clear()

        val select = NetworkService.naverApi
        call = select.getSearchMovie(query)

        call.enqueue(object : Callback<Movie> {
            override fun onFailure(call: Call<Movie>, t: Throwable) {
                onMessage("통신에 실패함")
            }

            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        it.items.let {
                            adapter.setItems(it)
                        }
                    } ?: onMessage("실패")
                }
            }
        })
    }
    */
}