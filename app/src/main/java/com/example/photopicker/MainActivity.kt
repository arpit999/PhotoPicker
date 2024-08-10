package com.example.photopicker

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.photopicker.ui.theme.PhotoPickerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            // Callback is invoked after the user selects a media item or closes the
            // photo picker.
            if (uri != null) {
                Log.d("PhotoPicker", "Selected URI: $uri")
            } else {
                Log.d("PhotoPicker", "No media selected")
            }
        }

        setContent {
            PhotoPickerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    PhotoPickerContent(
                        onSingleImageClick = {
                            // Launch the photo picker and let the user choose only images.
                            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                        },
                        singleImageContent = {
                        },
                        onMultiImageClick = {},
                        multiImageContent = {},
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }

//        // Include only one of the following calls to launch(), depending on the types
//        // of media that you want to let the user choose from.
//
//        // Launch the photo picker and let the user choose images and videos.
//        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))
//
//        // Launch the photo picker and let the user choose only images.
//        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
//
//        // Launch the photo picker and let the user choose only videos.
//        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.VideoOnly))
//
//        // Launch the photo picker and let the user choose only images/videos of a
//        // specific MIME type, such as GIFs.
//        val mimeType = "image/gif"
//        pickMedia.launch(
//            PickVisualMediaRequest(
//                ActivityResultContracts.PickVisualMedia.SingleMimeType(mimeType)
//            )
//        )

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