package com.jamessc94.recipe.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@Entity(tableName = "Recipe")
@JsonClass(generateAdapter = true)
data class Recipe(

    @PrimaryKey
    @ColumnInfo(name = "idMeal")
    var idMeal:String = "",

    @ColumnInfo(name = "strMeal")
    var strMeal:String = "",

    @ColumnInfo(name = "strCategory")
    var strCategory:String = "",

    @ColumnInfo(name = "strMealThumb")
    var strMealThumb:String = "",

    @ColumnInfo(name = "strInstructions")
    var strInstructions:String = "",

    @ColumnInfo(name = "recipe_type_id")
    var recipe_type_id:String = "",

    @ColumnInfo(name = "strIngredient")
    var strIngredient:String = "",

    @ColumnInfo(name = "strIngredient1")
    var strIngredient1:String = "",

    @ColumnInfo(name = "strIngredient2")
    var strIngredient2:String = "",

    @ColumnInfo(name = "strIngredient3")
    var strIngredient3:String = "",

    @ColumnInfo(name = "strIngredient4")
    var strIngredient4:String = "",

    @ColumnInfo(name = "strIngredient5")
    var strIngredient5:String = "",

    @ColumnInfo(name = "strIngredient6")
    var strIngredient6:String = "",

    @ColumnInfo(name = "strIngredient7")
    var strIngredient7:String = "",

    @ColumnInfo(name = "strIngredient8")
    var strIngredient8:String = "",

    @ColumnInfo(name = "strIngredient9")
    var strIngredient9:String = "",

    @ColumnInfo(name = "strIngredient10")
    var strIngredient10:String = "",
)

@JsonClass(generateAdapter = true)
data class RecipeListContainer(val meals : List<Recipe>)

fun RecipeListContainer.asDatabaseModel(): Array<Recipe> {
    return meals.map {
        Recipe(
            idMeal = it.idMeal,
            strMeal = it.strMeal,
            strCategory = it.strCategory,
            strMealThumb = it.strMealThumb,
            strInstructions = it.strInstructions,
            strIngredient1 = it.strIngredient1,
            strIngredient2 = it.strIngredient2,
            strIngredient3 = it.strIngredient3,
            strIngredient4 = it.strIngredient4,
            strIngredient5 = it.strIngredient5,
            strIngredient6 = it.strIngredient6,
            strIngredient7 = it.strIngredient7,
            strIngredient8 = it.strIngredient8,
            strIngredient9 = it.strIngredient9,
            strIngredient10 = it.strIngredient10,
        )
    }.toTypedArray()

}

data class RecipeAdap(
    var id:String = "",
    var name:String = "",
    var category:String = "",
    var img:String = "",
)

fun List<Recipe>.asDisplayList(): List<RecipeAdap> {
    return this.map {
        RecipeAdap(
            id = it.idMeal,
            name = it.strMeal,
            category = it.strCategory,
            img = it.strMealThumb,
        )
    }
}

@Entity(tableName = "RecipeType", primaryKeys = ["id","strCategory"])
data class RecipeType(

    @ColumnInfo(name = "id")
    var id:Int = 0,

    @ColumnInfo(name = "strCategory")
    var strCategory:String = "",

    @ColumnInfo(name = "isSelected")
    var isSelected:Boolean = false,

)

data class RecipeTypeAdap(
    var id:Int = 0,
    var name:String = "",
    var isSelected:Boolean = true,
)

fun Array<String>.asDatabaseModel(): Array<RecipeType> {
    val temp = arrayListOf<RecipeType>()

    this.forEachIndexed { index, s ->
        temp.add(RecipeType(
            id = index,
            strCategory = s,
            isSelected = true,
        ))
    }

    return temp.toTypedArray()
}

fun List<RecipeType>.asRecipeTypeList(): List<RecipeTypeAdap> {
    return this.map {
        RecipeTypeAdap(
            id = it.id,
            name = it.strCategory,
            isSelected = it.isSelected,
        )
    }
}