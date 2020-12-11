@file:Suppress("UNUSED_PARAMETER", "unused")

package lesson9.task1

import lesson1.task1.sqr
import java.lang.Math.sqrt

// Урок 9: проектирование классов
// Максимальное количество баллов = 40 (без очень трудных задач = 15)

/**
 * Ячейка матрицы: row = ряд, column = колонка
 */
data class Cell(val row: Int, val column: Int) {
    fun neighbour(other: Cell): Boolean =
        row - 1 == other.row || row + 1 == other.row || column - 1 == other.column || column + 1 == other.column
}


/**
 * Интерфейс, описывающий возможности матрицы. E = тип элемента матрицы
 */
interface Matrix<E> {
    /** Высота */
    val height: Int

    /** Ширина */
    val width: Int

    /**
     * Доступ к ячейке.
     * Методы могут бросить исключение, если ячейка не существует или пуста
     */
    operator fun get(row: Int, column: Int): E

    operator fun get(cell: Cell): E

    /**
     * Запись в ячейку.
     * Методы могут бросить исключение, если ячейка не существует
     */
    operator fun set(row: Int, column: Int, value: E)

    operator fun set(cell: Cell, value: E)

    infix fun cordsMovingTitle(element: E): Cell {
        for (i in 0 until height) {
            for (j in 0 until width)
                if (this[i, j] == element) return Cell(i, j)
        }
        return Cell(-1, -1)
    }
}

/**
 * Простая (2 балла)
 *
 * Метод для создания матрицы, должен вернуть РЕАЛИЗАЦИЮ Matrix<E>.
 * height = высота, width = ширина, e = чем заполнить элементы.
 * Бросить исключение IllegalArgumentException, если height или width <= 0.
 */
fun <E> createMatrix(height: Int, width: Int, e: E): Matrix<E> = MatrixImpl(height, width, e)

/**
 * Средняя сложность (считается двумя задачами в 3 балла каждая)
 *
 * Реализация интерфейса "матрица"
 */
class MatrixImpl<E>(override val height: Int, override val width: Int, defaultValue: E) : Matrix<E> {

    private var data = mutableListOf<E>()

    init {
        require(!(height <= 0 || width <= 0))
        data = MutableList(height * width) { defaultValue }
    }

    override fun get(row: Int, column: Int): E = data[width * row + column]

    override fun get(cell: Cell): E = get(cell.row, cell.column)

    override fun set(row: Int, column: Int, value: E) {
        data[width * row + column] = value
    }

    override fun set(cell: Cell, value: E) = set(cell.row, cell.column, value)

    override fun equals(other: Any?) =
        other is MatrixImpl<*> && other.height == height && other.width == width && other.data == data

    override fun toString(): String = data.toString()
}