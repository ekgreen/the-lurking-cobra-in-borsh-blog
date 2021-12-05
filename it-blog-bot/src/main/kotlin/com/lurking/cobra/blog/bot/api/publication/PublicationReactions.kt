package com.lurking.cobra.blog.bot.api.publication

import com.goodboy.telegram.bot.api.keyboard.InlineKeyboardButton
import com.goodboy.telegram.bot.api.keyboard.InlineKeyboardMarkup
import com.lurking.cobra.blog.bot.impl.receive.ItBlogReactionEventListener

data class PublicationReactions(
    var likesCount      : Int = 0,
    var lightingCount   : Int = 0,
    var pokerFaceCount  : Int = 0,
    var commentCount    : Int = 0,
)

fun createReactionsMarkup(reactions: PublicationReactions = PublicationReactions()): InlineKeyboardMarkup {
    return InlineKeyboardMarkup()
        .setKeyboardLine(
            InlineKeyboardButton()
                .setText("❤️ ${reactions.likesCount}")
                .setCallbackData(ItBlogReactionEventListener.LIKE_EVENT_NAME),
            InlineKeyboardButton()
                .setText("⚡️ ${reactions.lightingCount}")
                .setCallbackData(ItBlogReactionEventListener.ZIPPER_EVENT_NAME),
            InlineKeyboardButton()
                .setText("\uD83D\uDE10️ ${reactions.pokerFaceCount}")
                .setCallbackData(ItBlogReactionEventListener.POKER_FACE_EVENT_NAME)
        )
}
