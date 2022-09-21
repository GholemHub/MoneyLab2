package com.gholem.moneylab.features.createNewCategoryImage.adapter.item

import com.gholem.moneylab.R

sealed class NewCategoryImageItem {
    data class Image(
        var id: Int,
        var image: Int
    ) : NewCategoryImageItem()

    companion object {
        fun getImages(): List<NewCategoryImageItem> = listOf(
            Image(1,R.drawable.ic_category_food),
            Image(0, R.drawable.ic_baseline_local_grocery_store_24),
            Image(2,R.drawable.ic_category_sport),
            Image(3,R.drawable.ic_category_transport),
            Image(4, R.drawable.ic_baseline_school_24),
            Image(5, R.drawable.ic_baseline_phone_iphone_24),
            Image(6, R.drawable.favorite_24px),
            Image(7, R.drawable.home_24px),
            Image(8, R.drawable.pets_24px),
            Image(9, R.drawable.rocket_launch_24px),
            Image(10, R.drawable.build_24px),
            Image(11, R.drawable.account_balance_24px),
            Image(12, R.drawable.attach_money_24px),
            Image(13, R.drawable.euro_24px),
            Image(14, R.drawable.call_24px),
            Image(15, R.drawable.desktop_windows_24px),
            Image(16, R.drawable.diamond_24px),
            Image(17, R.drawable.directions_bus_24px),
            Image(18, R.drawable.dry_cleaning_24px),
            Image(19, R.drawable.extension_24px),
            Image(20, R.drawable.fitness_center_24px),
            Image(21, R.drawable.hiking_24px),
            Image(22, R.drawable.health_and_safety_24px),
            Image(23, R.drawable.home_24px),
            Image(24, R.drawable.lightbulb_24px),
            Image(25, R.drawable.local_cafe_24px),
            Image(26, R.drawable.local_florist_24px),
            Image(27, R.drawable.lunch_dining_24px),
            Image(28, R.drawable.medical_services_24px),
            Image(29, R.drawable.monitor_heart_24px),
            Image(30, R.drawable.photo_camera_24px),
            Image(31, R.drawable.plumbing_24px),
            Image(32, R.drawable.print_24px),
            Image(33, R.drawable.ramen_dining_24px),
            Image(34, R.drawable.rocket_launch_24px),
            Image(35, R.drawable.savings_24px),
            Image(36, R.drawable.sports_esports_24px),
            Image(37, R.drawable.sports_motorsports_24px),
            Image(38, R.drawable.sports_tennis_24px),
            Image(39, R.drawable.store_24px),
            Image(40, R.drawable.tram_24px),
            Image(41, R.drawable.two_wheeler_24px),
            Image(42, R.drawable.volunteer_activism_24px),
            Image(43, R.drawable.work_24px),

        )
    }
}