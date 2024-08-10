package com.example.photopicker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.photopicker.ui.theme.PhotoPickerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PhotoPickerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PhotoPickerContent(
                        singleImageContent = {},
                        onSingleImageClick = {},
                        onMultiImageClick = {},
                        multiImageContent = {},
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun PhotoPickerContent(
    onSingleImageClick: () -> Unit,
    singleImageContent: @Composable () -> Unit,
    onMultiImageClick: () -> Unit,
    multiImageContent: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        Button(onClick = onSingleImageClick) {
            Text(text = "Single Image")
        }

        Button(onClick = onMultiImageClick) {
            Text(text = "Multiple Image")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PhotoPickerTheme {
        PhotoPickerContent(
            onSingleImageClick = { /*TODO*/ },
            singleImageContent = { /*TODO*/ },
            onMultiImageClick = { /*TODO*/ },
            multiImageContent = { /*TODO*/ })
    }
}