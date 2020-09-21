package com.ozan.myapplication.activity

import android.os.Bundle
import android.os.Handler
import android.view.MotionEvent
import android.view.View
import android.view.View.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.ozan.myapplication.R
import com.ozan.myapplication.model.StoryModel
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import jp.shts.android.storiesprogressview.StoriesProgressView
import jp.shts.android.storiesprogressview.StoriesProgressView.StoriesListener


class MainActivity : AppCompatActivity(), StoriesListener {


    private lateinit var storiesProgressView: StoriesProgressView
    private lateinit var image : ImageView
    private lateinit var viewReverse : View
    private lateinit var viewSkip    : View
    private lateinit var username    : TextView

    var counter = 0
    var pressTime = 0L
    var photoLimit = 5000L

    var storyList      = ArrayList<StoryModel>()
    val storyTotalList = ArrayList<StoryModel>()

    var listNumber = 0
    var listName = ArrayList<String>()

    private var animFadeOut: Animation? = null
    private var animFadeIn: Animation? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        image           = findViewById(R.id.image)
        viewReverse     = findViewById(R.id.view_reverse)
        viewSkip        = findViewById(R.id.view_skip)
        username        = findViewById(R.id.story_user)
        storiesProgressView = findViewById(R.id.stories)


        viewReverse.setOnClickListener {
            storiesProgressView.reverse() }
        viewReverse.setOnTouchListener(onTouchListener)

        viewSkip.setOnClickListener {
            storiesProgressView.skip() }
        viewSkip.setOnTouchListener(onTouchListener)

        animFadeOut = AnimationUtils.loadAnimation(this, R.anim.fadeout)
        animFadeIn  = AnimationUtils.loadAnimation(this, R.anim.fadein)


        fillValues()
        callNextUser(false)

        storiesProgressView.setStoriesCount(storyList.size)
        storiesProgressView.setStoriesListener(this)
        storiesProgressView.setStoryDuration(5000L)

       firstStoryInit()

        storiesProgressView.startStories()

    }

    private fun firstStoryInit() {

        if (storyList.size > 0) {


            if ((storyList[0].imageUrl).isNotEmpty()) {

                Picasso.get().load(storyList[0].imageUrl).into(image, object : Callback{
                    override fun onSuccess() {
                        storiesProgressView.startStories()
                    }

                    override fun onError(e: Exception?) {

                    }

                })

            } else {

                Picasso.get().load(storyList[0].imageUrl).into(image, object : Callback{
                    override fun onSuccess() {
                        storiesProgressView.startStories()
                    }

                    override fun onError(e: Exception?) {

                    }

                })

                image.visibility = VISIBLE
            }

            username.text = storyList[0].storyId
        }
    }

    private fun callNextUser(increase : Boolean) {

        if (increase) {
            listNumber++
        }

        if (listNumber == listName.size){
            return
        }

        storyList.clear()

        val name = listName[listNumber]

        for (stories in storyTotalList){

            if (stories.storyId == name){
                storyList.add(stories)
            }

        }

        storiesProgressView = findViewById(R.id.stories)
        storiesProgressView.setStoriesCount(storyList.size)
        storiesProgressView.setStoryDuration(5000L)

        viewReverse.setOnClickListener {
            storiesProgressView.reverse() }
        viewReverse.setOnTouchListener(onTouchListener)

        viewSkip.setOnClickListener {
            storiesProgressView.skip() }
        viewSkip.setOnTouchListener(onTouchListener)


        animFadeIn?.reset()
        image.clearAnimation()
        image.startAnimation(animFadeIn)

        firstStoryInit()

    }
    private fun callPreUser() {

        if (listNumber > 0) listNumber--

        if (listNumber < 0){
            return
        }

        storyList.clear()


        val name = listName[listNumber]

        for (stories in storyTotalList){

            if (stories.storyId == name){
                storyList.add(stories)
            }

        }

        storiesProgressView = findViewById(R.id.stories)
        storiesProgressView.setStoriesCount(storyList.size)
        storiesProgressView.setStoryDuration(5000L)

        viewReverse.setOnClickListener {
            storiesProgressView.reverse() }
        viewReverse.setOnTouchListener(onTouchListener)

        viewSkip.setOnClickListener {
            storiesProgressView.skip() }
        viewSkip.setOnTouchListener(onTouchListener)

        animFadeIn?.reset()
        image.clearAnimation()
        image.startAnimation(animFadeIn)

        firstStoryInit()

    }

    private fun fillValues() {

        listName.clear()
        listNumber = 0
        storyList.clear()
        storyTotalList.clear()

        val storyModel : StoryModel = StoryModel("https://www.w3schools.com/w3css/img_lights.jpg","", "Ozan Orfa")
        storyTotalList.add(storyModel)

        val storyModel2 : StoryModel = StoryModel("https://futurestud.io/images/books/picasso.png","", "Ozan Orfa")
        storyTotalList.add(storyModel2)

        val storyModel3 : StoryModel = StoryModel("https://picsum.photos/id/237/200/300","", "Ozan Orfa")
        storyTotalList.add(storyModel3)

        val storyModel4 : StoryModel = StoryModel("https://picsum.photos/seed/picsum/200/300","", "Panda")
        storyTotalList.add(storyModel4)

        val storyModel7 : StoryModel = StoryModel("","https://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4", "Panda")
        storyTotalList.add(storyModel7)

        val storyModel5 : StoryModel = StoryModel("https://picsum.photos/200/300/?blur","", "Panda")
        storyTotalList.add(storyModel5)

        val storyModel6 : StoryModel = StoryModel("https://picsum.photos/200/300.jpg","", "Vedat")
        storyTotalList.add(storyModel6)

        for (storymodelFor in storyTotalList){
            if (!listName.contains(storymodelFor.storyId)){
                listName.add(storymodelFor.storyId)
            }
        }

    }

    override fun onComplete() {

        counter = 0

        animFadeOut?.reset()
        image.clearAnimation()
        image.startAnimation(animFadeOut)

        callNextUser(true)
    }

    override fun onPrev() {

        if ((counter - 1) < 0){
            counter = 0

            animFadeOut?.reset()
            image.clearAnimation()
            image.startAnimation(animFadeOut)

            callPreUser()
        }
        else{
            counter--

            if ((storyList[counter].imageUrl).isNotEmpty()) {

                Picasso.get().load(storyList[counter].imageUrl).into(image, object : Callback{
                    override fun onSuccess() {

                    }

                    override fun onError(e: Exception?) {

                    }

                })

                storiesProgressView.setStoryDuration(5000L)
            } else {

                Picasso.get().load(storyList[counter].videoUrl).into(image, object : Callback{
                    override fun onSuccess() {

                    }

                    override fun onError(e: Exception?) {

                    }

                })

            }

            username.text = storyList[counter].storyId

        }

    }

    override fun onNext() {
        counter++

        if ((storyList[counter].imageUrl).isNotEmpty()) {

            Picasso.get().load(storyList[counter].imageUrl).into(image, object : Callback{
                override fun onSuccess() {

                }

                override fun onError(e: Exception?) {

                }

            })

            storiesProgressView.setStoryDuration(5000L)
        } else {

            Picasso.get().load(storyList[counter].videoUrl).into(image, object : Callback{
                override fun onSuccess() {

                }

                override fun onError(e: Exception?) {

                }

            })

        }

        username.text = storyList[counter].storyId



    }

    override fun onDestroy() {

        storiesProgressView.destroy()
        super.onDestroy()
        
    }

    override fun onResume() {

        storiesProgressView.resume()
        super.onResume()

    }

    private val onTouchListener = OnTouchListener { v, event ->
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                pressTime = System.currentTimeMillis()
                Handler().post { if (storiesProgressView != null) storiesProgressView.pause() }
                //storiesProgressView.pause()
                storiesProgressView.visibility = GONE
                return@OnTouchListener false
            }
            MotionEvent.ACTION_UP -> {
                val now = System.currentTimeMillis()
                Handler().post { if (storiesProgressView != null) storiesProgressView.resume() }
                storiesProgressView.visibility = VISIBLE
                return@OnTouchListener photoLimit < now - pressTime
            }
        }
        false
    }


}