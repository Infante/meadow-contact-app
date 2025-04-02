package com.roberto.meadow.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.compose.*
import com.roberto.meadow.ui.screens.ContactDetailScreen
import com.roberto.meadow.ui.screens.ContactListScreen

@Composable
fun AppNavGraph(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "contacts",
        modifier = modifier
    ) {
        composable("contacts") {
            ContactListScreen(
                onContactClick = { contactId ->
                    navController.navigate("contact/$contactId")
                }
            )
        }
        composable(
            "contact/{contactId}",
            arguments = listOf(navArgument("contactId") { type = NavType.StringType })
        ) { backStackEntry ->
            val contactId = backStackEntry.arguments?.getString("contactId")
            ContactDetailScreen(contactId)
        }
    }
}
