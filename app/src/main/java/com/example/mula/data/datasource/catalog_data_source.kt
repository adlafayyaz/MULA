package com.example.mula.data.datasource

import com.example.mula.data.model.Branch
import com.example.mula.data.model.MenuCategory
import com.example.mula.data.model.MenuItem
import com.example.mula.data.model.ProductOption

interface CatalogDataSource {
    suspend fun get_catalog(order_method: String, branch_id: String?): Result<List<MenuCategory>>
    suspend fun get_branches(order_method: String): Result<List<Branch>>
    suspend fun get_product_detail(product_id: String): Result<MenuItem>
    suspend fun get_product_options(product_id: String): Result<List<ProductOption>>
    suspend fun toggle_favorite(product_id: String): Result<Boolean>
}

class FakeCatalogDataSource : CatalogDataSource {
    private val branches = listOf(
        Branch("branch_1", "mula_central", "1.2_km", "jalan_mula_1", "open", true),
        Branch("branch_2", "mula_north", "3.4_km", "jalan_mula_2", "open", true)
    )

    private val menuItems = listOf(
        MenuItem("product_1", "iced_latte", "smooth_milk_coffee", 28000, 4.8, "img_latte", "cat_coffee", false),
        MenuItem("product_2", "americano", "clean_black_coffee", 22000, 4.7, "img_americano", "cat_coffee", true)
    )

    override suspend fun get_catalog(order_method: String, branch_id: String?): Result<List<MenuCategory>> = Result.success(
        listOf(MenuCategory("cat_coffee", "coffee", menuItems))
    )

    override suspend fun get_branches(order_method: String): Result<List<Branch>> = Result.success(branches)

    override suspend fun get_product_detail(product_id: String): Result<MenuItem> =
        Result.success(menuItems.first { it.id == product_id })

    override suspend fun get_product_options(product_id: String): Result<List<ProductOption>> = Result.success(
        listOf(
            ProductOption("opt_1", "temperature", "ice", 0, true),
            ProductOption("opt_2", "cup_size", "regular", 0, true),
            ProductOption("opt_3", "free_topping", "none", 0, false)
        )
    )

    override suspend fun toggle_favorite(product_id: String): Result<Boolean> = Result.success(true)
}
