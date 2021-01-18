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
import com.bumptech.glide.Glide
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


    }

   private fun sendMessage(chatMessage: Any){
       chatMessages.add(chatMessage)
       chatAdapter!!.notifyItemInserted(chatMessages.size - 1)
       Handler().postDelayed({recyclerView.scrollToPosition(chatMessages.size - 1)}, 100)
       CloseKeyboard()
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

    fun CloseKeyboard() // 빈 화면을 터치하면 키보드가 내려감, 아이디와 비밀번호를 입력 후 로그인 버튼을 누를 때, 채팅기능에서 메세지를 입력 후 전송 버튼을 누를 때
    {
        var view = this.currentFocus

        if(view != null)
        {
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    /*fun glide(args: Array<String>){
        Glide.with(this)
                .load("file:///C:/Users/PC/Documents/%EB%84%A4%EC%9D%B4%ED%8A%B8%EC%98%A8%20%EB%B0%9B%EC%9D%80%20%ED%8C%8C%EC%9D%BC/%EA%B8%80%EB%9D%BC%EC%9D%B4%EB%93%9C%EB%A1%9C%20%EA%B0%80%EC%A0%B8%EC%98%A4%EA%B8%B0/noti_ico_avata_man.webp")
                .thumbnail(0.1f)
                .into(imageView) // 이미지 뷰를 가져와서 붙인다고 했을때 그러면 어떤 식으로 변하려나? 이미지를 다른 식으로
    }*/





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