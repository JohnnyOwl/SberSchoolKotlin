class ValidationException(val errorCode: Array<ErrorCode>) : RuntimeException(errorCode.joinToString(",") { it.msg })

enum class ErrorCode(val code: Int, val msg: String) {
    INVALID_CHARACTER_NAME(101, "Недопустимый символ в Имени или Фамилии"),
    INVALID_CHARACTER_PHONE(102, "Недопустимый символ в номере телефона"),
    INVALID_CHARACTER_EMAIL(103, "Недопустимый символ в почте"),
    INVALID_CHARACTER_SNILS(104, "Недопустимый символ в СНИЛС"),

    INVALID_SIZE_NAME(201, "Недопустимое количество символов в Имени или Фамилии"),
    INVALID_SIZE_PHONE(202, "Недопустимое количество символов в номере телефона"),
    INVALID_SIZE_EMAIL(203, "Недопустимое количество символов в почте"),
    INVALID_SIZE_SNILS(204, "Недопустимое количество символов в СНИЛС"),

    INVALID_SNILS_CHECK_NUMBER(301, "Ошибка проверки контрольного числа СНИЛС"),

    NULL_VALUE(400, "Введенное значение равно null")
}