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
import android.os.Parcelable
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.dialog_date_picker.view.*
import kotlinx.android.synthetic.main.dialog_filters.view.*
import pt.boldint.carbon.boldhunter.data.model.OrderBy
import pt.boldint.carbon.boldhunter.data.model.Post
import pt.boldint.carbon.boldhunter.data.model.SortBy
import pt.boldint.carbon.boldhunter.extension.*
import pt.boldint.carbon.boldhunter.presenter.main.MainPresenter
import pt.boldint.carbon.boldhunter.view.adapter.PostRecyclerViewAdapter
import java.util.*

class MainActivity : BaseActivity<MainPresenter, MainView>(), MainView {

    companion object {
        const val VIEW_STATE_ID = "VIEW_STATE_ID"
    }
    @Parcelize
    data class MainActivityViewState( //TODO make parcelable
            var loadingPages: Boolean = false,
            var currentPage: Int = 0,
            var listPosition :Int = 0,
            var showAll: Boolean = false,
            var sort_by : SortBy = SortBy.created_at,
            var order_by : OrderBy = OrderBy.desc,
            var allPosts :Boolean = false,
            var date : Calendar = Calendar.getInstance()
            ) : Parcelable

    @Inject
    override lateinit var presenter: MainPresenter

    lateinit var postRecyclerViewAdapter: PostRecyclerViewAdapter

    override lateinit var viewState: MainActivityViewState

    override fun injectDependencies() {
        app.applicationComponent
                .presenters()
                .injectTo(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewState = savedInstanceState?.let { savedInstanceState.getParcelable(VIEW_STATE_ID) as MainActivityViewState } ?: MainActivityViewState()

        initRecyclerView()

        fetchPosts()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewState.listPosition = (recycler_view.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
            outState.putParcelable(VIEW_STATE_ID, viewState)
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

            R.id.filter ->{
                showFiltersDialog()
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

        //recycler_view.scrollToPosition(viewState.listPosition) //TODO FIX, Has to be done after
    }

    override fun showPosts(posts: List<Post>) {
        Log.v(localClassName, "showing ${posts.size} posts")
        viewState.loadingPages = false
        showTitle()
        postRecyclerViewAdapter.addItems(posts)
    }

    private fun fetchPosts(keepCurrent: Boolean = false) {
        viewState.loadingPages = true

        if(!keepCurrent) postRecyclerViewAdapter.clear()

        val loadedPages = postRecyclerViewAdapter.itemCount / 10
        presenter.setPosts(
                loadedPages,
                viewState.currentPage++,
                10,
                order = viewState.order_by,
                sort_by = viewState.sort_by,
                day= if (viewState.allPosts) null else viewState.date.getDay(),
                month= if (viewState.allPosts) null else viewState.date.getMonth(),
                year= if (viewState.allPosts) null else viewState.date.getYear())
    }


    private fun showTitle(){
        val dateparsed = when{
            viewState.allPosts -> "All"
            viewState.date.isToday() -> "Today"
            viewState.date.isYesterday() -> "yesterday"
            else -> getDate(viewState.date)
        }

        title = "$dateparsed's posts"

        val ordertedText = when(viewState.order_by){
            OrderBy.desc -> "descending"
            OrderBy.asc -> "ascending"

        }

        val sortedText = when(viewState.sort_by){
            SortBy.created_at -> "creation date"
            SortBy.id -> "id"
            SortBy.updated_at -> "update date"
            SortBy.votes_count -> "vote count"
            SortBy.author -> "author"
        }
        supportActionBar?.subtitle = "ordered by $sortedText, $ordertedText"

    }

    private fun showDatePickerDialog() {

        var date = viewState.date
        var anyDate = false

        val dialogView = layoutInflater.inflate(R.layout.dialog_date_picker, null).apply {
            calendarView.maxDate = Calendar.getInstance().timeInMillis //TDAY
            calendarView.setDate(viewState.date.timeInMillis, true, true)

            calendarView.setOnDateChangeListener { _, year, month, day ->
                date = Calendar.getInstance().apply {  set(year,month,day)}
            }
            disableDate.setOnCheckedChangeListener { _, isSwiwched ->
                calendarView.isEnabled = !isSwiwched
                anyDate = isSwiwched
            }
        }


        AlertDialog.Builder(this)
                .setTitle("Date options")
                .setView(dialogView)
                .setPositiveButton("apply") { dialogInterface, i ->
                    viewState.showAll = anyDate
                    if(!anyDate) viewState.date = date
                    fetchPosts(); dialogInterface.dismiss()
                }
                .setNegativeButton("cancel") { dialogInterface, _ -> dialogInterface.cancel() }
                .create()
                .show()
    }


    private fun showFiltersDialog() {

        var sort_by : SortBy = SortBy.created_at
        var order_by : OrderBy = OrderBy.desc
        val dialogView = layoutInflater.inflate(R.layout.dialog_filters, null).apply {
            sort.setOnCheckedChangeListener { _, i ->

                sort_by = when (i) {
                    R.id.votes -> SortBy.votes_count
                    R.id.created_at -> SortBy.created_at
                    R.id.author -> SortBy.author
                    else -> SortBy.id
                }

            }

            order.setOnCheckedChangeListener { _, i ->
                order_by = when (i) {
                    R.id.asc -> OrderBy.asc
                    R.id.desc -> OrderBy.desc
                    else -> OrderBy.asc
                }
            }
        }


        AlertDialog.Builder(this)
                .setTitle("Filter options")
                .setView(dialogView)
                .setPositiveButton("apply") { dialogInterface, _ ->
                    viewState.sort_by = sort_by
                    viewState.order_by = order_by
                    fetchPosts()
                    dialogInterface.dismiss()
                }
                .setNegativeButton("cancel") { dialogInterface, _ -> dialogInterface.cancel() }
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
