package com.dokar.chiptextfield.sample.data

import com.dokar.chiptextfield.Chip
import kotlin.random.Random

object SampleChips {
    private const val LOREM_IPSUM = "Lorem ipsum dolor sit amet, consectetur adipisicing elit"

    private const val PICSUM_SEED_URL = "https://picsum.photos/seed/{seed}/100/100"

    val text: List<Chip> get() = LOREM_IPSUM.split(" ").map(::Chip)

    val checkable: List<CheckableChip>
        get() = LOREM_IPSUM
            .split(" ")
            .map { CheckableChip(text = it, isChecked = Random.nextBoolean()) }

    val avatar: List<AvatarChip>
        get() = LOREM_IPSUM
            .split(" ")
            .mapIndexed { index, it -> AvatarChip(it, randomAvatarUrl(index.toLong())) }

    fun randomAvatarUrl(seed: Long = System.currentTimeMillis()): String {
        return PICSUM_SEED_URL.replace("{seed}", seed.toString())
    }
}