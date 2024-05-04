package com.example.convenii.view

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.convenii.R
import com.example.convenii.view.account.SignInScreen
import com.example.convenii.view.account.StartScreen
import com.example.convenii.view.account.register.Register1Screen
import com.example.convenii.view.account.register.Register2Screen
import com.example.convenii.view.account.register.Register3Screen
import com.example.convenii.view.account.register.Register4Screen
import com.example.convenii.view.account.register.Register5Screen
import com.example.convenii.view.detail.ProductDetailScreen
import com.example.convenii.view.detail.ReviewAddScreen
import com.example.convenii.view.detail.ReviewDetailScreen
import com.example.convenii.view.main.home.HomeScreen
import com.example.convenii.view.main.home.MoreGSScreen
import com.example.convenii.view.main.home.MoreScreen
import com.example.convenii.view.main.search.SearchMainScreen
import com.example.convenii.view.profile.ProfileScreen

enum class ConveniiScreen(val route: String, val title: String, val icon: Int? = null) {
    Start("start", "시작", R.drawable.icon_home),
    SignIn("signIn", "로그인", R.drawable.icon_search),
    Register1("register1", "회원가입", R.drawable.icon_profile),
    Register2("register2", "회원가입"),
    Register3("register3", "회원가입"),
    Register4("register4", "회원가입"),
    Register5("register5", "회원가입"),
    Home("home", "홈", R.drawable.icon_home),
    ProductDetail("productDetail/{productIdx}", "상품 상세"),
    ReviewDetail("reviewDetail/{productIdx}", "리뷰 상세"),
    ReviewAdd("reviewAdd", "리뷰 작성", R.drawable.icon_search),
    SearchMain("searchMain", "검색", R.drawable.icon_search),
    MoreGS("moreGS", "더보기"),
    More("more/{type}", "더보기"),
    Profile("profile", "내정보", R.drawable.icon_profile),

}

fun NavGraphBuilder.composableWithAnimation(
    route: String,
    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit
) {
    composable(
        route = route,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(400)
            )

        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(400)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(400)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(400)
            )
        },
        content = content

    )
}

@Composable
fun ConveniiApp(
    startDestination: String
) {
    val navController = rememberNavController()


    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = { slideInVertically(initialOffsetY = { 500 }) },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {


        composableWithAnimation(route = ConveniiScreen.Start.name) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(ConveniiScreen.Start.name)
            }
            StartScreen(
                navController = navController,
            )
        }
        composableWithAnimation(route = ConveniiScreen.SignIn.name) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(ConveniiScreen.Start.name)
            }
            SignInScreen(
                navController = navController,
                parentEntry = parentEntry
            )
        }
        composableWithAnimation(route = ConveniiScreen.Register1.name) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(ConveniiScreen.Start.name)
            }
            Register1Screen(
                navController = navController,
                viewModel = hiltViewModel(parentEntry)
            )
        }

        composableWithAnimation(route = ConveniiScreen.Register2.name) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(ConveniiScreen.Start.name)
            }
            Register2Screen(
                navController = navController,
                viewModel = hiltViewModel(parentEntry)

            )
        }

        composableWithAnimation(route = ConveniiScreen.Register3.name) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(ConveniiScreen.Start.name)
            }

            Register3Screen(
                navController = navController,
                viewModel = hiltViewModel(parentEntry)

            )
        }

        composableWithAnimation(route = ConveniiScreen.Register4.name) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(ConveniiScreen.Start.name)
            }
            Register4Screen(
                navController = navController,
                viewModel = hiltViewModel(parentEntry)

            )
        }

        composableWithAnimation(route = ConveniiScreen.Register5.name) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(ConveniiScreen.Start.name)
            }
            Register5Screen(
                navController = navController,
                viewModel = hiltViewModel(parentEntry)
            )
        }

        composable(route = ConveniiScreen.Home.name,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(ConveniiScreen.Home.name)
            }
            HomeScreen(
                navController = navController,
                viewModel = hiltViewModel(parentEntry)
            )
        }

        composable(
            route = ConveniiScreen.ProductDetail.route,
            arguments = listOf(navArgument("productIdx") { type = NavType.StringType })
        ) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(ConveniiScreen.ProductDetail.route)
            }
            val productIdx = backStackEntry.arguments?.getString("productIdx")
            ProductDetailScreen(
                navController = navController,
                productIdx = productIdx,
                viewModel = hiltViewModel(parentEntry)
            )
        }

        composable(
            route = ConveniiScreen.ReviewDetail.route,
            arguments = listOf(navArgument("productIdx") { type = NavType.StringType })
        ) { backStackEntry ->
            val productIdx = backStackEntry.arguments?.getString("productIdx")
            ReviewDetailScreen(
                navController = navController,
                productIdx = productIdx
            )
        }

        composable(route = ConveniiScreen.ReviewAdd.name) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(ConveniiScreen.ProductDetail.route)
            }
            ReviewAddScreen(
                navController = navController,
                viewModel = hiltViewModel(parentEntry)
            )
        }

        composable(route = ConveniiScreen.SearchMain.name) {
            SearchMainScreen(
                navController = navController
            )
        }

        composable(route = ConveniiScreen.MoreGS.name) {
            MoreGSScreen(
                navController = navController,
            )
        }

        composable(
            route = ConveniiScreen.More.route,
            arguments = listOf(navArgument("type") { type = NavType.StringType })
        ) { backStackEntry ->
            val type = backStackEntry.arguments?.getString("type")
            MoreScreen(
                navController = navController,
                type = type
            )
        }

        composable(
            route = ConveniiScreen.Profile.name,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
        ) {
            ProfileScreen(
                navController = navController
            )
        }
    }
}