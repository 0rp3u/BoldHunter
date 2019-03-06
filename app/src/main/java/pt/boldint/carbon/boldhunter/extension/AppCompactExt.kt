package pt.boldint.carbon.boldhunter.extension

import androidx.appcompat.app.AppCompatActivity
import pt.boldint.carbon.boldhunter.BoldHunterApp

val AppCompatActivity.app: BoldHunterApp
    get() = application as BoldHunterApp
