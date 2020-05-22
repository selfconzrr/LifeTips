package com.example.zhangruirui.apkcompare

import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.text.NumberFormat
import java.util.*
import java.util.zip.ZipFile
import kotlin.math.abs

class FileDiffItem {
    internal var mOldFilename: String? = null
    internal var mNewFilename: String? = null
    internal var mDiffSize: Long = 0
}

fun main(args: Array<String>) {
    if (args.size != 3) {
        println("请输入三个参数：老版本的 apk 路径、新版本的 apk 路径、输出 markdown 文件名")
        return
    }

    val oldFilesInfo: MutableMap<String, Long> = getFilesInfo(args[0])
    val newFilesInfo: MutableMap<String, Long> = getFilesInfo(args[1])

    val outputDiffList = mutableListOf<FileDiffItem>()
    val oldEntries = oldFilesInfo.entries

    for (oldEntry in oldEntries) {
        val diffItem = FileDiffItem()
        val oldFilename = oldEntry.key
        diffItem.mOldFilename = oldFilename
        val oldFileSize = oldEntry.value
        val newFileSize = newFilesInfo[oldFilename]
        if (newFileSize != null) {
            diffItem.mNewFilename = diffItem.mOldFilename
            diffItem.mDiffSize = newFileSize - oldFileSize
        } else {
            diffItem.mNewFilename = "UNKNOWN"
            diffItem.mDiffSize = -oldFileSize
        }

        newFilesInfo.remove(oldFilename)

        // 仅统计文件大小有改变的情况
        if (diffItem.mDiffSize != 0L) {
            outputDiffList.add(diffItem)
        }
    }

    // 新版本中新增的文件
    val newEntries = newFilesInfo.entries
    for (newEntry in newEntries) {
        val diffItem = FileDiffItem()
        diffItem.mOldFilename = "UNKNOWN"
        diffItem.mNewFilename = newEntry.key
        diffItem.mDiffSize = newEntry.value
        outputDiffList.add(diffItem)
    }

    // 输出到 markdown 文件，主要是格式化处理，方便观察
    outputMarkDownFile(args, outputDiffList)
    println("比较已完成，请查看下述 md 文件")
    println("已输出到文件: " + File(args[2]).absolutePath + ".md")
}

fun getFilesInfo(apkPath: String): MutableMap<String, Long> {
    val map = LinkedHashMap<String, Long>()
    try {
        val apkFile = ZipFile(apkPath)
        val entries = apkFile.entries()
        while (entries.hasMoreElements()) {
            val entry = entries.nextElement()
            val fileName = entry.name
            val size = entry.size
            map[fileName] = size
        }

    } catch (e: IOException) {
        e.printStackTrace()
    }
    return map
}

fun outputMarkDownFile(fileNames: Array<String>, outputDiffList: MutableList<FileDiffItem>) {
    try {
        var increasedCnt = 0 // 文件增大
        var decreasedCnt = 0 // 文件减小
        var addedCnt = 0 // 新增文件
        var removedCnt = 0 // 删除文件

        val increased = ArrayList<FileDiffItem>()
        val decreased = ArrayList<FileDiffItem>()
        val added = ArrayList<FileDiffItem>()
        val removed = ArrayList<FileDiffItem>()

        for (diffItem in outputDiffList) {
            when {
                "UNKNOWN" == diffItem.mOldFilename -> { // 新增文件
                    added.add(diffItem)
                    addedCnt += diffItem.mDiffSize.toInt()
                }
                "UNKNOWN" == diffItem.mNewFilename -> { // 删除了的文件
                    removed.add(diffItem)
                    removedCnt += diffItem.mDiffSize.toInt()
                }
                diffItem.mDiffSize > 0 -> {
                    increased.add(diffItem)
                    increasedCnt += diffItem.mDiffSize.toInt()
                }
                else -> {
                    decreased.add(diffItem)
                    decreasedCnt += diffItem.mDiffSize.toInt()
                }
            }
        }

        val writer = BufferedWriter(FileWriter(fileNames[2] + ".md"))
        val oldApkFile = File(fileNames[0])
        writer.write(oldApkFile.name)
        writer.write("  VS  ")
        val newApkFile = File(fileNames[1])
        writer.write(newApkFile.name)
        writer.write("\n\n")

        val format = NumberFormat.getInstance()
        writer.write("### Pure growth Size : ")
        writer.write(format.format(newApkFile.length() - oldApkFile.length()))
        writer.write(" bytes\n")

        writer.write("| the size changed file | the size changed (byte) |\n")
        writer.write("| --------- | ---------: |\n")
        writer.append("| [new added file]| ").append(format.format(addedCnt.toLong())).append(" | \n")
        writer.append("| [size increased file]| ").append(format.format(increasedCnt.toLong())).append(" | \n")
        writer.append("| [size decreased file]| ").append(format.format(decreasedCnt.toLong())).append(" | \n")
        writer.append("| [deleted file]| ").append(format.format(removedCnt.toLong())).append(" | \n\n")

        outputMarkdownAddedList(writer, added)
        outputMarkdownIncreasedList(writer, increased)
        outputMarkdownDecreasedList(writer, decreased)
        outputMarkdownRemovedList(writer, removed)

    } catch (e: IOException) {
        e.printStackTrace()
    }
}

fun outputMarkdownAddedList(writer: BufferedWriter, added: ArrayList<FileDiffItem>) {
    if (added.isNotEmpty()) {
        try {
            // 自定义比较器进行排序
            val comparator = Comparator { o1: FileDiffItem, o2: FileDiffItem ->
                (abs(o2.mDiffSize) - abs(o1.mDiffSize)).toInt()
            }
            Collections.sort(added, comparator)
            writer.append("## ").append("Added New Files (in new version apk)\n")
            writer.append("| File Name | Size (byte)|\n")
            writer.append("| --------- | ---------: |\n")

            val format = NumberFormat.getInstance()
            for (item in added) {
                writer.append("| ")
                        .append(item.mNewFilename)
                        .append(" | ")
                        .append(format.format(item.mDiffSize))
                        .append(" |\n")
                writer.flush()
            }
            writer.write("\n")
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}

fun outputMarkdownIncreasedList(writer: BufferedWriter, increased: ArrayList<FileDiffItem>) {
    if (increased.isNotEmpty()) {
        try {
            val comparator = Comparator { o1: FileDiffItem, o2: FileDiffItem ->
                (abs(o2.mDiffSize) - abs(o1.mDiffSize)).toInt()
            }
            Collections.sort(increased, comparator)
            writer.append("## ").append("Size Increased Files (in new version apk)\n")
            writer.append("| File Name | Increased Size (byte)|\n")
            writer.append("| --------- | ---------: |\n")

            val format = NumberFormat.getInstance()
            for (item in increased) {
                writer.append("| ")
                        .append(item.mNewFilename)
                        .append(" | ")
                        .append(format.format(item.mDiffSize))
                        .append(" |\n")
                writer.flush()
            }
            writer.write("\n")
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}

fun outputMarkdownDecreasedList(writer: BufferedWriter, decreased: ArrayList<FileDiffItem>) {
    if (decreased.isNotEmpty()) {
        try {
            val comparator = Comparator { o1: FileDiffItem, o2: FileDiffItem ->
                (abs(o2.mDiffSize) - abs(o1.mDiffSize)).toInt()
            }
            Collections.sort(decreased, comparator)
            writer.append("## ").append("Size Decreased Files (in new version apk)\n")
            writer.append("| File Name | Decreased Size (byte)|\n")
            writer.append("| --------- | ---------: |\n")

            val format = NumberFormat.getInstance()
            for (item in decreased) {
                writer.append("| ")
                        .append(item.mNewFilename)
                        .append(" | ")
                        .append(format.format(item.mDiffSize))
                        .append(" |\n")
                writer.flush()
            }
            writer.write("\n")
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}

fun outputMarkdownRemovedList(writer: BufferedWriter, removed: ArrayList<FileDiffItem>) {
    if (removed.isNotEmpty()) {
        try {
            val comparator = Comparator { o1: FileDiffItem, o2: FileDiffItem ->
                (abs(o2.mDiffSize) - abs(o1.mDiffSize)).toInt()
            }
            Collections.sort(removed, comparator)
            writer.append("## ").append("Removed Files (in new version apk)\n")
            writer.append("| File Name | Decreased Size (byte)|\n")
            writer.append("| --------- | ---------: |\n")

            val format = NumberFormat.getInstance()
            for (item in removed) {
                writer.append("| ")
                        .append(item.mOldFilename)
                        .append(" | ")
                        .append(format.format(item.mDiffSize))
                        .append(" |\n")
                writer.flush()
            }
            writer.write("\n")
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}