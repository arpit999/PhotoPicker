package com.example.photopicker

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.photopicker.ui.theme.PhotoPickerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        var singleUri by mutableStateOf(Uri.EMPTY)
        val uriList by mutableStateOf(emptyList<Uri>())

        val pickMedia =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                // Callback is invoked after the user selects a media item or closes the
                // photo picker.
                if (uri != null) {
                    Log.d("PhotoPicker", "Selected URI: $uri")
                    val imageBitmap =
                        applicationContext.contentResolver.openInputStream(uri).use { data ->
                            BitmapFactory.decodeStream(data)
                        }.asImageBitmap()
                    singleUri = uri
                } else {
                    Log.d("PhotoPicker", "No media selected")
                }
            }


        setContent {
            PhotoPickerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    PhotoPickerContent(
                        singleImageSelection = {
                            // Launch the photo picker and let the user choose only images.
                            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                        },
                        multiImageSelection = {},
                        displayImageList = { ImageList(singleUri, uriList) },
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
fun ImageList(singleUri: Uri, uriList: List<Uri>, modifier: Modifier = Modifier) {
    LazyColumn(Modifier.fillMaxSize()) {
        item {
            AsyncImage(
                model = singleUri,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth
            )
        }
        items(uriList) {
            AsyncImage(
                model = singleUri,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Fit
            )
        }
    }
}

@Composable
fun PhotoPickerContent(
    singleImageSelection: () -> Unit,
    multiImageSelection: () -> Unit,
    displayImageList: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier) {

        Row(
            Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = singleImageSelection) {
                Text(text = "Single Image")
            }
            Button(onClick = multiImageSelection) {
                Text(text = "Multiple Image")
            }
        }

        displayImageList()

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PhotoPickerTheme {
        PhotoPickerContent(
            singleImageSelection = { /*TODO*/ },
            multiImageSelection = { /*TODO*/ },
            displayImageList = { /*TODO*/ })
    }
}