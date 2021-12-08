package com.yash1307.reporter

import android.graphics.Typeface
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.reporter.R

class MainActivity : AppCompatActivity() {

    lateinit var weatherET: EditText
    lateinit var checkWeatherBtn: Button
    lateinit var tempText: TextView
    lateinit var tempMinText: TextView
    lateinit var tempMaxText: TextView
    lateinit var feelsLikeText: TextView
    lateinit var pressureText: TextView
    lateinit var humidityText: TextView

    var temp: Double = 0.0
    var tempMin: Double = 0.0
    var tempMax: Double = 0.0
    var feelsLike: Double = 0.0
    var pressure: Double = 0.0
    var humidity: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        weatherET = findViewById(R.id.weatherET)
        checkWeatherBtn = findViewById(R.id.checkWeatherBtn)
        tempText = findViewById(R.id.showTempTxt)
        tempMinText = findViewById(R.id.showTempMinTxt)
        tempMaxText = findViewById(R.id.showTempMaxTxt)
        feelsLikeText = findViewById(R.id.feels_like)
        pressureText = findViewById(R.id.pressureTxt)
        humidityText = findViewById(R.id.humidityTxt)

        lateinit var getCityName: String

        val request = Volley.newRequestQueue(this)

        checkWeatherBtn.setOnClickListener {
            getCityName = weatherET.text.toString()
            when (weatherET.text.isEmpty()) {
                true -> weatherET.error = "Please Enter City Name"

                false -> {
                    val URL = "https://api.openweathermap.org/data/2.5/weather?q=" + getCityName +
                            "&appid="+ API_KEY.API_KEY

                    val newRequest = JsonObjectRequest(Request.Method.GET,
                        URL,
                        null, { response ->
                            val getJSONObject = response.getJSONObject("main")
                            temp = getJSONObject.getDouble("temp")
                            convertTemp(temp)

                            tempMin = getJSONObject.getDouble("temp_min")
                            convertTempMin(tempMin)

                            tempMax = getJSONObject.getDouble("temp_max")
                            convertTempMax(tempMax)

                            feelsLike = getJSONObject.getDouble("feels_like")
                            convertFeelsLike(feelsLike)

                            pressure = getJSONObject.getDouble("pressure")
                            convertPressure(pressure)

                            humidity = getJSONObject.getDouble("humidity")
                            convertHumidity(humidity)

                        },
                        { error ->
                            Toast.makeText(this, "An error occurred", Toast.LENGTH_SHORT).show()
                        }
                    )
                    request.add(newRequest)
                }
            }
        }
    }

    val convertTemp = { newTemp: Double ->
        tempText.setTypeface(null, Typeface.BOLD)
        val getTemp = newTemp - 273.15
        tempText.text = getTemp.toString().substring(0, 4) + " °C"
    }

    val convertTempMin = { newTempMin: Double ->
        tempMinText.setTypeface(null, Typeface.BOLD)
        val getTempMin = newTempMin - 273.15
        tempMinText.text = getTempMin.toString().substring(0, 4) + " °C"
    }

    val convertTempMax = { newTempMax: Double ->
        tempMaxText.setTypeface(null, Typeface.BOLD)
        val getTempMax = newTempMax - 273.15
        tempMaxText.text = getTempMax.toString().substring(0, 4) + " °C"
    }

    val convertFeelsLike = { newFeelsLike: Double ->
        feelsLikeText.setTypeface(null, Typeface.BOLD)
        val getFeelsLike = newFeelsLike - 273.15
        feelsLikeText.text = getFeelsLike.toString().substring(0, 4) + " °C"
    }

    val convertPressure = { newPressure: Double ->
        pressureText.setTypeface(null, Typeface.BOLD)
        pressureText.text = newPressure.toString()+ " hPa"
    }

    val convertHumidity = { newHumidity: Double ->
        humidityText.setTypeface(null, Typeface.BOLD)
        humidityText.text = newHumidity.toString() + " %"
    }
}