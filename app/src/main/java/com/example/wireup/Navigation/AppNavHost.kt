package com.example.wireup.Navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.navArgument
import com.example.wireup.ui.Screen.AboutScreen
import com.example.wireup.ui.Screen.AccountScreen
import com.example.wireup.ui.Screen.CommentScreen
import com.example.wireup.ui.Screen.EditProfileScreen
import com.example.wireup.ui.Screen.FlipFlopScreen
import com.example.wireup.ui.Screen.FollowNodes
import com.example.wireup.ui.Screen.FriendsScreen
import com.example.wireup.ui.Screen.HelpScreen
import com.example.wireup.ui.Screen.HomeScreen
import com.example.wireup.ui.Screen.LikedScreen
import com.example.wireup.ui.Screen.NodeScreen
import com.example.wireup.ui.Screen.OpenAudioPodcast
import com.example.wireup.ui.Screen.OpenVideoPodcast
import com.example.wireup.ui.Screen.PodcastScreen
import com.example.wireup.ui.Screen.ProfileScreenViewMode
import com.example.wireup.ui.Screen.ReadMore
import com.example.wireup.ui.Screen.SavedScreen
import com.example.wireup.ui.Screen.SearchScreen
import com.example.wireup.ui.Screen.SettingsScreen
import com.example.wireup.ui.Screen.SplashScreen
import com.example.wireup.ui.Screen.TagsScreen
import com.example.wireup.ui.Screen.login.AuthenticationScreen
import com.example.wireup.ui.Screen.profile.ProfileScreen
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = NavigationItem.Splash.route
    ) {
        composable(NavigationItem.Splash.route){
            SplashScreen {
                val destination = if (FirebaseAuth.getInstance().currentUser == null) {
                    NavigationItem.Authentication.route
                } else {
                    NavigationItem.Home.route
                }
                navController.navigate(destination) {
                    popUpTo(NavigationItem.Splash.route) { inclusive = true }
                    launchSingleTop = true
                }
            }
        }
        composable(NavigationItem.Authentication.route , enterTransition = {
            fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
        },
            exitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            }
        ) {
            AuthenticationScreen(navController = navController)
        }


        composable(NavigationItem.Home.route, enterTransition = {
            fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
        },
            exitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            }
        ) {
            HomeScreen(navController = navController)
        }


        composable(NavigationItem.FlipFlop.route, enterTransition = {
            fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
        },
            exitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            }
        ) {
            FlipFlopScreen( navController = navController)
        }


        composable(NavigationItem.Podcast.route, enterTransition = {
            fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
        },
            exitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            }
        ) {
            PodcastScreen( navController = navController)
        }

        composable(NavigationItem.Profile.route, enterTransition = {
            fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
        },
            exitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            }
        ) {
            ProfileScreen(navController = navController)
        }

        composable(NavigationItem.Search.route, enterTransition = {
            fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
        },
            exitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            }
        ) {
            SearchScreen( navController = navController)
        }

        composable(NavigationItem.Edit.route, enterTransition = {
            fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
        },
            exitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            }
        ) {
            EditProfileScreen( navController = navController)
        }

        composable(NavigationItem.Tags.route + "/{newsId}", enterTransition = {
            fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
        },
            exitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            }
        ) {backStackEntry ->
            val tag = backStackEntry.arguments?.getString("newsId").toString()
            TagsScreen( navController = navController, tag)
        }




        composable(NavigationItem.Node.route, enterTransition = {
            fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
        },
            exitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            }
        ) {
            NodeScreen(navController = navController)
        }
        composable(NavigationItem.Friends.route, enterTransition = {
            fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
        },
            exitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            }
        ) {
            FriendsScreen( navController = navController)
        }

        composable(NavigationItem.Saved.route, enterTransition = {
            fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
        },
            exitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            }
        ) {
            SavedScreen( navController = navController)
        }
        composable(NavigationItem.Settings.route, enterTransition = {
            fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
        },
            exitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            }
        ) {
            SettingsScreen( navController = navController)
        }
        composable(NavigationItem.About.route, enterTransition = {
            fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
        },
            exitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            }
        ) {
            AboutScreen( navController = navController)
        }
        composable(NavigationItem.Account.route, enterTransition = {
            fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
        },
            exitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            }
        ) {
            AccountScreen( navController = navController)
        }

        composable(NavigationItem.ReadMore.route + "/{newsId}", enterTransition = {
            fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
        },
            exitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            }
        ) { backStackEntry ->
            val newsId = backStackEntry.arguments?.getString("newsId").toString()
            ReadMore(navController = navController, newsId)
        }
        composable(NavigationItem.VideoPodcastOpened.route + "/{videoLink}/{heading}",
            arguments = listOf(
                navArgument("videoLink") { type = NavType.StringType },
                navArgument("heading") { type = NavType.StringType }
            ), enterTransition = {
                fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
            },
            exitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            }

        ) { backStackEntry ->
            val videoLink = backStackEntry.arguments?.getString("videoLink").toString()
            val videoHeading = backStackEntry.arguments?.getString("heading").toString()
            OpenVideoPodcast(navController = navController, videoLink, videoHeading)
        }
        composable(NavigationItem.AudioPodcastOpened.route, enterTransition = {
            fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
        },
            exitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            }
        ){
            OpenAudioPodcast(navController = navController)
        }

        composable(NavigationItem.ProfileViewMode.route + "/{userId}",
            deepLinks = listOf(navDeepLink { uriPattern = "https://wireup.com/user/{userId}" }),
            enterTransition = {
            fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
        },
            exitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            }
        ) { entry ->
            entry.arguments?.getString("userId")?.let { userId ->
                ProfileScreenViewMode(navController, userId)
            }
        }

        composable(NavigationItem.Liked.route, enterTransition = {
            fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
        },
            exitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            }
        ) {
            LikedScreen( navController = navController)
        }

        composable(NavigationItem.FollowNodes.route, enterTransition = {
            fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
        },
            exitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            }
        ){
            FollowNodes(navController = navController)
        }

        composable(NavigationItem.Help.route, enterTransition = {
            fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
        },
            exitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            }
        ){
            HelpScreen(navController = navController)
        }

        composable(NavigationItem.Comment.route + "/{tweetId}", enterTransition = {
            fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
        },
            exitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(durationMillis = 300)) // Quick fade in
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300)) // Quick fade out
            }
        ){backStackEntry ->
            val tweetId = backStackEntry.arguments?.getString("tweetId").toString()
            CommentScreen(navController = navController, tweetId)
        }


    }
}
