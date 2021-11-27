package fan.yumetsuki.entities

import kotlinx.serialization.Serializable

@Serializable
class Article(
    val id: Int,
    val title: String,
    val description: String,
    val createTime: Long,
    val updateTime: Long,
    val imgSrc: String? = null
)

@Serializable
class ArticleContent(
    val chat: String,
    val content: String? = null
)