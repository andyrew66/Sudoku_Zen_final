package rew.lightgames.sudoku2

import android.content.Context
import android.content.Intent
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import androidx.navigation.fragment.findNavController



class FirstFragment : Fragment() {
    var drawableAnimation = HashSet<AnimatedVectorDrawable>()
    private fun playPop() {

        // TODO: 13/05/2023  
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.main_menu_fragment, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mPrefs = requireActivity().getSharedPreferences(MainActivity.PREF_NAME, Context.MODE_PRIVATE)


        if (!mPrefs.contains("saved_game")) {
            requireView().findViewById<View>(R.id.Resume).visibility = View.GONE
        } else {
            requireView().findViewById<View>(R.id.Resume).visibility = View.VISIBLE
        }
        drawableAnimation.add(view.findViewById<View>(R.id.Start).background as AnimatedVectorDrawable)
        drawableAnimation.add(view.findViewById<View>(R.id.Resume).background as AnimatedVectorDrawable)
        drawableAnimation.add(view.findViewById<View>(R.id.OptnBttn).background as AnimatedVectorDrawable)
        buttonAnimator()
        view.findViewById<View>(R.id.Start).setOnClickListener { view1: View? ->
            playPop()
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        view.findViewById<View>(R.id.Resume).setOnClickListener { v: View? ->
            playPop()
            val i = Intent(activity, MainActivity::class.java)
            i.putExtra("Resume", true)
            startActivity(i)
        }
        view.findViewById<View>(R.id.OptnBttn).setOnClickListener { v: View? ->
            playPop()
            val i = Intent(activity, OptionsActivity::class.java)
            startActivity(i)
        }
    }

    private fun buttonAnimator() {
        for (animatedVectorDrawable in drawableAnimation) {
            animatedVectorDrawable.start()
        }
    }
}