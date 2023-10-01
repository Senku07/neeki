package com.example.nekki

import android.annotation.SuppressLint
import android.graphics.drawable.Icon
import android.os.Bundle
import android.service.quicksettings.Tile
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.OverscrollEffect
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.AccountBox
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material.icons.sharp.CheckCircle
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nekki.ui.theme.Calendar
import com.example.nekki.ui.theme.Diary
import com.example.nekki.ui.theme.Entries
import com.example.nekki.ui.theme.NekkiTheme
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.Stack
import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationRequest
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.LaunchedEffect
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat
import com.example.nekki.ui.theme.checkcInternetStatus
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.delay



enum class CurrentState {
    entries,
    calendar,
    diary
}

val Comfortaa = FontFamily(
    Font(R.font.comfortaa_light, FontWeight.Light),
    Font(R.font.comfortaa_regular, FontWeight.Normal),
//    Font(R.font.Comfortaa_Medium, FontStyle.Italic),
    Font(R.font.comfortaa_medium, FontWeight.Medium),
    Font(R.font.comfortaa_semibold, FontWeight.SemiBold),
    Font(R.font.comfortaa_bold, FontWeight.Bold)
)

val SkyBlue = Color(33, 150, 243)

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


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
                                  SkyBlue,
                                  shape = RoundedCornerShape(10.dp, 0.dp, 0.dp, 10.dp)
                              )
                              .background(
                                  if (choosenState == CurrentState.entries) SkyBlue else Color.White,
                                  shape = RoundedCornerShape(10.dp, 0.dp, 0.dp, 10.dp)
                              )
                      ) {
                          Text(
                              text = "Entries",
                              style = TextStyle(
                                  if(choosenState == CurrentState.entries)Color.White else SkyBlue ,
                                  letterSpacing = 1.sp,
                                  fontFamily = Comfortaa
                              ),
                              modifier = Modifier.padding(horizontal = 10.dp)
                          )
                      }
                      TextButton(
                              onClick = { choosenState = CurrentState.calendar },
                          Modifier
                              .border(
                                  2.dp,
                                  SkyBlue,
                                  shape = RoundedCornerShape(0.dp, 0.dp, 0.dp, 0.dp)
                              )
                              .background(
                                  if (choosenState == CurrentState.calendar) SkyBlue else Color.White,

                                  shape = RoundedCornerShape(0.dp, 0.dp, 0.dp, 0.dp)
                              )
                      ) {
                      Text(
                          text = "Calendar",
                          style = TextStyle(
                              if(choosenState == CurrentState.calendar)Color.White else SkyBlue ,
                              letterSpacing = 1.sp,
                              fontFamily = Comfortaa
                          ),
                          modifier = Modifier.padding(horizontal = 10.dp)
                      )
                  }
                     NaviDiary(
                         context = LocalContext.current,
                         choosenState = choosenState,
                         choosenStateFunc = {choosenState = CurrentState.diary},
                         latlang = {l-> latitude = l.latitude;longitude = l.longitude}
                     )
                  }
                      Text(text = "Diary", style = TextStyle(fontSize = 20.sp, fontFamily = Comfortaa), color = SkyBlue)
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
                   }
                   else if(choosenState == CurrentState.diary){
                       Diary(latitude,longitude)
                   }
               }
           }
        }
    )
}

@Composable
fun NaviDiary(context: Context,choosenState: CurrentState,choosenStateFunc: () -> Unit,latlang: (Location) -> Unit){
   TextButton(
        onClick = { choosenStateFunc()

            when{
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
            }
        },
        Modifier
            .border(
                2.dp,
                SkyBlue,
                shape = RoundedCornerShape(0.dp, 10.dp, 10.dp, 0.dp)
            )
            .background(
                if (choosenState == CurrentState.diary) SkyBlue else Color.White,
                shape = RoundedCornerShape(0.dp, 10.dp, 10.dp, 0.dp)
            )
    ) {
        Text(
            text = "Diary",
            style = TextStyle(
                if(choosenState == CurrentState.diary)Color.White else SkyBlue ,
                letterSpacing = 1.sp,
                fontFamily = Comfortaa
            ),
            modifier = Modifier.padding(horizontal = 10.dp)
        )
    }
}

