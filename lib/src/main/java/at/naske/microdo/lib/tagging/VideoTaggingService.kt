package at.naske.microdo.lib.tagging

import org.jcodec.movtool.MetadataEditor
import java.io.File
import org.jcodec.containers.mp4.boxes.MetaValue

/**
 * Service that can be used to read and write tags from an .mp4 file.
 */
class VideoTaggingService(
    val mFile: File,
    prefix: String = "",
    mPrefixDelimiter: String = "."
) {

    private val mMetadataEditor = MetadataEditor.createFrom(mFile)

    /**
     * The prefix is used to custom identify objects for an application by using e.g. the app identifier
     */
    private val mPrefix: String = if (prefix.isNotEmpty()) {
        prefix + if (prefix.endsWith(mPrefixDelimiter)) {
            ""
        } else {
            mPrefixDelimiter
        }
    } else {
        ""
    }

    /**
     * Tags the file handled by the tagging service with the given tag. If the key of the tag does not start with the
     * specified prefix, it is added automatically
     */
    fun tagFile(tag: Tag) {
        val prefixedKey = getPrefixedKey(tag.key)

        val keyedMeta = mMetadataEditor.keyedMeta

        val metaValue = if (tag.value == null) null else MetaValue.createString("${tag.value}")
        if (tag.value.isNullOrEmpty()) {
            keyedMeta.remove(tag.key)
        } else {
            keyedMeta[prefixedKey] = metaValue
        }
        mMetadataEditor.save(false)
    }

    /**
     * Reads a tag from the file handled by the tagging service. The value of the tag is null if no value has been
     * stored for the given key. If the key does not start with the given prefix, it is automatically added for reading
     * the value.
     */
    fun getTag(key: String): Tag {
        val prefixedKey = getPrefixedKey(key)

        val keyedMeta = mMetadataEditor.keyedMeta
        return Tag(key, keyedMeta[prefixedKey]?.toString())
    }

    private fun getPrefixedKey(key: String): String {
        return if (key.startsWith(mPrefix)) {
            key
        } else {
            mPrefix + key
        }
    }
}