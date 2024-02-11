package hr.ivandemsic.MyGamesList.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import hr.ivandemsic.MyGamesList.R
import hr.ivandemsic.MyGamesList.data.Personal



@Composable
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun StatsScreen(navigation: NavController) {

    val db=FirebaseFirestore.getInstance().collection("Games")

    var numberOfDocuments by remember {
        mutableStateOf(0)
    }

    var totalPlayTime by remember {
        mutableStateOf(0)
    }

    var totalAchievements by remember {
        mutableStateOf(0)
    }


    db.get().addOnSuccessListener { querySnapshot -> numberOfDocuments = querySnapshot.size()}
    db.get().addOnSuccessListener { result -> var sum=0

    for (document in result.documents){
        val value=document.getString("timePlayed")
        val valueInt=value?.toIntOrNull() ?:0

        sum+=valueInt
    }
        totalPlayTime=sum

    }

    db.get().addOnSuccessListener { result -> var sumA=0

        for (document in result.documents){
            val valueA=document.getString("completed_Achievements")
            val valueInt=valueA?.toIntOrNull() ?:0

            sumA+=valueInt
        }
        totalAchievements=sumA

    }

    Scaffold(bottomBar = {
        Row (modifier= Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
            IconButton(iconResource = R.drawable.badge, text = "Stats",navigation,"StatsScreen")
            IconButton(iconResource = R.drawable.home, text = "Home",navigation,"MainScreen")
            IconButton(iconResource = R.drawable.plus, text = "Add game",navigation,"AddGame")
        }
    }
    ){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Blue1, Blue2),
                        startY = 100f
                    )
                )
        ) {
            Column {
                Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
                    Text(
                        text = "Personal Stats",
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 25.sp,
                            fontStyle = FontStyle.Normal,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier
                            .padding(vertical = 12.dp)
                        )
                }
                    Text(text = "Games played: ${numberOfDocuments}\n" + "\n"+
                            "Total time played: ${formatTime(totalPlayTime.toString())}\n" +"\n" +
                            "Achievements: ${totalAchievements}",
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp)
                        , style = TextStyle(color = Color.White, fontSize = 25.sp)
                    )
                }
            }
        }
    }


