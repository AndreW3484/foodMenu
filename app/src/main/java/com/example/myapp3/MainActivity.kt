package com.example.myapp3

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp3.databinding.ActivityMainBinding
import com.example.myapp3.databinding.DialogAddNewItemBinding
import com.example.myapp3.databinding.DialogDeleteItemBinding
import com.example.myapp3.databinding.DialogUpdateItemBinding

class MainActivity : AppCompatActivity() , FoodAdaptor.FoodEvents {
    private lateinit var binding: ActivityMainBinding
    lateinit var myAdaptor :FoodAdaptor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView( binding.root )


        val foodList = arrayListOf(
            Food( "Hamburger" , "15" , "3" , "Isfahan, Iran" , "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food1.jpg" ,  20 , 4.5f ) ,
            Food( "Grilled fish" , "20" , "2.1" , "Tehran, Iran" , "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food2.jpg" ,  10 , 4f ) ,
            Food( "Lasania" , "40" , "1.4" , "Isfahan, Iran" , "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food3.jpg" ,  30 , 2f ) ,
            Food( "pizza" , "10" , "2.5" , "Zahedan, Iran" , "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food4.jpg" ,  80 , 1.5f ) ,
            Food( "Sushi" , "20" , "3.2" , "Mashhad, Iran" , "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food5.jpg" ,  200 , 3f ) ,
            Food( "Roasted Fish" , "40" , "3.7" , "Jolfa, Iran" , "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food6.jpg" ,  50 , 3.5f ) ,
            Food( "Fried chicken" , "70" , "3.5" , "NewYork, USA" , "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food7.jpg" ,  70 , 2.5f ) ,
            Food( "Vegetable salad" , "12" , "3.6" , "Berlin, Germany" , "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food8.jpg" ,  40 , 4.5f ) ,
            Food( "Grilled chicken" , "10" , "3.7" , "Beijing, China" , "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food9.jpg" ,  15 , 5f ) ,
            Food( "Baryooni" , "16" , "10" , "Ilam, Iran" , "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food10.jpg" ,  28 , 4.5f ) ,
            Food( "Ghorme Sabzi" , "11.5" , "7.5" , "Karaj, Iran" , "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food11.jpg" ,  27 , 5f ) ,
            Food( "Rice" , "12.5" , "2.4" , "Shiraz, Iran" , "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food12.jpg" ,  35 , 2.5f ) ,
        )

        myAdaptor = FoodAdaptor(foodList.clone() as ArrayList<Food>, this)
        binding.RecyclerMain.adapter = myAdaptor
        binding.RecyclerMain.layoutManager = LinearLayoutManager(this , RecyclerView.VERTICAL , false )
        binding.btnAddNewFood.setOnClickListener {

            val dialog = AlertDialog.Builder(this).create()

            val dialogBinding = DialogAddNewItemBinding.inflate(layoutInflater)
            dialog.setView(dialogBinding.root)
            dialog.setCancelable(true)
            dialog.show()


            dialogBinding.dialogBtnDone.setOnClickListener{

                if (
                    dialogBinding.dialogEdtNameFood.length() > 0 &&
                    dialogBinding.dialogEdtFoodCity.length() > 0 &&
                    dialogBinding.dialogEdtFoodPrice.length() > 0 &&
                    dialogBinding.dialogEdtFoodDistance.length() > 0
                ) {


                    val txtName = dialogBinding.dialogEdtNameFood.text.toString()
                    val txtPrice = dialogBinding.dialogEdtFoodPrice.text.toString()
                    val txtDistance = dialogBinding.dialogEdtFoodDistance.text.toString()
                    val txtCity = dialogBinding.dialogEdtFoodCity.text.toString()
                    val txtRatingNumber :Int = (1..150).random()
                    val ratingBarStar :Float =  (1..5).random().toFloat()

                    val randomForUrl = (0 until 12).random()
                    val urlPic = foodList[randomForUrl].urlImage

                    val newFood = Food(txtName , txtPrice , txtDistance , txtCity , urlPic , txtRatingNumber , ratingBarStar)
                    myAdaptor.addFood(newFood)


                    dialog.dismiss()
                    binding.RecyclerMain.scrollToPosition(0)

                } else{

                    Toast.makeText(this, "همه ی مقادیر را وارد کنید...!", Toast.LENGTH_SHORT).show()
                }



            }

        }

        binding.edtSearch.addTextChangedListener { editTextInput ->

            if (editTextInput!!.isNotEmpty()) {

            val cloneList = foodList.clone() as ArrayList<Food>
            val filteredList = cloneList.filter { foodItem ->
                foodItem.txtSubject.contains( editTextInput )
            }

            myAdaptor.setData( ArrayList( filteredList ) )


            }else{
                myAdaptor.setData(foodList.clone() as ArrayList<Food>)

            }





        }






    }

    override fun onFoodClicked(food :Food , position: Int) {

        val dialog = AlertDialog.Builder(this).create()

        val updateDialogBinding = DialogUpdateItemBinding.inflate(layoutInflater)

        dialog.setView(updateDialogBinding.root)
        dialog.setCancelable(true)
        dialog.show()
        updateDialogBinding.dialogEdtNameFood.setText( food.txtSubject )
        updateDialogBinding.dialogEdtFoodCity.setText( food.txtCity )
        updateDialogBinding.dialogEdtFoodPrice.setText( food.txtPrice )
        updateDialogBinding.dialogEdtFoodDistance.setText( food.txtDistance )


        updateDialogBinding.dialogUpdateBtnCancel.setOnClickListener {
            dialog.dismiss()

        }

        updateDialogBinding.dialogUpdateBtnDone.setOnClickListener {

            if (
                updateDialogBinding.dialogEdtNameFood.length() > 0 &&
                updateDialogBinding.dialogEdtFoodCity.length() > 0 &&
                updateDialogBinding.dialogEdtFoodPrice.length() > 0 &&
                updateDialogBinding.dialogEdtFoodDistance.length() > 0
            ) {


                val txtName = updateDialogBinding.dialogEdtNameFood.text.toString()
                val txtPrice = updateDialogBinding.dialogEdtFoodPrice.text.toString()
                val txtDistance = updateDialogBinding.dialogEdtFoodDistance.text.toString()
                val txtCity = updateDialogBinding.dialogEdtFoodCity.text.toString()


                val newFood = Food( txtName , txtPrice , txtDistance ,txtCity , food.urlImage , food.numOfRating , food.rating )



                myAdaptor.updateFood( newFood , position )

                dialog.dismiss()

            }else{
                Toast.makeText(this, "لطفا همه ی مقادیر را کامل کنید...!", Toast.LENGTH_SHORT).show()

                
            }

        }


    }

    override fun onFoodLongClicked(food: Food, position: Int) {

        val dialog = AlertDialog.Builder(this).create()
        val dialogDeleteBinding = DialogDeleteItemBinding.inflate(layoutInflater)
        dialog.setView(dialogDeleteBinding.root)
        dialog.setCancelable(true)
        dialog.show()

        dialogDeleteBinding.dialogBtnDeleteCancle.setOnClickListener{
            dialog.dismiss()
        }
        dialogDeleteBinding.dialogBtnDeleteSure.setOnClickListener {
            dialog.dismiss()
            myAdaptor.removeFood( food , position )


        }
    }


}





