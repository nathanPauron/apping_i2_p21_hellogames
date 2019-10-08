package fr.epita.android.tp2_hello_games_nathan_pauron_01_10_2019

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class GameListAdapter(
    val context: Context,
    val data: List<Game>,
    private val myItemClickListener: View.OnClickListener
) : RecyclerView.Adapter<GameListAdapter.ViewHolder>(){

    // the new RecyclerAdapter enforces the use of
    // the ViewHolder class performance pattern
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val logoImageView: ImageView = itemView.findViewById(R.id.gameImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // create the row from a layout inflater
        val rowView = LayoutInflater
            .from(context)
            .inflate(R.layout.activity_row, parent, false)
        // attach the onclicklistener
        rowView.setOnClickListener(myItemClickListener)
        // create a ViewHolder using this rowview
        val viewHolder = ViewHolder(rowView)
        // return this ViewHolder. The system will make sure view holders
        // are used and recycled
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // retrieve the item at the specified position
        val currentItem = data[position]
        // put the data
        holder!!.nameTextView.text = currentItem.name
        Glide
            .with(context)
            .load(currentItem.picture)
            .into(holder.logoImageView)
        holder.itemView.tag = position
    }
    override fun getItemCount(): Int {
            return data.size
    }
}