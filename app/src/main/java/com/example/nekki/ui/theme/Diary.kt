package com.example.nekki.ui.theme


import Root
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationRequest
import android.net.ConnectivityManager
import android.net.Uri
import android.provider.OpenableColumns
import android.provider.Settings
import android.util.Log
import android.util.LruCache
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandVertically
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import coil.compose.AsyncImage
import com.example.nekki.BuildConfig
import com.example.nekki.Comfortaa
import com.example.nekki.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.http.GET
import retrofit2.http.Url
import java.io.BufferedReader
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

val SkyBlue = Color(33, 150, 243)

val fontColorList = listOf<Color>(
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
@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun Diary(sharedPreferences: SharedPreferences) {
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
        mutableStateOf<List<Uri>>(emptyList())
    }

    val context = LocalContext.current

    val emojiList = listOf<Painter>(
        painterResource(id = R.drawable.neutral),
        painterResource(id = R.drawable.good),
        painterResource(id = R.drawable.verygood),
        painterResource(id = R.drawable.laugh),
        painterResource(id = R.drawable.excited),
        painterResource(id = R.drawable.sick),
        painterResource(id = R.drawable.confuse),
        painterResource(id = R.drawable.verybad),
        painterResource(id = R.drawable.frustrated),
        painterResource(id = R.drawable.extrem_angry),
        painterResource(id = R.drawable.color_bts),
        painterResource(id = R.drawable.cat_grinning),
        painterResource(id = R.drawable.cat_kissing),
        painterResource(id = R.drawable.cat_face_smile),
        painterResource(id = R.drawable.cat_smiling_cat_with_heart_eyes),
        painterResource(id = R.drawable.cat_weary),
        painterResource(id = R.drawable.cat_with_tears_of_joy),
        painterResource(id = R.drawable.cat_with_wry_smile),
        painterResource(id = R.drawable.cat_pouting),
        painterResource(id = R.drawable.cat_crying),
        painterResource(id = R.drawable.color_expressionless_face),
        painterResource(id = R.drawable.color_grinning_face),
        painterResource(id = R.drawable.color_beaming_face_with_smiling_eyes),
        painterResource(id = R.drawable.color_face_blowing_a_kiss),
        painterResource(id = R.drawable.color_kissing_face_with_closed_eyes),
        painterResource(id = R.drawable.color_astonished_face),
        painterResource(id = R.drawable.color_cold_face),
        painterResource(id = R.drawable.color_anxious_face_with_sweat),
        painterResource(id = R.drawable.color_face_vomiting),
        painterResource(id = R.drawable.color_confounded_face),
        painterResource(id = R.drawable.color_confused_face),
        painterResource(id = R.drawable.color_crying_face),
        painterResource(id = R.drawable.color_disappointed_face),
        painterResource(id = R.drawable.color_face_exhaling),
        painterResource(id = R.drawable.color_angry_face),
    )

    val emojiListCache = LruCache<String,List<Painter>>(4*1024*1024)
    emojiListCache.put("emojiListCache",emojiList)

    val colorListCache = LruCache<String,List<Color>>(4*1024*1024)
    colorListCache.put("colorListCache", fontColorList)

    val fontColor = sharedPreferences.getInt("Fontcolor",0)
    val accentColor = sharedPreferences.getInt("Accentcolor",1)
    val backGroundImage = sharedPreferences.getString("BackgroundImage",R.drawable.bts.toString())

    val selectedFontColor =  fontColorList.get(fontColor)
    val selectedAccentColor =  fontColorList.get(accentColor)


    var updateBackGroundImage by remember {
        mutableStateOf<Uri>(Uri.parse(backGroundImage))
    }

    var fontcolorBoolean by remember {
        mutableStateOf(false)
    }

    var fontColorId by remember {
        mutableStateOf(0)
    }

    var accentColorId by remember {
        mutableStateOf(1)
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
            emojiListCache,
            emojiList,
            selectedFontColor,
            selectedAccentColor
        )
    }

        fontcolorModal(
            cache = colorListCache,
        isOkay = fontcolorBoolean ,
        onFontClick = { clickedIndex ->
            val editor = sharedPreferences.edit()
            editor.putInt("Fontcolor",clickedIndex)
            editor.apply()
            fontColorId = clickedIndex
        },
        dismissFun = { fontcolorBoolean = false },
            color = selectedFontColor,
            onAccentClick = {clickedInex ->
                accentColorId = clickedInex
                val editor = sharedPreferences.edit()
                editor.putInt("Accentcolor",clickedInex)
                editor.apply()
                            },
            accentColor = selectedAccentColor

        )



    var checkInternet by remember {
        mutableStateOf(true)
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

    var showContactAndUrl by remember {
        mutableStateOf(false)
    }

    var showContactAndUrl2 by remember {
        mutableStateOf(false)
    }


    var contactsAndUrlList: List<Pair<String,String>> by remember {
        mutableStateOf(emptyList())
    }

    if(showContactAndUrl){
        showContactModal("Name","Contact Number",true,showContactAndUrl, dismissFun = {showContactAndUrl = false},onSubmit = {s,i -> contactsAndUrlList += Pair(
            s,
            i
        );showContactAndUrl = false},selectedFontColor,selectedAccentColor )
    }

    if(showContactAndUrl2){
        showContactModal("Name","Url",false,showContactAndUrl2, dismissFun = {showContactAndUrl2 = false},onSubmit = {s,i -> contactsAndUrlList += Pair(
            s,
            i
        );showContactAndUrl2 = false},selectedFontColor,selectedAccentColor )
    }

    if(showImage){
        attachMediaIntent(isOkay = showImage,
            showContactModal = {showContactAndUrl = true},
            showUrlModalFun = {showContactAndUrl2 = true},
            removeUrlandContact = {i -> contactsAndUrlList = contactsAndUrlList.minus(i)},
            removeUri = {a -> imageUriList = imageUriList.minus(a)},
            setSingleUri = {a->
           imageUriList =  imageUriList + a }, setList = { a ->
                imageUriList = imageUriList + a }, dismissFun = {showImage = false},
            accentColor = selectedAccentColor,color = selectedFontColor,
            imageUriList = imageUriList, contactandUrlList = contactsAndUrlList)
    }

    if(showImage){
        BackHandler(true) {
            showImage = false
        }
    }


    val keyboardController = LocalSoftwareKeyboardController.current
    var isContentFieldActive by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    if(isContentFieldActive){
        BackHandler {
            focusManager.clearFocus()
        }
    }


    var popUpweather by remember {
        mutableStateOf(false)
    }

    var weatherData by remember {
        mutableStateOf<Root?>(null)
    }

    if(latitudeDiary != 0.0 && longitudeDiary != 0.0 && weatherData == null) {
        rememberCoroutineScope().launch {
            weatherData = getWeather(latitudeDiary, longitudeDiary)
        }
    }


    if((weatherData != null) && (latitudeDiary != 0.0) && (longitudeDiary != 0.0) && (popUpweather == true) ){
        Log.e("Done","What are you doing")
        showWeatherModal(
            isOkay = popUpweather,
            dismissFun = { popUpweather =  false },
            data = weatherData!!,
            color = selectedFontColor,
            accentColor = selectedAccentColor
        )
    }

    val locationPermission = ContextCompat.checkSelfPermission(LocalContext.current,android.Manifest.permission.ACCESS_FINE_LOCATION)
    val storagePermission = ContextCompat.checkSelfPermission(LocalContext.current,android.Manifest.permission.READ_EXTERNAL_STORAGE)

    var askPermission by remember { mutableStateOf(false) }
    var askStoragePermission by remember { mutableStateOf(false) }

    var  storagePermissionDialogBoolean by remember { mutableStateOf(false) }


    if (askStoragePermission) {
        askStoragePermissionDialog(
            onDismissRequest = { askStoragePermission = false },
            onPermissionDenied = { askStoragePermission = false ; storagePermissionDialogBoolean = true},
            onPermissionGranted = {askStoragePermission = false}
        )
    }


    var nwLat by remember {
        mutableStateOf<Double>(0.0)
    }

    var nwLog by remember {
        mutableStateOf<Double>(0.0)
    }

    if (askPermission) {
        askPermissionDialog(
            onDismissRequest = { askPermission = false },
            onPermissionDenied = { showOfflineLoc = true },
            onPermissionGranted = { location ->
                    askPermission = false
                    latitudeDiary = location.latitude
                    longitudeDiary = location.longitude
            }
        )
    }

   if(locationPermission == PackageManager.PERMISSION_GRANTED && (latitudeDiary == 0.0) && (longitudeDiary == 0.0) ){
       var fusedLocation: FusedLocationProviderClient =
           LocationServices.getFusedLocationProviderClient(context)
            fusedLocation.getCurrentLocation(LocationRequest.QUALITY_HIGH_ACCURACY, null)
           .addOnSuccessListener { location: Location? ->
               run {
                   if (location != null) {
                        latitudeDiary = location.latitude
                       longitudeDiary = location.longitude
                   } else {
                       // Location is null, handle this case
                       Log.e("Loation Null", "This error show that Location Recived Null")
                   }
               }
           }
   }

    LaunchedEffect(Unit) {
        if (locationPermission == PackageManager.PERMISSION_GRANTED) {
                try {
                    weatherData = getWeather(latitudeDiary, longitudeDiary)
                    Log.e("1", "Error fetching weather: ${weatherData}")
                } catch (e: Exception) {
                    Log.e("1", "Error fetching weather: ${e.message}")
                }
        }
    }

    LaunchedEffect(locationPermission){
        if (locationPermission == PackageManager.PERMISSION_GRANTED) {
            try {
                weatherData = getWeather(latitudeDiary, longitudeDiary)
                Log.e("2", "Re run weather $weatherData $latitudeDiary $longitudeDiary")
            } catch (e: Exception) {
                Log.e("Re", "Error fetching weather: ${e.message}")
                Log.e("Re", "Re run weather $weatherData $latitudeDiary $longitudeDiary")
            }
        }
    }


    Box(modifier = Modifier
        .then(if (isContentFieldActive) {Modifier.padding(0.dp) }else Modifier.padding(10.dp))
        .fillMaxSize()) {


        AsyncImage(
            updateBackGroundImage,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp)),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Column(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(2.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Top
        ) {

            if(!isContentFieldActive) {
            Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically,modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp, vertical = 5.dp)){
                Text(text = formated, fontFamily = Comfortaa, fontSize = 24.sp, color = selectedFontColor)
                if(weatherData != null){
                    Text("${weatherData!!.current.temp_c}°C/${weatherData!!.current.condition.text} ", fontFamily = Comfortaa,color = selectedFontColor, fontSize = 18.sp)
                }else{
                    Icon(painterResource(id = R.drawable.cloud_off),contentDescription = null ,tint = selectedFontColor)
                }
            }

                OutlinedTextField(
                    value = titleText,
                    singleLine = true,
                    onValueChange = { titleText = it },
//                colors =
                    textStyle = TextStyle(
                        fontSize = 20.sp,
                        color = selectedFontColor,
                        lineHeight = 0.sp

                    ),
//                place
                    placeholder = {
                        Text(
                            "Title...",
                            color = selectedFontColor,
                            fontSize = 16.sp,
                        )
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onDone = { focusKeyboard.clearFocus() }),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 5.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Unspecified,
                        unfocusedBorderColor = Color.Unspecified,
                        cursorColor = selectedFontColor
                    )
                )
            }


            OutlinedTextField(
                value = contentText,
                onValueChange = { contentText = it },
                textStyle = TextStyle(
                    fontSize = 20.sp,
                    color = selectedFontColor
                ),
                placeholder = {
                    Text(
                        "Write here...",
                        color = selectedFontColor,
                        fontSize = 16.sp,

                    )
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Default),
                keyboardActions = KeyboardActions(onDone = { focusKeyboard?.clearFocus() }),
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.9f)
                    .then(if (isContentFieldActive) {Modifier.padding(0.dp) }else Modifier.padding(2.dp))
                    .focusRequester(focusRequester)
                    .onFocusChanged { focusState -> isContentFieldActive = focusState.isFocused },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Unspecified,
                    unfocusedBorderColor = Color.Unspecified,
                    cursorColor = selectedFontColor
                )
            )


            if(!isContentFieldActive)
            {
            Row(modifier = Modifier
                .horizontalScroll(
                    rememberScrollState()
                )) {
                IconButton(onClick = { }) {
                    Icon(
                        painterResource(id = R.drawable.save),
                        contentDescription = null,
                        tint = selectedFontColor
                    )
                }

                if(weatherData != null) {
                   val a = "https:" + weatherData!!.current.condition.icon
                    Log.e("Image Code","Getting Image $a and ${weatherData!!.current.condition.icon}")
                    IconButton(onClick = { popUpweather = true }) {
                        AsyncImage(model = a, contentDescription = null,modifier = Modifier
                            .width(64.dp)
                            .height(64.dp))
                    }
                }else if(!checkInternet){
                    IconButton(onClick = { showOfflineLoc = true}) {
                        Icon(painterResource(id = R.drawable.cloud_off),contentDescription = null,tint = selectedFontColor)
                    }
                }else if(locationPermission == PackageManager.PERMISSION_DENIED){
                    IconButton(onClick = { askPermission = true}) {
                        Icon(painterResource(id = R.drawable.cloud_off),contentDescription = null,tint = selectedFontColor)
                    }
                }else if(weatherData == null && locationPermission == PackageManager.PERMISSION_GRANTED){
                    IconButton(onClick = { }) {
                        Icon(painterResource(id = R.drawable.cloud_off),contentDescription = null,tint = selectedFontColor)
                    }
                }


                IconButton(onClick = { showEmoji = !showEmoji }) {
                    showChoosenEmoji(emojiId = emojiId, emojiList = emojiList, selectedFontColor)
                }

              if(locationPermission == PackageManager.PERMISSION_DENIED) {
                    IconButton(onClick = { askPermission = true }) {
                        Icon(
                            painterResource(id = R.drawable.location_off),
                            contentDescription = null,
                            tint = selectedFontColor
                        )
                    }
                }else if(!checkInternet) {
                   IconButton(onClick = { showOfflineLoc = true }) {
                       Icon(
                           painterResource(id = R.drawable.location_off),
                           contentDescription = null,
                           tint = selectedFontColor
                       )
                   }
                }else if(locationPermission == PackageManager.PERMISSION_GRANTED) {
                  IconButton(onClick = {
                      var fusedLocation: FusedLocationProviderClient =
                          LocationServices.getFusedLocationProviderClient(context)
                          fusedLocation.getCurrentLocation(LocationRequest.QUALITY_HIGH_ACCURACY, null)
                          .addOnSuccessListener { location: Location? ->
                              run {
                                  if (location != null) {
                                      latitudeDiary = location.latitude;
                                      longitudeDiary = location.longitude
                                      OpenGoogleMaps(location.latitude, location.longitude, context)
                                  } else {
                                      Toast.makeText(
                                          context,
                                          "Some thing went wrong",
                                          Toast.LENGTH_SHORT
                                      )
                                  }
                              }
                          }
                  }) {
                      Icon(
                          painterResource(id = R.drawable.location_on),
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


                if(storagePermission == PackageManager.PERMISSION_DENIED){
                IconButton(onClick = { askStoragePermission = true }) {
                    Icon(painterResource(id = R.drawable.attach_file_2), contentDescription = null, tint = selectedFontColor)
                }
                }else {
                    IconButton(onClick = { showImage = true }) {
                        Icon(
                            painterResource(id = R.drawable.attach_file_2),
                            contentDescription = null,
                            tint = selectedFontColor
                        )
                    }
                }



                addBackGround(changeBackGround = {uri ->
                    val editor = sharedPreferences.edit();
                    editor.putString("BackgroundImage",uri.toString())
                    Log.e("Uri","New Back Ground image $uri")
                    editor.apply()

                    updateBackGroundImage = uri
                },selectedFontColor)

            }
            }
        }

        if(isContentFieldActive){

                Row(verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.End, modifier = Modifier
                    .fillMaxSize()
                    .padding(0.dp)) {
                    FloatingActionButton(
                        onClick = { focusManager.clearFocus();isContentFieldActive = false },
                        containerColor = selectedAccentColor
                    ) {
                        Icon(
                            Icons.Rounded.ArrowBack,
                            contentDescription = null,
                            tint = selectedFontColor,
                            modifier = Modifier.size(32.dp)
                        )
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

        if (storagePermissionDialogBoolean) {
            storagePermissionSnackBar(context = context)
            rememberCoroutineScope().launch {
                delay(6000)
                storagePermissionDialogBoolean = false
            }
        }
    }
}


@SuppressLint("RememberReturnType")
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun emojiModal(isOkay: Boolean, onEmojiClick: (Int) -> Unit, function: () -> Unit, cache: LruCache<String,List<Painter>>,emojiList: List<Painter> ,fontColor: Color, accentColor: Color){

        if (isOkay) {

            val getEmojiListCache = cache.get("emojiListCache")

            Dialog(
                onDismissRequest = { function() },
                properties = DialogProperties(
                    dismissOnBackPress = true,
                    dismissOnClickOutside = true
                ),
                content = {
                    Card(
                        shape = RoundedCornerShape(10.dp), modifier = Modifier
                            .width(300.dp)
                            .height(400.dp)
                            .padding(8.dp)
                            .graphicsLayer { transformOrigin = TransformOrigin.Center },
                        colors = cardColors(accentColor)
                    ) {
                        FlowRow(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .verticalScroll(rememberScrollState())
//                    .background(Color.Red)
                                .padding(5.dp),
                        ) {
                            if(getEmojiListCache != null) {
                                for ((index, i) in getEmojiListCache.withIndex()) {
                                    IconButton(
                                        onClick = { onEmojiClick(index) }, modifier = Modifier
                                            .padding(vertical = 4.dp)
                                    ) {
                                        if (index < 10) {
                                            Icon(
                                                i,
                                                contentDescription = null,
                                                tint = fontColor,
                                                modifier = Modifier.size(48.dp)
                                            )
                                        } else {
                                            Icon(
                                                i,
                                                contentDescription = null,
                                                tint = Color.Unspecified,
                                                modifier = Modifier.size(48.dp)
                                            )
                                        }
                                    }
                                }
                            }else{
                                for ((index, i) in emojiList.withIndex()) {
                                    IconButton(
                                        onClick = { onEmojiClick(index) }, modifier = Modifier
                                            .padding(vertical = 4.dp)
                                    ) {
                                        if (index < 10) {
                                            Icon(
                                                i,
                                                contentDescription = null,
                                                tint = fontColor,
                                                modifier = Modifier.size(48.dp)
                                            )
                                        } else {
                                            Icon(
                                                i,
                                                contentDescription = null,
                                                tint = Color.Unspecified,
                                                modifier = Modifier.size(48.dp)
                                            )
                                        }
                                    }
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
            if(emojiId < 10){
                Icon(i, contentDescription = null,tint = color)
            }else{
                Icon(i, contentDescription = null,tint = Color.Unspecified)
            }

        }
    }
}

@Composable
fun showChoosenWeather(weatherId: Int,weatherList: List<Painter>,color: Color){
    for ((index,i) in weatherList.withIndex()){
        if(weatherId == index){
            Icon(i, contentDescription = null,tint = color)
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun requestPermission(context: Context,latlang: (Location) -> Unit,showSnack:() -> Unit,color: Color) {

    val permission = ContextCompat.checkSelfPermission(context,android.Manifest.permission.ACCESS_FINE_LOCATION)

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {


        }
    }


    Log.e("Self Permission","checking for self permsioon $permission")

    if(permission == PackageManager.PERMISSION_GRANTED) {
        IconButton( onClick = {
            var fusedLocation: FusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(context)
            fusedLocation.getCurrentLocation(LocationRequest.QUALITY_HIGH_ACCURACY, null)
                .addOnSuccessListener { location: Location? ->
                    run {
                        if (location != null) {
                            // Handle the location data
                            latlang(location)
                            OpenGoogleMaps(location.latitude,location.longitude,context)

                        } else {
                            // Location is null, handle this case
                            Log.e("Loation Null", "This error show that Location Recived Null")
                        }
                    }
                }
            }){
            Icon(
                painterResource(id = R.drawable.location_on),
                contentDescription = null,
                tint = color
                )
            }
        }
        else {
            IconButton(
                onClick = {
            when {
                ContextCompat.checkSelfPermission(
                    context,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED -> {
                    var fusedLocation: FusedLocationProviderClient =
                        LocationServices.getFusedLocationProviderClient(context)
                    fusedLocation.getCurrentLocation(LocationRequest.QUALITY_HIGH_ACCURACY, null)
                        .addOnSuccessListener { location: Location? ->
                            run {
                                if (location != null) {
                                    latlang(location)
                                    OpenGoogleMaps(location.latitude,location.longitude,context)
                                } else {
                                    // Location is null, handle this case
                                    Log.e(
                                        "Location Null",
                                        "This error show that Location Recived Null"
                                    )
                                }
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
                    Log.e("Clicked", "Didn't reached here")
                }
            }
         }){
                Icon(
                    painterResource(id = R.drawable.location_off),
                    contentDescription = null,
                    tint = color,
                )
        }

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
                    modifier = Modifier.padding(8.dp)
                ) {Text(text = "No Internet Connection! Weather and Location data cannot be fetched or stored.") }
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
                ) { Text(text = "Location permission is required to access and store your current Location and Weather Data.") }
            }
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun fontcolorModal(cache: LruCache<String,List<Color>>,isOkay: Boolean,dismissFun: () -> Unit,onFontClick: (Int) -> Unit,color: Color,onAccentClick: (Int) -> Unit,accentColor: Color){
    if (isOkay) {

        val colorListCache = cache.get("colorListCache")


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

                   if(colorListCache != null) {

                       for ((index, c) in colorListCache.withIndex()) {
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
                       Text(
                           "Accent Color (ɔ◔‿◔)ɔ",
                           modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp),
                           color = color,
                           fontSize = 20.sp,
                           fontFamily = Comfortaa
                       )
                       for ((index, c) in colorListCache.withIndex()) {
                           Button(
                               onClick = { onAccentClick(index) },
                               modifier = Modifier
                                   .width(48.dp)
                                   .height(48.dp)
                                   .padding(4.dp),
                               colors = ButtonDefaults.buttonColors(containerColor = c),
                               shape = RoundedCornerShape(4.dp)
                           ) {
                           }


                       }
                   }else{

                       for ((index, c) in fontColorList.withIndex()) {
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
                       Text(
                           "Accent Color (ɔ◔‿◔)ɔ",
                           modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp),
                           color = color,
                           fontSize = 20.sp,
                           fontFamily = Comfortaa
                       )
                       for ((index, c) in fontColorList.withIndex()) {
                           Button(
                               onClick = { onAccentClick(index) },
                               modifier = Modifier
                                   .width(48.dp)
                                   .height(48.dp)
                                   .padding(4.dp),
                               colors = ButtonDefaults.buttonColors(containerColor = c),
                               shape = RoundedCornerShape(4.dp)
                           ) {
                           }
                       }
                   }


            }
        }
        })
    }
}


@Composable
fun attachMediaIntent(isOkay: Boolean, removeUrlandContact: (Pair<String, String>) -> Unit,showContactModal:() -> Unit,showUrlModalFun: () -> Unit,removeUri:(Uri) -> Unit,setSingleUri:(Uri) -> Unit,dismissFun: () -> Unit,accentColor: Color,color: Color,setList:(List<Uri>)->Unit ,imageUriList: List<Uri>,contactandUrlList: List<Pair<String,String>>){

    val context = LocalContext.current
    val pickMedia = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(), onResult = { uris ->
            if (uris.isNotEmpty()) {
                Log.e("PhotoPicker", "Number of items selected: ${uris.get(0)}")
                setList(uris)
            }
        })

    if(isOkay){
        Box(
            Modifier
                .fillMaxSize()
                .background(accentColor)) {

                        Column(
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .verticalScroll(rememberScrollState())
                                .padding(vertical = 50.dp)
                        ) {

                            if (imageUriList.isNotEmpty()) {
                                for (e in imageUriList) {
                                    val form = getFileName(e, context)
                                    Log.e("Music is ", "I'm here before check ${form}")
                                    if (form != null) {
                                        if (form.endsWith(
                                                "png",
                                                ignoreCase = true
                                            ) || form.endsWith(
                                                "jpeg",
                                                ignoreCase = true
                                            ) || form.endsWith("jpg", ignoreCase = true)
                                        ) {
                                            Log.e(
                                                "Image",
                                                "I'm here before check with image format form "
                                            )

                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .fillMaxHeight()
                                                    .padding(
                                                        top = 5.dp,
                                                        bottom = 0.dp,
                                                        start = 5.dp,
                                                        end = 5.dp
                                                    )
                                                    .background(
                                                        color,
                                                        RoundedCornerShape(
                                                            topEnd = 8.dp,
                                                            topStart = 8.dp
                                                        )
                                                    )
                                            ) {
                                                Row(
                                                    horizontalArrangement = Arrangement.SpaceBetween,
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    modifier = Modifier.fillMaxWidth()
                                                ) {

                                                    Text(
                                                        "$form",
                                                        modifier = Modifier
                                                            .padding(15.dp)
                                                            .fillMaxWidth(0.9f),
                                                        color = accentColor,
                                                        fontSize = 18.sp,
                                                        softWrap = true,
                                                        fontFamily = Comfortaa
                                                    )

                                                    IconButton(onClick = { removeUri(e) }) {
                                                        Icon(
                                                            Icons.Rounded.Clear,
                                                            contentDescription = null,
                                                            tint = accentColor
                                                        )
                                                    }
                                                }
                                            }

                                            AsyncImage(
                                                model = e,
                                                contentDescription = null,
                                                contentScale = ContentScale.Fit,
                                                modifier = Modifier
                                                    .padding(
                                                        top = 0.dp,
                                                        start = 5.dp,
                                                        end = 5.dp,
                                                        bottom = 5.dp
                                                    )
                                                    .fillMaxHeight()
                                                    .height(450.dp)
                                                    .fillMaxWidth()
                                                    .background(
                                                        color,
                                                        RoundedCornerShape(
                                                            bottomStart = 8.dp,
                                                            bottomEnd = 8.dp
                                                        )
                                                    )
                                            )
                                        } else if (form.endsWith(
                                                "mp4",
                                                ignoreCase = true
                                            ) || form.endsWith(
                                                "gif",
                                                ignoreCase = true
                                            ) || form.endsWith("mkv", ignoreCase = true)
                                            || form.endsWith("wav", ignoreCase = true)
                                            || form.endsWith("m4a", ignoreCase = true)
                                            || form.endsWith("mp3", ignoreCase = true)
                                            || form.endsWith("opus", ignoreCase = true)
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .fillMaxHeight()
                                                    .padding(
                                                        top = 5.dp,
                                                        bottom = 0.dp,
                                                        start = 5.dp,
                                                        end = 5.dp
                                                    )
                                                    .background(
                                                        color,
                                                        RoundedCornerShape(
                                                            topEnd = 8.dp,
                                                            topStart = 8.dp
                                                        )
                                                    )
                                            ) {
                                                Row(
                                                    horizontalArrangement = Arrangement.SpaceBetween,
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    modifier = Modifier.fillMaxWidth()
                                                ) {

                                                    Text(
                                                        "$form",
                                                        modifier = Modifier
                                                            .padding(15.dp)
                                                            .fillMaxWidth(0.9f),
                                                        color = accentColor,
                                                        fontSize = 18.sp,
                                                        softWrap = true,
                                                        fontFamily = Comfortaa
                                                    )

                                                    IconButton(onClick = { removeUri(e) }) {
                                                        Icon(
                                                            Icons.Rounded.Clear,
                                                            contentDescription = null,
                                                            tint = accentColor
                                                        )
                                                    }
                                                }
                                            }
                                            Log.e("Mp3", "Mp3 attached")
                                            playMedia(context, e, color)
                                        } else {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .fillMaxHeight()
                                                    .padding(
                                                        5.dp
                                                    )
                                                    .background(
                                                        color,
                                                        RoundedCornerShape(
                                                            8.dp
                                                        )
                                                    )
                                            ) {
                                                Row(
                                                    horizontalArrangement = Arrangement.SpaceBetween,
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    modifier = Modifier.fillMaxWidth()
                                                ) {
                                                    Text(
                                                        "$form",
                                                        modifier = Modifier
                                                            .padding(15.dp)
                                                            .fillMaxWidth(0.9f),
                                                        color = accentColor,
                                                        fontSize = 24.sp,
                                                        softWrap = true,
                                                        fontFamily = Comfortaa
                                                    )

                                                    IconButton(onClick = { removeUri(e) }) {
                                                        Icon(
                                                            Icons.Rounded.Clear,
                                                            contentDescription = null,
                                                            tint = accentColor
                                                        )
                                                    }
                                                }
                                            }
                                        }

                                    }
                                }
                            }

                            if(contactandUrlList.isNotEmpty()){

                                for(c in contactandUrlList) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .fillMaxHeight()
                                            .padding(
                                                5.dp
                                            )
                                            .background(
                                                color,
                                                RoundedCornerShape(
                                                    8.dp
                                                )
                                            )
                                    ) {
                                        Row(
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Column {
                                                Text(
                                                    c.first,
                                                    modifier = Modifier
                                                        .padding(15.dp)
                                                        .fillMaxWidth(0.9f),
                                                    color = accentColor,
                                                    fontSize = 24.sp,
                                                    softWrap = true,
                                                    fontFamily = Comfortaa
                                                )
                                                Text(
                                                    c.second.toString(),
                                                    modifier = Modifier
                                                        .padding(15.dp)
                                                        .fillMaxWidth(0.9f),
                                                    color = accentColor,
                                                    fontSize = 24.sp,
                                                    softWrap = true,
                                                    fontFamily = Comfortaa
                                                )
                                            }

                                            IconButton(onClick = { removeUrlandContact(c) }) {
                                                Icon(
                                                    Icons.Rounded.Clear,
                                                    contentDescription = null,
                                                    tint = accentColor
                                                )
                                            }
                                        }
                                    }
                                }
                            }


                        }

            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .fillMaxWidth()
//                    .padding(5.dp)
                    .background(accentColor)
            ) {
                IconButton(onClick = {
                    showContactModal()
                })
                 {
                    Icon(
                        painterResource(id = R.drawable.attach_contacts),
                        contentDescription = null,
                        Modifier.size(26.dp),
                        tint = color
                    )
                }

                audioPicker(addMedia = { a -> setSingleUri(a)},color)

                IconButton(onClick = {
                    pickMedia.launch(PickVisualMediaRequest(mediaType = ActivityResultContracts.PickVisualMedia.ImageAndVideo))
                }) {
                    Icon(
                        painterResource(id = R.drawable.attach_image),
                        contentDescription = null,
                        Modifier.size(48.dp),
                        tint = color
                    )
                }

                filePicker(addMedia = {a -> setSingleUri(a)}, color = color)

                IconButton(onClick = {
                    showUrlModalFun()
                }) {
                    Icon(
                        painterResource(id = R.drawable.attach_website),
                        contentDescription = null,
                        Modifier.size(26.dp),
                        tint = color
                    )
                }
            }

            Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.Bottom){
                FloatingActionButton(onClick = { dismissFun() }, containerColor = accentColor, modifier = Modifier.padding(8.dp)) {
                    Icon(Icons.Rounded.ArrowBack, contentDescription = null,tint = color)
                }
            }
            }
        }
    }

@SuppressLint("Range")
fun getFileName(uri: Uri,context: Context): String? {
    var result: String? = null
    if (uri.scheme == "content") {

        val cursor = context.contentResolver.query(uri, null, null, null, null)
        try {
            if (cursor != null && cursor.moveToFirst()) {
                result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
            }
        } finally {
            cursor?.close()
        }
    }
    if (result == null) {
        result = uri.path
        val cut = result!!.lastIndexOf('/')
        if (cut != -1) {
            result = result.substring(cut + 1)
        }
    }
    return result
}

@Composable
fun playMedia(context: Context,uri: Uri,color: Color){

//    SimpleExoPlayer()
    Box(
        Modifier
            .width(600.dp)
            .height(450.dp)
            .padding(top = 0.dp, start = 5.dp, end = 5.dp, bottom = 5.dp)
            .clip(
                RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 0.dp,
                    bottomEnd = 8.dp,
                    bottomStart = 8.dp
                )
            )
            .background(color)

    ){
        val Player = ExoPlayer.Builder(context).build()
        val mediaItem = MediaItem.fromUri(uri)
        val playerView = PlayerView(context)
        Player.setMediaItem(mediaItem)
        playerView.player = Player
    
        DisposableEffect(AndroidView(factory = {playerView},modifier = Modifier
            .width(600.dp)
            .height(450.dp))){
            Player.prepare()
            Player.playWhenReady = false
            onDispose {
                Player.release()
            }
        }

   }

}

@Composable
fun audioPicker(addMedia:(Uri) -> Unit,color: Color){
    val c = LocalContext.current

    val e = ContextCompat.checkSelfPermission(c,android.Manifest.permission.READ_EXTERNAL_STORAGE)
    Log.e("Image","Value of e : $e")
    if(e == PackageManager.PERMISSION_GRANTED){

        val audioLaunch = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent(), onResult = {
                uri: Uri? ->
            run {
                if(uri != null){
                    addMedia(uri)
                    val d =  getFileName(uri,c)
                    Log.e("Music Uri","Music Uri is $uri and $d")
                }
            }
        }
        )

        Log.e("Music","Permission of Music is Remebered")

        IconButton(onClick = { audioLaunch.launch("audio/*") }) {
            Icon(painter = painterResource(id = R.drawable.attach_music), contentDescription = null,tint = color)
        }

    }else{

        val audioLaunch = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent(), onResult = {
                uri: Uri? ->
            run {
                if(uri != null){
                    addMedia(uri)
                    val d =  getFileName(uri,c)
                    Log.e("Music Uri","Music Uri is $uri and $d")
                }
            }
        }
        )

        val permissionLaunch = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission(), onResult ={
                isGranted ->
            run {
                audioLaunch.launch("audio/*")
            }
        } )

        Log.e("Music","Permission of Music Ask first time")
        IconButton(onClick = {permissionLaunch.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE) }) {
            Icon(painter = painterResource(id = R.drawable.attach_music), contentDescription = null,tint = color)
        }
    }
}



@Composable
fun filePicker(addMedia:(Uri) -> Unit,color: Color){

    val c = LocalContext.current

    val e = ContextCompat.checkSelfPermission(c,android.Manifest.permission.READ_EXTERNAL_STORAGE)
    Log.e("Image","Value of e : $e")
    if(e == PackageManager.PERMISSION_GRANTED){

        val audioLaunch = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent(), onResult = {
                uri: Uri? ->
            run {
                if(uri != null){
                    addMedia(uri)
                    val d =  getFileName(uri,c)
                    Log.e("Music Uri","Music Uri is $uri and $d")
                }
            }
        }
        )

        Log.e("Music","Permission of Music is Remebered")

        IconButton(onClick = { audioLaunch.launch("*/*") }) {
            Icon(painter = painterResource(id = R.drawable.attach_file), contentDescription = null,tint = color)
        }

    }else{

        val fileLaunch = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent(), onResult = {
                uri: Uri? ->
            run {
                if(uri != null){
                    addMedia(uri)
                    val d =  getFileName(uri,c)
                    Log.e("Music Uri","Music Uri is $uri and $d")
                }
            }
        }
        )

        val permissionLaunch = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission(), onResult ={
                isGranted ->
            run {
                fileLaunch.launch("*/*")
            }
        } )

        Log.e("Music","Permission of Music Ask first time")
        IconButton(onClick = {permissionLaunch.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE) }) {
            Icon(painter = painterResource(id = R.drawable.attach_file), contentDescription = null,tint = color)
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun showContactModal(nameOne:String,nameTwo:String,keyboard:Boolean,isOkay: Boolean,dismissFun: () -> Unit,onSubmit:( String,String ) ->Unit,fontColor: Color, accentColor: Color){

    var nameValue by remember {
        mutableStateOf<String>(" ")
    }

    var numberValue by remember {
        mutableStateOf<String>(" ")
    }

    if (isOkay) {
        Dialog(onDismissRequest = {dismissFun()}, properties = DialogProperties(dismissOnBackPress = true,dismissOnClickOutside = true),content = {
            Card(shape = RoundedCornerShape(10.dp), modifier = Modifier
                .width(500.dp)
                .height(400.dp)
                .padding(8.dp)
                .graphicsLayer { transformOrigin = TransformOrigin.Center },
                colors = cardColors(accentColor)
            ) {
                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp)
                    .verticalScroll(state = rememberScrollState(), enabled = true), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

                    Text("Attach $nameTwo :",color= fontColor, fontSize = 24.sp, fontFamily = Comfortaa ,modifier = Modifier.padding(5.dp))

                    OutlinedTextField(
                        value = nameValue,
                        singleLine = true,
                        onValueChange = { nameValue = it },
                        textStyle = TextStyle(
                            fontFamily = Comfortaa,
                            fontSize = 20.sp,
                            color = fontColor
                        ),
                        label = {
                            Text(
                                nameOne,
                                color = fontColor,
                                fontFamily = Comfortaa,
                                fontSize = 18.sp
                            )
                        },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 5.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = fontColor,
                            unfocusedBorderColor = fontColor,
                                    cursorColor = fontColor
                        )
                    )

                    if(keyboard) {
                        OutlinedTextField(
                            value = numberValue,
                            singleLine = true,
                            onValueChange = { numberValue = it },
                            textStyle = TextStyle(
                                fontFamily = Comfortaa,
                                fontSize = 20.sp,
                                color = fontColor
                            ),
                            label = {
                                Text(
                                    nameTwo,
                                    color = fontColor,
                                    fontFamily = Comfortaa,
                                    fontSize = 18.sp
                                )
                            },

                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Next,
                                keyboardType = KeyboardType.Phone
                            ),


                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp, vertical = 5.dp),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = fontColor,
                                unfocusedBorderColor = fontColor,
                                cursorColor = fontColor
                            )
                        )
                    }else{
                        OutlinedTextField(
                            value = numberValue,
                            singleLine = true,
                            onValueChange = { numberValue = it },
                            textStyle = TextStyle(
                                fontFamily = Comfortaa,
                                fontSize = 20.sp,
                                color = fontColor
                            ),
                            label = {
                                Text(
                                    nameTwo,
                                    color = fontColor,
                                    fontFamily = Comfortaa,
                                    fontSize = 18.sp
                                )
                            },

                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Next,
                            ),


                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp, vertical = 5.dp),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = fontColor,
                                unfocusedBorderColor = fontColor,
                                cursorColor = fontColor
                            )
                        )
                    }

                    TextButton(onClick = { onSubmit(nameValue,numberValue) }) {
                        Text("Add",color= accentColor, fontSize = 24.sp, fontFamily = Comfortaa ,modifier = Modifier
                            .background(
                                fontColor,
                                RoundedCornerShape(5.dp)
                            )
                            .padding(5.dp))
                    }


                }
            }
        })
    }
}




@Composable
fun addBackGround(changeBackGround:(Uri) -> Unit,fontColor: Color){
    val context = LocalContext.current

    val pickMedia = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(), onResult = { uri: Uri? ->
                 if(uri != null){
                     val imageName =getFileName(uri,context)
                     val internalDir = context.filesDir
                     val slectedUri: Uri = uri
                     val destined = File(internalDir,imageName)

                     try {
                         val inputStream: InputStream? = context.contentResolver.openInputStream(slectedUri)
                         val outputStream: OutputStream = FileOutputStream(destined)

                         inputStream?.use { input ->
                             outputStream.use { output ->
                                 input.copyTo(output, bufferSize = 4 * 1024)
                             }
                         }

                         val updatedUri = Uri.fromFile(File(internalDir,imageName))
                         changeBackGround(updatedUri)

                         Log.e("New Uri","$updatedUri is here")

                         // At this point, 'destinationFile' contains the copied image in internal storage
                     } catch (e: Exception) {
                         // Handle exceptions that may occur during the copy operation
                         Log.e("New Uri","$e in getting new uri")

                     }
                 }
            Log.e("New Uri","what going on $uri")


            val files = context.filesDir.listFiles()
                for (file in files) {
                    val fileName = file.name
                    Log.e("All File","File exist in inter ${file.absoluteFile} ${file.name} ${file}")
                }
        })

    IconButton(onClick = { pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) }) {
        Icon(painterResource(id = R.drawable.attach_background), contentDescription = null, tint = fontColor)
    } 
}
interface  ApiService{
    @GET
    suspend fun fetchWeather(
        @Url url: String
    ): Root
}

suspend fun getWeather(latitude: Double, longitude: Double):Root?{
    val baseUrl = "https://api.weatherapi.com/v1/"
    val apiKey = BuildConfig.WEATHER_KEY
    val aqi: String = "yes"
    val url = "current.json?key=$apiKey&q=$latitude,$longitude&aqi=$aqi"

    var connection: HttpURLConnection? = null

    return withContext(Dispatchers.IO) {
        try {

            val url = URL("https://api.weatherapi.com/v1/current.json?key=$apiKey&q=$latitude,$longitude&aqi=yes")

            connection = url.openConnection() as HttpURLConnection

            connection!!.requestMethod = "GET"
            connection!!.connectTimeout = 5000
            connection!!.readTimeout = 5000

            val responseCode = connection!!.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val inputStream = connection!!.inputStream
                val reader = BufferedReader(InputStreamReader(inputStream))
                val response = StringBuilder()
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    response.append(line)
                }
                reader.close()
                Log.e("Weahter", "Getting Success while fetching data:$responseCode  $response")
                return@withContext Gson().fromJson<Root>(response.toString(), Root::class.java)
            } else {
                Log.e("Weahter", "Getting error while fetching data: $responseCode")
                return@withContext null
            }

        } catch (e: Exception) {
            Log.e("Weahter", "Getting error while fetching data: $e")
            return@withContext null
        } finally {
            connection?.disconnect()
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun showWeatherModal(isOkay: Boolean,dismissFun: () -> Unit,data:Root,color:Color,accentColor:Color){

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
                            text = "Location: ${data.location.name}",
                            fontSize = 18.sp,
                            fontFamily = Comfortaa, // Replace with your actual font family
                            color = color
                        )
                        Text(
                            text = "Weather: ${data.current.condition.text}",
                            fontSize = 18.sp,
                            fontFamily = Comfortaa, // Replace with your actual font family
                            color = color
                        )
                       Text(
                            text = "Temperature: ${data.current.temp_c}°C",
                            fontSize = 18.sp,
                            fontFamily = Comfortaa, // Replace with your actual font family
                            color = color
                        )
                        Text(
                            text = "Feel Like: ${data.current.feelslike_c}°C",
                            fontSize = 18.sp,
                            fontFamily = Comfortaa, // Replace with your actual font family
                            color = color
                        )
                        Text(
                            text = "Cloud: ${data.current.cloud}%",
                            fontSize = 18.sp,
                            fontFamily = Comfortaa, // Replace with your actual font family
                            color = color
                        )
                        Text(
                            text = "US EPA AQI: ${data.current.air_quality.`us-epa-index`}",
                            fontSize = 18.sp,
                            fontFamily = Comfortaa, // Replace with your actual font family
                            color = color
                        )
                        Text(
                            text = "GB DEFRA AQI: ${data.current.air_quality.`gb-defra-index`}",
                            fontSize = 18.sp,
                            fontFamily = Comfortaa, // Replace with your actual font family
                            color = color
                            )

                        Text(
                            text = "Humidity: ${data.current.humidity}%",
                            fontSize = 18.sp,
                            fontFamily = Comfortaa,
                            color =color
                        )

                        Text(
                            text = "UV Index: ${data.current.uv}",
                            fontSize = 18.sp,
                            fontFamily = Comfortaa,
                            color =color
                        )

                            Text(
                                text = "Wind Speed (kph): ${data.current.wind_kph}",
                                fontSize = 18.sp,
                                fontFamily = Comfortaa,
                                color =color
                            )

                            Text(
                                text = "Wind Degree: ${data.current.wind_degree}",
                                fontSize = 18.sp,
                                fontFamily = Comfortaa,
                                color =color
                            )

                            Text(
                                text = "Wind Direction: ${data.current.wind_dir}",
                                fontSize = 18.sp,
                                fontFamily = Comfortaa,
                                color =color
                            )

                            Text(
                                text = "Pressure (mb): ${data.current.pressure_mb}",
                                fontSize = 18.sp,
                                fontFamily = Comfortaa,
                                color =color
                            )

                            Text(
                                text = "Pressure (in): ${data.current.pressure_in}",
                                fontSize = 18.sp,
                                fontFamily = Comfortaa,
                                color =color
                            )


                            Text(
                                text = "Visibility (Miles): ${data.current.vis_miles}",
                                fontSize = 18.sp,
                                fontFamily = Comfortaa,
                                color =color
                            )


                            Text(
                                text = "Gust Speed (mph): ${data.current.gust_mph}",
                                fontSize = 18.sp,
                                fontFamily = Comfortaa,
                                color =color
                            )

                            Text(
                                text = "Air Quality (CO): ${data.current.air_quality.co}",
                                fontSize = 18.sp,
                                fontFamily = Comfortaa,
                                color =color
                            )

                            Text(
                                text = "Air Quality (NO2): ${data.current.air_quality.no2}",
                                fontSize = 18.sp,
                                fontFamily = Comfortaa,
                                color =color
                            )

                            Text(
                                text = "Air Quality (O3): ${data.current.air_quality.o3}",
                                fontSize = 18.sp,
                                fontFamily = Comfortaa,
                                color =color
                            )

                            Text(
                                text = "Air Quality (SO2): ${data.current.air_quality.so2}",
                                fontSize = 18.sp,
                                fontFamily = Comfortaa,
                                color =color
                            )

                            Text(
                                text = "Air Quality (PM2.5): ${data.current.air_quality.pm2_5}",
                                fontSize = 18.sp,
                                fontFamily = Comfortaa,
                                color =color
                            )

                            Text(
                                text = "Air Quality (PM10): ${data.current.air_quality.pm10}",
                                fontSize = 18.sp,
                                fontFamily = Comfortaa,
                                color =color
                            )
                        }
                }
            })
        }
    }

@Composable
fun askPermissionDialog(
    onDismissRequest: () -> Unit,
    onPermissionGranted: (Location) -> Unit,
    onPermissionDenied: () -> Unit
) {
    val context = LocalContext.current
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {

            val a = ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
            if (a == PackageManager.PERMISSION_GRANTED) {
                var fusedLocation: FusedLocationProviderClient =
                    LocationServices.getFusedLocationProviderClient(context)
                fusedLocation.getCurrentLocation(LocationRequest.QUALITY_HIGH_ACCURACY, null)
                    .addOnSuccessListener { location: Location? ->
                        run {
                            if (location != null) {
                                onPermissionGranted(location)
                                Log.e(
                                    "Re3",
                                    "This Lcoation is ${location.latitude} ${location.longitude} "
                                )
                            } else {
                                Log.e(
                                    "Location Null",
                                    "This error show that Location Recived Null"
                                )
                            }
                        }
                    }
            }
        } else {
            onPermissionDenied()
        }
    }

    val onPermissionDeniedWithRationale: () -> Unit = {
        val permission = android.Manifest.permission.ACCESS_FINE_LOCATION
        locationPermissionLauncher.launch(permission)
    }

    AlertDialog(
        icon = {
            Icon(painterResource(id = R.drawable.location_on), contentDescription = "null")
        },
        title = {
            Text(text = "Location Permission")
        },
        text = {
            Text(
                text = "To enhance your diary entries with weather information, our app uses your location for real-time data. Rest assured, we don't store or use your location for anything else."
            )
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    // Launch the permission request when the "Confirm" button is clicked.
                    val permission = android.Manifest.permission.ACCESS_FINE_LOCATION
                    locationPermissionLauncher.launch(permission)
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Dismiss")
            }
        }
    )
}

@Composable
fun askStoragePermissionDialog(
    onDismissRequest: () -> Unit,
    onPermissionGranted: () -> Unit,
    onPermissionDenied: () -> Unit
) {
    val context = LocalContext.current
    val storagePermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            val permission = android.Manifest.permission.READ_EXTERNAL_STORAGE
            val permissionStatus = ContextCompat.checkSelfPermission(
                context,
                permission
            )
            if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
                onPermissionGranted()
            } else {
                onPermissionDenied()
            }
        } else {
            onPermissionDenied()
        }
    }

    AlertDialog(
        modifier = Modifier.fillMaxWidth(),
        icon = {
            Icon(painterResource(R.drawable.folder), contentDescription = "Storage Icon", modifier = Modifier.fillMaxWidth())
        },
        title = {
            Text(text = "Storage Access Permission")
        },
        text = {
            Text(
                text = "To enhance your diary entries with attachment information, our app requires access to your device's storage. We only use this access to save and retrieve attachments for your diary entries."
            )
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val permission = android.Manifest.permission.READ_EXTERNAL_STORAGE
                    storagePermissionLauncher.launch(permission)
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Dismiss")
            }
        }
    )
}


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun storagePermissionSnackBar(context: Context) {
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
                ) {
                    Text(text = "Storage access permission is required to save and retrieve attachments for your diary entries.")
                }
            }
        }
    }
}



