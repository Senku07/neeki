package com.example.nekki.ui.theme


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize

import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color.rgb
import android.location.Location
import android.location.LocationRequest
import android.net.Uri
import android.util.Log
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandVertically
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Date
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.nekki.Comfortaa
import com.example.nekki.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import android.net.ConnectivityManager
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.rounded.Build
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.style.TextAlign
import androidx.core.content.getSystemService
import coil.compose.AsyncImage
import com.google.android.gms.common.api.Response
import com.google.gson.internal.GsonBuildConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.Locale
import kotlin.coroutines.coroutineContext


val SkyBlue = Color(33, 150, 243)

val fontColorList = listOf(
    Color.White,
    Color(93, 137, 179, 255),
            SkyBlue,
    Color(85, 239, 196, 255),
    Color(129, 236, 236,255),
    Color(116, 185, 255),
    Color(162, 155, 254),
    Color(223, 230, 233),
    Color(0, 184, 148),
    Color(0, 206, 201),
    Color (9, 132, 227),
    Color   (108, 92, 231),
    Color   (178, 190, 195),
    Color   (255, 234, 167),
    Color   (250, 177, 160),
    Color   (255, 118, 117),
    Color   (253, 121, 168),
    Color   (99, 110, 114),
    Color   (253, 203, 110),
    Color   (225, 112, 85),
    Color   (214, 48, 49),
    Color   (232, 67, 147),
    Color   (45, 52, 54),
    Color   (26, 188, 156),
    Color   (46, 204, 113),
    Color   (52, 152, 219),
    Color   (155, 89, 182),
    Color    (52, 73, 94),
    Color    (22, 160, 133),
    Color    (39, 174, 96),
    Color    (41, 128, 185),
    Color    (142, 68, 173),
    Color    (44, 62, 80),
    Color    (241, 196, 15),
    Color    (230, 126, 34),
    Color   (231, 76, 60),
    Color(236, 240, 241),
    Color   (149, 165, 166),
    Color   (243, 156, 18),
    Color   (211, 84, 0),
    Color   (192, 57, 43),
    Color   (189, 195, 199),
    Color.Black,
    Color.Gray,
    Color.DarkGray,
    Color.LightGray,
    Color.Red,
    Color.Green,
    Color.Blue,
    Color.Cyan,
    Color.Magenta,
    Color.Yellow,
    Color(255, 165, 0),  // Orange
    Color(128, 0, 128),  // Purple
    Color(0, 128, 128),
    Color   (127, 140, 141),
)


@SuppressLint("CoroutineCreationDuringComposition", "SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun Diary(latitude: Double,longitude: Double) {
    val date = Date()
    var showEmoji by remember {
        mutableStateOf(true)
    }
    val format = SimpleDateFormat("dd/MM/yyyy")
    var formated = format.format(date)
    val focusKeyboard = LocalFocusManager.current
    var titleText by remember {
        mutableStateOf("")
    }
    var contentText by remember {
        mutableStateOf("")
    }
    var emojiId by remember {
        mutableStateOf(0)
    }


    var latitudeDiary by remember {
        mutableStateOf(0.0)
    }
    var longitudeDiary by remember {
        mutableStateOf(0.0)
    }

    var imageUriList by remember {
        mutableStateOf<List<Uri>?>(null)
    }

    val context = LocalContext.current

    latitudeDiary = latitude
    longitudeDiary = longitude

    val emojiList = listOf<Painter>(
        painterResource(id = R.drawable.neutral),
        painterResource(id = R.drawable.good),
        painterResource(id = R.drawable.verygood),
        painterResource(id = R.drawable.laugh),
        painterResource(id = R.drawable.excited),
        painterResource(id = R.drawable.sick),
        painterResource(id = R.drawable.confuse),
        painterResource(id = R.drawable.worried),
        painterResource(id = R.drawable.sad),
        painterResource(id = R.drawable.bad),
        painterResource(id = R.drawable.verybad),
        painterResource(id = R.drawable.frustrated),
        painterResource(id = R.drawable.extrem_angry),
    )


    val weatherIconMappings: Map<String, Painter> = mapOf(
        "01d" to painterResource(id = R.drawable.clear_sky),
        "01n" to painterResource(id = R.drawable.moon),
        "02d" to painterResource(id = R.drawable.cloud_sun),
        "02n" to painterResource(id = R.drawable.cloud_moon),
        "03d" to painterResource(id = R.drawable.few_cloud),
        "03n" to painterResource(id = R.drawable.few_cloud),
        "04d" to painterResource(id = R.drawable.clouds_broken),
        "04n" to painterResource(id = R.drawable.clouds_broken),
        "09d" to painterResource(id = R.drawable.cloud_sun_rain),
        "09n" to painterResource(id = R.drawable.cloud_moon_rain),
        "10d" to painterResource(id = R.drawable.rain),
        "10n" to painterResource(id = R.drawable.rain),
        "11d" to painterResource(id = R.drawable.thunderstorm_scattered),
        "11n" to painterResource(id = R.drawable.night_alt_thunderstorm),
        "13d" to painterResource(id = R.drawable.snow),
        "13n" to painterResource(id = R.drawable.snow),
        "50d" to painterResource(id = R.drawable.mist),
        "50n" to painterResource(id = R.drawable.mist)
    )

    var attachmentClicked by remember {
        mutableStateOf(false)
    }

    var fontcolorBoolean by remember {
        mutableStateOf(false)
    }


    var fontColorId by remember {
        mutableStateOf(0)
    }

    var selectedFontColor by remember {
        mutableStateOf<Color>(fontColorList.get(0))
    }

    var accentColorId by remember {
        mutableStateOf(1)
    }

    var selectedAccentColor by remember {
        mutableStateOf<Color>(fontColorList.get(0))
    }

    for((index,color) in fontColorList.withIndex()){
        if(index == accentColorId){
            selectedAccentColor = color
        }
    }

    for((index,color) in fontColorList.withIndex()){
        if(index == fontColorId){
            selectedFontColor = color
        }
    }



    AnimatedVisibility(
        visible = showEmoji,
        enter = scaleIn() + expandVertically(expandFrom = Alignment.CenterVertically),
        exit = scaleOut() + shrinkVertically(shrinkTowards = Alignment.CenterVertically)
    ) {
        emojiModal(
            isOkay = showEmoji,
            onEmojiClick = { clickedIndex -> emojiId = clickedIndex;showEmoji = false },
            function = { showEmoji = false },
            emojiList,
            selectedFontColor,
            selectedAccentColor
        )
    }



        fontcolorModal(
        isOkay = fontcolorBoolean ,
        onFontClick = { clickedIndex -> fontColorId = clickedIndex},
        dismissFun = { fontcolorBoolean = false },
            color = selectedFontColor,
            onAccentClick = {clickedInex -> accentColorId = clickedInex },
            accentColor = selectedAccentColor

        )



    var checkInternet by remember {
        mutableStateOf(true)
    }

    var weatherDiloag by remember {
        mutableStateOf(false)
    }

    var weatherData by remember {
        mutableStateOf<Root?>(null)
    }


    LaunchedEffect(Unit) {
        while (true) {
            val isConnected = checkcInternetStatus(context)
            checkInternet = isConnected
            delay(5000)
        }
    }

    var permissionGranted by remember {
        mutableStateOf(true)
    }

    var showOfflineLoc by remember {
        mutableStateOf(false)
    }
    var showImage by remember {
        mutableStateOf(false)
    }

    if (checkInternet && (latitudeDiary != 0.0) && (longitudeDiary != 0.0)) {
        rememberCoroutineScope().launch {
            weatherData = Weather(latitudeDiary, longitudeDiary)
        }
    }

    if (weatherDiloag) {
        weatherData?.let { ShowWeather(weatherDiloag, dismissFun = { weatherDiloag = false }, it,color = selectedFontColor, accentColor = selectedAccentColor) }
    }

    if(showImage){
        imageUriList?.let { showImageModal(isOkay = showImage, dismissFun = { showImage = false }, accentColor = selectedAccentColor , imageUriList = it) }
    }


    Box(modifier = Modifier.padding(10.dp)) {
        Image(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .clip(RoundedCornerShape(15.dp)),
            painter = painterResource(id = R.drawable.night),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Column(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(2.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(text = formated, fontFamily = Comfortaa, fontSize = 24.sp, color = selectedFontColor)
            OutlinedTextField(
                value = titleText,
                singleLine = true,
                onValueChange = { titleText = it },
                textStyle = TextStyle(
                    fontFamily = Comfortaa,
                    fontSize = 20.sp,
                    color = selectedFontColor
                ),
                label = {
                    Text(
                        "Title",
                        color = selectedFontColor,
                        fontFamily = Comfortaa,
                        fontSize = 18.sp
                    )
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onDone = { focusKeyboard?.clearFocus() }),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 5.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = selectedAccentColor,
                    unfocusedBorderColor = selectedFontColor
                )
            )

            OutlinedTextField(
                value = contentText,
                onValueChange = { contentText = it },
                textStyle = TextStyle(
                    fontFamily = Comfortaa,
                    fontSize = 20.sp,
                    color = selectedFontColor
                ),
                label = {
                    Text(
                        "About Day...",
                        color = selectedFontColor,
                        fontFamily = Comfortaa,
                        fontSize = 18.sp
                    )
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Default),
                keyboardActions = KeyboardActions(onDone = { focusKeyboard?.clearFocus() }),
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.9f)
//                    .verticalScroll(state = rememberScrollState(),enabled = true)
                    .padding(horizontal = 20.dp, vertical = 5.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = selectedAccentColor,
                    unfocusedBorderColor = selectedFontColor
                )
            )


            Row(modifier = Modifier
                .horizontalScroll(
                    rememberScrollState()
                )) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painterResource(id = R.drawable.save),
                        contentDescription = null,
                        tint = selectedFontColor
                    )
                }
                IconButton(onClick = { showEmoji = !showEmoji }) {
                    showChoosenEmoji(emojiId = emojiId, emojiList = emojiList, selectedFontColor)
                }
                if (!checkInternet) {
                    IconButton(onClick = { showOfflineLoc = true }) {
                        Icon(
                            painterResource(id = R.drawable.cloud_off),
                            contentDescription = null,
                            tint = selectedFontColor
                        )
                    }
                } else if (weatherData != null) {
                    IconButton(onClick = { weatherDiloag = true }) {
                        val weatherId = weatherData?.weather?.get(0)?.icon
                        val weatherIcon = weatherIconMappings[weatherId]

                        if (weatherIcon != null) {
                            Icon(
                                weatherIcon,
                                contentDescription = null,
                                tint = selectedFontColor
                            )
                        }
                    }

                } else {
                    weatherData = weatherPermission(
                        context = context,
                        showSnack = { permissionGranted = false },
                        weatherMap = weatherIconMappings,
                        color = selectedFontColor
                    )
                }


//                IconButton(onClick = {attachmentClicked = true}) {
//                    Icon(
//                        painterResource(id = R.drawable.attach),
//                        contentDescription = null,
//                        tint = selectedFontColor
//                    )
//                }

                if (checkInternet) {
                    requestPermission(
                        context = context, latlang = { location ->
                            latitudeDiary = location.latitude;longitudeDiary = location.longitude;
                            OpenGoogleMaps(
                                latitude = latitudeDiary,
                                longitude = longitudeDiary,
                                context = context
                            );
                            checkcInternetStatus(context)
                        },
                        showSnack = {
                            permissionGranted = false
                            Log.i(
                                "Permission",
                                "Permission not granted, permissionGranted set to false $permissionGranted"
                            )
                        },
                        selectedFontColor
                    )
                } else {
                    IconButton(onClick = { showOfflineLoc = true }) {
                        Icon(
                            painterResource(id = R.drawable.location_off),
                            contentDescription = null,
                            tint = selectedFontColor
                        )
                    }
                }

                IconButton(onClick = { fontcolorBoolean = true }) {
                    Icon(
                        painterResource(id = R.drawable.color_lens),
                        contentDescription = null,
                        tint = selectedFontColor
                    )
                }

                    attachMediaIntent(assignUri = { a -> imageUriList = a },selectedFontColor)


                IconButton(onClick = { showImage = true }) {
                    Icon(Icons.Outlined.List, contentDescription = null, tint = selectedFontColor)
                }

                IconButton(onClick = { showImage = true }) {
                    Icon(painterResource(id = R.drawable.attach), contentDescription = null, tint = selectedFontColor)
                }

            }

               


        }

        if (!checkInternet) {
            offlineSnackBar(context)
        }

        if (showOfflineLoc) {
            offlineSnackBar(context)

            rememberCoroutineScope().launch {
                delay(6000)
                showOfflineLoc = false
            }
        }

        if (!permissionGranted) {
            grantPermissionSnackBar(context = context)
            rememberCoroutineScope().launch {
                delay(6000)
                permissionGranted = true
            }
        }
    }
}


@Composable
fun emojiModal(isOkay: Boolean,onEmojiClick: (Int) -> Unit ,function: () -> Unit,emojiList: List<Painter>,fontColor: Color, accentColor: Color){

    if (isOkay) {
        Dialog(onDismissRequest = {function()}, properties = DialogProperties(dismissOnBackPress = true,dismissOnClickOutside = true),content = {
            Card(shape = RoundedCornerShape(10.dp), modifier = Modifier
                .width(300.dp)
                .height(400.dp)
                .padding(8.dp)
                .graphicsLayer { transformOrigin = TransformOrigin.Center },
                colors = cardColors(accentColor)
                ) {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(5.dp)
                    .verticalScroll(state = rememberScrollState(), enabled = true), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                    for ((index,i) in emojiList.withIndex()) {
                        IconButton(onClick = { onEmojiClick(index) },modifier = Modifier
                            .fillMaxWidth(1f)
                            .padding(vertical = 4.dp)
                        ) {
                            Icon(i, contentDescription = null, tint = fontColor,modifier = Modifier.size(48.dp) )
                        }
                    }
                }
            }
        })
    }
}

@Composable
fun showChoosenEmoji(emojiId: Int,emojiList: List<Painter>,color: Color){
    for ((index,i) in emojiList.withIndex()){
        if(emojiId == index){
            Icon(i, contentDescription = null,tint = color)
        }
    }

}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun requestPermission(context: Context, latlang: (Location) -> Unit,showSnack:() -> Unit,color: Color) {
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted: Boolean ->
            if (isGranted) {
                Log.i("Permission", "Granted")
            } else {
                Log.i("Permission", "Denied")
                showSnack()
            }
        }
    )

   IconButton(onClick = {
        when {
            ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                var fusedLocation: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
                fusedLocation.getCurrentLocation(LocationRequest.QUALITY_HIGH_ACCURACY,null).addOnSuccessListener { location: Location ->
                    run {
                        latlang(
                            location
                        )
                    }
                }
            }
            ActivityCompat.shouldShowRequestPermissionRationale(
                context as Activity,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) -> {

                requestPermissionLauncher.launch(
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                )

            }
            else -> {
                requestPermissionLauncher.launch(
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                )
                showSnack()
                Log.e("Clicked","Didn't reached here")
            }
        }
    }) {
        Icon(painterResource(id = R.drawable.location_on), contentDescription = null, tint = color)
    }
}


//@SuppressLint("QueryPermissionsNeeded")
fun OpenGoogleMaps(latitude: Double,longitude: Double,context: Context){

    val markerLabel = "Your Location while Writing"
    val gmmIntentUri = Uri.parse("geo:$latitude,$longitude?q=$latitude,$longitude($markerLabel)")
    val mapIntent = Intent(Intent.ACTION_VIEW,gmmIntentUri)
    mapIntent.setPackage("com.google.android.apps.maps")

    mapIntent.resolveActivity(context.packageManager)
        try {
            context.startActivity(mapIntent)
            Log.i("Map","Map Open")
        } catch (e: Exception) {
            Log.e("OpenGoogleMaps", "Error opening Google Maps: ${e.message}")
        }
}


fun checkcInternetStatus(context: Context):Boolean{
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapability = cm.activeNetwork ?: return false
        return true
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun offlineSnackBar(context: Context) {
    Column {
        var snackbarVisibleState by remember { mutableStateOf(true) }
        if (snackbarVisibleState) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                Snackbar(
                    dismissAction = {
                        IconButton(onClick = { snackbarVisibleState = false }) {
                            Icon(Icons.Rounded.Clear, contentDescription = null)
                        }
                        rememberCoroutineScope().launch {
                            delay(5000)
                            snackbarVisibleState = false
                        }
                    },
                    action = {
                        TextButton(onClick = {
                            val intent = Intent().apply {
                                action = android.provider.Settings.ACTION_WIRELESS_SETTINGS
                            }
                            context.startActivity(intent)
                        }) {
                            Text(text = "Settings")
                        }
                    },
                    modifier = Modifier.padding(8.dp)
                ) { Text(text = "No Internet Connection! You'll miss Weather and Location") }
            }
        }
    }
}


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun grantPermissionSnackBar(context: Context) {
    Column {
        var snackbarVisibleState by remember { mutableStateOf(true) }
        if (snackbarVisibleState) {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                Snackbar(
                    dismissAction = {
                        IconButton(onClick = { snackbarVisibleState = false }) {
                            Icon(Icons.Rounded.Clear, contentDescription = null)
                        }

                        rememberCoroutineScope().launch {
                            delay(5000)
                            snackbarVisibleState = false
                        }
                    },
                    action = {
                        TextButton(onClick = {
                            // Open the app settings to grant permission
                            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                            val uri = Uri.fromParts("package", context.packageName, null)
                            intent.data = uri
                            context.startActivity(intent)

                        }) {
                            Text(text = "Settings")
                        }
                    },
                    modifier = Modifier.padding(8.dp)
                ) { Text(text = "Grant Location Permission") }
            }
        }
    }
}


data class Root(
    val coord: Coord,
    val weather: List<Weather>,
    val base: String,
    val main: Main,
    val visibility: Long,
    val wind: Wind,
    val rain: Rain,
    val clouds: Clouds,
    val dt: Long,
    val sys: Sys,
    val timezone: Long,
    val id: Long,
    val name: String,
    val cod: Long
)

data class Coord(
    val lon: Double,
    val lat: Double
)

data class Weather(
    val id: Long,
    val main: String,
    val description: String,
    val icon: String
)

data class Main(
    val temp: Double,
    val feels_like: Double,
    val temp_min: Double,
    val temp_max: Double,
    val pressure: Long,
    val humidity: Long,
    val sea_level: Long,
    val grnd_level: Long
)

data class Wind(
    val speed: Double,
    val deg: Long,
    val gust: Double
)

data class Rain(
    val `1h`: Double
)

data class Clouds(
    val all: Long
)

data class Sys(
    val type: Long,
    val id: Long,
    val country: String,
    val sunrise: Long,
    val sunset: Long
)



interface apiService{
    @GET("/data/2.5/weather") // Specify the endpoint path here
    suspend fun getWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String
    ): Root
}


suspend fun Weather(latitude: Double,longitude: Double): Root?{
    val retrofit = Retrofit.Builder().
    baseUrl("https://api.openweathermap.org")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val api = retrofit.create(apiService::class.java)

    val apiKey = "adf"


    try {
        return api.getWeather(latitude,longitude, apiKey)
    }
    catch (e:Exception){
        Log.e("API Error","Calling todo create a problem $e")
       return null
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ShowWeather(isOkay: Boolean,dismissFun: () -> Unit,data: Root,color: Color,accentColor: Color){

    when {
        data == null -> {
            if(isOkay){
                Dialog(onDismissRequest = {dismissFun()}, properties = DialogProperties(dismissOnBackPress = true,dismissOnClickOutside = true),content = {
                    Card(shape = RoundedCornerShape(10.dp), modifier = Modifier
                        .width(300.dp)
                        .height(400.dp)
                        .padding(8.dp)
                        .graphicsLayer { transformOrigin = TransformOrigin.Center },
                        colors = cardColors(accentColor)
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
//                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(
                                text = "Loading...",
                                textAlign = TextAlign.Center,
                                fontSize = 24.sp,
                                fontFamily = Comfortaa,
                                color = color
                            )
                            rememberCoroutineScope().launch {
                                delay(10000)
                                dismissFun()
                            }
                        }
                    }
                })
            }

        }
        data != null -> {

            if (isOkay) {
                Dialog(onDismissRequest = {dismissFun()}, properties = DialogProperties(dismissOnBackPress = true,dismissOnClickOutside = true),content = {
                    Card(shape = RoundedCornerShape(10.dp), modifier = Modifier
                        .width(300.dp)
                        .height(400.dp)
                        .padding(8.dp)
                        .graphicsLayer { transformOrigin = TransformOrigin.Center },
                        colors = cardColors(accentColor)
                    ) {
                        Column(modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .padding(5.dp)
                            .verticalScroll(state = rememberScrollState(), enabled = true), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {


                            Text(
                                text = "Location: ${data?.name}",
                                fontSize = 18.sp,
                                fontFamily = Comfortaa, // Replace with your actual font family
                                color = color
                            )
                            Text(
                                text = "Main: ${data?.weather?.get(0)?.main}",
                                fontSize = 18.sp,
                                fontFamily = Comfortaa, // Replace with your actual font family
                                color = color
                            )
                            Text(
                                text = "Description: ${data?.weather?.get(0)?.description}",
                                fontSize = 18.sp,
                                fontFamily = Comfortaa, // Replace with your actual font family
                                color = color
                            )
                            Text(
                                text = "Temperature: ${(data?.main?.temp?.minus(273.15))?.toInt()}°C",
                                fontSize = 18.sp,
                                fontFamily = Comfortaa, // Replace with your actual font family
                                color = color
                            )
                            Text(
                                text = "Feel Like: ${(data?.main?.feels_like?.minus(273.15))?.toInt()}°C",
                                fontSize = 18.sp,
                                fontFamily = Comfortaa, // Replace with your actual font family
                                color = color
                            )
                            Text(
                                text = "Temp Min: ${(data?.main?.temp_min?.minus(273.15))?.toInt()}°C ",
                                fontSize = 18.sp,
                                fontFamily = Comfortaa, // Replace with your actual font family
                                color = color
                            )
                            Text(
                                text = "Temp Max: ${(data?.main?.temp_max?.minus(273.15))?.toInt()}°C",
                                fontSize = 18.sp,
                                fontFamily = Comfortaa, // Replace with your actual font family
                                color = color
                            )
                            Text(
                                text = "Humidity: ${data?.main?.humidity}%",
                                fontSize = 18.sp,
                                fontFamily = Comfortaa, // Replace with your actual font family
                                color = color
                            )
                            Text(
                                text = "Pressure: ${data?.main?.pressure} hPa",
                                fontSize = 18.sp,
                                fontFamily = Comfortaa, // Replace with your actual font family
                                color = color
                            )
                            Text(
                                text = "Wind Speed: ${data?.wind?.speed} m/s",
                                fontSize = 18.sp,
                                fontFamily = Comfortaa, // Replace with your actual font family
                                color = color
                            )
                            Text(
                                text = "Wind Direction: ${data?.wind?.deg}°",
                                fontSize = 18.sp,
                                fontFamily =Comfortaa , // Replace with your actual font family
                                color = color
                            )
                            Text(
                                text = "Cloudiness: ${data?.clouds?.all}%",
                                fontSize = 18.sp,
                                fontFamily = Comfortaa, // Replace with your actual font family
                                color = color
                            )
                            Text(
                                text = "Rain Past 1 Hour: ${data?.rain?.`1h`} mm",
                                fontSize = 18.sp,
                                fontFamily = Comfortaa, // Replace with your actual font family
                                color = color
                            )
                            Text(
                                text = "Sunrise: ${data?.sys?.sunrise?.let { timestampChange(it) }}",
                                fontSize = 18.sp,
                                fontFamily = Comfortaa, // Replace with your actual font family
                                color = color
                            )
                            Text(
                                text = "Sunset: ${data?.sys?.sunset?.let{timestampChange(it)}}",
                                fontSize = 18.sp,
                                fontFamily = Comfortaa, // Replace with your actual font family
                                color = color
                            )
                            Text(
                                text = "Visibility: ${data?.visibility} meters",
                                fontSize = 18.sp,
                                fontFamily = Comfortaa, // Replace with your actual font family
                                color = color
                            )
                        }
                        }
                })
            }
        }
        else -> {
            Text(text = "Error fetching weather data.")
        }
    }
}


fun timestampChange(timeStamp: Long):String{
    try{
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val date = Date(timeStamp * 1000L)
        return sdf.format(date)
    }
    catch (e:Exception){
        e.printStackTrace()
    }
    return ""
}


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun weatherPermission(context: Context,showSnack:() -> Unit,weatherMap: Map<String,Painter>,color: Color ): Root? {

    var loc by remember {
        mutableStateOf<Location?>(null)
    }
    var result by remember {
        mutableStateOf<Root?>(null)
    }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted: Boolean ->
            if (isGranted) {
                Log.i("Permission", "Granted")
            } else {
                Log.i("Permission", "Denied")
                showSnack()
            }
        }
    )

    IconButton(onClick = {
        when {
            ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                var fusedLocation: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
                fusedLocation.getCurrentLocation(LocationRequest.QUALITY_HIGH_ACCURACY,null).addOnSuccessListener { location: Location ->
                    run {
                      loc = location
                    }
                }
            }
            ActivityCompat.shouldShowRequestPermissionRationale(
                context as Activity,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) -> {

                requestPermissionLauncher.launch(
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                )

            }
            else -> {
                requestPermissionLauncher.launch(
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                )
                showSnack()
                Log.e("Clicked","Didn't reached here")
            }
        }
    }) {
        var weatherIcon by remember {
            mutableStateOf<Painter?>(null)
        }
       if(loc != null){
        rememberCoroutineScope().launch {
         val weatherData =  Weather(loc!!.latitude, loc!!.longitude)
            val weatherID = weatherData?.weather?.get(0)?.icon
             weatherIcon = weatherMap[weatherID]
            result =  weatherData
            Log.e("First Weather","I came here to get first time data")

        }
           if (weatherIcon != null) {
               Icon(weatherIcon!!, contentDescription = null, tint = color)
           }
        }
       else{
            Icon(painterResource(id = R.drawable.cloud_off), contentDescription = null, tint = color)
        }

    }
    return result
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun fontcolorModal(isOkay: Boolean,dismissFun: () -> Unit,onFontClick: (Int) -> Unit,color: Color,onAccentClick: (Int) -> Unit,accentColor: Color){
    if (isOkay) {
        Dialog(onDismissRequest = {dismissFun()}, properties = DialogProperties(dismissOnBackPress = true,dismissOnClickOutside = true),content = {
            Card(shape = RoundedCornerShape(10.dp), modifier = Modifier
                .width(300.dp)
                .height(400.dp)
                .padding(8.dp)
                .graphicsLayer { transformOrigin = TransformOrigin.Center },
                colors = cardColors(accentColor)
            ) {
                Text("Color (ㆆ_ㆆ) :",modifier = Modifier.padding(vertical =  0.dp, horizontal = 20.dp),color = color, fontSize = 24.sp, fontFamily = Comfortaa)
                FlowRow(modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(
                        rememberScrollState(),

                        ), horizontalArrangement = Arrangement.SpaceBetween)
               {
                        Text("Font Color ≧◠ᴥ◠≦",modifier = Modifier.padding(vertical =  0.dp, horizontal = 8.dp),color = color, fontSize = 22.sp, fontFamily = Comfortaa)
                       for((index,c) in fontColorList.withIndex()) {
                           Button(
                               onClick = { onFontClick(index) },
                               modifier = Modifier
                                   .width(48.dp)
                                   .height(48.dp)
                                   .padding(4.dp),
                               colors = ButtonDefaults.buttonColors(containerColor = c),
                               shape = RoundedCornerShape(4.dp)
                           ) {
                           }
                       }
                           Text("Accent Color (ɔ◔‿◔)ɔ",modifier = Modifier.padding(vertical =  8.dp, horizontal = 8.dp),color = color, fontSize = 20.sp, fontFamily = Comfortaa)
                           for((index,c) in fontColorList.withIndex()){
                               Button(onClick = {  onAccentClick(index) },modifier = Modifier
                                   .width(48.dp)
                                   .height(48.dp)
                                   .padding(4.dp)
                                   ,colors = ButtonDefaults.buttonColors(containerColor = c), shape = RoundedCornerShape(4.dp)) {
                               }


                           }


            }
        }
        })
    }
}


@Composable
fun attachMediaIntent(assignUri:(List<Uri>) -> Unit,color: Color){

    Log.e("PhotoPicker", "Reached Here Before Crash")
    val pickMedia = rememberLauncherForActivityResult(contract = ActivityResultContracts.PickMultipleVisualMedia(), onResult = {
        uris ->
            if(uris.isNotEmpty()){
                Log.e("PhotoPicker", "Number of items selected: ${uris.get(0)}")
                assignUri(uris)
            }
    })

    IconButton(onClick = {
        pickMedia.launch(PickVisualMediaRequest(mediaType = ActivityResultContracts.PickVisualMedia.ImageAndVideo))
    }) {
        Icon(painterResource(id = R.drawable.add_image),contentDescription = null, tint = color)
    }
}



@OptIn(ExperimentalLayoutApi::class)
@Composable
fun showImageModal(isOkay: Boolean,dismissFun: () -> Unit,accentColor: Color,imageUriList: List<Uri>?){
    if (isOkay) {
        Dialog(onDismissRequest = {dismissFun()}, properties = DialogProperties(dismissOnBackPress = true,dismissOnClickOutside = true),content = {
            Card(shape = RoundedCornerShape(10.dp), modifier = Modifier
                .width(420.dp)
                .height(620.dp)
                .padding(14.dp)
                .graphicsLayer { transformOrigin = TransformOrigin.Center },
                colors = cardColors(accentColor)
            ) {
                Column(modifier = Modifier
                    .verticalScroll(rememberScrollState())) {
                    if(imageUriList != null){
                    for (i in imageUriList) {
                        AsyncImage(
                            model = i, contentDescription = null, modifier = Modifier
                                .padding(8.dp), contentScale = ContentScale.Fit
                        )
                    }
                    }else{
                        Text("O File Attached")
                    }
                }
                }
            })
        }
}