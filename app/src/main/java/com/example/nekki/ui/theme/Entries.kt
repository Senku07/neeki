package com.example.nekki.ui.theme

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.nekki.R
import org.json.JSONObject
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Entries(sharedPreferences: SharedPreferences) {
    val context = LocalContext.current
    val entriesFolder = File(context.filesDir, "entries")
    var noEntry by remember {
        mutableStateOf(false)
    }
    var showSearch by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        AsyncImage(
            R.drawable.cloud,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )


        var onlyFavourite by remember {
            mutableStateOf(false)
        }
        var refresh by remember {
            mutableStateOf(false)
        }


        if (noEntry) {
            noEntries()
        }

        var searchInput by remember { mutableStateOf(TextFieldValue("")) }

        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            if (entriesFolder.exists() && entriesFolder.isDirectory) {
                val filesInEntriesFolder = entriesFolder.listFiles()
                if (filesInEntriesFolder != null) {

                    if(showSearch){
                        val keyboardController = LocalSoftwareKeyboardController.current

                        OutlinedTextField(modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 8.dp),
                            leadingIcon = {
                                Icon(
                                    Icons.Filled.Search,
                                    contentDescription = null,
                                    tint = Color.White
                                )
                            },
                            value = searchInput,
                            onValueChange = { searchInput = it },
                            textStyle = TextStyle(
                            fontFamily = FontFamily(Font(fontFamilyList[0])),
                            color = Color.White,
                            lineHeight = 0.sp
                        ),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                           keyboardActions = KeyboardActions( onSearch = { keyboardController?.hide() }),
                            colors = OutlinedTextFieldDefaults.colors(
                                cursorColor = Color.White,
                                focusedBorderColor = Color.White,
                                unfocusedBorderColor = Color.White,
                            ),
                            trailingIcon = {
                                IconButton(onClick = {
                                    searchInput = TextFieldValue("")
                                    showSearch = false
                                }) {
                                    Icon(Icons.Outlined.Clear, contentDescription = null,tint = Color.White)
                                }
                            }

                        )
                    }


                    for ((index, file) in filesInEntriesFolder.reversed().withIndex()) {
                        val jsonString = file.readText()
                        val jsonObject = JSONObject(jsonString)
                        val dateString = jsonObject.getString("time")
                        val title = jsonObject.getString("title")
                        val content = jsonObject.getString("content")
                        val emoji = jsonObject.getString("emojiIndex")
                        val weatherIcon = jsonObject.getString("weatherEmoji")
                        var favourite by remember {
                            mutableStateOf(jsonObject.getString("favourite").toBoolean())
                        }
                        if (dateString.isNotEmpty()) {
                            val inputDateFormat =
                                "EEE MMM dd HH:mm:ss zzz yyyy"

                            val inputDate =
                                SimpleDateFormat(inputDateFormat, Locale.ENGLISH).parse(
                                    dateString
                                )
                            if (inputDate != null) {
                                val outputDateFormatDate =
                                    SimpleDateFormat("dd").format(inputDate)
                                val outputDateFormatDay =
                                    SimpleDateFormat("EEE").format(inputDate)
                                val outputDateFormatMonthYearTime =
                                    SimpleDateFormat("MMM HH:mm:ss").format(inputDate)

                                if(searchInput.text == "") {
                                    if (onlyFavourite) {
                                        if (favourite) {
                                            entriesView(
                                                date = outputDateFormatDate,
                                                day = outputDateFormatDay,
                                                monthTime = outputDateFormatMonthYearTime,
                                                title = title,
                                                content = content,
                                                emoji = emoji,
                                                weatherIcon = weatherIcon,
                                                index = index,
                                                favourite = favourite,
                                                file = file,
                                                jsonObject = jsonObject,
                                                refresh = { refresh = !refresh },
                                                updateFavourite = { favourite = !favourite }
                                            )
                                        }
                                    } else {
                                        entriesView(
                                            date = outputDateFormatDate,
                                            day = outputDateFormatDay,
                                            monthTime = outputDateFormatMonthYearTime,
                                            title = title,
                                            content = content,
                                            emoji = emoji,
                                            weatherIcon = weatherIcon,
                                            index = index,
                                            favourite = favourite,
                                            file = file,
                                            jsonObject = jsonObject,
                                            refresh = { refresh = !refresh },
                                            updateFavourite = { favourite = !favourite }
                                        )
                                    }
                                }else{
                                    Log.e("Searched","The valur of searched input ${searchInput.text}")
                                   if( title.contains(searchInput.text,true) ||
                                       content.contains(searchInput.text,true) ||
                                       outputDateFormatDate.contains(searchInput.text,true)
                                       ){
                                        entriesView(
                                            date = outputDateFormatDate,
                                            day = outputDateFormatDay,
                                            monthTime = outputDateFormatMonthYearTime,
                                            title = title,
                                            content = content,
                                            emoji = emoji,
                                            weatherIcon = weatherIcon,
                                            index = index,
                                            favourite = favourite,
                                            file = file,
                                            jsonObject = jsonObject,
                                            refresh = { refresh = !refresh },
                                            updateFavourite = { favourite = !favourite }
                                        )
                                    }
                                }

                            } else {
                                Log.e("Error", "Failed to parse date from $dateString")
                            }
                        }
                    }
                } else {
                    noEntry = true
                }
            } else {
                noEntry = true
            }


        }

        if(!showSearch) {
            bottomNav(onlyShowFavourite = {
                onlyFavourite = !onlyFavourite
            },
                searchDiary = {
                    showSearch = !showSearch
                }
            )
        }


    }
}

@Composable
fun entriesView(date: String,day : String ,monthTime: String,title: String,content:String,weatherIcon : String,emoji: String,index : Int,favourite: Boolean,file: File,jsonObject: JSONObject,refresh:() -> Unit,updateFavourite: () -> Unit){
    Box(modifier = Modifier
        .padding(horizontal = 6.dp, vertical = 6.dp)
        .fillMaxWidth()){
    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(
        containerColor =  Color.White), shape = RoundedCornerShape(8.dp)) {
        Row(modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween){
            Column(modifier = Modifier.padding(horizontal = 4.dp), horizontalAlignment = Alignment.CenterHorizontally){
                Text(text = date, fontSize = 28.sp, fontWeight = FontWeight.Bold, lineHeight = 1.sp, color = if(index%2 == 0){  Color(93, 137, 179, 255)}else{Color(252,108,133)},)
                Text(text = "$day.", fontSize = 20.sp, fontWeight = FontWeight.Normal,color = if(index%2 == 0){  Color(93, 137, 179, 255)}else{Color(252,108,133)})
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
            Column(modifier = Modifier.padding(horizontal = 5.dp)){
                Text(text = monthTime,fontSize =14.sp, fontWeight = FontWeight.ExtraLight,color = if(index%2 == 0){  Color(93, 137, 179, 255)}else{Color(252,108,133)})
                Text(text = title,fontSize =18.sp,color = if(index%2 == 0){  Color(93, 137, 179, 255)}else{Color(252,108,133)})

                if(content.length > 15){
                    val shortContent = remember(content) {
                        content.subSequence(0,30).toString()
                    }
                    Text(text = "$shortContent...",fontSize =14.sp,color = Color(93, 137, 179, 255))
                }else{
                    Text(text = content,fontSize =14.sp,color = Color(93, 137, 179, 255))
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxHeight()){
                if(weatherIcon == "N/A"){
                    Icon( painterResource(id = R.drawable.cloud_off), contentDescription = null, tint =  Color(93, 137, 179, 255),modifier = Modifier
                        .width(24.dp)
                        .height(24.dp))
                    }else {
                    AsyncImage(
                        model = "https:$weatherIcon",
                        contentDescription = null, modifier = Modifier
                            .width(34.dp)
                            .height(34.dp)
                    )
                }
                Icon(painter = painterResource(id = emojiList[emoji.toInt()]),contentDescription = null, tint =  if(emoji.toInt() < 10){ Color(93, 137, 179, 255)}else{
                    Color.Unspecified},modifier = Modifier
                    .width(24.dp)
                    .height(24.dp))
                    IconButton(onClick = {
                        updateFavourite()
                        val updatedFavouriteValue = !favourite
                        jsonObject.put("favourite", updatedFavouriteValue)
                        val updatedJsonString = jsonObject.toString()
                        file.writeText(updatedJsonString)
                        refresh()
                    }) {
                        Icon(if(favourite){Icons.Outlined.Favorite}else{Icons.Filled.FavoriteBorder}, contentDescription = null, tint =  Color(93, 137, 179, 255),modifier = Modifier
                            .width(24.dp)
                            .height(24.dp))
                    }
            }
            }
        }
    }
    }
}

@Composable
fun noEntries(){
    Box(modifier = Modifier.fillMaxHeight().fillMaxWidth().background(Color.Unspecified), contentAlignment = Alignment.Center){
           Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center, modifier = Modifier
                    .background(Color.White, shape = RoundedCornerShape(16.dp))
                    .padding(horizontal = 8.dp, vertical = 8.dp)
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    "NO Entries",
                    fontSize = 54.sp,
                    color = Color.DarkGray,
                    letterSpacing = 2.sp,
                    modifier = Modifier.padding(horizontal = 10.dp)
                )
                Text("There are No Diary ", fontSize = 24.sp, color = Color.DarkGray)
                Spacer(modifier = Modifier.height(28.dp))
                Text(
                    "Create Account for Sync",
                    fontSize = 24.sp,
                    color = Color(93, 137, 179, 255),
                    textDecoration = TextDecoration.Underline
                )
                Spacer(modifier = Modifier.height(18.dp))
            }
    }
}

@Composable
fun bottomNav(onlyShowFavourite:() -> Unit,searchDiary : ()-> Unit){
    Row(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(), verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.Center){
        Row(modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(vertical = 16.dp), horizontalArrangement = Arrangement.SpaceAround){
            Icon(Icons.Filled.AccountCircle, contentDescription = null)


            Icon(Icons.Filled.Settings, contentDescription = null)

            IconButton(onClick = { searchDiary() }) {
            Icon(Icons.Filled.Search, contentDescription = null,modifier = Modifier
                .width(24.dp)
                .height(24.dp))
            }

            IconButton(onClick = { onlyShowFavourite() }) {
                Icon(Icons.Filled.Favorite, contentDescription = null)
            }
        }
    }
}

