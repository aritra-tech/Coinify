package component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import coinify.composeapp.generated.resources.Res
import coinify.composeapp.generated.resources.search_for_coins
import org.jetbrains.compose.resources.stringResource

@Composable
fun SearchBar(
    search: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    onSearchClick: () -> Unit
) {

    val keyboardController = LocalSoftwareKeyboardController.current

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(CircleShape)

    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = search,
            onValueChange = onValueChange,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                unfocusedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
                focusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                focusedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            singleLine = true,
            leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "") },
            trailingIcon = {
                AnimatedVisibility(search.isNotEmpty()) {
                    IconButton(onClick = {
                        if (search.isNotEmpty()) {
                            onSearchClick()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Send,
                            contentDescription = "Search",
                            tint = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    if (search.isNotEmpty()) {
                        keyboardController?.hide()
                        onSearchClick()
                    }
                }
            ),
            placeholder = {
                Text(
                    text = stringResource(Res.string.search_for_coins),
                    style = TextStyle(color = MaterialTheme.colorScheme.onSecondaryContainer)
                )
            }
        )
    }

}