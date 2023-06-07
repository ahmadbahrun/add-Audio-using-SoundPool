package com.example.appvideoyt

import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.appvideoyt.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var soundPool: SoundPool
    private val MAX_STREAM = 1
    private var loaded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val audioManager = getSystemService(AUDIO_SERVICE) as AudioManager
        val actualVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC).toFloat()
        val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC).toFloat()
        val volume = actualVolume / maxVolume

        val audioAttribute = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        val builder = SoundPool.Builder()
        builder.setAudioAttributes(audioAttribute).setMaxStreams(MAX_STREAM)
        soundPool = builder.build()

        soundPool.setOnLoadCompleteListener{_,_,_, ->
            loaded = true
        }

        val soundGun = soundPool.load(this,R.raw.gun, 1)
        val soundDestroy = soundPool.load(this,R.raw.destroy, 1)

        binding.btnGun.setOnClickListener{
            if (loaded){
                soundPool.play(soundGun, volume, 1F,0,1, 0F)
            }
        }
        binding.btnDestroy.setOnClickListener{
            if (loaded){
                soundPool.play(soundDestroy, volume, 1F,0, 1,0F)
            }
        }
    }
}