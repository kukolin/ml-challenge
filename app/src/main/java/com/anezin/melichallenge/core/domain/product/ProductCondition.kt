package com.anezin.melichallenge.core.domain.product

enum class ProductCondition(val value: String) {
    New("Nuevo"),
    Used("Usado"),
    Reconditioned("Reacondicionado");

    override fun toString(): String {
        return value
    }

    companion object {
        fun fromString(value: String): ProductCondition {
            return when (value.lowercase()) {
                "new" -> New
                "used" -> Used
                "reconditioned" -> Reconditioned
                else -> throw IllegalArgumentException("Unknown product condition: $value")
            }
        }
    }
}