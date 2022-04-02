package com.dicoding.picodiploma.mygithubuserapp.helper

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.picodiploma.mygithubuserapp.database.SettingsPreferences
import com.dicoding.picodiploma.mygithubuserapp.model.SearchResponse
import com.dicoding.picodiploma.mygithubuserapp.model.User
import com.dicoding.picodiploma.mygithubuserapp.view.base.ListUserFragment

class ActivityHelper {
    companion object {
        @JvmStatic
        inline fun <reified T : ListUserFragment> commitFragment(
            listUser: List<User>,
            isLoading: Boolean?,
            supportFragmentManager: FragmentManager,
            @IdRes containerViewId: Int,
            fragmentClass: T,
        ) {
            val bundle = Bundle()
            if (isLoading != null) bundle.putBoolean(ListUserFragment.EXTRA_IS_LOADING, isLoading)

            bundle.putParcelable(ListUserFragment.EXTRA_LIST_USER,
                SearchResponse(null, null, listUser))

            fragmentClass.arguments = bundle
            val fragmentTag = T::class.java.simpleName

            val fragment =
                supportFragmentManager.findFragmentByTag(fragmentTag)

            if (!supportFragmentManager.fragments.contains(fragment)) {
                supportFragmentManager.commit { add(containerViewId, fragmentClass, fragmentTag) }
            } else {
                supportFragmentManager.commit {
                    replace(containerViewId,
                        fragmentClass,
                        fragmentTag)
                }
            }
        }

        @JvmStatic
        fun commitFragment(
            listUser: List<User>,
            supportFragmentManager: FragmentManager,
            @IdRes containerViewId: Int,
            fragment: ListUserFragment,
        ) = commitFragment(listUser, null, supportFragmentManager, containerViewId, fragment)

        @JvmStatic
        inline fun <reified T : ViewModel> obtainViewModel(
            activity: AppCompatActivity,
            user: User?,
        ): T {
            val factory = ViewModelFactory.getInstance(activity.application, user, null)
            return ViewModelProvider(activity, factory)[T::class.java]
        }

        @JvmStatic
        inline fun <reified T : ViewModel> obtainViewModel(
            activity: AppCompatActivity,
            preferences: SettingsPreferences,
        ): T {
            val factory = ViewModelFactory.getInstance(activity.application, null, preferences)
            return ViewModelProvider(activity, factory)[T::class.java]
        }
    }
}