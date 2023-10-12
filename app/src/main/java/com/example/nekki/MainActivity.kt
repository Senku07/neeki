package com.example.nekki

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nekki.ui.theme.Calendar
import com.example.nekki.ui.theme.Diary
import com.example.nekki.ui.theme.Entries
import com.example.nekki.ui.theme.fontColorList
import com.example.nekki.ui.theme.fontFamilyList


enum class CurrentState {
    entries,
    calendar,
    diary
}


val SkyBlue = Color(33, 150, 243)

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        window.navigationBarColor = R.color.teal_200

        setContent {
            Box(){
               MyApp()
            }

        }
      }
    }

@Composable
fun CustomScaffold(
    topBar: @Composable () -> Unit,
    content: @Composable () -> Unit
) {
    Box(){
        Column(verticalArrangement = Arrangement.SpaceBetween, modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()) {
            topBar()
            content()
        }
    }

}



@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun MyApp() {
    var choosenState by remember {
        mutableStateOf(CurrentState.entries)
    }

    var latitude by remember {
        mutableStateOf(0.0)
    }
    var longitude by remember {
        mutableStateOf(0.0)
    }

    var globalBackgroundImage by remember {
        mutableStateOf(R.drawable.bts)
    }

    val defaultImageUri = Uri.parse("android.resource://${LocalContext.current.packageName}/${R.drawable.cloud}")
    val context = LocalContext.current

    val sharedPreferences = remember {
     context.getSharedPreferences("customaization",Context.MODE_PRIVATE)
    }

    val editor = remember {
        sharedPreferences.edit()
    }

    if (!sharedPreferences.contains("Accentcolor")) {
        editor.putInt("Accentcolor", 1)
    }

    if (!sharedPreferences.contains("Fontfamily")) {
        editor.putInt("Fontfamily", 0)
    }

    if (!sharedPreferences.contains("Fontcolor")) {
        editor.putInt("Fontcolor", 0)
    }

    if (!sharedPreferences.contains("Fontsize")) {
        editor.putInt("Fontsize", 2)
    }

    if (!sharedPreferences.contains("BackgroundImage")) {
        editor.putString("BackgroundImage", defaultImageUri.toString())
    }
    editor.apply()


    val fontColor = sharedPreferences.getInt("Fontcolor",0)
    val fontFamily = sharedPreferences.getInt("Fontfamily",0)
    val accentColor = sharedPreferences.getInt("Accentcolor",1)
    val backGroundImage = sharedPreferences.getString("BackgroundImage",R.drawable.bts.toString())
    val selectedFontFamily = FontFamily(Font(fontFamilyList.get(fontFamily)))
    val selectedFontColor =  fontColorList.get(fontColor)
    val selectedAccentColor =  fontColorList.get(accentColor)

    CustomScaffold(
       topBar = {
              Box(
                  Modifier
                      .fillMaxWidth()
                      .background(color = Color.White)){
                  Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(vertical = 10.dp)) {

                  Row(horizontalArrangement = Arrangement.Center, modifier = Modifier
                      .fillMaxWidth()
                      .padding(vertical = 2.dp)) {
                      TextButton(
                          onClick = { choosenState = CurrentState.entries },
                          Modifier
                              .border(
                                  2.dp,
                                  selectedAccentColor,
                                  shape = RoundedCornerShape(10.dp, 0.dp, 0.dp, 10.dp)
                              )
                              .background(
                                  if (choosenState == CurrentState.entries) selectedAccentColor else selectedFontColor,
                                  shape = RoundedCornerShape(10.dp, 0.dp, 0.dp, 10.dp)
                              )
                      ) {
                          Text(
                              text = "Entries",
                              style = TextStyle(
                                  if(choosenState == CurrentState.entries) selectedFontColor else selectedAccentColor ,
                                  letterSpacing = 1.sp,
                                  fontFamily = selectedFontFamily
                              ),
                              modifier = Modifier.padding(horizontal = 10.dp)
                          )
                      }
                      TextButton(
                              onClick = { choosenState = CurrentState.calendar },
                          Modifier
                              .border(
                                  2.dp,
                                  selectedAccentColor,
                                  shape = RoundedCornerShape(0.dp, 0.dp, 0.dp, 0.dp)
                              )
                              .background(
                                  if (choosenState == CurrentState.calendar) selectedAccentColor else selectedFontColor,

                                  shape = RoundedCornerShape(0.dp, 0.dp, 0.dp, 0.dp)
                              )
                      ) {
                      Text(
                          text = "Calendar",
                          style = TextStyle(
                              if(choosenState == CurrentState.calendar) selectedFontColor else selectedAccentColor,
                              letterSpacing = 1.sp,
                              fontFamily = selectedFontFamily
                          ),
                          modifier = Modifier.padding(horizontal = 10.dp)
                      )
                  }
                     NaviDiary(
                         context = LocalContext.current,
                         selectedFontColor,
                         selectedAccentColor,
                         selectedFontFamily,
                         choosenState = choosenState,
                         choosenStateFunc = {choosenState = CurrentState.diary},
                     )
                  }
//                      Text(text = "Diary", style = TextStyle(fontSize = 20.sp, fontFamily = Comfortaa), color = SkyBlue)
              }
              }
       },
       content = {
           Box(modifier = Modifier
               .fillMaxHeight()
               .fillMaxWidth()
               .background(Color.White)
           ) {
               Column {
                   if(choosenState == CurrentState.entries)
                   {
                       Entries()
                   }
                   else if(choosenState == CurrentState.calendar)
                   {
                       Calendar()
                       BackHandler(true){
                           choosenState = CurrentState.entries
                       }
                   }
                   else if(choosenState == CurrentState.diary){
                       Diary(sharedPreferences)
                       BackHandler(true){
                           choosenState = CurrentState.entries
                       }
                   }
               }
           }
        }
    )
}

@Composable
fun NaviDiary(context: Context,selectedFontColor: Color,selectedAccentColor:Color,selectedFontFamily: FontFamily,choosenState: CurrentState,choosenStateFunc: () -> Unit){
   TextButton(
        onClick = { choosenStateFunc() },
       Modifier
           .border(
               2.dp,
               selectedAccentColor,
               shape = RoundedCornerShape(0.dp, 10.dp, 10.dp, 0.dp)
           )
           .background(
               if (choosenState == CurrentState.diary) selectedAccentColor else selectedFontColor,
               shape = RoundedCornerShape(0.dp, 10.dp, 10.dp, 0.dp)
           )
    ) {
        Text(
            text = "Diary",
            style = TextStyle(
                if(choosenState == CurrentState.diary) selectedFontColor else selectedAccentColor ,
                letterSpacing = 1.sp,
                fontFamily = selectedFontFamily
            ),
            modifier = Modifier.padding(horizontal = 10.dp)
        )
    }
}

