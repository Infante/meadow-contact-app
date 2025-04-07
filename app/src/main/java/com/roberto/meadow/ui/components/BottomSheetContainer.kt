package com.roberto.meadow.ui.components

import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetContainer(
    onDismiss: () -> Unit,
    content: @Composable () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { true }
    )
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        dragHandle = null,
        sheetState = sheetState,
    ) {
        content()
    }
}