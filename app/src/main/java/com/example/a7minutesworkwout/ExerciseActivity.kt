package com.example.a7minutesworkwout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import com.example.a7minutesworkwout.databinding.ActivityExerciseBinding
import com.example.a7minutesworkwout.databinding.ActivityMainBinding

class ExerciseActivity : AppCompatActivity() {

    private var binding: ActivityExerciseBinding? = null

    private var restTimer: CountDownTimer? = null
    private var restProgress = 0

    private var exerciseTimer: CountDownTimer? = null
    private var exerciseProgress = 0

    private var exerciseList: ArrayList<ExerciseModel>? = null
    private var currentExerciseposition = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarExercise)

        currentExerciseposition ++

        if(supportActionBar!=null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        exerciseList = Constants.defaultExerciseList()

        binding?.toolbarExercise?.setNavigationOnClickListener{
            onBackPressed()
        }

        setupRestView()
    }

    private fun setRestProgressBar(){
        binding?.progressBar?.progress = restProgress

        restTimer = object: CountDownTimer(10000,1000){
            override fun onTick(p0: Long) {
                restProgress ++
                binding?.progressBar?.progress = 10 - restProgress
                binding?.tvTimer?.text = (10 - restProgress).toString()
            }

            override fun onFinish() {
                binding?.tvTimer?.text = "GO!"
                currentExerciseposition ++
                setupProgressView()
            }

        }.start()
    }

    private fun setExerciseProgressBar(){
        binding?.progressBarExercise?.progress = exerciseProgress

        exerciseTimer = object: CountDownTimer(30000,1000){
            override fun onTick(p0: Long) {
                exerciseProgress ++
                binding?.progressBarExercise?.progress = 30 - exerciseProgress
                binding?.tvTimerExercise?.text = (30 - exerciseProgress).toString()
            }

            override fun onFinish() {
                binding?.tvTimerExercise?.text = "Rest"
                if(currentExerciseposition<exerciseList?.size!! -1){
                    setupRestView()
                }else{
                    TODO()
                }
            }

        }.start()
    }

    private fun setupRestView(){
        binding?.flProgressBar?.visibility = View.VISIBLE
        binding?.tvTitle?.visibility = View.VISIBLE
        binding?.ivImage?.visibility = View.INVISIBLE
        binding?.tvExercise?.visibility = View.INVISIBLE

        binding?.flProgressBarExercise?.visibility = View.INVISIBLE
        if(restTimer != null){
            restTimer?.cancel()
            restProgress = 0
        }
        setRestProgressBar()
    }

    private fun setupProgressView(){
        binding?.flProgressBar?.visibility = View.INVISIBLE
        binding?.tvTitle?.visibility = View.INVISIBLE
        binding?.ivImage?.visibility = View.VISIBLE
        binding?.tvExercise?.visibility = View.VISIBLE

        binding?.flProgressBarExercise?.visibility = View.VISIBLE

        if(exerciseTimer != null){
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }

        binding?.ivImage?.setImageResource(exerciseList!![currentExerciseposition].getImage())
        binding?.tvExercise?.text = exerciseList!![currentExerciseposition].getName()
        setExerciseProgressBar()
    }

    override fun onDestroy() {
        super.onDestroy()
        if(restTimer != null){
            restTimer?.cancel()
            restProgress = 0
        }

        if(exerciseTimer != null){
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }
        binding = null
    }


}