package work.racka.thinkrchive.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Sort
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import work.racka.thinkrchive.R
import work.racka.thinkrchive.ui.theme.Dimens
import work.racka.thinkrchive.ui.theme.Shapes
import work.racka.thinkrchive.ui.theme.ThinkRchiveTheme

@ExperimentalComposeUiApi
@Composable
fun CustomSearchBar(
    modifier: Modifier = Modifier,
    hint: String = "Search Model Name",
    onSearch: (String) -> Unit = { },
    onOptionsClicked: () -> Unit = { },
    onBackButtonClicked: () -> Unit = { },
    keyboardController: SoftwareKeyboardController?
        = LocalSoftwareKeyboardController.current,
    focusManager: FocusManager = LocalFocusManager.current
) {
    var searchText by remember {
        mutableStateOf("")
    }

    var isHintDisplayed by remember {
        mutableStateOf(hint.isNotEmpty())
    }

    BoxWithConstraints(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colors.surface,
                shape = Shapes.large
            )
    ) {
        ConstraintLayout(Modifier.fillMaxWidth()) {
            val (search, field, options) = createRefs()

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .constrainAs(search) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                    }
            ) {
                if (isHintDisplayed) {
                    Icon(
                        imageVector = Icons.Outlined.Search,
                        contentDescription = stringResource(id = R.string.search_icon),
                        tint = MaterialTheme.colors.onSurface,
                        modifier = Modifier
                            .padding(Dimens.MediumPadding.size)
                    )
                } else {
                    Icon(
                        imageVector = Icons.Outlined.ArrowBack,
                        contentDescription = stringResource(id = R.string.back_icon),
                        tint = MaterialTheme.colors.onSurface,
                        modifier = Modifier
                            .padding(Dimens.MediumPadding.size)
                            .background(shape = CircleShape, color = Color.Transparent)
                            .clickable {
                                onBackButtonClicked()
                                searchText = ""
                                keyboardController?.hide()
                                focusManager.clearFocus()
                            }
                    )
                }

            }

            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = Modifier
                    .padding(vertical = Dimens.SmallPadding.size)
                    .constrainAs(field) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(search.end)
                        end.linkTo(options.start)
                        width = Dimension.fillToConstraints
                    }
            ) {
                if (isHintDisplayed) {
                    Text(
                        text = hint,
                        color = MaterialTheme.colors.onSurface,
                        style = MaterialTheme.typography.subtitle1
                    )
                }
                BasicTextField(
                    value = searchText,
                    onValueChange = {
                        searchText = it
                        onSearch(it)
                    },
                    maxLines = 1,
                    cursorBrush = SolidColor(MaterialTheme.colors.primary),
                    singleLine = true,
                    textStyle = MaterialTheme.typography.subtitle1
                        .copy(color = MaterialTheme.colors.onSurface),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                            if (searchText.isBlank()) {
                                focusManager.clearFocus()
                            }
                        }
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .onFocusChanged {
                            isHintDisplayed = !it.isFocused
                        }
                )
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .constrainAs(options) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                    }
            ) {
                if (!isHintDisplayed) {
                    Icon(
                        imageVector = Icons.Outlined.Close,
                        contentDescription = stringResource(id = R.string.clear_icon),
                        tint = MaterialTheme.colors.onSurface,
                        modifier = Modifier
                            .padding(Dimens.MediumPadding.size)
                            .background(shape = CircleShape, color = Color.Transparent)
                            .clickable {
                                onBackButtonClicked()
                                searchText = ""
                            }
                    )
                } else {
                    Icon(
                        imageVector = Icons.Outlined.Sort,
                        contentDescription = stringResource(id = R.string.search_icon),
                        tint = MaterialTheme.colors.onSurface,
                        modifier = Modifier
                            .padding(Dimens.MediumPadding.size)
                            .background(shape = CircleShape, color = Color.Transparent)
                            .clickable {
                                onOptionsClicked()
                                searchText = ""
                                keyboardController?.hide()
                                focusManager.clearFocus()
                            }
                    )
                }
            }
        }
    }
}

@ExperimentalComposeUiApi
@Preview
@Composable
fun CustomSearchBarPreview() {
    ThinkRchiveTheme {
        CustomSearchBar()
    }
}
