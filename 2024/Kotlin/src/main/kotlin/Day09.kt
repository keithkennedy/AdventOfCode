import java.util.concurrent.atomic.AtomicInteger

class File(val id: Int, val number: Int) {
    fun isFile(): Boolean = !isSpace()
    fun isSpace(): Boolean = id == -1

    fun output(): String {
        if (isFile()) {
            return id.toString().repeat(number)
        }
        return ".".repeat(number)
    }
}

fun output(diskMap: List<File>) : String {
    return diskMap.joinToString(separator = "", transform = File::output)
}

fun main() {
    val diskMap = readInput("Day09").first()
//    val diskMap = "2333133121414131402"
//    val .dilutedDiskMap = .dilutedDiskMap(diskMap)
//    val fragmentedDiskMap = .fragment(.dilutedDiskMap)
    val dilutedDiskMap = dilutedDiskMap2(diskMap)
    val fragmentedDiskMap = fragment2(dilutedDiskMap)
    println(checksum2(fragmentedDiskMap))
}

// Part 1 methods
fun dilutedDiskMap(diskMap : String) : List<File> {
    val ai = AtomicInteger(0)
    return diskMap.flatMapIndexed { idx, char ->
        if (idx % 2 == 0) {
            val id = ai.getAndIncrement();
            (0 until char.toString().toInt()).map { File(id, char.toString().toInt()) }
        } else {
            (0 until char.toString().toInt()).map { File(-1, char.toString().toInt()) }
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
tailrec fun fragment(diskMap: List<File>) : List<File> {
    val firstSpaceIndex = diskMap.indexOfFirst { it.isSpace() }
    val lastFileIndex = diskMap.indexOfLast { it.isFile() }
    if (firstSpaceIndex == lastFileIndex-1) {
        return diskMap
    }

    val out = ArrayList<File>(diskMap)
    out[firstSpaceIndex] = diskMap[lastFileIndex]
    out[lastFileIndex] = diskMap[firstSpaceIndex]
    return fragment(out)
}

fun checksum(fileMap: List<File>): Long {
    return fileMap.filter { it.isFile() }.mapIndexed { i, c ->
        (i * c.id).toLong()
    }.sum()
}

// Part 2 methods
fun dilutedDiskMap2(diskMap : String) : List<File> {
    val ai = AtomicInteger(0)
    return diskMap.mapIndexed { idx, char ->
        if (idx % 2 == 0) {
            val id = ai.getAndIncrement();
            File(id, char.toString().toInt())
        } else {
            File(-1, char.toString().toInt())
        }
    }.toList()
}

fun fragment2(diskMap: List<File>) : List<File> {
    val _diskMap = ArrayList<File>(diskMap)

    val lastFileId = _diskMap.last { it.isFile() }.id
    for (i in lastFileId downTo 0) {
        val fileIndex = _diskMap.indexOfFirst { it.isFile() && it.id == i }
        val file = _diskMap.get(fileIndex)
        // find the first space that can hold this file based on its size "number"
        val firstSpaceIndex = _diskMap.indexOfFirst { it.isSpace() && it.number >= file.number }
        // check the space is "before" the file
        if (firstSpaceIndex == -1 ||
            fileIndex < firstSpaceIndex) {
            continue
        }

        val space = _diskMap.get(firstSpaceIndex)
        _diskMap[fileIndex] = space
        _diskMap[firstSpaceIndex] = file

        if (space.number > file.number){
            // space needs to be split and then swapped
            val remainingSpace = File(-1, space.number - file.number)
            // decrement original space -- file has filled it
            val originalSpace = File(-1, file.number)
            _diskMap[fileIndex] = originalSpace
            _diskMap.add(firstSpaceIndex + 1, remainingSpace)
        }
    }
    return _diskMap
}

fun checksum2(fileMap: List<File>): Long {
    var pos = 0L
    var total = 0L
    fileMap.forEach { file ->
        (0 until file.number).forEach { j ->
            if (file.isFile()) {
                total += (pos * file.id).toLong()
            }
            pos ++
        }
    }

    return total
}
