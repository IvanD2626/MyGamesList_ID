package hr.ivandemsic.MyGamesList.ui.theme

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import hr.ivandemsic.MyGamesList.Routes
import hr.ivandemsic.MyGamesList.data.Game



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun firebaseUI(context: Context,navigation:NavController) {
    val titleX = remember {
        mutableStateOf("")
    }

    val achievementsX = remember {
        mutableStateOf("")
    }

    val descriptionX = remember {
        mutableStateOf("")
    }

    val imageX= remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Blue1, Blue2),
                    startY = 100f
                )
            ),
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
    ) {


        TextField(
            value = titleX.value,
            onValueChange = { titleX.value = it },
            placeholder = { Text(text = "Title") },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            value = achievementsX.value,
            onValueChange = { achievementsX.value = it },
            placeholder = { Text(text = "Number of achievements") },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            value = descriptionX.value,
            onValueChange = { descriptionX.value = it },
            placeholder = { Text(text = "Enter your game description") },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            value = imageX.value,
            onValueChange = { imageX.value = it },
            placeholder = { Text(text = "Enter image address") },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(10.dp))


        Button(
            onClick = {
                if (TextUtils.isEmpty(titleX.value.toString())) {
                    Toast.makeText(context, "Please enter number of achievements", Toast.LENGTH_SHORT).show()
                } else if (TextUtils.isEmpty(achievementsX.value.toString())) {
                    Toast.makeText(context, "Please enter game description", Toast.LENGTH_SHORT)
                        .show()
                } else if (TextUtils.isEmpty(descriptionX.value.toString())) {
                    Toast.makeText(context, "Please enter course description", Toast.LENGTH_SHORT)
                        .show()
                } else if (TextUtils.isEmpty(imageX.value.toString())) {
                    Toast.makeText(context, "Please enter image address", Toast.LENGTH_SHORT)
                        .show()
                }else {
                    addDataToFirebase(
                        imageX.value,titleX.value,achievementsX.value,descriptionX.value,context
                    )
                }
            },

            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Gray1),
        ) {

            Text(text = "Add Game", modifier = Modifier.padding(8.dp))
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                navigation.navigate(Routes.MainScreen)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Gray1),
        ) {
            Text(text = "Home Screen", modifier = Modifier.padding(8.dp))
        }


    }
}

fun addDataToFirebase(
    imageX:String,titleX: String, achievementsX: String, descriptionX: String, context: Context
) {
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    val dbGames: CollectionReference = db.collection("Games")
    val gameList = Game(title = titleX, image = imageX, description = descriptionX, achievements = achievementsX)


    dbGames.add(gameList).addOnSuccessListener {
        Toast.makeText(
            context, "Your game has been added to Firebase Firestore", Toast.LENGTH_SHORT
        ).show()

    }.addOnFailureListener { e ->
        Toast.makeText(context, "Fail to add game \n$e", Toast.LENGTH_SHORT).show()
    }

}
