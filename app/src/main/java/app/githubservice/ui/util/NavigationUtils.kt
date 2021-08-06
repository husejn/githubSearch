package app.githubservice.ui.util

import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDirections


fun NavController.navigateSafe(frag: Fragment, directions: NavDirections) {
    if (frag.isDetached || frag.isRemoving) {
        return
    }

    val current: NavDestination? = this.currentDestination
    if (current == null || current.getAction(directions.actionId) != null) {
        this.navigate(directions)
    }
}
