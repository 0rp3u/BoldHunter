package pt.boldint.carbon.boldhunter.extension

import android.net.Uri


fun Uri.removeQuery(parameter: String):Uri{

    val queries = queryParameterNames.filterNot {it  == parameter}.map {
        Pair<String,String?>(it, getQueryParameter(it)) }
    return buildUpon()
            .clearQuery()
            .apply { queries.forEach { appendQueryParameter(it.first, it.second) } }
            .build()

}

fun Uri.removeAllQueries():Uri{
    return buildUpon()
            .clearQuery()
            .build()
}