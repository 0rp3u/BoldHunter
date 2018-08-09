package pt.boldint.carbon.boldhunter.view.main

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.progress_bar.*
import pt.boldint.carbon.boldhunter.R
import pt.boldint.carbon.boldhunter.view.base.BaseActivity
import pt.boldint.carbon.boldhunter.view.postdetails.PostDetailsActivity
import javax.inject.Inject
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.dialog_date_picker.view.*
import pt.boldint.carbon.boldhunter.data.model.Post
import pt.boldint.carbon.boldhunter.extension.*
import pt.boldint.carbon.boldhunter.presenter.MainPresenter
import pt.boldint.carbon.boldhunter.view.adapter.PostRecyclerViewAdapter
import java.util.*


class MainActivity : BaseActivity<MainPresenter, MainView>(), MainView {

    data class MainActivityViewState( //TODO make parcelable
            var loadingPages: Boolean = false,
            var currentPage: Int = 0,
            var showAll: Boolean = false,
            var sort_by: String = "created_at",
            var order: String = "desc",
            var date : Calendar = Calendar.getInstance()
    )

    @Inject
    override lateinit var presenter: MainPresenter

    lateinit var postRecyclerViewAdapter: PostRecyclerViewAdapter

    private val viewState = MainActivityViewState()

    override fun injectDependencies() {
        app.applicationComponent
                .presenters()
                .injectTo(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecyclerView()

        fetchPosts()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        //TODO save ViewState
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_base, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }

            R.id.day ->{
                showDatePickerDialog()
                true
            }

            R.id.settings -> {
                //TODO open settings Activity
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initRecyclerView(){
        postRecyclerViewAdapter = PostRecyclerViewAdapter {
            startActivity(
                    Intent(this, PostDetailsActivity::class.java)
                            .run { putExtra(PostDetailsActivity.EXTRA_POST_ID, it.id) }
            )
        }

        recycler_view.apply {
            adapter = postRecyclerViewAdapter
            val linearLayoutManager = LinearLayoutManager(this@MainActivity)
            layoutManager = linearLayoutManager

            addOnScrollListener(object : RecyclerView.OnScrollListener() { //TODO this might leak, save refrence so we can remove it onDestroy
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (dy > 0 && !viewState.loadingPages)
                        with(linearLayoutManager){
                            if ((childCount + findFirstVisibleItemPosition()) >= itemCount) fetchPosts(keepCurrent = true)

                        }
                }
            })
        }
    }

    private fun fetchPosts(keepCurrent: Boolean = false) {
        if(!keepCurrent) postRecyclerViewAdapter.clear()

        viewState.loadingPages = true
        presenter.setPosts(
                viewState.currentPage++,
                50,
                day= viewState.date.getDay(),
                month= viewState.date.getMonth(),
                year= viewState.date.getYear())
    }

    override fun showPosts(posts: List<Post>) {
        viewState.loadingPages = false

        val dateparsed = when{
            viewState.date.isToday() -> "Today"
            viewState.date.isYesterday() -> "yesterday"
            else -> getDate(viewState.date)
        }
        title = "$dateparsed's posts"
        supportActionBar?.subtitle = "ordered by votes"

        postRecyclerViewAdapter.addItems(posts)
    }


    private fun showDatePickerDialog() {

        var date = viewState.date //needed because inside positive button we dont have calendarView
        val dialogView = layoutInflater.inflate(R.layout.dialog_date_picker, null).apply {
            calendarView.maxDate = Calendar.getInstance().timeInMillis //TDAY
            calendarView.setDate(viewState.date.timeInMillis, true, true)
            calendarView.setOnDateChangeListener { _, year, month, day ->
                date = Calendar.getInstance().apply {  set(year,month,day)}
            }
        }



        AlertDialog.Builder(this)
                .setTitle("filter options")
                .setView(dialogView)
                .setPositiveButton("apply") { dialogInterface, i ->
                    viewState.date = date
                    fetchPosts(); dialogInterface.dismiss()
                }
                .setNegativeButton("cancel") { dialogInterface, i -> dialogInterface.cancel() }
                .create()
                .show()
    }

    override fun showLoadingIndicator() {
        progressBar.isVisible = true
    }

    override fun hideLoadingIndicator() {
        progressBar.isVisible = false
    }

    override fun showErrorMessage(error: String?, action: ((View) -> Unit)?) {
        if (action != null)
            longSnackbar(recycler_view, error ?: "error, something when wrong", "repeat?", action)
        else
            snackbar(recycler_view, error ?: "error, something when wrong")
    }
}
