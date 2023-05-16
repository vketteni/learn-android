package com.example.learn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent


import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.learn.ui.navigation.LearnNavGraph
import com.example.learn.ui.theme.ScratchynotesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScratchynotesTheme {
                LearnNavGraph()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {


}
