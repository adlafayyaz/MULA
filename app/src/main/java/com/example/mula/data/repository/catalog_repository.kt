package com.example.mula.data.repository

import com.example.mula.data.model.Branch
import com.example.mula.data.model.MenuCategory
import com.example.mula.data.model.MenuItem
import com.example.mula.data.model.ProductOption

interface CatalogRepository {
    suspend fun get_catalog(order_method: String, branch_id: String?): Result<List<MenuCategory>>
    suspend fun get_branches(order_method: String): Result<List<Branch>>
    suspend fun get_product_detail(product_id: String): Result<MenuItem>
    suspend fun get_product_options(product_id: String): Result<List<ProductOption>>
    suspend fun toggle_favorite(product_id: String): Result<Boolean>
}
