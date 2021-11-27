package fan.yumetsuki.domain

import kotlinx.serialization.Serializable

@Serializable
class Article(
    val id: Int,
    val title: String,
    val description: String,
    val createTime: Long,
    val updateTime: Long,
    val imgSrc: String?
)

@Serializable
class ArticleContent(
    val chat: String,
    val content: String?
)