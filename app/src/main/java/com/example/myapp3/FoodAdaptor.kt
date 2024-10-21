package com.example.myapp3

import android.annotation.SuppressLint
import android.content.Context
import android.icu.text.Transliterator.Position
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.myapp3.databinding.ActivityMainBinding
import com.example.myapp3.databinding.ItemFoodBinding


class FoodAdaptor( private val data :ArrayList<Food> , private val foodEvents: FoodEvents) : RecyclerView.Adapter<FoodAdaptor.FoodViewHolder>() {

    inner class FoodViewHolder( private val binding: ItemFoodBinding) : RecyclerView.ViewHolder( binding.root ) {





        @SuppressLint("SetTextI18n")
        fun bindData(position: Int ){

            binding.itemTxtSubject.text = data[position].txtSubject
            binding.itemTxtCity.text = data[position].txtCity
            binding.itemTxtPrice.text =  "$" + data[position].txtPrice + " vip"
            binding.itemTxtDistance.text = data[position].txtDistance + "miles from you"
            binding.itemRatingMain.rating = data[position].rating
            binding.itemTxtRating.text = "(" + data[position].numOfRating.toString() + "Rating)"

            Glide
                .with(binding.root.context)
                .load(data[position].urlImage)
                .transform( RoundedCorners(16,))
                .into(binding.itemImageMain)

            itemView.setOnClickListener{

                foodEvents.onFoodClicked(data[adapterPosition] , adapterPosition)

            }
            itemView.setOnLongClickListener {

                foodEvents.onFoodLongClicked( data[adapterPosition] , adapterPosition )

                true
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val binding = ItemFoodBinding.inflate(LayoutInflater.from(parent.context) , parent , false)
        return FoodViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        Log.v("testApp", "onBindViewHolder called")
        holder.bindData( position )
        

    }


    fun addFood(newFood :Food){

        // add food to list
        data.add( 0 , newFood )
        notifyItemInserted(0)

    }
    fun removeFood(oldFood :Food , oldPosition: Int){

        data.remove(oldFood)

        notifyItemRemoved(oldPosition)

    }
    fun updateFood(newFood: Food , position: Int){

        data[position] = newFood

        notifyItemChanged( position )


    }
    @SuppressLint("NotifyDataSetChanged")
    fun setData(newList: ArrayList<Food>) {

        data.clear()
        data.addAll( newList )

        notifyDataSetChanged()
    }


    interface FoodEvents {

        fun onFoodClicked(food: Food , position: Int)
        fun onFoodLongClicked( food: Food , position: Int )
    }


}