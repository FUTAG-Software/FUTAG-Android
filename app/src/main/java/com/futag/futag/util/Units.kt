package com.futag.futag.util

import com.futag.futag.R

enum class Units(
    val id: Int, val unitImage: Int, val unitName: Int, val unitDescription: Int,
) {
    FOREIGN_RELATIONS(
        1,
        R.drawable.foreign_relations_unit_image,
        R.string.foreign_relations_unit,
        R.string.foreign_relations_unit_text
    ),
    ENTREPRENEURSHIP(
        2,
        R.drawable.entrepreneurship_unit_image,
        R.string.entrepreneurship_unit,
        R.string.entrepreneurship_unit_text
    ),
    MEDIA(
        3,
        R.drawable.media_unit_image,
        R.string.media_unit,
        R.string.media_unit_text
    ),
    PROJECT_RESEARCH(
        4,
        R.drawable.project_unit_image,
        R.string.project_rd_unit,
        R.string.project_rd_unit_text,
    ),
    SOCIAL_RESPONSIBILITY(
        5,
        R.drawable.social_responsibility_unit_image,
        R.string.social_responsibility_unit,
        R.string.social_responsibility_unit_text,
    ),
    SOFTWARE(
        6,
        R.drawable.software_unit_image,
        R.string.software_unit,
        R.string.software_unit_text
    );

    companion object {
        fun getUnitDetail(unitId: Int): Units {
            val unit = values().first {
                it.id == unitId
            }
            return unit
        }
    }
}