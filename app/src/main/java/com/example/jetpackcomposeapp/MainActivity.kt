package com.example.jetpackcomposeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpackcomposeapp.ui.theme.JetpackComposeAppTheme

data class Student(val name: String, val group: String)

private val textStyle =

    TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black
    )
// Другие стили
/*
   TextStyle(
       fontFamily = FontFamily.Serif,
       fontSize = 21.sp,
       fontWeight = FontWeight.Light,
       color = Color.Magenta
   )
   */


@Composable
fun StudentInfo(studentName: String, group: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "ФИО: $studentName\nГруппа: $group",
            style = textStyle,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun MyApp(students: List<Student>) {
    MaterialTheme(
        typography = Typography(
            TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        ),
        content = {
            Surface(
                color = MaterialTheme.colorScheme.background,
                modifier = Modifier.fillMaxSize()
            ) {
                LazyColumn {
                    items(students) { student ->
                        StudentInfo(student.name, student.group)
                    }
                }
            }
        }
    )
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val students = listOf(
            Student("Осипов М.А.", "ИКБО-12-21"),
            Student("RomanRazdorov", "ИКБО-100-23"),
            Student("Programming war crimes 1", "ИКБО-102-23"),
            Student("Escape the backrooms 2", "ИКБО-100-23"),
            // Добавьте других студентов по аналогии
        )
        setContent {
            MyApp(students)
        }
    }
}

