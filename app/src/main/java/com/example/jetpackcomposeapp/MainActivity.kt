package com.example.jetpackcomposeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kotlinx.coroutines.launch
import androidx.compose.runtime.*



data class Student(val name: String, val group: String)

enum class Screen {
    Home,
    Menu
}

private val textStyle =
    TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black
    )

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
fun StudentListScreen(students: List<Student>, navigateToDetail: (Student) -> Unit) {
    // Экран со списком студентов
    // Можно использовать LazyColumn для отображения списка студентов
    LazyColumn {
        items(students) { student ->
            // Нажатие на студента должно вызвать переход на экран деталей
            Text(
                text = "ФИО: ${student.name}\nГруппа: ${student.group}",
                style = textStyle,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(16.dp)
                    .clickable { navigateToDetail(student) }
            )
        }
    }
}

@Composable
fun DrawerItem(
    screen: Screen,
    currentScreen: Screen,
    onClick: (Screen) -> Unit
) {
    Text(
        text = stringResource(id = R.string.screen_home),
        style = textStyle,
        modifier = Modifier
            .padding(16.dp)
            .clickable {
                if (screen != currentScreen) {
                    onClick(screen)
                }
            }
    )
}

@Composable
fun DrawerContent(
    currentScreen: Screen,
    onScreenSelected: (Screen) -> Unit
) {
    Column {
        DrawerItem(
            screen = Screen.Home,
            currentScreen = currentScreen,
            onClick = onScreenSelected
        )
        DrawerItem(
            screen = Screen.Menu,
            currentScreen = currentScreen,
            onClick = onScreenSelected
        )
    }
}


@Composable
fun StudentDetailScreen(student: Student) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "ФИО: ${student.name}\nГруппа: ${student.group}",
            style = textStyle,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun MyApp(students: List<Student>) {
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Home) }


    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Список студентов")
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                backgroundColor = Color.Blue
            ) {

                IconButton(
                    onClick = {
                        scope.launch {
                            scaffoldState.drawerState.open()
                        }
                    }
                ) {
                    Icon(imageVector = Icons.Default.Menu, contentDescription = "Меню")
                }
            }
        },
        drawerContent = {
            DrawerContent(
                currentScreen = currentScreen,
                onScreenSelected = { newScreen ->
                    currentScreen = newScreen // Обновляем currentScreen с использованием mutableStateOf
                    scope.launch {
                        scaffoldState.drawerState.close()
                    }
                }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "studentList"
        ) {
            composable("studentList") {
                StudentListScreen(students) { student ->
                    navController.navigate("studentDetail/${student.name}/${student.group}")
                }
            }
            composable(
                "studentDetail/{studentName}/{group}",
                arguments = listOf(
                    navArgument("studentName") { type = NavType.StringType },
                    navArgument("group") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val studentName = backStackEntry.arguments?.getString("studentName")
                val group = backStackEntry.arguments?.getString("group")
                if (studentName != null && group != null) {
                    val student = students.find { it.name == studentName && it.group == group }
                    if (student != null) {
                        StudentDetailScreen(student)
                    }
                }
            }
        }
    }
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

