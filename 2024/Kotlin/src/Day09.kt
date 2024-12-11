import java.util.concurrent.atomic.AtomicInteger

fun main() {
    val diskMap = readInput("Day09")[0]
//    val diskMap = "2333133121414131402"
    val dilutedDiskMap = dilutedDiskMap(diskMap)
    val fragmentedDiskMap = fragment(dilutedDiskMap)
    println(checksum(fragmentedDiskMap))
}

fun dilutedDiskMap(diskMap : String) : List<Int> {
    val ai = AtomicInteger(0)
    return diskMap.flatMapIndexed { idx, char ->
        if (idx % 2 == 0) {
            val id = ai.getAndIncrement();
            (0 until char.toString().toInt()).map { id }
        } else {
            (0 until char.toString().toInt()).map {  -1 }
        }
    }.toList()
}

/*
list of "files" with value and size
then when we "frag"
val lastFile = diskMap.indexOfLast { it.isFile() }
val firstSpaceIndex = diskMap.indexOfFirst { !it.isFile() && size > lastFile.size }

then swap "space" object (size 3) with file (size 3)

 */
tailrec fun fragment(diskMap: List<Int>) : List<Int> {
    val firstSpaceIndex = diskMap.indexOfFirst { it == -1 }
    val lastFileIndex = diskMap.indexOfLast { it != -1 }
    if (firstSpaceIndex == lastFileIndex-1) {
        return diskMap
    }

    val out = ArrayList<Int>(diskMap)
    out[firstSpaceIndex] = diskMap[lastFileIndex]
    out[lastFileIndex] = diskMap[firstSpaceIndex]
    return fragment(out)
}

fun checksum(fileMap: List<Int>): Long {
    return fileMap.filter { it != -1 }.mapIndexed { i, c ->
        (i * c).toLong()
    }.sum()
}

//fun _dilutedDiskMap(diskMap: String): List<File> {
//    val ai = AtomicInteger(0)
//    return diskMap.mapIndexed { i, c ->
//        if (i % 2 == 0) {
//            val id = ai.getAndIncrement();
//            File(id, c.toString().toInt())
//        } else {
//            File(-1, c.toString().toInt())
//        }
//    }
//        .filter { !(it.isSpace() && it.size == 0) }
//        .toList()
//}
//
//tailrec fun _fragment(diskMap: List<File>) : List<File> {
//    val firstSpaceIndex = diskMap.indexOfFirst { it.isSpace() }
//    val lastFileIndex = diskMap.indexOfLast { !it.isSpace() }
//    if (firstSpaceIndex == lastFileIndex-1) {
//        return diskMap
//    }
//
//    val out = ArrayList<File>(diskMap)
//    out[firstSpaceIndex] = diskMap[lastFileIndex]
//    out[lastFileIndex] = diskMap[firstSpaceIndex]
//    return _fragment(out)
//}
//
//fun output(diskMap: List<File>) : String {
//    return diskMap.joinToString("") {
//        it.output()
//    }
//}
//
//fun dilutedDiskMap(diskMap: String): String {
//    val ai = AtomicInteger(0)
//    return diskMap.mapIndexed { i, c ->
//        val fileId = if (i % 2 == 0) {
//            //val x = File(i, c.toString().toInt())
//            ai.getAndIncrement().toString() // even -> this is a file
//        } else {
//            "." // this is free space
//        }
//        val size = c.toString().toInt()
//        fileId.repeat(size)
//    }.joinToString("")
//}
//
//tailrec fun fragment(diskMap: String) : String {
//    // left move space
//    val firstSpaceIndex = diskMap.indexOfFirst { it == '.' }
//    val lastFileIndex = diskMap.indexOfLast { it != '.' }
//    val out = diskMap.toCharArray()
//    out[firstSpaceIndex] = out[lastFileIndex]
//    out[lastFileIndex] = '.'
//    if (firstSpaceIndex == lastFileIndex-1) {
//        return String(out)
//    }
//    return fragment(String(out))
//}
//
//fun checksum(fileMap: String) : Int {
//    // it is not 79669620
//    return fileMap.filter { it != '.' }.mapIndexed { i, c ->
//        i * c.toString().toInt()
//    }.sum()
//}