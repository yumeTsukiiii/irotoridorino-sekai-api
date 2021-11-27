package fan.yumetsuki.utils

inline fun <T, R> List<T>.mapThis(transform: T.() -> R): List<R> {
    return this.map {
        it.transform()
    }
}